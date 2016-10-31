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
	
	public ItemStack makeGun(Player player, String gun) {
		
		Material m;
		String name;
		String desc;
		
		if (gun.equals("sniperRifle")) {
			m = Material.FEATHER;
			name = ChatColor.GRAY + "" + ChatColor.BOLD + "[SNIPER RIFLE]";
			desc = ChatColor.DARK_GRAY + " " + ChatColor.ITALIC + "Human Firearm";
		} else if (gun.equals("battleRifle")) {
			m = Material.WOOD_HOE;
			name = ChatColor.GRAY + "" + ChatColor.BOLD + "[BATTLE RIFLE]";
			desc = ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Human Firearm";
		} else {
			m = Material.GOLD_AXE;
			name = ChatColor.GRAY + "" + ChatColor.BOLD + "[PISTOL]";
			desc = ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Human Firearm";
		}
	
      //Gets rid of durability

      	net.minecraft.server.v1_9_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(new ItemStack(m));
      	NBTTagCompound tag = new NBTTagCompound(); //Create the NMS Stack's NBT (item data)
      	tag.setBoolean("Unbreakable", true); //Set unbreakable value to true
      	nmsItem.setTag(tag); //Apply the tag to the item
      	ItemStack item = CraftItemStack.asCraftMirror(nmsItem); //Get the bukkit version of the stack
		
	  //Unarmed = 1 damage, 4.0 speed
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
	
	public boolean giveGun(String[] args, Player player) {
		
    	ItemStack gunItem;
    	
    	if (args.length == 1 && args[0].equalsIgnoreCase("sniper")) {
    		
        	gunItem = makeGun(player, "sniperRifle");
        	
    	} else if (args.length == 1 && ( args[0].equalsIgnoreCase("br") || args[0].equalsIgnoreCase("battleRifle") ) ) {
    		
        	gunItem = makeGun(player, "battleRifle");
        	
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
		String desc;
		
		if (armor.equals("pvtHelm")) {
			m = Material.DIAMOND_HELMET;
			name = ChatColor.GRAY + "" + ChatColor.BOLD + "[PRIVATE'S HELMET]";
			desc = ChatColor.DARK_GRAY + " " + ChatColor.ITALIC + "Human Armor";
		} else if (armor.equals("pvtChest")) {
			m = Material.DIAMOND_CHESTPLATE;
			name = ChatColor.GRAY + "" + ChatColor.BOLD + "[PRIVATE'S BODY ARMOR]";
			desc = ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Human Armor";
		} else if (armor.equals("pvtLegs")) {
			m = Material.DIAMOND_LEGGINGS;
			name = ChatColor.GRAY + "" + ChatColor.BOLD + "[PRIVATE'S LEG ARMOR]";
			desc = ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Human Armor";
		} else if (armor.equals("pvtBoots")) {
			m = Material.DIAMOND_BOOTS;
			name = ChatColor.GRAY + "" + ChatColor.BOLD + "[PRIVATE'S BOOTS]";
			desc = ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Human Armor";
		} else {
			m = Material.DIAMOND_HELMET;
			name = ChatColor.GRAY + "" + ChatColor.BOLD + "[PRIVATE'S HELMET]";
			desc = ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Human Armor";
		}
	
      //Gets rid of durability

      	net.minecraft.server.v1_9_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(new ItemStack(m));
      	NBTTagCompound tag = new NBTTagCompound(); //Create the NMS Stack's NBT (item data)
      	tag.setBoolean("Unbreakable", true); //Set unbreakable value to true
      	nmsItem.setTag(tag); //Apply the tag to the item
      	ItemStack item = CraftItemStack.asCraftMirror(nmsItem); //Get the bukkit version of the stack
		
	  //Unarmed = 1 damage, 4.0 speed
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
	
	public ItemStack makeGrenade(Player player, String grenade) {
		
		Material m;
		String name;
		String desc;
		
		if (grenade.equals("fragNade")) {
			m = Material.MAGMA_CREAM;
			name = ChatColor.GRAY + "" + ChatColor.BOLD + "[FRAG GRENADE]";
			desc = ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Human Equipment";
		} else if (grenade.equals("plasmaNade")) {
			m = Material.SPLASH_POTION;
			name = ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "[PLASMA GRENADE]";
			desc = ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Covenant Equipment";
		} else {
			m = Material.MAGMA_CREAM;
			name = ChatColor.GRAY + "" + ChatColor.BOLD + "[FRAG GRENADE]";
			desc = ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Human Equipment";
		}
		
      	ItemStack item = new ItemStack(m);
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
	
}
