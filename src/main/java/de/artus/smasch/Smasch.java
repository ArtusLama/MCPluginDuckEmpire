package de.artus.smasch;

import de.artus.smasch.commands.SmashCommand;
import de.artus.smasch.commands.SmashCommandTabCompleter;
import de.artus.smasch.events.onHit;
import de.artus.smasch.utils.mapLoader;
import de.artus.smasch.commands.testcommand_unreachable;
import de.artus.smasch.commands.tpsmash;
import de.artus.smasch.events.DoubleJump;
import de.artus.smasch.events.InventoryEvents;
import de.artus.smasch.events.disableExplosions;
import de.artus.smasch.gameLogic.ItemsInventory;
import de.artus.smasch.gameLogic.JumpPad;
import de.artus.smasch.utils.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Smasch extends JavaPlugin implements Listener {

    public static final String gameWorld = "smashWorld";
    public static Game game;


    public void onEnable() {

        ItemsInventory.init();


        //getCommand("test").setExecutor(new testcommand_unreachable());
        getCommand("tpsmash").setExecutor(new tpsmash());

        getCommand("smash").setExecutor(new SmashCommand());
        getCommand("smash").setTabCompleter(new SmashCommandTabCompleter());




        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new DoubleJump(), this);
        pluginManager.registerEvents(new JumpPad(), this);

        pluginManager.registerEvents(new InventoryEvents(), this);

        pluginManager.registerEvents(new disableExplosions(), this);
        pluginManager.registerEvents(new onHit(), this);

        pluginManager.registerEvents(this, this);


        game = new Game(this);
        mapLoader.placeMap("sky", true);
    }

    @Override
    public void onDisable() {
        game.stop();
    }

    public static void sendSmashMSG(Player p, String msg){

        p.sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GREEN + "SMASH" + ChatColor.DARK_GREEN + "] " +
                ChatColor.GRAY + "-> " + ChatColor.GREEN + msg);

    }


    // TEST FOR COMMAND BLOCKS
    /*@EventHandler
    public void onCommand(ServerCommandEvent e){

        if (e.getSender() instanceof BlockCommandSender block) {

            Bukkit.getOnlinePlayers().forEach(player -> {

                if (e.getCommand().contains("Willkommen5")){
                    player.sendMessage("CommandBlock -> " + block.getBlock().getLocation());
                }

            });

        }

    }*/

}
