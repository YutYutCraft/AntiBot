package net.mortalheroes.antibot;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public final class AntiBot extends JavaPlugin implements Listener {

    public HashMap<Player, Integer> map = new HashMap<>();


    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info(ChatColor.translateAlternateColorCodes('&', "&2                      "));
        getLogger().info(ChatColor.translateAlternateColorCodes('&', "&2            ____      "));
        getLogger().info(ChatColor.translateAlternateColorCodes('&', "&2     /\\    |  _ \\   &a SimpleAntiBot &fhas been successfully loaded!"));
        getLogger().info(ChatColor.translateAlternateColorCodes('&', "&2    /  \\   | |_) |   &fAuthor: &adxcf"));
        getLogger().info(ChatColor.translateAlternateColorCodes('&', "&2   / /\\ \\  |  _ <   &f Version: &a1.0.0"));
        getLogger().info(ChatColor.translateAlternateColorCodes('&', "&2  / ____ \\ | |_) |   &fDescription: &aA simple AntiBot plugin"));
        getLogger().info(ChatColor.translateAlternateColorCodes('&', "&2 /_/    \\_\\|____/   &f Be sure to rate this plugin &a5 Stars&f for more updates and bypass patches!"));
        getLogger().info(ChatColor.translateAlternateColorCodes('&', "&2                      "));
        getLogger().info(ChatColor.translateAlternateColorCodes('&', "&2                      "));
    }

    @Override
    public void onDisable() {
        getLogger().info(ChatColor.translateAlternateColorCodes('&', "&2                      "));
        getLogger().info(ChatColor.translateAlternateColorCodes('&', "&2            ____      "));
        getLogger().info(ChatColor.translateAlternateColorCodes('&', "&2     /\\    |  _ \\   &a SimpleAntiBot &fhas been successfully disabled!"));
        getLogger().info(ChatColor.translateAlternateColorCodes('&', "&2    /  \\   | |_) |   &fAuthor: &adxcf"));
        getLogger().info(ChatColor.translateAlternateColorCodes('&', "&2   / /\\ \\  |  _ <   &f Version: &a1.0.0"));
        getLogger().info(ChatColor.translateAlternateColorCodes('&', "&2  / ____ \\ | |_) |   &fDescription: &aA simple AntiBot plugin"));
        getLogger().info(ChatColor.translateAlternateColorCodes('&', "&2 /_/    \\_\\|____/   &f Be sure to rate this plugin &a5 Stars&f for more updates and bypass patches!"));
        getLogger().info(ChatColor.translateAlternateColorCodes('&', "&2                      "));
        getLogger().info(ChatColor.translateAlternateColorCodes('&', "&2                      "));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("antibot")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2  "));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&cA&4B&8] &fThis server is currently running &2SimpleAntiBot &fversion &a1.0.0-BETA &fmade by &2THA1LAND (aka dxcf)&f."));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2  "));
            } else {
                reloadConfig();
                getLogger().info("Configuration reloaded!");
            }

            return true;
        }
        return false;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        //Creating a gui
        Inventory inv = Bukkit.createInventory(null, 9, "" + ChatColor.translateAlternateColorCodes('&', getConfig().getString("gui-title")));


        //Items

        //Click me
        ItemStack clickme = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        //Don't click me
        ItemStack dontclickme = new ItemStack(Material.RED_STAINED_GLASS_PANE);

        //Metas

        //Click me
        ItemMeta clickme1 = clickme.getItemMeta();
        clickme1.setDisplayName(ChatColor.translateAlternateColorCodes('&', "" + getConfig().getString("clickme-title")));
        clickme.setItemMeta(clickme1);
        //Don't click me
        ItemMeta clickme2 = dontclickme.getItemMeta();
        clickme2.setDisplayName(ChatColor.translateAlternateColorCodes('&', "" + getConfig().getString("dont-clickme-title")));
        dontclickme.setItemMeta(clickme2);

        //set item

        //Randomize slot
        Integer random = getRandomNumberInRange(0, 8);

        //setup slots
        inv.setItem(0, dontclickme);
        inv.setItem(1, dontclickme);
        inv.setItem(2, dontclickme);
        inv.setItem(3, dontclickme);
        inv.setItem(4, dontclickme);
        inv.setItem(5, dontclickme);
        inv.setItem(6, dontclickme);
        inv.setItem(7, dontclickme);
        inv.setItem(8, dontclickme);
        inv.setItem(random, clickme);

        player.openInventory(inv);

        //timeout

        //getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
        //    public void run() {
        //        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "" + getConfig().getString("cmd-fail-prefix") + player.getName() + getConfig().getString("cmd-fail-suffix"));
        //        getLogger().warning("Player with uuid [" + player.getUniqueId() + "] timed out while verifying.");
        //        getLogger().info("Successfully dispatched command /" + getConfig().getString("cmd-fail-prefix") + player.getName() + getConfig().getString("cmd-fail-suffix") + " with sender \"Console\".");
        //    }
        //}, 0, 20);

        new BukkitRunnable(){
            int countdown = getConfig().getInt("timeout");
            public void run(){
                if(countdown <= 0 || !player.isOnline()){
                    this.cancel();
                    if (!map.containsKey(player)) {
                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "" + getConfig().getString("cmd-fail-prefix") + player.getName() + getConfig().getString("cmd-fail-suffix"));
                        getLogger().warning("Player with uuid [" + player.getUniqueId() + "] timed out while verifying.");
                        getLogger().info("Successfully dispatched command /" + getConfig().getString("cmd-fail-prefix") + player.getName() + getConfig().getString("cmd-fail-suffix") + " with sender \"Console\".");
                        return;
                    }
                }
                countdown--;
            }
        }.runTaskTimer(this, 0, 20);


    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        return (int)(Math.random() * ((max - min) + 1)) + min;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInventoryClick(final InventoryClickEvent e) {
        Inventory invee = e.getInventory();
        //fix console spam
        if (invee == null) {
            return;
        }
        //if (invee == inv) {
        //    e.setCancelled(true);
        //}
        if (e.getView().getTitle().equals("" + ChatColor.translateAlternateColorCodes('&', getConfig().getString("gui-title")))) {
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();
            ItemStack item = e.getCurrentItem();
            //Success
            if (item.getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
                map.put(player, 0);
                player.closeInventory();
                player.sendMessage("" + ChatColor.translateAlternateColorCodes('&', getConfig().getString("pass-msg")));
            }
            //Fail
            if (item.getType().equals(Material.RED_STAINED_GLASS_PANE)) {
                player.closeInventory();
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "" + getConfig().getString("cmd-fail-prefix") + player.getName() + getConfig().getString("cmd-fail-suffix"));
                getLogger().warning("Player with uuid [" + player.getUniqueId() + "] failed the captcha.");
                getLogger().info("Successfully dispatched command /" + getConfig().getString("cmd-fail-prefix") + player.getName() + getConfig().getString("cmd-fail-suffix") + " with sender \"Console\".");
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryClose(final InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
        if (map.containsKey(player)) {
            return;
        } else {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "" + getConfig().getString("cmd-fail-prefix") + player.getName() + getConfig().getString("cmd-fail-suffix"));
            getLogger().warning("Player with uuid [" + player.getUniqueId() + "] closed the captcha inventory but is not verified.");
            getLogger().info("Successfully dispatched command /" + getConfig().getString("cmd-fail-prefix") + player.getName() + getConfig().getString("cmd-fail-suffix") + " with sender \"Console\".");
        }
    }

    @EventHandler
    public void onLogout(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        if (map.containsKey(player)) {
            map.remove(player);
        }
    }
}
