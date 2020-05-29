package me.ethan.mobcontrol;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class MobControl extends JavaPlugin {
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "MobControl has been enabled!");
        getServer().getPluginManager().registerEvents(new EventsClass(), this);
    }
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "MobControl has been disabled!");
    }
}
