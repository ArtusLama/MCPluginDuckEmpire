package de.artus.smasch.events;

import de.artus.smasch.Smasch;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class disableExplosions implements Listener {

    @EventHandler
    public void onExplode(EntityExplodeEvent e){

        if (e.getEntity().getWorld() != Bukkit.getWorld(Smasch.gameWorld)) return;

        e.setCancelled(true);
    }
}
