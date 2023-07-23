package com.lypaka.ladyselphy;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.List;
import java.util.Map;

public class ConfigGetters {

    public static int cooldown;
    public static String dialogueTitle;
    public static Map<String, List<String>> dialogueMap;
    public static List<String> locations;
    public static int mode;
    public static int modeValue;
    public static boolean mustBeOT;
    public static List<String> commonCommands;
    public static List<String> commonItems;
    public static double rareChance;
    public static List<String> rareCommands;
    public static List<String> rareItems;
    public static int saveInterval;

    public static Map<String, String> cooldownStorageMap;
    public static Map<String, Map<String, String>> storageMap;

    public static void load() throws ObjectMappingException {

        cooldown = LadySelphy.configManager.getConfigNode(0, "Cooldown").getInt();
        dialogueTitle = LadySelphy.configManager.getConfigNode(0, "Dialogue", "Title").getString();
        dialogueMap = LadySelphy.configManager.getConfigNode(0, "Dialogue", "Messages").getValue(new TypeToken<Map<String, List<String>>>() {});
        locations = LadySelphy.configManager.getConfigNode(0, "Location").getList(TypeToken.of(String.class));
        mode = LadySelphy.configManager.getConfigNode(0, "Mode").getInt();
        modeValue = LadySelphy.configManager.getConfigNode(0, "Mode-Value").getInt();
        mustBeOT = LadySelphy.configManager.getConfigNode(0, "Must-Be-OT").getBoolean();
        commonCommands = LadySelphy.configManager.getConfigNode(0, "Prize-Pool", "Common", "Commands").getList(TypeToken.of(String.class));
        commonItems = LadySelphy.configManager.getConfigNode(0, "Prize-Pool", "Common", "Items").getList(TypeToken.of(String.class));
        rareChance = LadySelphy.configManager.getConfigNode(0, "Prize-Pool", "Rare", "Chance").getDouble();
        rareCommands = LadySelphy.configManager.getConfigNode(0, "Prize-Pool", "Rare", "Commands").getList(TypeToken.of(String.class));
        rareItems = LadySelphy.configManager.getConfigNode(0, "Prize-Pool", "Rare", "Items").getList(TypeToken.of(String.class));
        saveInterval = LadySelphy.configManager.getConfigNode(0, "Save-Interval").getInt();

        cooldownStorageMap = LadySelphy.configManager.getConfigNode(1, "Cooldown-Storage").getValue(new TypeToken<Map<String, String>>() {});
        storageMap = LadySelphy.configManager.getConfigNode(1, "Storage").getValue(new TypeToken<Map<String, Map<String, String>>>() {});

    }

}
