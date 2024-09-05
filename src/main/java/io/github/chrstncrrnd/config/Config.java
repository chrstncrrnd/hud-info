package io.github.chrstncrrnd.config;

import com.google.gson.Gson;
import io.github.chrstncrrnd.render.RenderPosition;
import net.minecraft.client.MinecraftClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static io.github.chrstncrrnd.Hudinfo.LOGGER;
import static io.github.chrstncrrnd.Hudinfo.MOD_ID;

public class Config {
    public static final Config instance = new Config();
    private final String CONFIG_FILE_NAME = MOD_ID + "-conf.json";
    private final Gson jsonSerializer;
    public RenderPosition renderPosition;
    public boolean showFps;
    public boolean showTps;
    public boolean showCoords;
    public boolean showDimensionCoords;
    private boolean saveConfig;

    private Config() {
        this.jsonSerializer = new Gson();
        this.saveConfig = true;
//        Defaults in case config doesn't load
        this.renderPosition = RenderPosition.TopLeft;
        this.showFps = true;
        this.showTps = true;
        this.showCoords = true;
        this.showDimensionCoords = true;
    }

    public void loadConfig() {
        File configFile = new File(MinecraftClient.getInstance().runDirectory, CONFIG_FILE_NAME);

        if (!configFile.exists()) {
            try {
                LOGGER.info("Config file does not exist, attempting to create new config file...");
                configFile.createNewFile();
                LOGGER.info("Successfully created config file");
            } catch (IOException | SecurityException e) {
                LOGGER.error("Error creating new config file");
                e.printStackTrace();
                this.saveConfig = false;
                return;
            }
        }

        try {
            LOGGER.info("Opening config file");
            Scanner sc = new Scanner(configFile);
            StringBuilder jsonString = new StringBuilder();
            while (sc.hasNext()) {
                jsonString.append(sc.next());
            }
            if (jsonString.toString().isEmpty()) {
                return;
            }
            sc.close();
            SerializedJson json = this.jsonSerializer.fromJson(jsonString.toString(), SerializedJson.class);
            this.renderPosition = json.renderPosition;
            this.showFps = json.showFps;
            this.showTps = json.showTps;
            this.showCoords = json.showCoords;
            this.showDimensionCoords = json.showDimensionCoords;
        } catch (NoSuchElementException | FileNotFoundException e) {
            LOGGER.error("Error with reading file:");
            e.printStackTrace();
//            Just don't save config no use now
            this.saveConfig = false;
        }

    }

    public void saveConfig() {
        if (!saveConfig) return;
        File configFile = new File(MinecraftClient.getInstance().runDirectory, CONFIG_FILE_NAME);
        FileWriter fw;
        String json = this.jsonSerializer.toJson(new SerializedJson(this.renderPosition, this.showFps, this.showTps, this.showCoords, this.showDimensionCoords));
        try {
            fw = new FileWriter(configFile);
            fw.write(json);
            fw.close();
        } catch (IOException e) {
            LOGGER.error("Could not write to file");
            throw new RuntimeException(e);
        }

    }

    private record SerializedJson(RenderPosition renderPosition, boolean showFps, boolean showTps, boolean showCoords,
                                  boolean showDimensionCoords) {
    }

}
