package com.cursee.peaceful_hunger.impl.client.network;

import com.cursee.peaceful_hunger.impl.common.network.packet.ConfigSyncS2CPacket;
import net.neoforged.neoforge.network.ConfigSync;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class NeoForgeConfigSyncClientHandler {

  public static void handle(ConfigSyncS2CPacket packet, IPayloadContext context) {
    context.enqueueWork(packet::handle);
  }

}
