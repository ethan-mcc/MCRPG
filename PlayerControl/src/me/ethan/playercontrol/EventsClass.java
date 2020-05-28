package me.ethan.playercontrol;

import org.bukkit.*;
import org.bukkit.block.Chest;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EventsClass implements Listener {


    @EventHandler
    //Player Death Handler
    public void onPlayerDeath (PlayerDeathEvent e) {
        Player p = (Player) e.getEntity();
        Location loc = p.getLocation();
        //build chest
        loc.add(0,-1,0);
        loc.getWorld().getBlockAt(loc).setType(Material.CHEST);
        //----
        Chest c = (Chest) loc.getWorld().getBlockAt(loc).getState();
        //get date time of server
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate now = LocalDate.now();
        //set custom name of chest
        c.setCustomName(ChatColor.RED + p.getDisplayName() + "'s" + ChatColor.GRAY + " grave site " + ChatColor.DARK_GRAY + dtf.format(now));
        //update chest with name
        c.update();
        //fill chest with items from dead player inventory
        for(ItemStack drops : e.getDrops()){
            c.getBlockInventory().addItem(drops);
        }
        //build grave
        loc.add(0,1,0);
        loc.getBlock().setType(Material.STONE_BRICK_SLAB);
        loc.add(0,1,0);
        loc.getBlock().setType(Material.STONE_BRICK_WALL);
        //clear items that dropped from dead player
        e.getDrops().clear();
        //send message to player who died
        p.sendMessage(ChatColor.RED + "You have been killed by " + ChatColor.GRAY + p.getKiller().getName() + ChatColor.RED + " :(");
        //send message to killer
        p.getKiller().sendMessage(ChatColor.RED + "You have killed " + ChatColor.GRAY + p.getName() + ChatColor.RED + " collect their loot in the chest!");

        //create particle effect
        for(int i = 0; i < 5; i++) {
            loc.getWorld().spawnParticle(Particle.TOTEM, loc, 25);
        }
    }

}
