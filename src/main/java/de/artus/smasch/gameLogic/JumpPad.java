package de.artus.smasch.gameLogic;

import de.artus.smasch.Smasch;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class JumpPad implements Listener {


    @EventHandler
    public void onEnterJumpPad(PlayerMoveEvent e){

        // for knockback world
        if (e.getPlayer().getWorld() == Bukkit.getWorld("Test")){
            if (e.getPlayer().getLocation().getBlock().getType() == Material.LIGHT_WEIGHTED_PRESSURE_PLATE){
                e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(2).setY(2));
                e.getPlayer().playEffect(e.getPlayer().getLocation(), Effect.BLAZE_SHOOT, 15);
                e.getPlayer().spawnParticle(Particle.CLOUD, e.getPlayer().getLocation(), 20, 0.1, 0.1, 0.1, 0.1);
            }
        }

        if (e.getPlayer().getWorld() != Bukkit.getWorld(Smasch.gameWorld)) return;

        if (e.getPlayer().getLocation().getBlock().getType() == Material.LIGHT_WEIGHTED_PRESSURE_PLATE){
            e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(0.25).setY(2));
            e.getPlayer().playEffect(e.getPlayer().getLocation(), Effect.BLAZE_SHOOT, 15);
            e.getPlayer().spawnParticle(Particle.CLOUD, e.getPlayer().getLocation(), 20, 0.1, 0.1, 0.1, 0.1);
        }


    }

}
