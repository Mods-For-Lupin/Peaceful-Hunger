package com.cursee.peaceful_hunger.platform;

import com.cursee.peaceful_hunger.platform.services.IPlatformHelper;
import java.nio.file.Path;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.network.PacketDistributor;

public class NeoForgePlatformHelper implements IPlatformHelper {

  @Override
  public String getPlatformName() {

    return "NeoForge";
  }

  @Override
  public boolean isModLoaded(String modId) {

    return ModList.get().isLoaded(modId);
  }

  @Override
  public boolean isDevelopmentEnvironment() {

    return !FMLLoader.isProduction();
  }

  @Override
  public Path getGameDirectory() {

    return FMLLoader.getGamePath();
  }

  @Override
  public String getGameDirectoryString() {

    return getGameDirectory().toString();
  }

  @Override
  public <T extends CustomPacketPayload> void sendToPlayer(ServerPlayer serverPlayer, T packet) {
    PacketDistributor.sendToPlayer(serverPlayer, packet);
  }
}