package com.cursee.happy_ghasts_boost;

import com.cursee.happy_ghasts_boost.api.common.entity.IHappyGhastBoostDataHolder;
import com.cursee.happy_ghasts_boost.impl.common.config.EarlyLoadConfig;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.HappyGhast;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.javafmlmod.FMLModContainer;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

public class HappyGhastsBoostClientNeoForge {

  public static IEventBus modEventBus;

  private static float topSpeedOnGhast = 0f;

  public HappyGhastsBoostClientNeoForge(IEventBus modEventBus, FMLModContainer fmlModContainer) {
    HappyGhastsBoostClientNeoForge.modEventBus = modEventBus;

    NeoForge.EVENT_BUS.addListener((Consumer<EntityJoinLevelEvent>) event -> {
      if (event.getEntity() instanceof Player) {
        topSpeedOnGhast = 0.0f;
      }
    });

    NeoForge.EVENT_BUS.addListener((Consumer<RenderGuiEvent.Post>) event -> {

      // FABRIC -> NEOFORGE
      GuiGraphics guiGraphics = event.getGuiGraphics();

      LocalPlayer player = Minecraft.getInstance().player;

      if (player != null && player.isPassenger() && player.getVehicle() instanceof HappyGhast ghast) {

        IHappyGhastBoostDataHolder dataHolder = (IHappyGhastBoostDataHolder) ghast;

        float speed = Mth.sqrt((float) ((ghast.getDeltaMovement().x * ghast.getDeltaMovement().x) + (ghast.getDeltaMovement().z * ghast.getDeltaMovement().z)));

        if (EarlyLoadConfig.displayBoostBar && speed >= 0.1 && dataHolder.happy_ghasts_boost$getForwardFlightDuration() > 0) {

          int xStart = guiGraphics.guiWidth() / 2 - 91;
          int yStart = guiGraphics.guiHeight() - 32 + 3;

          float jumpRidingScale = Math.min((float) (dataHolder.happy_ghasts_boost$getForwardFlightDuration()) / (EarlyLoadConfig.secondsUntilBoost * 20), 1.0f);
          jumpRidingScale -= 0.1f;
          int displayWidth = Math.min((int)((jumpRidingScale * 183.0F - Mth.randomBetween(ghast.getRandom(), 0.25f, 0.75f))), 183);

          // debugging: render boost bar scale and display width text
          if (EarlyLoadConfig.debugging) {
            guiGraphics.drawCenteredString(Minecraft.getInstance().font, String.valueOf(jumpRidingScale), guiGraphics.guiWidth() / 2, 5, 0xFFFFFFFF);
            guiGraphics.drawCenteredString(Minecraft.getInstance().font, String.valueOf(displayWidth), guiGraphics.guiWidth() / 2, 15, 0xFFFFFFFF);
          }

          guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "textures/gui/icons_copy.png"), xStart, yStart, (float) 0, (float) 84, 182, 5, 256, 256);
//          if (rideable.getJumpCooldown() > 0) {
//            guiGraphics.blit(Constants.GUI_ICONS_LOCATION, xStart, yStart, 0, 74, 182, 5);
//          } else
          if (displayWidth > 0) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "textures/gui/icons_copy.png"), xStart, yStart, (float) 0, (float) 89, displayWidth, 5, 256, 256);
          }
        }

        // debugging: update top speed, render boosted and top speed text
        if (EarlyLoadConfig.debugging) {

          if (speed > topSpeedOnGhast) {
            topSpeedOnGhast = speed;
          }

          guiGraphics.drawString(Minecraft.getInstance().font, "Speed: " + speed, 5, 5, 0xFFFFFFFF);
          guiGraphics.drawString(Minecraft.getInstance().font, "Boosted: " + dataHolder.happy_ghasts_boost$isBoosted(), 5, 15, 0xFFFFFFFF);
          guiGraphics.drawString(Minecraft.getInstance().font, "Top Speed: " + topSpeedOnGhast, 5, 25, 0xFFFFFFFF);
        }
      }
    });
  }
}