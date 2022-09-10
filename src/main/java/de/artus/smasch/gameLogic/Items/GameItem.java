package de.artus.smasch.gameLogic.Items;

import de.artus.smasch.Smasch;
import de.artus.smasch.utils.DestroyBlock;
import de.artus.smasch.utils.mapLoader;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class GameItem implements Listener {

    private ItemStack item;
    private long id;
    private Item onGroundItem;


    private boolean isSimpleItem;
    private String simpleName;

    //properties
    private boolean isThrowable = false;
    private boolean isBomb = false;
    private boolean isBow = false;
    private boolean onGround;


    // BOMBS
    private int BOMBknockBack;
    private int BOMBradius;
    private int BOMBDelay;

    // BOWS
    private String BOWType;
    private Location BOWTimeWarp_ploc;


    public GameItem(Material mat, String name, long ID) {
        this.id = ID;
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(this, Smasch.getPlugin(Smasch.class));

        this.item = new ItemStack(mat);
        ItemMeta itemMeta = this.item.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_GRAY + "" + id);
        itemMeta.setLore(lore);
        this.item.setItemMeta(itemMeta);
    }

    public ItemStack getItem() {
        return this.item;
    }

    public GameItem clone() {
        GameItem gameItem = new GameItem(item.getType(), item.getItemMeta().getDisplayName(), System.currentTimeMillis());
        if (isBow) gameItem.setBow(BOWType);
        if (isBomb) gameItem.setBomb(BOMBknockBack, BOMBradius, BOMBDelay);
        if (isSimpleItem) gameItem.setSimpleItem(simpleName);
        return gameItem;
    }


    @EventHandler
    public void onThrow(PlayerInteractEvent e) {

        if (e.getPlayer().getWorld() != Bukkit.getWorld(Smasch.gameWorld)) return;

        if (e.getItem() != null) {
            if (e.getItem().isSimilar(this.item) && this.isBomb) {
                e.setCancelled(true);
            }
        }

        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getItem() != null) {
                if (e.getItem().isSimilar(this.item)) {
                    if (this.isThrowable) {
                        e.getItem().setAmount(-1);
                        this.onGroundItem = e.getPlayer().getWorld().dropItem(e.getPlayer().getEyeLocation(), this.item);
                        this.onGroundItem.getItemStack().setAmount(1);
                        this.onGroundItem.setPickupDelay(999999999);
                        Vector vel = e.getPlayer().getEyeLocation().getDirection();
                        this.onGroundItem.setVelocity(vel.normalize().multiply(0.75));
                        e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.ITEM_FIRECHARGE_USE, 10, 1);

                        e.getItem().setType(Material.AIR);

                        this.onGround = true;
                        if (this.isBomb) {
                            startBombCountdown();
                        }
                    } else if (isSimpleItem) {
                        if (simpleName.equals("mapReset")){
                            mapLoader.placeMap("sky", false);
                            e.getItem().setAmount(-1);
                        }
                    }
                }
            }
        }
    }


    @EventHandler
    public void onBowUse(EntityShootBowEvent e) {

        if (e.getEntity().getWorld() != Bukkit.getWorld(Smasch.gameWorld)) return;

        if (e.getProjectile() instanceof Arrow) {
            if (e.getEntity() instanceof Player) {
                if (e.getBow().isSimilar(this.item)) {
                    if (Objects.equals(this.BOWType, "teleport")) {
                        e.getProjectile().setCustomName(ChatColor.RED + "TELEBOW-ARROW_" + id);
                    } else if (Objects.equals(this.BOWType, "timeWarp")) {
                        e.getProjectile().setCustomName(ChatColor.RED + "TIMEWARP-ARROW_" + id);
                        this.BOWTimeWarp_ploc = e.getEntity().getLocation();
                    }

                    e.getBow().setAmount(-1);
                }
            }
        }
    }

    @EventHandler
    public void onBowArrowHit(ProjectileHitEvent e) {

        if (e.getEntity().getWorld() != Bukkit.getWorld(Smasch.gameWorld)) return;

        if (e.getEntity() instanceof Arrow) {
            if (Objects.equals(this.BOWType, "teleport") || Objects.equals(this.BOWType, "timeWarp")) {
                if (Objects.equals(e.getEntity().getCustomName(), ChatColor.RED + "TELEBOW-ARROW_" + id) || Objects.equals(e.getEntity().getCustomName(), ChatColor.RED + "TIMEWARP-ARROW_" + id)) {
                    if (e.getEntity().getShooter() instanceof Player p) {
                        p.teleport(e.getEntity().getLocation().add(0, 1, 0).setDirection(p.getLocation().getDirection()));
                        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                        e.getEntity().remove();
                        e.setCancelled(true);

                        if (Objects.equals(e.getEntity().getCustomName(), ChatColor.RED + "TIMEWARP-ARROW_" + id)) {
                            new BukkitRunnable() {

                                int timer = 4;

                                @Override
                                public void run() {
                                    timer--;
                                    if (!Smasch.game.gameOn()) {
                                        cancel();
                                    }
                                    if (timer == 1){
                                        if (BOWTimeWarp_ploc != null){
                                            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ILLUSIONER_PREPARE_BLINDNESS, 1, 1);
                                        }
                                    }
                                    if (timer <= 0) {
                                        if (BOWTimeWarp_ploc != null){
                                            p.teleport(BOWTimeWarp_ploc);
                                            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                                        }
                                        cancel();
                                    }

                                }
                            }.runTaskTimer(Smasch.getPlugin(Smasch.class), 20, 20);
                        }
                    }
                }
            }
        }

    }


    public GameItem setSimpleItem(String name) {
        simpleName = name;
        isSimpleItem = true;

        return this;

    }

    public GameItem setBomb(int knockBack, int radius, int delay) {
        this.BOMBknockBack = knockBack;
        this.BOMBradius = radius;
        this.BOMBDelay = delay;
        this.isBomb = true;
        this.isThrowable = true;

        return this;
    }

    public GameItem setBow(String type) {

        this.isBow = true;

        this.item.addUnsafeEnchantment(Enchantment.LURE, 1);
        ItemMeta itemMeta = this.item.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        this.item.setItemMeta(itemMeta);

        this.BOWType = type;

        return this;
    }

    public void explodeBomb() {

        if (!Smasch.game.gameOn()) {
            this.onGroundItem.remove();
            return;
        }

        for (Player p : Bukkit.getOnlinePlayers()) {


            Vector pLoc = p.getLocation().toVector();
            Vector bombLoc = this.onGroundItem.getLocation().toVector();
            if (bombLoc.distance(pLoc) < this.BOMBradius) {
                Vector force = pLoc.subtract(bombLoc);
                if (force.getX() > 0) {
                    force.setX(this.BOMBradius - force.getX());
                } else {
                    force.setX(-this.BOMBradius - force.getX());
                }
                if (force.getY() > 0) {
                    force.setY(this.BOMBradius - force.getY());
                } else {
                    force.setY(-this.BOMBradius - force.getY());
                }
                if (force.getZ() > 0) {
                    force.setZ(this.BOMBradius - force.getZ());
                } else {
                    force.setZ(-this.BOMBradius - force.getZ());
                }
                force.multiply(this.BOMBknockBack / 10D);
                force.setY(this.BOMBknockBack / 6.66);
                force.setY(0.75);


                p.setVelocity(force);
            }
        }

        for (int x = -this.BOMBradius / 2; x <= this.BOMBradius / 2; x++) {
            for (int z = -this.BOMBradius / 2; z <= this.BOMBradius / 2; z++) {
                for (int y = -this.BOMBradius / 2; y <= this.BOMBradius / 2; y++) {
                    Block block = this.onGroundItem.getLocation().add(x, y, z).getBlock();

                    if (block.isLiquid()) continue;

                    if (block.getLocation().distance(this.onGroundItem.getLocation()) < this.BOMBradius / 3D) {
                        FallingBlock fBlock = block.getWorld().spawnFallingBlock(block.getLocation(), block.getBlockData());

                        Random rand = new Random();
                        double randForce = 5 - rand.nextInt(10) / 10D;

                        fBlock.setVelocity(fBlock.getLocation().toVector().subtract(this.onGroundItem.getLocation().toVector())
                                .multiply(this.BOMBknockBack / 20D + randForce).setY(0.5));
                        fBlock.setDropItem(false);
                        block.setType(Material.AIR);
                    } else {

                        if (block.getLocation().distance(this.onGroundItem.getLocation()) < 10) {
                            DestroyBlock.destroyBlockAt(block.getLocation(), 10 - (int) block.getLocation().distance(this.onGroundItem.getLocation()));
                        }

                    }

                }
            }
        }


        this.onGroundItem.remove();

    }

    public void startBombCountdown() {

        int delay = this.BOMBDelay;
        boolean explodeOnGround = (this.BOMBDelay == -1);

        Item groundItem = this.onGroundItem;

        new BukkitRunnable() {

            int timer = delay + 1;

            @Override
            public void run() {

                timer--;

                if (groundItem.isOnGround() && explodeOnGround) {
                    explodeBomb();
                    cancel();
                }
                if (timer < -100) {
                    cancel();
                }
                if (timer <= 0 && !explodeOnGround) {
                    explodeBomb();
                    cancel();
                }
            }

        }.runTaskTimer(Smasch.getPlugin(Smasch.class), 20, 20);
    }

}
