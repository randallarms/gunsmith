package com.kraken.gunsmith;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GunSmithGUI {

	public static String language;
	public static ItemSmith smithy = new ItemSmith(language);
	public static Inventory gsGUI = Bukkit.createInventory(null, 36, "GunSmith GUI");
	
	public GunSmithGUI(String language) {
         GunSmithGUI.language = language;
    }
	
	public static boolean openGSGUI(Player player) {
		
		gsGUI.setItem(0, new ItemStack(Material.AIR, 1));
		gsGUI.setItem(1, new ItemStack(Material.AIR, 1));
		gsGUI.setItem(2, new ItemStack(Material.AIR, 1));
		gsGUI.setItem(3, new ItemStack(Material.AIR, 1));
		gsGUI.setItem(4, new ItemStack(Material.AIR, 1));
		gsGUI.setItem(5, new ItemStack(Material.AIR, 1));
		gsGUI.setItem(6, new ItemStack(Material.AIR, 1));
		gsGUI.setItem(7, new ItemStack(Material.AIR, 1));
		gsGUI.setItem(8, new ItemStack(Material.AIR, 1));
		
		gsGUI.setItem(9, smithy.makeGun("pistol", 1));
		gsGUI.setItem(10, smithy.makeGun("br", 1));
		gsGUI.setItem(11, smithy.makeGun("ar", 1));
		gsGUI.setItem(12, smithy.makeGun("lmg", 1));
		gsGUI.setItem(13, smithy.makeGun("hmg", 1));
		gsGUI.setItem(14, smithy.makeGun("sniper", 1));
		gsGUI.setItem(15, smithy.makeGun("shotgun", 1));
		gsGUI.setItem(16, smithy.makeGun("rocketLauncher", 1));
		gsGUI.setItem(17, smithy.makeGun("bow", 1));
		
		gsGUI.setItem(18, smithy.makeAmmo("pistol", 1));
		gsGUI.setItem(19, smithy.makeAmmo("br", 1));
		gsGUI.setItem(20, smithy.makeAmmo("ar", 1));
		gsGUI.setItem(21, smithy.makeAmmo("lmg", 1));
		gsGUI.setItem(22, smithy.makeAmmo("hmg", 1));
		gsGUI.setItem(23, smithy.makeAmmo("sniper", 1));
		gsGUI.setItem(24, smithy.makeAmmo("shotgun", 1));
		gsGUI.setItem(25, smithy.makeAmmo("rocketLauncher", 1));
		gsGUI.setItem(26, smithy.makeAmmo("bow", 1));
		
		gsGUI.setItem(27, new ItemStack(Material.AIR, 1));
		gsGUI.setItem(28, new ItemStack(Material.AIR, 1));
		gsGUI.setItem(29, new ItemStack(Material.AIR, 1));
		gsGUI.setItem(30, new ItemStack(Material.AIR, 1));
		gsGUI.setItem(31, new ItemStack(Material.AIR, 1));
		gsGUI.setItem(32, new ItemStack(Material.AIR, 1));
		gsGUI.setItem(33, new ItemStack(Material.AIR, 1));
		gsGUI.setItem(34, new ItemStack(Material.AIR, 1));
		gsGUI.setItem(35, new ItemStack(Material.AIR, 1));
		
		player.openInventory(gsGUI);
		
		return true;
		
	}
	
}
