package com.cursee.happy_ghasts_boost.mixin;

import com.blackgear.vanillabackport.common.level.entities.happyghast.HappyGhast;
import com.cursee.happy_ghasts_boost.api.common.entity.IHappyGhastBoostDataHolder;
import com.cursee.happy_ghasts_boost.impl.common.config.EarlyLoadConfig;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HappyGhast.class)
public class HappyGhastMixin implements IHappyGhastBoostDataHolder {

  /// Accessible via {@link IHappyGhastBoostDataHolder}
  @Unique
  private boolean happy_ghasts_boost$boosted = false;

  /// measured in ticks
  @Unique
  private int happy_ghasts_boost$forwardFlightDuration = 0;

  /// Used to check over-steering/turning too far
  @Unique
  private float happy_ghasts_boost$oldYDegrees = 0.0f;

  @Unique
  private static boolean happy_ghasts_boost$withinFivePercentMargin(double a, double b) {

    double c = Math.abs(a - b) / a;

    return c <= 0.05D; // 5% threshold
  }

  @Unique
  private static boolean happy_ghasts_boost$checkThreshold(double xDelta, double zDelta) {

    float checkedValue = Mth.sqrt((float) Mth.square(xDelta)) + Mth.sqrt((float) Mth.square(zDelta));

    return checkedValue > 0.1;
  }

  @Inject(at = @At("HEAD"), method = "tick")
  private void happy_ghasts_boost$tick(CallbackInfo ci) {

    HappyGhast ghast = (HappyGhast) (Object) this;
    // only operate on the server once per second when a player is controlling the ghast
//    if (!(ghast.level() instanceof ServerLevel level) || level.getGameTime() % 20 != 0 || ghast.getControllingPassenger() == null) {
//      return;
//    }
    if (ghast.getControllingPassenger() == null) {
      return;
    }

    // get known server movement and forward vector
    // Vec3 movement = ghast.getKnownMovement();
    Vec3 movement = ghast.getDeltaMovement();
    Vec3 forward = ghast.getForward();

    // create normalized vectors from movement and forward direction
    Vec3 movementNormal = movement.normalize();
    Vec3 forwardNormal = forward.normalize();

    boolean movingForward = happy_ghasts_boost$withinFivePercentMargin(movementNormal.x, forwardNormal.x) && happy_ghasts_boost$withinFivePercentMargin(movementNormal.z, forwardNormal.z);
    boolean velocityCheck = happy_ghasts_boost$checkThreshold(movement.x, movement.z);

    // Constants.LOG.info("forward {} velocity {}", movingForward, velocityCheck);

    if (movingForward && velocityCheck) {

      float currentYDegrees = ghast.getVisualRotationYInDegrees();
      boolean turnedTooFar = !happy_ghasts_boost$withinFivePercentMargin(currentYDegrees, happy_ghasts_boost$oldYDegrees);

      if (turnedTooFar) {
        // Constants.LOG.info("turned too far");
        happy_ghasts_boost$resetTrackedValues();
      }

      // Constants.LOG.info("current flight duration {}", faster_happy_ghasts$straightFlightSeconds);
      boolean readyToBoost = happy_ghasts_boost$forwardFlightDuration / 20 >= EarlyLoadConfig.secondsUntilBoost;

      if (readyToBoost) {
        happy_ghasts_boost$boosted = true;
//         if (faster_happy_ghasts$forwardFlightDuration < 8) {
//           Constants.LOG.info("should boost {}, {}s", faster_happy_ghasts$boosted, faster_happy_ghasts$forwardFlightDuration);
//         }
      }

      // Constants.LOG.info("incrementing flight duration");
      // increment flight duration
      happy_ghasts_boost$forwardFlightDuration += 1;
    } else if (!movingForward || !velocityCheck) {
      // Constants.LOG.info("resetting, failed movement or velocity check");
      // reset if not moving forward or not moving at all
      happy_ghasts_boost$resetTrackedValues();
    }

    // Constants.LOG.info("setting old rotation value");
    // keep the last known rotation
    happy_ghasts_boost$oldYDegrees = ghast.getVisualRotationYInDegrees();
  }

  @Unique
  private void happy_ghasts_boost$resetTrackedValues() {
    happy_ghasts_boost$forwardFlightDuration = 0;
    happy_ghasts_boost$boosted = false;
  }

  @Override
  public boolean happy_ghasts_boost$isBoosted() {
    return happy_ghasts_boost$boosted;
  }

  @Override
  public int happy_ghasts_boost$getForwardFlightDuration() {
    return happy_ghasts_boost$forwardFlightDuration;
  }
}