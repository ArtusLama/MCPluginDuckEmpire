package de.artus.smasch.gameLogic;

import de.artus.smasch.gameLogic.Items.GameItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class ItemsInventory {


    public static Inventory inventory;
    public static List<GameItem> ITEMS = new ArrayList<>();

    public static void init() {
        ITEMS.add(new GameItem(Material.EGG, ChatColor.WHITE + "Little time bomb", System.currentTimeMillis())
                .setBomb(1, 6, 3));
        ITEMS.add(new GameItem(Material.FIREWORK_STAR, ChatColor.GRAY + "Grenade", System.currentTimeMillis())
                .setBomb(5, 4, -1));

        ITEMS.add(new GameItem(Material.BOW, ChatColor.LIGHT_PURPLE + "Teleport bow", System.currentTimeMillis())
                .setBow("teleport"));
        ITEMS.add(new GameItem(Material.BOW, ChatColor.LIGHT_PURPLE + "TimeWarp bow", System.currentTimeMillis())
                .setBow("timeWarp"));

        ITEMS.add(new GameItem(Material.MOJANG_BANNER_PATTERN, ChatColor.AQUA + "Map reset", System.currentTimeMillis())
                .setSimpleItem("mapReset"));
    }

    public static void openItemsInventory(Player player){
        inventory = Bukkit.createInventory(null, 9, ChatColor.RED + "Items:");

        for (int slot = 0; slot < ITEMS.size(); slot++){
            inventory.setItem(slot, ITEMS.get(slot).clone().getItem());
        }

        player.openInventory(inventory);

    }


}
