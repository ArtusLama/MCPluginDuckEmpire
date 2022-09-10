package de.artus.smasch.utils;

import net.minecraft.core.BlockPosition;
import net.minecraft.network.protocol.game.PacketPlayOutBlockBreakAnimation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_18_R2.CraftServer;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;


public class DestroyBlock {


    public static void destroyBlockAt(Location location, int state){

        Block block = location.getBlock();

       ((CraftServer) Bukkit.getServer()).getHandle().a(null, block.getX(), block.getY(), block.getZ(), 120, ((CraftWorld) block.getLocation().getWorld()).getHandle().aa(),
                new PacketPlayOutBlockBreakAnimation(((block.getX() & 0xFFF) << 20 | (block.getZ() & 0xFFF) << 8) | (block.getY() & 0xFF), new BlockPosition(block.getX(), block.getY(), block.getZ()), state));

    }

}
