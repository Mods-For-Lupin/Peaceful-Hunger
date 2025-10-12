package com.cursee.peaceful_hunger;

import net.fabricmc.api.ModInitializer;

public class PeacefulHungerFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        PeacefulHunger.init();
    }
}
