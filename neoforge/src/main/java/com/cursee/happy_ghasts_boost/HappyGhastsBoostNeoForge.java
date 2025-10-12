package com.cursee.happy_ghasts_boost;


import com.cursee.monolib.core.sailing.Sailing;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLModContainer;
import net.neoforged.fml.loading.FMLLoader;

@Mod(Constants.MOD_ID)
public class HappyGhastsBoostNeoForge {

    public HappyGhastsBoostNeoForge(IEventBus modEventBus, FMLModContainer fmlModContainer) {
        Sailing.register(Constants.MOD_ID, Constants.MOD_NAME, Constants.MOD_VERSION, Constants.MOD_PUBLISHER, Constants.MOD_URL);
        HappyGhastsBoost.init();
        if (FMLLoader.getDist() == Dist.CLIENT) new HappyGhastsBoostClientNeoForge(modEventBus, fmlModContainer);
    }
}