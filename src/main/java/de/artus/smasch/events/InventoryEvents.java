package de.artus.smasch.events;

import de.artus.smasch.Smasch;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryEvents implements Listener {


    @EventHandler
    public void playerDropEvent(PlayerDropItemEvent e){

        if (e.getPlayer().getWorld() != Bukkit.getWorld(Smasch.gameWorld)) return;

        ItemStack kbStick = new ItemStack(Material.STICK);
        ItemMeta kbStickMeta = kbStick.getItemMeta();
        kbStickMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
        kbStick.setItemMeta(kbStickMeta);
        if (!Smasch.game.gameOn()) e.setCancelled(true);
        if (e.getPlayer().getGameMode() == GameMode.ADVENTURE && e.getItemDrop().getItemStack().isSimilar(kbStick)) e.setCancelled(true);
    }

}
