package net.spudacious5705.shops.platform;
public final class ClientPlatform { public static void send(net.minecraft.network.protocol.common.custom.CustomPacketPayload packet) { net.neoforged.neoforge.client.network.ClientPacketDistributor.sendToServer(packet); } }
