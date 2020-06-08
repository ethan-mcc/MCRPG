package me.ethan.classcontrol;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class EventsClass implements Listener {

    @EventHandler
    public void onJoin (PlayerJoinEvent p)
    {
        Player pS = (Player) p.getPlayer();
        //Location loc = new Location(Bukkit.getWorld("SelectWorld"), 0.5,1,0.5);
        //pS.teleport(loc);
        PlayerInventory inv = pS.getInventory();
        inv.clear();

        ItemStack stats = ClassControl.getPlugin(ClassControl.class).getConfig().getItemStack("SpawnItems.stats");
        inv.setItem(4, stats);
        pS.sendMessage(" " + stats);

    }

    /*public void onDeath (PlayerDeathEvent death) {
        Player playerD = (Player) death.getEntity();
        death.setDeathMessage(ChatColor.RED + playerD.getName() + ChatColor.GRAY + " decided to end it all...");
        Location loc = new Location(Bukkit.getWorld("SelectWorld"), 0.5,1,0.5);
        playerD.teleport(loc);
    }
    /*/

    /*public static class ItemDeserialiser {
        ItemStack deserialise(Map<String, Object> map) {
            map.computeIfPresent("meta", ($, serialised) -> (ItemMeta) ConfigurationSerialization.deserializeObject((Map<String, Object>) serialised));
            return ItemStack.deserialize(map);
        }
    }/*/
}
