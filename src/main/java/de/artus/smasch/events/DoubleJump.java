package de.artus.smasch.events;

import de.artus.smasch.Smasch;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class DoubleJump implements Listener {


    @EventHandler
    public void playerJoin(PlayerJoinEvent e){

        if (e.getPlayer().getWorld() != Bukkit.getWorld(Smasch.gameWorld)) return;

        e.getPlayer().setAllowFlight(true);
        e.getPlayer().setFlying(false);
    }



    @EventHandler (priority = EventPriority.HIGHEST)
    public void onDoubleJump(PlayerToggleFlightEvent e){

        if (e.getPlayer().getWorld() != Bukkit.getWorld(Smasch.gameWorld)) return;

        Player player = e.getPlayer();


        if (player.getGameMode() == GameMode.ADVENTURE){
            if (!Smasch.game.gameOn()) {
                player.setAllowFlight(false);
                player.setFlying(false);
                return;
            }
            e.setCancelled(true);
            player.setAllowFlight(false);
            player.setFlying(false);
            player.setVelocity(player.getLocation().getDirection().multiply(0.75).setY(1));
            //player.playSound(player, Sound.ENTITY_BLAZE_SHOOT, 1, 1);
            player.playEffect(player.getLocation(), Effect.BLAZE_SHOOT, 15);
            player.spawnParticle(Particle.CLOUD, player.getLocation(), 20, 0.1, 0.1, 0.1, 0.1);


            if (player.isOp() && player.getInventory().getItemInMainHand().getType() == Material.NETHER_STAR){
                player.setAllowFlight(true);
            } else {
                Smasch.game.startDoubleJumpCooldown(player);
            }
        }
    }



    @EventHandler (priority = EventPriority.HIGHEST)
    public void onWorldSwitch(PlayerChangedWorldEvent e){
        if (e.getFrom() == Bukkit.getWorld(Smasch.gameWorld)){
            if (e.getPlayer().getGameMode() == GameMode.ADVENTURE){
                e.getPlayer().setAllowFlight(false);
                e.getPlayer().setFlying(false);
            }
        }
    }

    @EventHandler
    public void onGameModeChange(PlayerGameModeChangeEvent e){
        if (e.getPlayer().getWorld() != Bukkit.getWorld(Smasch.gameWorld)) return;

        if (e.getNewGameMode() == GameMode.ADVENTURE) {
            Smasch.game.playerDoubleJumpCooldown.remove(e.getPlayer());
            e.getPlayer().setAllowFlight(true);
            e.getPlayer().setFlying(false);
            Smasch.game.startDoubleJumpCooldown(e.getPlayer());
        }


    }


}
