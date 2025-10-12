package com.cursee.happy_ghasts_boost.impl.common.event;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;

public interface HudRenderCallback {
  Event<HudRenderCallback> EVENT = EventFactory.createArrayBacked(HudRenderCallback.class, (listeners) -> (matrixStack, delta) -> {
    for (HudRenderCallback event : listeners) {
      event.onHudRender(matrixStack, delta);
    }
  });

  /**
   * Called after rendering the whole hud, which is displayed in game, in a world.
   *
   * @param drawContext the {@link GuiGraphics} instance
   * @param tickCounter the {@link DeltaTracker} instance
   */
  void onHudRender(GuiGraphics drawContext, DeltaTracker tickCounter);
}
