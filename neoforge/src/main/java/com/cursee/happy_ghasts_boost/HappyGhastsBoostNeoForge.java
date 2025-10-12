package com.cursee.happy_ghasts_boost;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLModContainer;
import net.neoforged.fml.loading.FMLLoader;

@Mod(Constants.MOD_ID)
public class HappyGhastsBoostNeoForge {

    public HappyGhastsBoostNeoForge(IEventBus modEventBus, FMLModContainer fmlModContainer) {
        HappyGhastsBoost.init();
        if (FMLLoader.getCurrent().getDist() == Dist.CLIENT) new HappyGhastsBoostClientNeoForge(modEventBus, fmlModContainer);
    }
}