package com.cursee.peaceful_hunger.mixin;

import com.cursee.peaceful_hunger.impl.common.config.PeacefulHungerConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameRules.BooleanValue;
import net.minecraft.world.level.GameRules.Key;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {

  /// Check if the mod allows regeneration in peaceful. If the mod doesn't allow regeneration in peaceful, cancel tick regeneration being called on server player otherwise, continue as normal
  @Redirect(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/GameRules;getBoolean(Lnet/minecraft/world/level/GameRules$Key;)Z"))
  private boolean peaceful_hunger$tickInject(GameRules instance, Key<BooleanValue> key) {

    boolean allowedByGameRule = instance.getBoolean(key);

    boolean naturalRegenAllowedInPeaceful = PeacefulHungerConfig.getInstance().isNaturalRegenAllowedInPeaceful();

    if (!naturalRegenAllowedInPeaceful) {
      return allowedByGameRule && PeacefulHungerConfig.getInstance().getHungerDifficulty() != Difficulty.PEACEFUL;
    }

    return allowedByGameRule;
  }
}