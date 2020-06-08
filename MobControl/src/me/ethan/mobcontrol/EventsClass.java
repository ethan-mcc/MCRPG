package me.ethan.mobcontrol;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.Main;
import org.bukkit.craftbukkit.v1_15_R1.metadata.EntityMetadataStore;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.LazyMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.persistence.PersistentDataType;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.DoubleToIntFunction;

public class EventsClass implements Listener
{
    public double level;

    private MobControl instance;

    public EventsClass(final MobControl instance) {
        this.instance = instance;
    }

    //Set METADATA, that MobControl plugin part is really important
    @EventHandler
    public void onEntitySpawn(final CreatureSpawnEvent e) {
        level = Math.random() * 5;
        final int Level = (int)level;
        LivingEntity livingEntity = e.getEntity();
        String isLevelled = livingEntity.getPersistentDataContainer().get(instance.isLevelledKey, PersistentDataType.STRING);
        final double baseMaxHealth = Objects.requireNonNull(e.getEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH)).getBaseValue();
        final double newMaxHealth = baseMaxHealth + (baseMaxHealth * (0.2D) * level);
        Objects.requireNonNull(livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(newMaxHealth);
        livingEntity.setHealth(newMaxHealth); //set there max health

        livingEntity.getPersistentDataContainer().set(instance.levelKey, PersistentDataType.INTEGER, Level);
        livingEntity.getPersistentDataContainer().set(instance.isLevelledKey, PersistentDataType.STRING, "true");

    }

    @EventHandler
    public void onEntityDeath (EntityDeathEvent e) {
        //the drop range 0-100
        double drop = Math.random() * 100;
        //for later use
        double enchan = Math.random();
        double dur = Math.random();
        //ZOMBIE
        if (e.getEntity() instanceof Zombie) {
            //type cast zombie
            Zombie z = (Zombie) e.getEntity();
            int Level = z.getPersistentDataContainer().get(instance.levelKey, PersistentDataType.INTEGER);
            //clear zombie drops
            e.getDrops().clear();
            //get world
            World w = z.getWorld();
            //drop under 15, 15%
            if(drop <= 15 && Level > 2) {
                String itemName = ChatColor.ITALIC + "" + ChatColor.BOLD + "" + ChatColor.RED + "Iron Bar";
                List<String> itemLore = Arrays.asList(ChatColor.DARK_GRAY + "Crafting Material", ChatColor.GRAY + "Made by thievin' ogres", ChatColor.GRAY + "at the great forges in the north.",
                        ChatColor.GREEN + "Uncommon");
                createItem((int) drop, itemName, itemLore, Material.IRON_INGOT, w, z);
            }
            //15%
            else if (drop <= 30) {
                String itemName = ChatColor.ITALIC + "" + ChatColor.BOLD + "" + ChatColor.RED + "Zombie Flesh";
                List<String> itemLore = Arrays.asList(ChatColor.DARK_GRAY + "Crafting Material", ChatColor.GRAY + "Rotten flesh of the undead", "",
                        ChatColor.DARK_GRAY + "Common");
                createItem((int) drop, itemName, itemLore, Material.LEATHER, w, z);
            }
            //greater than 50 less than 60 idk what the percentage would be lol
            else if (drop >= 50 && drop <= 60 && Level > 3) {
                drop = 0;
                String itemName = ChatColor.ITALIC + "" + ChatColor.BOLD + "" + ChatColor.GREEN + "Utility Blade";
                List<String> itemLore =  Arrays.asList(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Pitted Dull Cast Iron",
                        ChatColor.GRAY + "This ain't the cleanest nor the sharpest blade!", "", ChatColor.GREEN + "Uncommon");
                createItem((int) drop, itemName, itemLore, Material.IRON_SWORD, w ,z);
            }
            //if none of those return, and don't drop anything
            else {
                return;
            }
        }
        //SKELETON
        else if (e.getEntity() instanceof Skeleton) {
            Skeleton s = (Skeleton) e.getEntity();
            int Level = 0;
            e.getDrops().clear();
            World w = s.getWorld();
            if(drop <= 11 && Level > 3) {
                String itemName = ChatColor.ITALIC + "" + ChatColor.BOLD + "" + ChatColor.GREEN + "Oak Long Bow";
                List<String> itemLore = Arrays.asList(ChatColor.DARK_GRAY + "Range Weapon", ChatColor.GRAY + "Weak and not well built",
                        ChatColor.GRAY + "assembled hastily by unskilled labor." + "" + ChatColor.GREEN + "Common");
                createItem((int) drop, itemName, itemLore, Material.BOW, w, s);
            }
            else if (drop <= 30 && Level > 2) {
                String itemName = ChatColor.ITALIC + "" + ChatColor.BOLD + "" + ChatColor.RED + "Oak Arrows";
                List<String> itemLore = Arrays.asList(ChatColor.DARK_GRAY + "Consumable",
                        ChatColor.GRAY + "Expendable munition used in all bow types" + "" + ChatColor.GREEN + "Common");
                createItem((int) drop, itemName, itemLore, Material.ARROW, w, s);
            }
            else {
                return;
            }
        }
        //SPIDER
        else if (e.getEntity() instanceof Spider) {
            Spider sp = (Spider) e.getEntity();
            int Level = 0;
            e.getDrops().clear();
            World w = sp.getWorld();
            if (drop <= 10 ) {
                String itemName = ChatColor.ITALIC + "" + ChatColor.BOLD + "" + ChatColor.LIGHT_PURPLE + "Spider Cornea";
                List<String> itemLore = Arrays.asList(ChatColor.DARK_GRAY + "Potion Material", "",
                        ChatColor.GRAY + "Eyeball lens found on spiders", ChatColor.GRAY + "Useful with the right potion master.",
                        "", ChatColor.LIGHT_PURPLE + "RARE");
                createItem((int) drop, itemName, itemLore, Material.SPIDER_EYE, w, sp);
            }
            else if(drop <= 35) {
                String itemName = ChatColor.ITALIC + "" + ChatColor.BOLD + "" + ChatColor.RED + "Spider Silk";
                List<String> itemLore = Arrays.asList(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Useful material", "",
                        ChatColor.GRAY + "Used to make clothing, and various fabrics.", "", ChatColor.GREEN + "Uncommon");
                createItem((int) drop, itemName, itemLore, Material.STRING, w, sp);
            }
            else {
                return;
            }
        }
        //CREEPER (for later)
        else if (e.getEntity() instanceof Creeper) {
            Creeper c = (Creeper) e.getEntity();
            e.getDrops().clear();
        }
        else {
            return;
        }
    }


    @Deprecated
    @EventHandler
    //Create the health bar above entity's
    public void onEntityDamage(EntityDamageEvent e) {
            //ZOMBIE
            if (e.getEntity() instanceof Zombie) {
                Zombie en = (Zombie) e.getEntity();
                //METADATA get "zombie"
                int Level = en.getPersistentDataContainer().get(instance.levelKey, PersistentDataType.INTEGER);
                System.out.println(ChatColor.RED + " " + Level);
                String name = ChatColor.BOLD + "" + ChatColor.RED + "Necrosis";
                double maxHealth = en.getMaxHealth();
                double currentHealth = en.getHealth() - e.getDamage();
                createHealthBar(en, name, maxHealth, currentHealth, Level);
            }
            //SKELETON
            if (e.getEntity() instanceof Skeleton) {
                Skeleton en = (Skeleton) e.getEntity();
                //METADATA get "skeleton"
                int Level = 0;
                String name = ChatColor.BOLD + "" + ChatColor.RED + "Skeleton";
                double maxHealth = en.getMaxHealth();
                double currentHealth = en.getHealth() - e.getDamage();
                createHealthBar(en, name, maxHealth, currentHealth, Level);
            }
            //SPIDER
            if (e.getEntity() instanceof Spider) {
                Spider en = (Spider) e.getEntity();
                //METADATA get "spider"
                int Level = 0;
                String name = ChatColor.BOLD + "" + ChatColor.RED + "Araneae";
                double maxHealth = en.getMaxHealth();
                double currentHealth = en.getHealth() - e.getDamage();
                createHealthBar(en, name, maxHealth, currentHealth, Level);
        }
    }


    //Method to create the health bar for onEntityDamage
    private void createHealthBar(Entity en, String name, double maxHealth, double currentHealth, int Level) {
        //If less than 0, set to 0, no negative health values
        if ((int)currentHealth <= 0) currentHealth = 0;
        String healthbar = "";
        //Create lines for health bar
        for (int i = 0; i != (int)maxHealth; i++) {
            if(currentHealth > i)
                healthbar += ChatColor.RED + "|";
            else
                healthbar += ChatColor.DARK_GRAY + "|";
        }
        //Set custom name for health bar
        en.setCustomName(name + ChatColor.RED + " Lv." + ChatColor.RED + Level + " " + ChatColor.DARK_GRAY + healthbar + ChatColor.DARK_GRAY);
        en.setCustomNameVisible(true);
    }

    //Method to create items for on EntityDeath
    public void createItem(int drop, String itemName, List<String> itemLore, Material leather, World w, Entity e) {
        ItemStack dropItem = new ItemStack(leather, 1 + Math.round(drop / 69));
        ItemMeta itemMeta = dropItem.getItemMeta();
        itemMeta.setDisplayName(itemName);
        itemMeta.setLore(itemLore);
        dropItem.setItemMeta(itemMeta);
        w.dropItemNaturally(e.getLocation(), dropItem);
    }

}

