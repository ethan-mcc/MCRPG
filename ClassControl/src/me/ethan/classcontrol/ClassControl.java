package me.ethan.classcontrol;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class ClassControl extends JavaPlugin {

    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "ClassControl has been enabled!");
        getServer().getPluginManager().registerEvents(new EventsClass(), this);
    }

    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "ClassControl has been disabled!");
    }

}
