package com.kraken.gunsmith;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.minecraft.server.v1_12_R1.NBTTagCompound;

public class ItemSmith {

	String language;
	ArrayList<String> guns = new ArrayList<String>();
	
	public ItemSmith(String language) {
		
          this.language = language;
          guns.addAll( Arrays.asList("pistol", "sniper", "br", "lmg", "bow", "rocketLauncher", "shotgun", "ar", "hmg") );
          
    }
	
	public ItemStack makeItem(Material m, String name, String desc, int amount, Integer data, boolean unbreakable) {
		
		net.minecraft.server.v1_12_R1.ItemStack nmsItem;
		
	    //Gets rid of durability
      	nmsItem = CraftItemStack.asNMSCopy(new ItemStack(m, amount));
      	NBTTagCompound tag = new NBTTagCompound(); //Create the NMS Stack's NBT (item data)
      	tag.setBoolean("Unbreakable", unbreakable); //Set unbreakable value to true
      	nmsItem.setTag(tag); //Apply the tag to the item
      	ItemStack item = CraftItemStack.asCraftMirror(nmsItem); //Get the bukkit version of the stack
		
    	//Create the item's meta data (name, lore/desc text, etc.)
      	item.setDurability( (short) data.shortValue() );
    	ItemMeta im = item.getItemMeta();
    	im.setDisplayName(name);
    	//Creates the lore
    	ArrayList<String> lore = new ArrayList<String>();
    	lore.add(desc);
    	im.setLore(lore);
    	//Hides the vanilla Minecraft tooltip text
    	im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    	im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	//Sets the item's meta data to the custom "im" meta data
    	item.setItemMeta(im);
    	
    	return item;
		
	}
	
	public ItemStack makeGun(String gun, int amount) {
		
		Material m = Material.DIAMOND_HOE;
		String name = "Null";
		String desc = ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Firearm";
		int data = 0;
		
		if (gun.equalsIgnoreCase("pistol")) { 
			name = ChatColor.WHITE + "" + ChatColor.BOLD + "[PISTOL]";
			data = 601;
		} else if (gun.equalsIgnoreCase("sniperRifle") || gun.equalsIgnoreCase("sniper")) {
			name = ChatColor.AQUA + "" + ChatColor.BOLD + "[SNIPER RIFLE]";
			data = 602;
		} else if (gun.equalsIgnoreCase("battleRifle") || gun.equalsIgnoreCase("br")) {
			name = ChatColor.GREEN + "" + ChatColor.BOLD + "[BATTLE RIFLE]";
			data = 603;
		} else if (gun.equalsIgnoreCase("lightMachineGun") || gun.equalsIgnoreCase("lmg")) {
			name = ChatColor.AQUA + "" + ChatColor.BOLD + "[LIGHT MACHINE GUN]";
			data = 604;
		} else if (gun.equalsIgnoreCase("crossbow") || gun.equalsIgnoreCase("bow")) {
			name = ChatColor.GREEN + "" + ChatColor.BOLD + "[CROSSBOW]";
			desc = ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Ranged Weapon";
			data = 605;
		} else if (gun.equalsIgnoreCase("hammerOfDawn") || gun.equalsIgnoreCase("orbital")) {
			name = ChatColor.GOLD + "" + ChatColor.BOLD + "[HAMMER OF DAWN]";
			desc = ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Weapon of Mass Destruction";
			data = 606;
		} else if (gun.equalsIgnoreCase("rocketLauncher")) {
			name = ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "[ROCKET LAUNCHER]";
			data = 607;
		} else if (gun.equalsIgnoreCase("shotgun")) {
			name = ChatColor.GREEN + "" + ChatColor.BOLD + "[SHOTGUN]";
			data = 608;
		} else if (gun.equalsIgnoreCase("assaultRifle") || gun.equalsIgnoreCase("ar")) {
			name = ChatColor.GREEN + "" + ChatColor.BOLD + "[ASSAULT RIFLE]";
			data = 609;
		} else if (gun.equalsIgnoreCase("heavyMachineGun") || gun.equalsIgnoreCase("hmg")) {
			name = ChatColor.GREEN + "" + ChatColor.BOLD + "[HEAVY MACHINE GUN]";
			data = 610;
		}
	
    	return makeItem(m, name, desc, amount, data, true);
		
	}
	
	public boolean giveGun(String[] args, Player player) {
		
    	ItemStack gunItem = makeGun("pistol", 1);
    	
    	if ( args[0].equalsIgnoreCase("sniper") ) {
    		
        	gunItem = makeGun("sniperRifle", 1);
        	
    	} else if ( args[0].equalsIgnoreCase("br") || args[0].equalsIgnoreCase("battleRifle") ) {
    		
        	gunItem = makeGun("battleRifle", 1);
        	
    	} else if ( args[0].equalsIgnoreCase("lmg") || args[0].equalsIgnoreCase("lightMachineGun") ) {
    		
        	gunItem = makeGun("lightMachineGun", 1);
        	
    	} else if ( args[0].equalsIgnoreCase("bow") || args[0].equalsIgnoreCase("crossbow") ) {
    		
    		gunItem = makeGun("crossbow", 1);
        	
    	} else if ( args[0].equalsIgnoreCase("rpg") ) {
    		
        	gunItem = makeGun("rocketLauncher", 1);
        	
    	} else if ( args[0].equalsIgnoreCase("orbital") || args[0].equalsIgnoreCase("hammerOfDawn") ) {
    		
        	gunItem = makeGun("hammerOfDawn", 1);
        	
    	} else if ( args[0].equalsIgnoreCase("shotgun") ) {
    		
    		gunItem = makeGun("shotgun", 1);
        	
    	} else if ( args[0].equalsIgnoreCase("ar") || args[0].equalsIgnoreCase("assaultRifle") ) {
    		
        	gunItem = makeGun("assaultRifle", 1);
        	
    	} else if ( args[0].equalsIgnoreCase("hmg") || args[0].equalsIgnoreCase("heavyMachineGun") ) {
    		
        	gunItem = makeGun("heavyMachineGun", 1);
        	
    	}
    	
    	new Messages(language).makeMsg(player, "cmdGiveGun");
        player.getInventory().addItem(new ItemStack(gunItem));
        
        return true;
		
	}
	
	public ItemStack makeAmmo(String ammo, int amount) {
		
		Material m = Material.MELON_SEEDS;
		String name = "";
		String ammoFor = "";
		
		if (ammo.equals("sniperAmmo") || ammo.equals("sniper")) {
			name = ChatColor.AQUA + "" + ChatColor.BOLD + "[SNIPER AMMO]";
			ammoFor = "Sniper Rifle";
		} else if (ammo.equals("brAmmo") || ammo.equals("br")) {
			name = ChatColor.GREEN + "" + ChatColor.BOLD + "[BR AMMO]";
			ammoFor = "Battle Rifle";
		} else if (ammo.equals("lmgAmmo") || ammo.equals("lmg")) {
			name = ChatColor.AQUA + "" + ChatColor.BOLD + "[LMG AMMO]";
			ammoFor = "LMG";
		} else if (ammo.equals("pistolAmmo") || ammo.equals("pistol")) {
			name = ChatColor.WHITE + "" + ChatColor.BOLD + "[PISTOL AMMO]";
			ammoFor = "Pistol";
		} else if (ammo.equals("crossbowAmmo") || ammo.equals("bow")) {
			m = Material.ARROW;
			name = ChatColor.GREEN + "" + ChatColor.BOLD + "[CROSSBOW BOLTS]";
			ammoFor = "Crossbow";
		} else if (ammo.equals("rpgAmmo") || ammo.equals("rocketLauncher")) {
			m = Material.FIREWORK;
			name = ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "[LIGHT WARHEAD]";
			ammoFor = "Rocket Launcher";
		} else if (ammo.equals("shotgunAmmo") || ammo.equals("shotgun")) {
			name = ChatColor.GREEN + "" + ChatColor.BOLD + "[SHOTGUN SHELLS]";
			ammoFor = "Shotgun";
		} else if (ammo.equals("arAmmo") || ammo.equals("ar")) {
			name = ChatColor.GREEN + "" + ChatColor.BOLD + "[AR AMMO]";
			ammoFor = "Assault Rifle";
		} else if (ammo.equals("hmgAmmo") || ammo.equals("hmg")) {
			name = ChatColor.GREEN + "" + ChatColor.BOLD + "[HMG AMMO]";
			ammoFor = "HMG";
		}
	
		String desc = ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Ammunition | " + ammoFor;
    	return makeItem(m, name, desc, amount, 0, true);
		
	}
	
	public boolean giveAmmo(String arg, Player player) {
		
    	ItemStack ammoItem;
    	
    	if ( arg.equalsIgnoreCase("sniper") ) {
    		
    		ammoItem = makeAmmo("sniperAmmo", 1);
        	
    	} else if ( arg.equalsIgnoreCase("br") || arg.equalsIgnoreCase("battleRifle") ) {
    		
    		ammoItem = makeAmmo("brAmmo", 1);
        	
    	} else if ( arg.equalsIgnoreCase("lmg") || arg.equalsIgnoreCase("lightMachineGun") ) {
    		
    		ammoItem = makeAmmo("lmgAmmo", 1);
        	
    	} else if ( arg.equalsIgnoreCase("pistol") ) {
    		
    		ammoItem = makeAmmo("pistolAmmo", 1);	
    		
    	} else if ( arg.equalsIgnoreCase("bow")  || arg.equalsIgnoreCase("crossbow") ) {
    		
    		ammoItem = makeAmmo("crossbowAmmo", 1);	
    		
		} else if ( arg.equalsIgnoreCase("rpg") || arg.equalsIgnoreCase("rocketLauncher") ) {
    	
    		ammoItem = makeAmmo("rpgAmmo", 1);
        	
		} else if ( arg.equalsIgnoreCase("shotgun") ) {
    		
    		ammoItem = makeAmmo("shotgunAmmo", 1);	
    		
    	} else if ( arg.equalsIgnoreCase("ar") || arg.equalsIgnoreCase("assaultRifle") ) {
    		
    		ammoItem = makeAmmo("arAmmo", 1);	
    		
		} else if ( arg.equalsIgnoreCase("hmg") || arg.equalsIgnoreCase("heavyMachineGun") ) {
    		
    		ammoItem = makeAmmo("hmgAmmo", 1);
        	
    	} else {
			
			return false;
			
		}
    	
    	//Put ammo into inventory
    	for (int i = 0; i < 64; i++) {
    		player.getInventory().addItem(new ItemStack(ammoItem));
    	}
        
    	new Messages(language).makeMsg(player, "cmdGiveAmmo");
        return true;
		
	}
	
	public ItemStack makePart(String part, int amount) {
		
		Material m = Material.DIAMOND_HOE;
		String name = "";
		String desc = ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Firearms Part";
		int data = 0;
		
		if (part.equals("casing")) {
			name = ChatColor.WHITE + "" + ChatColor.BOLD + "[CASING]";
			data = 651;
		} else if (part.equals("smallStock")) {
			name = ChatColor.WHITE + "" + ChatColor.BOLD + "[SMALL STOCK]";
			data = 652;
		} else if (part.equals("largeStock")) {
			name = ChatColor.WHITE + "" + ChatColor.BOLD + "[LARGE STOCK]";
			data = 653;
		} else if (part.equals("shortBarrel")) {
			name = ChatColor.WHITE + "" + ChatColor.BOLD + "[SHORT BARREL]";
			data = 654;
		} else if (part.equals("mediumBarrel")) {
			name = ChatColor.WHITE + "" + ChatColor.BOLD + "[MEDIUM BARREL]";
			data = 655;
		} else if (part.equals("longBarrel")) {
			name = ChatColor.WHITE + "" + ChatColor.BOLD + "[LONG BARREL]";
			data = 656;
		} else if (part.equals("chamber")) {
			name = ChatColor.WHITE + "" + ChatColor.BOLD + "[CHAMBER]";
			data = 657;
		} else if (part.equals("muzzle")) {
			name = ChatColor.WHITE + "" + ChatColor.BOLD + "[MUZZLE]";
			data = 658;
		} else if (part.equals("crossbowStock")) {
			name = ChatColor.WHITE + "" + ChatColor.BOLD + "[CROSSBOW STOCK]";
			desc = ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Crossbow Part";
			data = 659;
		}
		
		return makeItem(m, name, desc, amount, data, true);
		
	}
	
}
