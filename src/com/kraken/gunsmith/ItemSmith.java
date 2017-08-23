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
		String name = ChatColor.WHITE + "" + ChatColor.BOLD + "[GUN]";
		String desc = ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Firearm";
		int data = 601;
		
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
		} else if (gun.equalsIgnoreCase("rocketLauncher") || gun.equalsIgnoreCase("rpg")) {
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
	
	public ItemStack makeCustomGun(int dataId) {
		
		Material m = Material.DIAMOND_HOE;
		String name = ChatColor.WHITE + "" + ChatColor.BOLD + "[CUSTOM GUN]";
		String desc = ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Firearm";
		int amount = 1;
		
		//Data values represented by custom guns: 701-799
		int data = dataId + 700;
		
		if (dataId < 1 || dataId > 99) {
			return makeGun("pistol", amount);
		}
		
		// In custom.yml, parent node = dataId & children = attributes
		// Assign dmg, range, cooldown variables
		
		// Create guns.yml for regular guns, as well
		// Change GSListener, GunShot, etc. classes to reference yml for stats instead
	
    	return makeItem(m, name, desc, amount, data, true);
		
	}
	
	public boolean giveGun(String gun, Player player, int amount) {
		
    	ItemStack gunItem = makeGun(gun, amount);
        player.getInventory().addItem(new ItemStack(gunItem));
        return true;
		
	}
	
	public ItemStack makeAmmo(String ammo, int amount) {
		
		Material m = Material.MELON_SEEDS;
		String name = "";
		String ammoFor = "";
		
		if (ammo.equalsIgnoreCase("sniperAmmo") || ammo.equalsIgnoreCase("sniper")) {
			name = ChatColor.AQUA + "" + ChatColor.BOLD + "[SNIPER AMMO]";
			ammoFor = "Sniper Rifle";
		} else if (ammo.equalsIgnoreCase("brAmmo") || ammo.equalsIgnoreCase("br")) {
			name = ChatColor.GREEN + "" + ChatColor.BOLD + "[BR AMMO]";
			ammoFor = "Battle Rifle";
		} else if (ammo.equalsIgnoreCase("lmgAmmo") || ammo.equalsIgnoreCase("lmg")) {
			name = ChatColor.AQUA + "" + ChatColor.BOLD + "[LMG AMMO]";
			ammoFor = "LMG";
		} else if (ammo.equalsIgnoreCase("pistolAmmo") || ammo.equalsIgnoreCase("pistol")) {
			name = ChatColor.WHITE + "" + ChatColor.BOLD + "[PISTOL AMMO]";
			ammoFor = "Pistol";
		} else if (ammo.equalsIgnoreCase("crossbowAmmo") || ammo.equalsIgnoreCase("bow")) {
			m = Material.ARROW;
			name = ChatColor.GREEN + "" + ChatColor.BOLD + "[CROSSBOW BOLTS]";
			ammoFor = "Crossbow";
		} else if (ammo.equalsIgnoreCase("rpgAmmo") || ammo.equalsIgnoreCase("rocketLauncher")) {
			m = Material.FIREWORK;
			name = ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "[LIGHT WARHEAD]";
			ammoFor = "Rocket Launcher";
		} else if (ammo.equalsIgnoreCase("shotgunAmmo") || ammo.equalsIgnoreCase("shotgun")) {
			name = ChatColor.GREEN + "" + ChatColor.BOLD + "[SHOTGUN SHELLS]";
			ammoFor = "Shotgun";
		} else if (ammo.equalsIgnoreCase("arAmmo") || ammo.equalsIgnoreCase("ar")) {
			name = ChatColor.GREEN + "" + ChatColor.BOLD + "[AR AMMO]";
			ammoFor = "Assault Rifle";
		} else if (ammo.equalsIgnoreCase("hmgAmmo") || ammo.equalsIgnoreCase("hmg")) {
			name = ChatColor.GREEN + "" + ChatColor.BOLD + "[HMG AMMO]";
			ammoFor = "HMG";
		}
	
		String desc = ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Ammunition | " + ammoFor;
    	return makeItem(m, name, desc, amount, 0, true);
		
	}
	
	public boolean giveAmmo(String gun, Player player, int amount) {
		
    	ItemStack ammoItem = makeAmmo(gun, amount);
    	
    	//Put ammo into inventory
    	for (int i = 0; i < amount; i++) {
    		player.getInventory().addItem(new ItemStack(ammoItem));
    	}
    	
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
	
	public ItemStack makeArmor(String armor) {
		
		Material m;
		String name;
		String desc = ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Armor";
		
		if (armor.equalsIgnoreCase("pvtHelm")) {
			m = Material.IRON_HELMET;
			name = ChatColor.GREEN + "" + ChatColor.BOLD + "[PVT. HELMET]";
		} else if (armor.equalsIgnoreCase("pvtChest")) {
			m = Material.IRON_CHESTPLATE;
			name = ChatColor.GREEN + "" + ChatColor.BOLD + "[PVT. BODY ARMOR]";
		} else if (armor.equalsIgnoreCase("pvtLegs")) {
			m = Material.IRON_LEGGINGS;
			name = ChatColor.GREEN + "" + ChatColor.BOLD + "[PVT. LEG ARMOR]";
		} else if (armor.equalsIgnoreCase("pvtBoots")) {
			m = Material.IRON_BOOTS;
			name = ChatColor.GREEN + "" + ChatColor.BOLD + "[PVT. BOOTS]";
		} else {
			m = Material.IRON_HELMET;
			name = ChatColor.GREEN + "" + ChatColor.BOLD + "[PVT. HELMET]";
			
		}
	
		return makeItem(m, name, desc, 1, 0, false);
		
	}
	
	public ItemStack makeGrenade(String grenade) {
		
		Material m = Material.MAGMA_CREAM;
		String name = ChatColor.GREEN + "" + ChatColor.BOLD + "[FRAG GRENADE]";
		String desc = ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Equipment | ";
		
		if (grenade.equals("frag")) {
			m = Material.MAGMA_CREAM;
			name = ChatColor.GREEN + "" + ChatColor.BOLD + "[FRAG GRENADE]";
			desc = desc + "Frag Grenade";
		}
		
		return makeItem(m, name, desc, 4, 0, true);
	
	}
	
}
