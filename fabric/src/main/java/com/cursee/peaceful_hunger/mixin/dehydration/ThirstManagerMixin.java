package com.cursee.peaceful_hunger.mixin.dehydration;

import com.cursee.peaceful_hunger.impl.common.config.PeacefulHungerConfig;
import net.dehydration.thirst.ThirstManager;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ThirstManager.class)
public class ThirstManagerMixin {

  @ModifyVariable(method = "update", at = @At("STORE"), ordinal = 0)
  private Difficulty peaceful_hunger$tick$getDifficulty(Difficulty value) {

    return PeacefulHungerConfig.getInstance().getHungerDifficulty();
  }
}
