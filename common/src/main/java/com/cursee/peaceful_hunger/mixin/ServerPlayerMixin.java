package com.cursee.peaceful_hunger.mixin;

import com.cursee.peaceful_hunger.impl.common.config.PeacefulHungerConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {

//    /// If the world has natural regeneration
//    @Inject(at = @At("HEAD"), method = "tickRegeneration", cancellable = true)
//    private void peaceful_hunger$tickRegeneration(CallbackInfo ci) {
//        if (PeacefulHungerConfig.getInstance().getHungerDifficulty() != Difficulty.PEACEFUL) ci.cancel();
//    }

  /// Check if the mod allows regeneration in peaceful. If the mod doesn't allow regeneration in peaceful, cancel tick regeneration being called on server player otherwise, continue as normal
  @Inject(method = "tickRegeneration", at = @At("HEAD"), cancellable = true)
  private void peaceful_hunger$tick$getNaturalRegeneration(CallbackInfo ci) {

    ServerPlayer serverPlayer = (ServerPlayer) (Object) this;
    ServerLevel serverlevel = serverPlayer.level();

    boolean allowedByGameRule = serverlevel.getGameRules().getBoolean(GameRules.RULE_NATURAL_REGENERATION);
    boolean naturalRegenAllowedInPeaceful = PeacefulHungerConfig.getInstance().isNaturalRegenAllowedInPeaceful();

    if (!naturalRegenAllowedInPeaceful) {
      if (allowedByGameRule && PeacefulHungerConfig.getInstance().getHungerDifficulty() != Difficulty.PEACEFUL) {
        ci.cancel();
      }
    }

    // return allowedByGameRule; // do nothing
  }
}