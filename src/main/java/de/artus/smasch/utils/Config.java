package de.artus.smasch.utils;


import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;

public class Config {

    public static File file = new File("plugins/smashArtus", "config.yml");
    public static FileConfiguration config = YamlConfiguration.loadConfiguration(file);

    public static void save() {
        try {
            config.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void saveMap(String name, Map<Location, Material> blocks){


        config.set("map." + name , null);

        int c = 1;
        for (Map.Entry<Location, Material> block : blocks.entrySet()){
            config.set("map." + name + "." + c + ".loc", block.getKey());
            config.set("map." + name + "." + c + ".mat", block.getValue().toString());
            c++;
        }
        save();
    }
    public static Map<Location, Material> getMap(String name){
        Map<Location, Material> map = new HashMap<>();
        for (int c = 1; c < config.getConfigurationSection("map." + name).getKeys(false).size() + 1; c++) {
            Location loc = config.getLocation("map." + name + "." + c + ".loc");
            Material mat = Material.getMaterial(config.getString("map." + name + "." + c + ".mat"));
            map.put(loc, mat);
        }
        return map;
    }
}
