package de.artus.smasch.commands;

import de.artus.smasch.Smasch;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.bukkit.event.Listener;


public class tpsmash implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        Location loc = new Location(Bukkit.getWorld(Smasch.gameWorld), 0, 102, 0);
        ((Player) sender).teleport(loc);


        return false;
    }


}
