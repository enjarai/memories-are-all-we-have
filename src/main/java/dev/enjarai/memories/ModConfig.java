package dev.enjarai.memories;

import com.google.gson.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModConfig {
    public static final Gson GSON = new GsonBuilder()
            .setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setPrettyPrinting()
            .create();
    public static final Path CONFIG_FILE = MemoriesAreAllWeHave.SCREENSHOTS_PATH.resolve("memories-are-all-we-have.json");
    public static ModConfig INSTANCE = new ModConfig();

    private final JsonObject json;

    public static void touch() {
    }

    public ModConfig() {
        if (Files.exists(CONFIG_FILE)) {
            try (var fileReader = Files.newBufferedReader(CONFIG_FILE)) {
                json = (JsonObject) JsonParser.parseReader(fileReader);
            } catch (IOException | ClassCastException e) {
                throw new RuntimeException("Problem occurred when trying to load config: ", e);
            }
        } else {
            json = new JsonObject();
        }

        screenshotAdvancements = loadWithDefault("screenshot_advancements", false);

        try {
            Files.createDirectories(CONFIG_FILE.getParent());
            try (var writer = Files.newBufferedWriter(CONFIG_FILE)) {
                GSON.toJson(json, writer);
            }
        } catch (IOException e) {
            MemoriesAreAllWeHave.LOGGER.error("Couldn't save config file: ", e);
        }
    }


    public boolean screenshotAdvancements;


    private boolean loadWithDefault(String key, boolean def) {
        var element = json.get(key);
        if (element != null) {
            return element.getAsBoolean();
        }
        json.addProperty(key, def);
        return def;
    }
}
