package me.ethan.mobcontrol;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public class MobControl extends JavaPlugin {

    public NamespacedKey levelKey;
    public NamespacedKey isLevelledKey;

    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "MobControl has been enabled!");
        getServer().getPluginManager().registerEvents(new EventsClass(this), this);
    }
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "MobControl has been disabled!");
    }

}
