package com.kraken.gunsmith;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_9_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.minecraft.server.v1_9_R1.NBTTagCompound;

public class ItemSmith {

	String language;
	
	public ItemSmith(String language) {
          this.language = language;
    }
	
	public ItemStack makeItem(Material m, String name, String desc, int amount, byte data) {
		
		net.minecraft.server.v1_9_R1.ItemStack nmsItem;
		
	    //Gets rid of durability
		if (data == (byte) 0) {
      		nmsItem = CraftItemStack.asNMSCopy(new ItemStack(m, amount, data));
		} else {
			nmsItem = CraftItemStack.asNMSCopy(new ItemStack(m, amount));
		}
      	NBTTagCompound tag = new NBTTagCompound(); //Create the NMS Stack's NBT (item data)
      	tag.setBoolean("Unbreakable", true); //Set unbreakable value to true
      	nmsItem.setTag(tag); //Apply the tag to the item
      	ItemStack item = CraftItemStack.asCraftMirror(nmsItem); //Get the bukkit version of the stack
		
    	//Create the item's meta data (name, lore/desc text, etc.)
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
		
		Material m;
		String name;
		String desc = ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Firearm";
		byte data = (byte) 0;
		
		if (gun.equalsIgnoreCase("sniperRifle")) {
			m = Material.FEATHER;
			name = ChatColor.AQUA + "" + ChatColor.BOLD + "[SNIPER RIFLE]";
		} else if (gun.equalsIgnoreCase("battleRifle")) {
			m = Material.WOOD_HOE;
			name = ChatColor.GREEN + "" + ChatColor.BOLD + "[BATTLE RIFLE]";
		} else if (gun.equalsIgnoreCase("lightMachineGun")) {
			m = Material.DIAMOND_PICKAXE;
			name = ChatColor.AQUA + "" + ChatColor.BOLD + "[LIGHT MACHINE GUN]";
		} else if (gun.equalsIgnoreCase("crossbow")) {
			m = Material.FLINT;
			name = ChatColor.GREEN + "" + ChatColor.BOLD + "[CROSSBOW]";
			desc = ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Ranged Weapon";
		} else if (gun.equalsIgnoreCase("rocketLauncher")) {
			m = Material.DIAMOND_SPADE;
			name = ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "[ROCKET LAUNCHER]";
		} else if (gun.equalsIgnoreCase("hammerOfDawn")) {
			m = Material.DIAMOND_AXE;
			name = ChatColor.GOLD + "" + ChatColor.BOLD + "[HAMMER OF DAWN]";
			desc = ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Weapon of Mass Destruction";
		} else if (gun.equalsIgnoreCase("shotgun")) {
			m = Material.WOOD_SPADE;
			name = ChatColor.GREEN + "" + ChatColor.BOLD + "[SHOTGUN]";
		} else if (gun.equalsIgnoreCase("assaultRifle")) {
			m = Material.WOOD_PICKAXE;
			name = ChatColor.GREEN + "" + ChatColor.BOLD + "[ASSAULT RIFLE]";
		} else if (gun.equalsIgnoreCase("heavyMachineGun")) {
			m = Material.GOLD_PICKAXE;
			name = ChatColor.GREEN + "" + ChatColor.BOLD + "[HEAVY MACHINE GUN]";
		} else {
			m = Material.GOLD_AXE;
			name = ChatColor.WHITE + "" + ChatColor.BOLD + "[PISTOL]";
		}
	
    	return makeItem(m, name, desc, amount, data);
		
	}
	
	public boolean giveGun(String[] args, Player player) {
		
    	ItemStack gunItem;
    	
    	if ( args[0].equalsIgnoreCase("sniper") ) {
    		
        	gunItem = makeGun("sniperRifle", 1);
        	
    	} else if ( args[0].equalsIgnoreCase("br") || args[0].equalsIgnoreCase("battleRifle") ) {
    		
        	gunItem = makeGun("battleRifle", 1);
        	
    	} else if ( args[0].equalsIgnoreCase("lmg") || args[0].equalsIgnoreCase("lightMachineGun") ) {
    		
        	gunItem = makeGun("lightMachineGun", 1);
        	
    	} else if ( args[0].equalsIgnoreCase("bow") ) {
    		
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
        	
    	} else {
    		
    		gunItem = makeGun("pistol", 1);	
    		
		}
    	
    	new Messages(language).makeMsg(player, "cmdGiveGun");
        player.getInventory().addItem(new ItemStack(gunItem));
        
        return true;
		
	}
	
	public ItemStack makeAmmo(String ammo, int amount) {
		
		Material m = Material.MELON_SEEDS;
		String name = "";
		String ammoFor = "";
		
		if (ammo.equals("sniperAmmo")) {
			name = ChatColor.AQUA + "" + ChatColor.BOLD + "[SNIPER AMMO]";
			ammoFor = "Sniper Rifle";
		} else if (ammo.equals("brAmmo")) {
			name = ChatColor.GREEN + "" + ChatColor.BOLD + "[BR AMMO]";
			ammoFor = "Battle Rifle";
		} else if (ammo.equals("lmgAmmo")) {
			name = ChatColor.AQUA + "" + ChatColor.BOLD + "[LMG AMMO]";
			ammoFor = "LMG";
		} else if (ammo.equals("pistolAmmo")) {
			name = ChatColor.WHITE + "" + ChatColor.BOLD + "[PISTOL AMMO]";
			ammoFor = "Pistol";
		} else if (ammo.equals("crossbowAmmo")) {
			m = Material.ARROW;
			name = ChatColor.GREEN + "" + ChatColor.BOLD + "[CROSSBOW BOLTS]";
			ammoFor = "Crossbow";
		} else if (ammo.equals("rpgAmmo")) {
			m = Material.FIREWORK;
			name = ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "[LIGHT WARHEAD]";
			ammoFor = "Rocket Launcher";
		} else if (ammo.equals("shotgunAmmo")) {
			name = ChatColor.GREEN + "" + ChatColor.BOLD + "[SHOTGUN SHELLS]";
			ammoFor = "Shotgun";
		} else if (ammo.equals("arAmmo")) {
			name = ChatColor.GREEN + "" + ChatColor.BOLD + "[AR AMMO]";
			ammoFor = "Assault Rifle";
		} else if (ammo.equals("hmgAmmo")) {
			name = ChatColor.GREEN + "" + ChatColor.BOLD + "[HMG AMMO]";
			ammoFor = "HMG";
		}
	
		String desc = ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Ammunition | " + ammoFor;
    	return makeItem(m, name, desc, amount, (byte) 0);
		
	}
	
	public boolean giveAmmo(String[] args, Player player) {
		
    	ItemStack ammoItem;
    	
    	if ( args[0].equalsIgnoreCase("sniper") ) {
    		
    		ammoItem = makeAmmo("sniperAmmo", 1);
        	
    	} else if ( args[0].equalsIgnoreCase("br") || args[0].equalsIgnoreCase("battleRifle") ) {
    		
    		ammoItem = makeAmmo("brAmmo", 1);
        	
    	} else if ( args[0].equalsIgnoreCase("lmg") || args[0].equalsIgnoreCase("lightMachineGun") ) {
    		
    		ammoItem = makeAmmo("lmgAmmo", 1);
        	
    	} else if ( args[0].equalsIgnoreCase("pistol") ) {
    		
    		ammoItem = makeAmmo("pistolAmmo", 1);	
    		
    	} else if ( args[0].equalsIgnoreCase("bow") ) {
    		
    		ammoItem = makeAmmo("crossbowAmmo", 1);	
    		
		} else if ( args[0].equalsIgnoreCase("rpg") ) {
    		
    		ammoItem = makeAmmo("rpgAmmo", 1);
        	
		} else if ( args[0].equalsIgnoreCase("shotgun") ) {
    		
    		ammoItem = makeAmmo("shotgunAmmo", 1);	
    		
    	} else if ( args[0].equalsIgnoreCase("ar") || args[0].equalsIgnoreCase("assaultRifle") ) {
    		
    		ammoItem = makeAmmo("arAmmo", 1);	
    		
		} else if ( args[0].equalsIgnoreCase("hmg") || args[0].equalsIgnoreCase("heavyMachineGun") ) {
    		
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
		byte data = 0;
		
		if (part.equals("casing")) {
			name = ChatColor.WHITE + "" + ChatColor.BOLD + "[CASING]";
			data = (byte) 1001;
		} else if (part.equals("smallStock")) {
			name = ChatColor.WHITE + "" + ChatColor.BOLD + "[SMALL STOCK]";
			data = (byte) 1002;
		} else if (part.equals("largeStock")) {
			name = ChatColor.WHITE + "" + ChatColor.BOLD + "[LARGE STOCK]";
			data = (byte) 1003;
		} else if (part.equals("shortBarrel")) {
			m = Material.STONE_HOE;
			name = ChatColor.WHITE + "" + ChatColor.BOLD + "[SHORT BARREL]";
			data = (byte) 101;
		} else if (part.equals("mediumBarrel")) {
			m = Material.IRON_HOE;
			name = ChatColor.WHITE + "" + ChatColor.BOLD + "[MEDIUM BARREL]";
			data = (byte) 201;
		} else if (part.equals("longBarrel")) {
			m = Material.GOLD_HOE;
			name = ChatColor.WHITE + "" + ChatColor.BOLD + "[LONG BARREL]";
			data = (byte) 31;
		} else if (part.equals("chamber")) {
			name = ChatColor.WHITE + "" + ChatColor.BOLD + "[CHAMBER]";
			data = (byte) 1007;
		} else if (part.equals("muzzle")) {
			name = ChatColor.WHITE + "" + ChatColor.BOLD + "[MUZZLE]";
			data = (byte) 1008;
		} else if (part.equals("crossbowStock")) {
			name = ChatColor.WHITE + "" + ChatColor.BOLD + "[CROSSBOW STOCK]";
			desc = ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Crossbow Part";
			data = (byte) 1009;
		}
		
		return makeItem(m, name, desc, amount, data);
		
	}
	
}
