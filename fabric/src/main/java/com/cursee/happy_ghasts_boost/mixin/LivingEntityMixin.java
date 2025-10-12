package com.cursee.happy_ghasts_boost.mixin;

import com.blackgear.vanillabackport.common.level.entities.happyghast.HappyGhast;
import com.cursee.happy_ghasts_boost.api.common.entity.IHappyGhastBoostDataHolder;
import com.cursee.happy_ghasts_boost.impl.common.config.EarlyLoadConfig;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

  @Inject(at = @At("TAIL"), method = "getAttributeValue(Lnet/minecraft/world/entity/ai/attributes/Attribute;)D", cancellable = true)
  private void happy_ghasts_boost$getAttributeValue(Attribute attribute, CallbackInfoReturnable<Double> cir) {

    LivingEntity living = (LivingEntity) (Object) this;

    if (!(living instanceof HappyGhast ghast) || !attribute.equals(Attributes.FLYING_SPEED)) {
      return;
    }

    IHappyGhastBoostDataHolder dataHolder = (IHappyGhastBoostDataHolder) ghast;

    MobEffectInstance effect = ghast.getEffect(MobEffects.MOVEMENT_SPEED);

    boolean boosted = dataHolder.happy_ghasts_boost$isBoosted();

    // if (boosted || effect != null) {
    if (dataHolder.happy_ghasts_boost$getForwardFlightDuration() > 0 || effect != null) {

      if (boosted) {
        Vec3 pos = ghast.position();
        ghast.level().addParticle(ParticleTypes.POOF, pos.x, pos.y, pos.z, 0.1, 0.1, 0.1);
      }

      if (effect == null) {

//        float percentComplete = dataHolder.happy_ghasts_boost$getForwardFlightDuration() / ();
//        float multiplier = EarlyLoadConfig.forwardMultiplier * percentComplete;

        // 0.05 * 2 = 0.1
        // cir.setReturnValue((0.05D * EarlyLoadConfig.forwardMultiplier) * (((double) dataHolder.happy_ghasts_boost$getForwardFlightDuration() / 20.0D) / (EarlyLoadConfig.secondsUntilBoost)));

        double percentage = Math.min(1.0D, dataHolder.happy_ghasts_boost$getForwardFlightDuration() / ((double) EarlyLoadConfig.secondsUntilBoost * 20.0D));
        double calculated = (0.05D * EarlyLoadConfig.forwardMultiplier) * percentage;
        cir.setReturnValue(Math.max(0.05D, calculated));
        // cir.setReturnValue(0.05 * EarlyLoadConfig.forwardMultiplier);
      } else {

        // System.out.println(effect.getAmplifier());

        float potionForwardMod = boosted ? 0.025f : 0f;

        if (effect.getAmplifier() == 0) {
          cir.setReturnValue((0.05 * (EarlyLoadConfig.potionMultiplier)) + potionForwardMod);
        } else if (effect.getAmplifier() == 1) {
          cir.setReturnValue((0.05 * (EarlyLoadConfig.potionMultiplierTwo)) + potionForwardMod);
        }
      }

      // cir.setReturnValue(0.05 * (ghast.getEffect(MobEffects.SPEED) == null ? EarlyLoadConfig.forwardMultiplier : EarlyLoadConfig.potionMultiplier));
    }
  }
}
