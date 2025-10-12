package com.cursee.peaceful_hunger.platform;

import com.cursee.peaceful_hunger.platform.services.IPlatformHelper;
import java.nio.file.Path;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

public class FabricPlatformHelper implements IPlatformHelper {

  @Override
  public String getPlatformName() {
    return "Fabric";
  }

  @Override
  public boolean isModLoaded(String modId) {

    return FabricLoader.getInstance().isModLoaded(modId);
  }

  @Override
  public boolean isDevelopmentEnvironment() {

    return FabricLoader.getInstance().isDevelopmentEnvironment();
  }

  @Override
  public Path getGameDirectory() {

    return FabricLoader.getInstance().getGameDir();
  }

  @Override
  public String getGameDirectoryString() {

    return getGameDirectory().toString();
  }

  @Override
  public <T extends CustomPacketPayload> void sendToPlayer(ServerPlayer serverPlayer, T packet) {
    ServerPlayNetworking.send(serverPlayer, packet);
  }
}
