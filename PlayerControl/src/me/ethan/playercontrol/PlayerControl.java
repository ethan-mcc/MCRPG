package me.ethan.playercontrol;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerControl extends JavaPlugin {

    private Commands commands = new Commands();

    public void onEnable() {
        getCommand(commands.cmd1).setExecutor(commands);
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "PlayerControl has been enabled!");
        getServer().getPluginManager().registerEvents(new EventsClass(), this);
    }

    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "PlayerControl has been disabled!");
    }

}
