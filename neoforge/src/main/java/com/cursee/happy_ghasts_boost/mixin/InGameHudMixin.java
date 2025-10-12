package com.cursee.happy_ghasts_boost.mixin;

import com.cursee.happy_ghasts_boost.impl.common.event.HudRenderCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;

@Mixin(Gui.class)
public class InGameHudMixin {
  @Inject(method = "render", at = @At(value = "TAIL"))
  public void render(GuiGraphics drawContext, DeltaTracker tickCounter, CallbackInfo callbackInfo) {
    HudRenderCallback.EVENT.invoker().onHudRender(drawContext, tickCounter);
  }
}
