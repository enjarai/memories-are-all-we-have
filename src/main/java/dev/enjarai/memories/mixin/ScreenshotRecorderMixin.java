package dev.enjarai.memories.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import dev.enjarai.memories.MemoriesAreAllWeHave;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.util.ScreenshotRecorder;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.util.function.Consumer;

@Mixin(ScreenshotRecorder.class)
public abstract class ScreenshotRecorderMixin {

    @Inject(
            method = "saveScreenshotInner",
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=screenshots"
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/io/File;mkdir()Z",
                    ordinal = 0
            )
    )
    private static void modifyScreenshotDir(File gameDirectory, @Nullable String fileName, Framebuffer framebuffer, Consumer<Text> messageReceiver, CallbackInfo ci, @Local(ordinal = 1) LocalRef<File> file) {
        file.set(MemoriesAreAllWeHave.SCREENSHOTS_PATH.toFile());
    }
}
