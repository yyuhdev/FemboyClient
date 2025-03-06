/*
 * This file is part of the BleachHack distribution (https://github.com/BleachDev/BleachHack/).
 * Copyright (c) 2021 Bleach and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package dev.yyuh.femboyclient.client.mixin;

import dev.yyuh.femboyclient.client.FemboyClient;
import dev.yyuh.femboyclient.client.events.BeginRenderTickEvent;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.render.RenderTickCounter;

@Mixin(RenderTickCounter.Dynamic.class)
public class RenderTickCounterMixin {

    @Inject(method = "beginRenderTick(JZ)I", at = @At("HEAD"), cancellable = true)
    private void onBeginRenderTick(long timeMillis, boolean tick, CallbackInfoReturnable<Integer> ci) {
        BeginRenderTickEvent event = new BeginRenderTickEvent(timeMillis, ci);
        FemboyClient.EVENT_BUS.post(event);
        if (event.isCancelled()) ci.cancel();
    }
}
