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

	public ItemSmith() {
          
    }
	
	public ItemStack makeItem(Material m, String name, String desc) {
		
	    //Gets rid of durability
      	net.minecraft.server.v1_9_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(new ItemStack(m));
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
	
	public ItemStack makeGun(Player player, String gun) {
		
		Material m;
		String name;
		String desc = ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Firearm";
		
		if (gun.equals("sniperRifle")) {
			m = Material.FEATHER;
			name = ChatColor.AQUA + "" + ChatColor.BOLD + "[SNIPER RIFLE]";
		} else if (gun.equals("battleRifle")) {
			m = Material.WOOD_HOE;
			name = ChatColor.GREEN + "" + ChatColor.BOLD + "[BATTLE RIFLE]";
		} else if (gun.equals("lightMachineGun")) {
			m = Material.DIAMOND_PICKAXE;
			name = ChatColor.AQUA + "" + ChatColor.BOLD + "[LIGHT MACHINE GUN]";
		} else {
			m = Material.GOLD_AXE;
			name = ChatColor.WHITE + "" + ChatColor.BOLD + "[PISTOL]";
		}
	
    	return makeItem(m, name, desc);
		
	}
	
	public boolean giveGun(String[] args, Player player) {
		
    	ItemStack gunItem;
    	
    	if (args.length == 1 && args[0].equalsIgnoreCase("sniper")) {
    		
        	gunItem = makeGun(player, "sniperRifle");
        	
    	} else if (args.length == 1 && ( args[0].equalsIgnoreCase("br") || args[0].equalsIgnoreCase("battleRifle") ) ) {
    		
        	gunItem = makeGun(player, "battleRifle");
        	
    	} else if (args.length == 1 && ( args[0].equalsIgnoreCase("lmg") || args[0].equalsIgnoreCase("lightMachineGun") ) ) {
    		
        	gunItem = makeGun(player, "lightMachineGun");
        	
    	} else {
    		
    		gunItem = makeGun(player, "pistol");	
    		
		}
    	
        player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Pew pew!");
        player.getInventory().addItem(new ItemStack(gunItem));
        
        return true;
		
	}
	
	public ItemStack makeArmor(Player player, String armor) {
		
		Material m;
		String name;
		String desc = ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Armor";
		
		if (armor.equals("pvtHelm")) {
			m = Material.DIAMOND_HELMET;
			name = ChatColor.GREEN + "" + ChatColor.BOLD + "[PVT. HELMET]";
		} else if (armor.equals("pvtChest")) {
			m = Material.DIAMOND_CHESTPLATE;
			name = ChatColor.GREEN + "" + ChatColor.BOLD + "[PVT. BODY ARMOR]";
		} else if (armor.equals("pvtLegs")) {
			m = Material.DIAMOND_LEGGINGS;
			name = ChatColor.GREEN + "" + ChatColor.BOLD + "[PVT. LEG ARMOR]";
		} else if (armor.equals("pvtBoots")) {
			m = Material.DIAMOND_BOOTS;
			name = ChatColor.GREEN + "" + ChatColor.BOLD + "[PVT. BOOTS]";
		} else {
			m = Material.DIAMOND_HELMET;
			name = ChatColor.GREEN + "" + ChatColor.BOLD + "[PVT. HELMET]";
			
		}
	
		return makeItem(m, name, desc);
		
	}
	
	public ItemStack makeGrenade(Player player, String grenade) {
		
		Material m;
		String name;
		String desc = ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Equipment";
		
		if (grenade.equals("fragNade")) {
			m = Material.MAGMA_CREAM;
			name = ChatColor.GRAY + "" + ChatColor.BOLD + "[FRAG GRENADE]";
			
		} else {
			m = Material.MAGMA_CREAM;
			name = ChatColor.GRAY + "" + ChatColor.BOLD + "[FRAG GRENADE]";
		}
		
		return makeItem(m, name, desc);
		
	}
	
}
