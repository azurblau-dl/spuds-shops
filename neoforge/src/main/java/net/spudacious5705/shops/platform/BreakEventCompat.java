package net.spudacious5705.shops.platform;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Consumer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.level.BlockEvent;

/** Bridges the event rename within NeoForge 26.1.x without linking either concrete class. */
public final class BreakEventCompat {
    private final Class<? extends BlockEvent> eventType;
    private final Method getPlayer;
    private final Method notifyClient;

    public BreakEventCompat() {
        try {
            Class<?> type;
            Method notify;
            try {
                type = Class.forName("net.neoforged.neoforge.event.level.block.BreakBlockEvent");
                notify = type.getMethod("setNotifyClient", boolean.class);
            } catch (ClassNotFoundException oldApi) {
                type = Class.forName("net.neoforged.neoforge.event.level.BlockEvent$BreakEvent");
                notify = null; // The old event always notifies the client when cancelled.
            }
            eventType = type.asSubclass(BlockEvent.class);
            if (Modifier.isAbstract(type.getModifiers())) {
                throw new IllegalStateException("Block-break event must be concrete: " + type.getName());
            }
            getPlayer = type.getMethod("getPlayer");
            notifyClient = notify;
        } catch (ReflectiveOperationException failure) {
            throw new IllegalStateException("Unsupported NeoForge block-break event API", failure);
        }
    }

    public void register(IEventBus bus, Consumer<BlockEvent> listener) {
        registerConcrete(bus, eventType, listener);
    }

    private static <T extends BlockEvent> void registerConcrete(
            IEventBus bus, Class<T> type, Consumer<BlockEvent> listener) {
        // The inferred Consumer type is BlockEvent (abstract). Always supply the concrete class.
        bus.addListener(type, event -> listener.accept(event));
    }

    public ServerPlayer serverPlayer(BlockEvent event) {
        if (!eventType.isInstance(event)) return null;
        try {
            return getPlayer.invoke(event) instanceof ServerPlayer player ? player : null;
        } catch (ReflectiveOperationException failure) {
            throw new IllegalStateException("Cannot read block-break player", failure);
        }
    }

    public void cancel(BlockEvent event) {
        ((ICancellableEvent) event).setCanceled(true);
        try {
            if (notifyClient != null) notifyClient.invoke(event, true);
        } catch (ReflectiveOperationException failure) {
            throw new IllegalStateException("Cannot notify client of protected shop", failure);
        }
    }
}
