package net.spudacious5705.shops.util;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/** Registers entries in declaration order at the platform registry lifecycle. */
public final class PendingRegistry<T> {
    private final Registry<T> registry;
    private final String namespace;
    private final List<Runnable> pending = new ArrayList<>();
    public PendingRegistry(Registry<T> registry, String namespace) {
        this.registry = registry;
        this.namespace = namespace;
    }
    public <V extends T> Supplier<V> register(String name, Supplier<V> factory) {
        class Entry implements Supplier<V> {
            private V value;
            public V get() {
                if (value == null) throw new IllegalStateException("Entry not registered: " + name);
                return value;
            }
            void register() { value = Registry.register(registry, Identifier.fromNamespaceAndPath(namespace, name), factory.get()); }
        }
        Entry entry = new Entry();
        pending.add(entry::register);
        return entry;
    }
    public void registerAll() { net.spudacious5705.shops.platform.Platform.register(registry, () -> { pending.forEach(Runnable::run); pending.clear(); }); }
}
