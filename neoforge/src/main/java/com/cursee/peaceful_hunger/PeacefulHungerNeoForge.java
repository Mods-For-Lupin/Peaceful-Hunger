package com.cursee.peaceful_hunger;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLModContainer;
import net.neoforged.fml.loading.FMLLoader;

@Mod(Constants.MOD_ID)
public class PeacefulHungerNeoForge {

    public PeacefulHungerNeoForge(IEventBus modEventBus, FMLModContainer fmlModContainer) {
        PeacefulHunger.init();
    }
}