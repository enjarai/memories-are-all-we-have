package dev.enjarai.memories;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MemoriesAreAllWeHave implements ModInitializer {
	public static final String MOD_ID = "memories-are-all-we-have";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Path SCREENSHOTS_PATH = Path.of(System.getProperty("user.home"))
			.resolve("Pictures").resolve("Minecraft").resolve("Screenshots");

	@Override
	public void onInitialize() {
		try {
			Files.createDirectories(SCREENSHOTS_PATH);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		ModConfig.touch();

		if (ModConfig.INSTANCE.screenshotAdvancements) {
			AdvancementScreenshotter.init();
		}
	}
}