package com.cursee.peaceful_hunger;

import com.cursee.peaceful_hunger.impl.client.network.ForgeConfigSyncClientHandler;
import com.cursee.peaceful_hunger.impl.common.network.packet.ConfigSyncS2CPacket;
import java.util.function.Consumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Constants.MOD_ID)
public class PeacefulHungerForge {

  private static int packetId = 0;
  private static SimpleChannel NETWORK;

  public PeacefulHungerForge(FMLJavaModLoadingContext context) {
    IEventBus modEventBus = context.getModEventBus();

    // Register the commonSetup method for modloading
    modEventBus.addListener(this::commonSetup);

    // Register ourselves for server and other game events we are interested in
    MinecraftForge.EVENT_BUS.register(this);

    SimpleChannel net = NetworkRegistry.ChannelBuilder.named(PeacefulHungerForge.identifier("network")).networkProtocolVersion(() -> "1.0").clientAcceptedVersions(s -> true)
        .serverAcceptedVersions(s -> true).simpleChannel();

    NETWORK = net;

    net.messageBuilder(ConfigSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT).decoder(ConfigSyncS2CPacket::read).encoder(ConfigSyncS2CPacket::write)
        .consumerMainThread((packet, contextSupplier) -> ForgeConfigSyncClientHandler.handle(packet, contextSupplier.get())).add();

    MinecraftForge.EVENT_BUS.addListener((Consumer<EntityJoinLevelEvent>) event -> ConfigSyncS2CPacket.createAndSend(event.getEntity(), event.getLevel()));
  }

  @Deprecated @SuppressWarnings("all")
  public PeacefulHungerForge() {
    this(FMLJavaModLoadingContext.get());
  }

  private static int id() {
    return packetId++;
  }

  public static <MSG> void sendToPlayer(ServerPlayer serverPlayer, MSG message) {
    NETWORK.send(PacketDistributor.PLAYER.with(() -> serverPlayer), message);
  }

  public static ResourceLocation identifier(String path) {
    return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, path);
  }

  private void commonSetup(final FMLCommonSetupEvent event) {
    // Some common setup code
//        Constants.LOG.info("HELLO FROM COMMON SETUP");
//
//        if (Config.logDirtBlock)
//            Constants.LOG.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
//
//        Constants.LOG.info(Config.magicNumberIntroduction + Config.magicNumber);
//
//        Config.items.forEach((item) -> Constants.LOG.info("ITEM >> {}", item.toString()));
  }

  // You can use SubscribeEvent and let the Event Bus discover methods to call
  @SubscribeEvent
  public void onServerStarting(ServerStartingEvent event) {
    // Do something when the server starts
//        Constants.LOG.info("HELLO from server starting");
  }

  // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
  @Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
  public static class ClientModEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
      // Some client setup code
//            Constants.LOG.info("HELLO FROM CLIENT SETUP");
//            Constants.LOG.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }
  }
}
