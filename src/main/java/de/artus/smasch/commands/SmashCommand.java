package de.artus.smasch.commands;

import de.artus.smasch.Smasch;
import de.artus.smasch.utils.mapLoader;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class SmashCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player player) {
            if (!player.isOp()){
                Smasch.sendSmashMSG(player, ChatColor.RED + "Du hast keine Rechte auf diesen Command!");
                return false;
            }

            if (args[0].equalsIgnoreCase("map")){
                if (args[1].equalsIgnoreCase("save")){
                    Smasch.sendSmashMSG(player, ChatColor.RED + "Momentan kann man keine Maps speichern (wegen bugs)! Bitte wende dich an _Artus_");

                    //if (args.length != 9) {
                    //    Smasch.sendSmashMSG(player, ChatColor.RED + "Wrong amount of arguments (use '/smash help' for more information)");
                    //    return false;
                    //}

                    return false;
                    //Todo check args count
                    //Location from = new Location(player.getWorld(), Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]));
                    //Location to = new Location(player.getWorld(), Integer.parseInt(args[6]), Integer.parseInt(args[7]), Integer.parseInt(args[8]));
                    //mapLoader.saveMap(args[2], from, to);
                }
                if (args[1].equalsIgnoreCase("load")){
                    if (args.length != 3) {
                        Smasch.sendSmashMSG(player, ChatColor.RED + "Wrong amount of arguments (use '/smash help' for more information)");
                        return false;
                    }
                    mapLoader.placeMap(args[2], false);
                }
            }
            if (args[0].equalsIgnoreCase("game")){
                if (args.length == 3 && args[1].equalsIgnoreCase("start") && args[2].equalsIgnoreCase("debug")) Smasch.game.start(true);
                if (args.length != 2) {
                    Smasch.sendSmashMSG(player, ChatColor.RED + "Wrong amount of arguments (use '/smash help' for more information)");
                    return false;
                }
                if (args[1].equalsIgnoreCase("start")) Smasch.game.start(false);
                if (args[1].equalsIgnoreCase("stop")) Smasch.game.restart();
            }

            if (args[0].equalsIgnoreCase("help")){
                Smasch.sendSmashMSG(player, "Usage of commands:");
                Smasch.sendSmashMSG(player, "1: /smash map save [mapName] [x:from] [y:from] [z:from] [x:to] [y:to] [z:to]");
                Smasch.sendSmashMSG(player, "2: /smash map load [mapName]");
                Smasch.sendSmashMSG(player, "");
                Smasch.sendSmashMSG(player, "3: /smash game start");
                Smasch.sendSmashMSG(player, "4: /smash game stop");
            }
        }







        return true;
    }
}
