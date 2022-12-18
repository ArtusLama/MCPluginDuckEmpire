package de.artus.smasch.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SmashCommandTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {


        if (!sender.isOp()){
            return null;
        }

        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();

        if (args.length == 1){
            commands.add("help");
            commands.add("game");
            commands.add("map");

            StringUtil.copyPartialMatches(args[0], commands, completions);
        }
        else if (args.length == 2){

            if (args[0].equalsIgnoreCase("game")) {
                commands.add("start");
                commands.add("stop");
            } else if (args[0].equalsIgnoreCase("map")) {
                commands.add("save");
                commands.add("load");
            }

            StringUtil.copyPartialMatches(args[1], commands, completions);
        } else if (args.length == 3) {
            if (args[1].equalsIgnoreCase("load") || args[1].equalsIgnoreCase("save")) commands.add("[MapName]");
            StringUtil.copyPartialMatches(args[2], commands, completions);
        } else if (args.length == 4) {
            if (args[1].equalsIgnoreCase("save")) commands.add(((Player) sender).getTargetBlock(null, 5).getX() + "");
            StringUtil.copyPartialMatches(args[3], commands, completions);
        } else if (args.length == 5) {
            if (args[1].equalsIgnoreCase("save")) commands.add(((Player) sender).getTargetBlock(null, 5).getY() + "");
            StringUtil.copyPartialMatches(args[4], commands, completions);
        } else if (args.length == 6) {
            if (args[1].equalsIgnoreCase("save")) commands.add(((Player) sender).getTargetBlock(null, 5).getZ() + "");
            StringUtil.copyPartialMatches(args[5], commands, completions);
        } else if (args.length == 7) {
            if (args[1].equalsIgnoreCase("save")) commands.add(((Player) sender).getTargetBlock(null, 5).getX() + "");
            StringUtil.copyPartialMatches(args[6], commands, completions);
        } else if (args.length == 8) {
            if (args[1].equalsIgnoreCase("save")) commands.add(((Player) sender).getTargetBlock(null, 5).getY() + "");
            StringUtil.copyPartialMatches(args[7], commands, completions);
        } else if (args.length == 9) {
            if (args[1].equalsIgnoreCase("save")) commands.add(((Player) sender).getTargetBlock(null, 5).getZ() + "");
            StringUtil.copyPartialMatches(args[8], commands, completions);
        }

        Collections.sort(completions);
        return completions;
    }
}
