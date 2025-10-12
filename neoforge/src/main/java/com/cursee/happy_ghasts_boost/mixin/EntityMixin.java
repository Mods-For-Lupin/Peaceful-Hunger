package com.cursee.happy_ghasts_boost.mixin;

import com.blackgear.vanillabackport.common.level.entities.happyghast.HappyGhast;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ThrownPotion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {

  @Inject(at = @At("TAIL"), method = "distanceToSqr(Lnet/minecraft/world/entity/Entity;)D", cancellable = true)
  private void injected(Entity entity, CallbackInfoReturnable<Double> cir) {

    Entity self = (Entity) (Object) this;

    if (self instanceof ThrownPotion && entity instanceof HappyGhast) {

      double dist = cir.getReturnValue();

      System.out.println("dist to happy ghast from thrown potion: " + String.valueOf(dist) + " reduced to " + String.valueOf(dist/2));
      cir.setReturnValue(dist / 2);
    }
  }
}
