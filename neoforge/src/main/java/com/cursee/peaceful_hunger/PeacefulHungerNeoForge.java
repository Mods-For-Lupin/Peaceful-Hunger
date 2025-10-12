package com.cursee.peaceful_hunger;

import com.cursee.peaceful_hunger.impl.client.network.NeoForgeConfigSyncClientHandler;
import com.cursee.peaceful_hunger.impl.common.network.PeacefulHungerNetwork.Packets;
import com.cursee.peaceful_hunger.impl.common.network.packet.ConfigSyncS2CPacket;
import java.util.function.Consumer;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@Mod(Constants.MOD_ID)
public class PeacefulHungerNeoForge {

  public PeacefulHungerNeoForge(IEventBus modEventBus, FMLModContainer fmlModContainer) {
    PeacefulHunger.init();

    modEventBus.addListener((Consumer<RegisterPayloadHandlersEvent>) event -> {
      final PayloadRegistrar registrar = event.registrar("1");
      registrar.playToClient(
          Packets.CONFIG_SYNC_ID,
          Packets.CONFIG_SYNC_CODEC,
          NeoForgeConfigSyncClientHandler::handle // handle as enqueued work
      );
    });

    NeoForge.EVENT_BUS.addListener((Consumer<EntityJoinLevelEvent>) event -> ConfigSyncS2CPacket.createAndSend(event.getEntity(), event.getLevel()));
  }
}