package de.artus.smasch.utils;

import de.artus.smasch.Smasch;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class mapLoader {


        public static void saveMap(String mapName, Location from, Location to){

            Smasch.sendSmashMSG(Bukkit.getPlayer("_Artus_"), "scanning blocks...");

            int xDiff = (int) (to.getX() - from.getX());
            int yDiff = (int) (to.getY() - from.getY());
            int zDiff = (int) (to.getZ() - from.getZ());

            Map<Location, Material> blockList = new HashMap<>();

            for (int x = -xDiff; x <= 0; x++){
                for (int y = -yDiff; y <= 0; y++){
                    for (int z = -zDiff; z <= 0; z++){
                        Block block = to.getBlock().getRelative(x, y, z);
                        if (block.getType() != Material.AIR){
                            blockList.put(block.getLocation(), block.getType());
                            Particle.DustOptions color = new Particle.DustOptions(Color.RED, 0.5F);
                            for (double px = 0; px < 0.5; px = px + 0.1){
                                for (double pz = 0; pz < 0.5; pz = pz + 0.1){
                                    block.getWorld().spawnParticle(Particle.REDSTONE, block.getLocation().add(px * 2, 1, pz * 2), 1, color);
                                }
                            }
                        }
                    }
                }
            }
            Config.saveMap(mapName, blockList);
        }


        public static void placeMap(String mapName, boolean clearMap){
            for (int x = -35; x <= 35; x++){
                for (int z = -35; z <= 35; z++){
                    for (int y = 60; y <= 110; y++){
                        Bukkit.getWorld(Smasch.gameWorld).getBlockAt(x, y, z).setType(Material.AIR);
                    }
                }
            }

            for (Map.Entry<Location, Material> block : Config.getMap(mapName).entrySet()){
                block.getKey().getBlock().setType(block.getValue());
            }
            Bukkit.getWorld(Smasch.gameWorld).getEntities().forEach(entity -> {
                if (entity instanceof Item item){
                    if (item.getItemStack().getType() == Material.LIGHT_WEIGHTED_PRESSURE_PLATE){
                        entity.remove();
                    }
                    if (clearMap){
                        if (!(entity instanceof Player)){
                            entity.remove();
                        }
                    }
                }
            });
        }



}
