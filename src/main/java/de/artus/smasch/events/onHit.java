package de.artus.smasch.events;

import de.artus.smasch.Smasch;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class onHit implements Listener {


    @EventHandler (priority = EventPriority.HIGHEST)
    public void onDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getEntity().getWorld() != Bukkit.getWorld(Smasch.gameWorld)) return;
        e.setCancelled(!Smasch.game.gameOn());
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e){

        if (e.getEntity() instanceof Player){
            if (e.getEntity().getWorld() != Bukkit.getWorld(Smasch.gameWorld)) return;

            e.setDamage(0);

            if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                e.setCancelled(true);
            }
        }
    }
}
