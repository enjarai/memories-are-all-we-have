package dev.enjarai.memories;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.util.ScreenshotRecorder;

public class AdvancementScreenshotter {
    private static int ticksTilScreenshot = -1;

    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (ticksTilScreenshot == 0) {
                ScreenshotRecorder.saveScreenshot(client.runDirectory, client.getFramebuffer(), message -> {
                    client.execute(() -> {
                        client.inGameHud.getChatHud().addMessage(message);
                    });
                });
            }

            if (ticksTilScreenshot >= 0) {
                ticksTilScreenshot--;
            }
        });
    }

    public static void advancementGet() {
        ticksTilScreenshot = 20;
    }
}
