package de.artus.smasch.commands;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class testcommand_unreachable implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //ItemsInventory.openItemsInventory((Player) sender);

        /*
        if (sender instanceof Player player){

            new BukkitRunnable() {

                float radians = 0f;
                float y = 0.1f;
                int dir = 1;
                Player p = player;

                @Override
                public void run() {

                    if (!Smasch.game.gameOn()){
                        cancel();
                    }

                    radians += 0.1;
                    y += 0.02 * dir;
                    if (y >= 2 || y <= 0) y = dir * -1;

                    double x = Math.cos(radians);
                    double z = Math.sin(radians);
                    Location loc = p.getLocation().clone().add(x, y, z);

                    Particle.DustTransition dustTransition = new Particle.DustTransition(Color.AQUA, Color.WHITE, 1);
                    loc.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, loc, 5, dustTransition);

                }
            }.runTaskTimer(Smasch.getPlugin(Smasch.class), 2, 2);

        }


         */


        /*Block block = ((Player) sender).getLocation().subtract(0, 1, 0).getBlock();

       ((CraftServer) Bukkit.getServer()).getHandle().a(null, block.getX(), block.getY(), block.getZ(), 120, ((CraftWorld) block.getLocation().getWorld()).getHandle().aa(),
                new PacketPlayOutBlockBreakAnimation(((block.getX() & 0xFFF) << 20 | (block.getZ() & 0xFFF) << 8) | (block.getY() & 0xFF), new BlockPosition(block.getX(), block.getY(), block.getZ()), 3));
        */
        return false;
    }
}
