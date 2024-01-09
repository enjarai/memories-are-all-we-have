package dev.enjarai.memories.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.enjarai.memories.AdvancementScreenshotter;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.advancement.AdvancementProgress;
/*? if >1.20.1 */
import net.minecraft.advancement.PlacedAdvancement;
import net.minecraft.client.network.ClientAdvancementManager;
import net.minecraft.network.packet.s2c.play.AdvancementUpdateS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientAdvancementManager.class)
public abstract class ClientAdvancementManagerMixin {
    @Inject(
            method = "onAdvancements",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"
            )
    )
    private void takeScreenshotOfAdvancement(
            AdvancementUpdateS2CPacket packet, CallbackInfo ci,
            /*? if >1.20.1 { */
            @Local PlacedAdvancement advancement,
            /*? } else { *//*
            @Local Advancement advancement,
            *//*? } */
            @Local AdvancementProgress progress
    ) {
        /*? if >1.20.1 { */
        var hasDisplay = advancement.getAdvancement().display().map(AdvancementDisplay::shouldShowToast).orElse(false);
        /*? } else { *//*
        var hasDisplay = advancement.getDisplay() != null && advancement.getDisplay().shouldShowToast();
        *//*? } */

        if (progress.isDone() && hasDisplay) {
            AdvancementScreenshotter.advancementGet();
        }
    }
}
