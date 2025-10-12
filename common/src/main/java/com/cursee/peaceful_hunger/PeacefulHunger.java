package com.cursee.peaceful_hunger;

import net.minecraft.resources.ResourceLocation;

public class PeacefulHunger {

  public static void init() {
  }

  public static ResourceLocation identifier(String path) {
    return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, path);
  }
}