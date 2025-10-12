package com.cursee.happy_ghasts_boost.api.common.entity;

import com.cursee.happy_ghasts_boost.impl.common.config.EarlyLoadConfig;

/**
 * Implemented by loader-specific Mixins, this interface serves as an intermediary to get current flight duration (measured in ticks or 20ths of a second) and whether current flight duration has
 * reached its peak.
 */
public interface IHappyGhastBoostDataHolder {

  /**
   * Whether flight duration has reached its peak as defined by {@link EarlyLoadConfig#secondsUntilBoost}
   */
  boolean happy_ghasts_boost$isBoosted();

  /**
   * Current flight duration, measured in ticks.
   */
  int happy_ghasts_boost$getForwardFlightDuration();

}
