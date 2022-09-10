package de.artus.smasch.utils;

import de.artus.smasch.Smasch;
import de.artus.smasch.gameLogic.ItemsInventory;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Game implements Listener {


    public boolean isCountdown = false;
    private boolean isGameOn = false;
    private Plugin plugin;
    private List<Location> spawn_locations = new ArrayList<>();
    private List<Location> cageLocations = new ArrayList<>();
    private List<Material> cageBlocks = new ArrayList<>();
    private List<Location> itemSpawnLocs = new ArrayList<>();


    public Map<Player, Double> playerDoubleJumpCooldown = new HashMap<>();

    public List<Player> inGamePlayers = new ArrayList<>();


    public Game(Plugin BukkitPlugin) {
        plugin = BukkitPlugin;

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(this, plugin);


        cageBlocks.clear();
        cageBlocks.add(Material.AIR);
        cageBlocks.add(Material.AIR);
        cageBlocks.add(Material.AIR);
        cageBlocks.add(Material.AIR);

        cageBlocks.add(Material.SMOOTH_QUARTZ_STAIRS);
        cageBlocks.add(Material.GLASS);
        cageBlocks.add(Material.GLASS);
        cageBlocks.add(Material.SMOOTH_QUARTZ_SLAB);

        cageBlocks.add(Material.AIR);
        cageBlocks.add(Material.AIR);
        cageBlocks.add(Material.AIR);
        cageBlocks.add(Material.AIR);

        cageBlocks.add(Material.SMOOTH_QUARTZ_STAIRS);
        cageBlocks.add(Material.GLASS);
        cageBlocks.add(Material.GLASS);
        cageBlocks.add(Material.SMOOTH_QUARTZ_SLAB);

        cageBlocks.add(Material.GLASS);
        cageBlocks.add(Material.AIR);
        cageBlocks.add(Material.AIR);
        cageBlocks.add(Material.SMOOTH_QUARTZ_SLAB);

        cageBlocks.add(Material.SMOOTH_QUARTZ_STAIRS);
        cageBlocks.add(Material.GLASS);
        cageBlocks.add(Material.GLASS);
        cageBlocks.add(Material.SMOOTH_QUARTZ_SLAB);

        cageBlocks.add(Material.AIR);
        cageBlocks.add(Material.AIR);
        cageBlocks.add(Material.AIR);
        cageBlocks.add(Material.AIR);

        cageBlocks.add(Material.SMOOTH_QUARTZ_STAIRS);
        cageBlocks.add(Material.GLASS);
        cageBlocks.add(Material.GLASS);
        cageBlocks.add(Material.SMOOTH_QUARTZ_SLAB);

        cageBlocks.add(Material.AIR);
        cageBlocks.add(Material.AIR);
        cageBlocks.add(Material.AIR);
        cageBlocks.add(Material.AIR);


    }


    public void setSpawnCage(Location loc) {


        int block = 0;
        int stairC = 0;
        int slabC = 0;
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                for (int y = 0; y <= 3; y++) {
                    if (cageBlocks.get(block) == Material.SMOOTH_QUARTZ_STAIRS) {
                        stairC++;
                    }
                    loc.getBlock().getRelative(x, y, z).setType(cageBlocks.get(block));
                    if (cageBlocks.get(block) == Material.SMOOTH_QUARTZ_SLAB) {
                        slabC++;
                        if (slabC == 3) {
                            setCageBlockRotation(loc.getBlock().getRelative(x, y, z).getLocation(), BlockFace.UP);
                        } else {
                            setCageBlockRotation(loc.getBlock().getRelative(x, y, z).getLocation(), BlockFace.DOWN);
                        }
                    }
                    switch (stairC) {
                        case 1 ->
                                setCageBlockRotation(loc.getBlock().getRelative(x, y, z).getLocation(), BlockFace.EAST);
                        case 2 ->
                                setCageBlockRotation(loc.getBlock().getRelative(x, y, z).getLocation(), BlockFace.SOUTH);
                        case 3 ->
                                setCageBlockRotation(loc.getBlock().getRelative(x, y, z).getLocation(), BlockFace.NORTH);
                        case 4 ->
                                setCageBlockRotation(loc.getBlock().getRelative(x, y, z).getLocation(), BlockFace.WEST);
                    }
                    block++;
                }
            }
        }

    }

    public void setCageBlockRotation(Location loc, BlockFace facing) {
        if (loc.getBlock().getType() == Material.SMOOTH_QUARTZ_STAIRS) {
            Stairs stairs = (Stairs) loc.getBlock().getBlockData();
            stairs.setHalf(Bisected.Half.TOP);
            stairs.setFacing(facing);
            loc.getBlock().setBlockData(stairs);
        }
        if (loc.getBlock().getType() == Material.SMOOTH_QUARTZ_SLAB) {
            Slab slab = (Slab) loc.getBlock().getBlockData();
            if (facing == BlockFace.UP) {
                slab.setType(Slab.Type.TOP);
            }
            if (facing == BlockFace.DOWN) {
                slab.setType(Slab.Type.BOTTOM);
            }
            loc.getBlock().setBlockData(slab);
        }
    }

    public void resetItemSpawnLocations() {

        itemSpawnLocs.clear();
        itemSpawnLocs.add(new Location(Bukkit.getWorld(Smasch.gameWorld), 27, 77, 0));
        itemSpawnLocs.add(new Location(Bukkit.getWorld(Smasch.gameWorld), 14, 65, 0));
        itemSpawnLocs.add(new Location(Bukkit.getWorld(Smasch.gameWorld), 14, 67, 11));
        itemSpawnLocs.add(new Location(Bukkit.getWorld(Smasch.gameWorld), 14, 67, -11));
        itemSpawnLocs.add(new Location(Bukkit.getWorld(Smasch.gameWorld), 0, 71, 7));
        itemSpawnLocs.add(new Location(Bukkit.getWorld(Smasch.gameWorld), 0, 71, -7));
        itemSpawnLocs.add(new Location(Bukkit.getWorld(Smasch.gameWorld), 0, 71, 0));
        itemSpawnLocs.add(new Location(Bukkit.getWorld(Smasch.gameWorld), 0, 81, 0));
        itemSpawnLocs.add(new Location(Bukkit.getWorld(Smasch.gameWorld), -27, 77, 0));
        itemSpawnLocs.add(new Location(Bukkit.getWorld(Smasch.gameWorld), -14, 65, 0));
        itemSpawnLocs.add(new Location(Bukkit.getWorld(Smasch.gameWorld), -14, 67, 11));
        itemSpawnLocs.add(new Location(Bukkit.getWorld(Smasch.gameWorld), -14, 67, -11));


        for (Entity entity : Bukkit.getWorld(Smasch.gameWorld).getEntities()){
            if (entity instanceof Item item){
                if (item.getPickupDelay() < 100){
                    List<Location> deleteLocs = new ArrayList<>();
                    for (Location loc : itemSpawnLocs){
                        if (loc.clone().add(0.5, 1, 0.5).distance(entity.getLocation()) <= 1){
                            deleteLocs.add(loc);
                        }
                    }
                    //itemSpawnLocs.removeIf(loc -> loc.add(0.5, 1, 0.5).distance(entity.getLocation()) <= 1);
                    itemSpawnLocs.removeAll(deleteLocs);
                }
            }
        }

    }


    public void spawnRandomItem() {
        if (itemSpawnLocs.isEmpty()) {
            resetItemSpawnLocations();
            if (itemSpawnLocs.isEmpty()) return;
        }
        Random random = new Random();
        int pos = random.nextInt(itemSpawnLocs.size());
        int item = random.nextInt(ItemsInventory.ITEMS.size());
        Location loc = itemSpawnLocs.get(pos);
        itemSpawnLocs.remove(loc);
        if (loc.getBlock().getType() != Material.AIR && loc.clone().add(0.5, 1.25, 0.5).getBlock().getType() == Material.AIR){
            Item droppedItem = loc.getWorld().dropItem(loc.clone().add(0.5, 1.25, 0.5), ItemsInventory.ITEMS.get(item).clone().getItem());
            droppedItem.setVelocity(new Vector(0, 0.1, 0));
            //droppedItem.teleport(loc.clone().add(0.5, 1.25, 0.5));
        }


    }

    public void startGameLoop(boolean debug) {

        new BukkitRunnable() {

            int itemSpawnDelay = 10;
            Random random = new Random();


            @Override
            public void run() {


                if (itemSpawnDelay <= 0) {
                    itemSpawnDelay = random.nextInt(10) + 15;
                    spawnRandomItem();
                }
                itemSpawnDelay--;

                if (inGamePlayers.size() == 1 && !debug) {
                    setWinner(inGamePlayers.get(0));
                    stop();


                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {

                        getInWorldPlayers().forEach(player -> {

                            player.setGameMode(GameMode.ADVENTURE);
                            player.teleport(new Location(player.getWorld(), 0, 102, 0));
                            setDefaultEquip(player);

                        });
                        checkPlayerCount();
                        sendInGamePlayersMSG("restarting game!");
                        mapLoader.placeMap("sky", true);
                        startCountdown();

                        }, 20 * 10);
                    }

                    inGamePlayers.forEach(player -> {


                        if (player.getInventory().getItem(9) == null) {
                            player.getInventory().setItem(9, new ItemStack(Material.ARROW, 64));
                        } else if (player.getInventory().getItem(9).getType() != Material.ARROW) {
                            player.getInventory().setItem(9, new ItemStack(Material.ARROW, 64));
                        }


                    });


                    for (Map.Entry<Player, Double> entry : playerDoubleJumpCooldown.entrySet()) {

                        Player player = entry.getKey();


                        playerDoubleJumpCooldown.put(player, (playerDoubleJumpCooldown.get(player) - 0.5));

                        StringBuilder charge = new StringBuilder();
                        charge.append(ChatColor.GREEN);
                        for (double i = 0; i < 3; i += 0.5) {
                            if (i < 3 - playerDoubleJumpCooldown.get(player)) {
                                charge.append("▌");
                            } else {
                                charge.append(ChatColor.GRAY);
                                charge.append("▌");
                            }
                        }
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.DARK_GRAY + "[" + charge + ChatColor.DARK_GRAY + "]"));


                        if (playerDoubleJumpCooldown.get(player) <= 0) {
                            playerDoubleJumpCooldown.remove(player);
                            player.setAllowFlight(true);
                            player.playEffect(player.getLocation(), Effect.WITHER_SHOOT, 15);
                        }


                    }

                    if (!isGameOn) {
                        cancel();
                    }
                }
            }.runTaskTimer(plugin, 20,10);
        }

        @EventHandler
        public void onItemsMoveInInventory(InventoryClickEvent e){
            Player p = (Player) e.getWhoClicked();
            if (p.getWorld() != Bukkit.getWorld(Smasch.gameWorld)) return;
            if (p.getGameMode() == GameMode.ADVENTURE) {
                e.setCancelled(true);
            }
        }





        public List<Player> getInWorldPlayers () {
            List<Player> players = new ArrayList<>();
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getWorld() == Bukkit.getWorld(Smasch.gameWorld)) {
                    players.add(p);
                }
            }
            return players;
        }


        public List<Player> checkPlayerCount () {
            inGamePlayers.clear();

            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getWorld() == Bukkit.getWorld(Smasch.gameWorld)) {
                    if (p.getGameMode() != GameMode.SPECTATOR) {
                        inGamePlayers.add(p);
                    }
                }
            }

            return inGamePlayers;
        }

        @EventHandler(priority = EventPriority.HIGHEST)
        public void onWorldChange (PlayerChangedWorldEvent e){

            if (e.getFrom() == Bukkit.getWorld(Smasch.gameWorld)) {
                checkPlayerCount();

                e.getPlayer().getInventory().clear();
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        e.getPlayer().setGameMode(GameMode.ADVENTURE);
                    }
                }.runTaskLater(plugin, 2);
                setDefaultEquip(e.getPlayer());

                if (isGameOn) {
                    Smasch.sendSmashMSG(e.getPlayer(), "You lost! because you left the active game!");

                    if (inGamePlayers.size() < 2) {

                    } else {
                        sendInGamePlayersMSG(e.getPlayer().getName() + " ist raus!");
                    }
                }

            } else if (e.getPlayer().getWorld() == Bukkit.getWorld(Smasch.gameWorld)) {
                checkPlayerCount();

                sendInGamePlayersMSG(e.getPlayer().getName() + " joined the smash game!");

                if (isGameOn) {
                    e.getPlayer().setGameMode(GameMode.SPECTATOR);
                    Smasch.sendSmashMSG(e.getPlayer(), "A game is running! Please wait...");
                } else {
                    setDefaultEquip(e.getPlayer());
                    if (!isCountdown) {
                        startCountdown();
                    }
                }

            }
        }

        @EventHandler
        public void onLeave (PlayerQuitEvent e){
            if (e.getPlayer().getWorld() == Bukkit.getWorld(Smasch.gameWorld)) {
                checkPlayerCount();

                if (isGameOn) {
                    if (inGamePlayers.size() < 2) {

                    } else {
                        sendInGamePlayersMSG(e.getPlayer().getName() + "  ist raus!");
                    }
                }

            }
        }




        public void setDefaultEquip (Player player){
            player.setGameMode(GameMode.ADVENTURE);
            player.getInventory().clear();

            ItemStack compass = new ItemStack(Material.COMPASS, 1);
            compass.addUnsafeEnchantment(Enchantment.QUICK_CHARGE, 1);
            compass.addUnsafeEnchantment(Enchantment.LOYALTY, 1);
            ItemMeta meta = compass.getItemMeta();
            meta.setDisplayName(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Navigator");
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            compass.setItemMeta(meta);
            player.getInventory().setItem(4, compass);

        }

        @EventHandler
        public void onPickupItem(EntityPickupItemEvent e){
            if (e.getEntity() instanceof Player player){
                if (player.getWorld() != Bukkit.getWorld(Smasch.gameWorld)) return;
                if (isGameOn && ((Player) e.getEntity()).getGameMode() == GameMode.ADVENTURE){
                    ItemStack item = player.getInventory().getItem(1);
                    if (item != null){
                        if (item.getType() != Material.AIR){
                            e.setCancelled(true);
                        }
                    }
                }

            }
        }

        @EventHandler
        public void onFellInVoid (PlayerMoveEvent e){

            if (e.getPlayer().getWorld() == Bukkit.getWorld(Smasch.gameWorld)) {
                checkPlayerCount();

                if (isGameOn && e.getTo().getY() < 0) {
                    if (inGamePlayers.size() < 2) {

                    } else {
                        sendInGamePlayersMSG(e.getPlayer().getName() + " ist raus!");
                        e.getPlayer().teleport(new Location(e.getPlayer().getWorld(), 0, 85, 0));
                        e.getPlayer().setGameMode(GameMode.SPECTATOR);
                    }
                }

            }

        }


        public void setWinner (Player player){
            stop();

            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getWorld() == Bukkit.getWorld(Smasch.gameWorld)) {
                    Smasch.sendSmashMSG(p, player.getName() + " hat Gewonnen!!!");
                }
            }


            for (int i = 0; i <= 10; i++) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    if (player.getWorld() != Bukkit.getWorld(Smasch.gameWorld)) return;
                    Random random = new Random();
                    final int randomNumX = random.nextInt(10) - 5;
                    final int randomNumZ = random.nextInt(10) - 5;
                    Location loc = player.getLocation();
                    Location fwLoc = new Location(player.getWorld(), loc.getX() + randomNumX, loc.getY() + 2, loc.getZ() + randomNumZ);
                    Firework fw = (Firework) player.getWorld().spawnEntity(fwLoc, EntityType.FIREWORK);
                    FireworkMeta fwm = fw.getFireworkMeta();
                    fwm.setPower(2);
                    fwm.addEffect(FireworkEffect.builder().withColor(Color.LIME).flicker(true).trail(true).build());
                    fw.setFireworkMeta(fwm);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, fw::detonate, 20);
                }, i * 20);
            }

        }

        public void sendInGamePlayersMSG (String msg){
            for (Player p : getInWorldPlayers()) {
                Smasch.sendSmashMSG(p, msg);
            }
        }


        public void startDoubleJumpCooldown (Player player){
            playerDoubleJumpCooldown.put(player, 3.0);
        }


        public void start (boolean debug) {
            isGameOn = true;

            cageLocations.clear();
            spawn_locations.clear();


            spawn_locations.add(new Location(Bukkit.getWorld(Smasch.gameWorld), 27, 80, 0));
            spawn_locations.add(new Location(Bukkit.getWorld(Smasch.gameWorld), 0, 84, 0));
            spawn_locations.add(new Location(Bukkit.getWorld(Smasch.gameWorld), -27, 80, 0));
            spawn_locations.add(new Location(Bukkit.getWorld(Smasch.gameWorld), 0, 74, -7));
            spawn_locations.add(new Location(Bukkit.getWorld(Smasch.gameWorld), 0, 74, 7));
            spawn_locations.add(new Location(Bukkit.getWorld(Smasch.gameWorld), -14, 68, 0));
            spawn_locations.add(new Location(Bukkit.getWorld(Smasch.gameWorld), 14, 68, 0));


            Bukkit.getWorld(Smasch.gameWorld).getEntities().forEach(entity -> {
                if (!(entity instanceof Player)) {
                    entity.remove();
                }
            });

            resetItemSpawnLocations();
            for (int i = 1; i <= 5; i++) {
                spawnRandomItem();
            }

            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getWorld() == Bukkit.getWorld(Smasch.gameWorld)) {

                    Random randomizer = new Random();
                    int randSpawn = randomizer.nextInt(spawn_locations.size());
                    Location spawnLoc = spawn_locations.get(randSpawn);
                    spawn_locations.remove(randSpawn);
                    cageLocations.add(spawnLoc);

                    setSpawnCage(spawnLoc);
                    player.teleport(spawnLoc.add(0.5, 1, 0.5));


                    player.getInventory().clear();

                    ItemStack kbStick = new ItemStack(Material.STICK);
                    ItemMeta kbStickMeta = kbStick.getItemMeta();
                    kbStickMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
                    kbStick.setItemMeta(kbStickMeta);

                    player.getInventory().setItem(0, kbStick);

                    player.setGameMode(GameMode.ADVENTURE);
                    player.setAllowFlight(true);
                    player.setFlying(false);

                }
            }

            new BukkitRunnable() {
                @Override
                public void run() {
                    removeSpawnCages();
                }
            }.runTaskLater(plugin, 3 * 20);


            startGameLoop(debug);
        }

    public void removeSpawnCages() {
        for (Location cageLoc : cageLocations) {
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 3; y++) {
                    for (int z = -1; z <= 1; z++) {
                        cageLoc.getBlock().getRelative(x, y, z).setType(Material.AIR);
                    }
                }
            }
        }
    }

    public void startCountdown () {

        if (isCountdown || isGameOn) return;

        isCountdown = true;


        if (checkPlayerCount().size() > 2) {
            sendInGamePlayersMSG("Starting the smash countdown!");
        } else {
            sendInGamePlayersMSG("Waiting for other players...");
        }

        new BukkitRunnable() {

            int countdown = 31;
            boolean startingGame = false;


            @Override
            public void run() {
                int playercount = 0;
                for (Player p : Bukkit.getOnlinePlayers()){
                    if (p.getWorld() == Bukkit.getWorld(Smasch.gameWorld)){
                        playercount++;
                    }
                }

                if (playercount >= 2) {
                    if (!startingGame) {
                        countdown = 31;
                        startingGame = true;
                        isCountdown = true;
                        sendInGamePlayersMSG("Starting the smash countdown!");
                    }
                } else {
                    if (startingGame) {
                        isCountdown = false;
                        startingGame = false;
                        countdown = 31;
                        sendPlayersTitle(" ", ChatColor.RED + "Countdown abgebrochen!", 20, 40, 20);
                        playSoundAtPlayers(Sound.BLOCK_STONE_BUTTON_CLICK_OFF, 1);
                        sendInGamePlayersMSG("The countdown was canceled!");
                        cancel();
                    }
                }

                if (startingGame && countdown == 31) {
                    sendInGamePlayersMSG("Starting in 30...");
                }

                if (isCountdown && playercount >= 2) {
                    countdown -= 1;
                }

                if (countdown == 15) {
                    sendInGamePlayersMSG("Starting in 15...");
                }
                if (countdown == 10) {
                    sendInGamePlayersMSG("Starting in 10...");
                }

                if (countdown == 3) {
                    sendPlayersTitle(ChatColor.GREEN + "Starting in", ChatColor.YELLOW + "3", 1, 20, 1);
                    sendInGamePlayersMSG("Starting in " + ChatColor.YELLOW + "3" + ChatColor.GREEN + "...");
                    playSoundAtPlayers(Sound.BLOCK_NOTE_BLOCK_PLING, 1);
                    start(false);
                }
                if (countdown == 2) {
                    sendPlayersTitle(ChatColor.GREEN + "Starting in", ChatColor.GOLD + "2", 1, 20, 1);
                    sendInGamePlayersMSG("Starting in " + ChatColor.GOLD + "2" + ChatColor.GREEN + "...");
                    playSoundAtPlayers(Sound.BLOCK_NOTE_BLOCK_PLING, 1);
                }
                if (countdown == 1) {
                    sendPlayersTitle(ChatColor.GREEN + "Starting in", ChatColor.RED + "1", 1, 20, 1);
                    sendInGamePlayersMSG("Starting in " + ChatColor.RED + "1" + ChatColor.GREEN + "...");
                    playSoundAtPlayers(Sound.BLOCK_NOTE_BLOCK_PLING, 3);
                }

                if (countdown <= 0) {
                    isCountdown = false;
                    cancel();
                }

            }

        }.runTaskTimer(plugin, 20, 20);

    }

    public void sendPlayersTitle (String title, String subtitle,int fadeIn, int stay, int fadeOut){
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getWorld() == Bukkit.getWorld(Smasch.gameWorld)) {
                player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
            }
        }
    }
    public void playSoundAtPlayers (Sound sound,int pitch){
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getWorld() == Bukkit.getWorld(Smasch.gameWorld)) {
                player.playSound(player, sound, 10, pitch);
            }
        }
    }
    public void stop () {
        isGameOn = false;

        getInWorldPlayers().forEach(player -> {

            player.setGameMode(GameMode.ADVENTURE);
            player.teleport(new Location(player.getWorld(), 0, 102, 0));
            setDefaultEquip(player);

        });

        mapLoader.placeMap("sky", true);
    }

    public void restart() {
        stop();


        checkPlayerCount();
        sendInGamePlayersMSG("restarting game!");
        mapLoader.placeMap("sky", true);
        startCountdown();


    }

    public boolean gameOn () {
        return isGameOn;
    }
}
