package com.cursee.peaceful_hunger.impl.common.network;

import com.cursee.peaceful_hunger.PeacefulHunger;
import com.cursee.peaceful_hunger.impl.common.network.packet.ConfigSyncS2CPacket;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class PeacefulHungerNetwork {

  public static class Packets {
    public static final StreamCodec<RegistryFriendlyByteBuf, ConfigSyncS2CPacket> CONFIG_SYNC_CODEC =
        StreamCodec.ofMember(ConfigSyncS2CPacket::write, ConfigSyncS2CPacket::read);
    public static final CustomPacketPayload.Type<ConfigSyncS2CPacket> CONFIG_SYNC_ID =
        new CustomPacketPayload.Type<ConfigSyncS2CPacket>(PeacefulHunger.identifier("config_sync"));
  }
}
