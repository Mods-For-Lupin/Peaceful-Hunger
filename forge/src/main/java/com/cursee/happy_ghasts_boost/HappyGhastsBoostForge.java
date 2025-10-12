package com.cursee.happy_ghasts_boost;

import com.cursee.monolib.core.sailing.Sailing;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;

@Mod(Constants.MOD_ID)
public class HappyGhastsBoostForge {

  public static IEventBus modEventBus;

  public HappyGhastsBoostForge(FMLJavaModLoadingContext context) {
    Sailing.register(Constants.MOD_ID, Constants.MOD_NAME, Constants.MOD_VERSION, Constants.MOD_PUBLISHER, Constants.MOD_URL);
    HappyGhastsBoostForge.modEventBus = modEventBus;
    HappyGhastsBoost.init();
    if (FMLLoader.getDist() == Dist.CLIENT) {
      new HappyGhastsBoostClientForge(modEventBus, context.getContainer());
    }
  }

  @Deprecated
  @SuppressWarnings("all")
  public HappyGhastsBoostForge() {
    this(FMLJavaModLoadingContext.get());
  }
}