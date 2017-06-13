package com.kraken.gunsmith;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class RecipeSmith {
	
	int rTotal = 20;
	
	String language;
	
	ShapedRecipe[] recipesList = new ShapedRecipe[rTotal];
	ItemStack sniper = new ItemStack( new ItemSmith(language).makeGun("sniperRifle", 1) );
	ItemStack sniperAmmo = new ItemStack( new ItemSmith(language).makeAmmo("sniperAmmo", 2) );
	ItemStack pistol = new ItemStack( new ItemSmith(language).makeGun("pistol", 1) );
	ItemStack pistolAmmo = new ItemStack( new ItemSmith(language).makeAmmo("pistolAmmo", 1) );
	ItemStack br = new ItemStack( new ItemSmith(language).makeGun("battleRifle", 1) );
	ItemStack brAmmo = new ItemStack( new ItemSmith(language).makeAmmo("brAmmo", 2) );
	ItemStack lmg = new ItemStack( new ItemSmith(language).makeGun("lightMachineGun", 1) );
	ItemStack lmgAmmo = new ItemStack( new ItemSmith(language).makeAmmo("lmgAmmo", 3) );
	ItemStack bow = new ItemStack( new ItemSmith(language).makeGun("crossbow", 1) );
	ItemStack bowAmmo = new ItemStack( new ItemSmith(language).makeAmmo("crossbowAmmo", 1) );
	ItemStack smallStock = new ItemStack( new ItemSmith(language).makePart("smallStock", 1) );
	ItemStack largeStock = new ItemStack( new ItemSmith(language).makePart("largeStock", 1) );
	ItemStack shortBarrel = new ItemStack( new ItemSmith(language).makePart("shortBarrel", 1) );
	ItemStack mediumBarrel = new ItemStack( new ItemSmith(language).makePart("mediumBarrel", 1) );
	ItemStack longBarrel = new ItemStack( new ItemSmith(language).makePart("longBarrel", 1) );
	ItemStack chamber = new ItemStack( new ItemSmith(language).makePart("chamber", 1) );
	ItemStack muzzle = new ItemStack( new ItemSmith(language).makePart("muzzle", 1) );
	ItemStack crossbowStock = new ItemStack( new ItemSmith(language).makePart("crossbowStock", 1) );
	ItemStack rocketLauncher = new ItemStack( new ItemSmith(language).makeGun("rocketLauncher", 1) );
	ItemStack rpgAmmo = new ItemStack( new ItemSmith(language).makeAmmo("rpgAmmo", 1) );
	
	public RecipeSmith(String language) {
		
		this.language = language;
		
		recipesList[0] = craftRecipe(sniper);
		recipesList[1] = craftRecipe(sniperAmmo);
		recipesList[2] = craftRecipe(pistol);
		recipesList[3] = craftRecipe(pistolAmmo);
		recipesList[4] = craftRecipe(br);
		recipesList[5] = craftRecipe(brAmmo);
		recipesList[6] = craftRecipe(lmg);
		recipesList[7] = craftRecipe(lmgAmmo);
		recipesList[8] = craftRecipe(bow);
		recipesList[9] = craftRecipe(bowAmmo);
		recipesList[10] = craftRecipe(smallStock);
		recipesList[11] = craftRecipe(largeStock);
		recipesList[12] = craftRecipe(shortBarrel);
		recipesList[13] = craftRecipe(mediumBarrel);
		recipesList[14] = craftRecipe(longBarrel);
		recipesList[15] = craftRecipe(chamber);
		recipesList[16] = craftRecipe(muzzle);
		recipesList[17] = craftRecipe(crossbowStock);
		recipesList[18] = craftRecipe(rocketLauncher);
		recipesList[19] = craftRecipe(rpgAmmo);
        
    }
	
	public ShapedRecipe getRecipe(int num) {
		
		return recipesList[num];
		
	}
	
	@SuppressWarnings("deprecation")
	public ShapedRecipe craftRecipe(ItemStack item) {
		
		ShapedRecipe recipe = new ShapedRecipe(item);
		
		//specific recipe ingredients
		if ( item.equals(sniper) ) {
			recipe.shape("   ", "768", "3  ");
			recipe.setIngredient('7', Material.DIAMOND_HOE);
			recipe.setIngredient('6', Material.DIAMOND_HOE);
			recipe.setIngredient('8', Material.DIAMOND_HOE);
			recipe.setIngredient('3', Material.DIAMOND_HOE);
		} else if ( item.equals(sniperAmmo) ) {
			recipe.shape(" g ", " g ", "ccc");
			recipe.setIngredient('g', Material.SULPHUR);
			recipe.setIngredient('c', Material.IRON_INGOT);
		} else if ( item.equals(pistol) ) {
			recipe.shape("   ", "74 ", "2  ");
			recipe.setIngredient('7', Material.DIAMOND_HOE);
			recipe.setIngredient('4', Material.DIAMOND_HOE);
			recipe.setIngredient('2', Material.DIAMOND_HOE);
		} else if ( item.equals(pistolAmmo) ) {
			recipe.shape("   ", " g ", " c ");
			recipe.setIngredient('g', Material.SULPHUR);
			recipe.setIngredient('c', Material.IRON_INGOT);
		} else if ( item.equals(br) ) {
			recipe.shape("   ", "758", "3  ");
			recipe.setIngredient('7', Material.DIAMOND_HOE);
			recipe.setIngredient('5', Material.DIAMOND_HOE);
			recipe.setIngredient('8', Material.DIAMOND_HOE);
			recipe.setIngredient('3', Material.DIAMOND_HOE);
		} else if ( item.equals(brAmmo) ) {
			recipe.shape(" g ", " g ", " c ");
			recipe.setIngredient('g', Material.SULPHUR);
			recipe.setIngredient('c', Material.IRON_INGOT);
		} else if ( item.equals(lmg) ) {
			recipe.shape("   ", "i68", "37 ");
			recipe.setIngredient('i', Material.IRON_INGOT);
			recipe.setIngredient('6', Material.DIAMOND_HOE);
			recipe.setIngredient('8', Material.DIAMOND_HOE);
			recipe.setIngredient('3', Material.DIAMOND_HOE);
			recipe.setIngredient('7', Material.DIAMOND_HOE);
		} else if ( item.equals(lmgAmmo) ) {
			recipe.shape("   ", "ggg", "ccc");
			recipe.setIngredient('g', Material.SULPHUR);
			recipe.setIngredient('c', Material.IRON_INGOT);
		} else if ( item.equals(bow) ) {
			recipe.shape("   ", "ab ", "   ");
			recipe.setIngredient('a', Material.DIAMOND_HOE);
			recipe.setIngredient('b', Material.BOW);
		} else if ( item.equals(bowAmmo) ) {
			recipe.shape("   ", " i ", " s ");
			recipe.setIngredient('i', Material.IRON_INGOT);
			recipe.setIngredient('s', Material.STICK);
		} else if ( item.equals(smallStock) ) {
			recipe.shape("   ", " i ", "w  ");
			recipe.setIngredient('i', Material.IRON_INGOT);
			recipe.setIngredient('w', Material.WOOD);
		} else if ( item.equals(largeStock) ) {
			recipe.shape("   ", "ii ", "w  ");
			recipe.setIngredient('i', Material.IRON_INGOT);
			recipe.setIngredient('w', Material.WOOD);
		} else if ( item.equals(shortBarrel) ) {
			recipe.shape("   ", "   ", "iii");
			recipe.setIngredient('i', Material.IRON_INGOT);
		} else if ( item.equals(mediumBarrel) ) {
			recipe.shape("   ", "iii", "   ");
			recipe.setIngredient('i', Material.IRON_INGOT);
		} else if ( item.equals(longBarrel) ) {
			recipe.shape("iii", "   ", "   ");
			recipe.setIngredient('i', Material.IRON_INGOT);
		} else if ( item.equals(chamber) ) {
			recipe.shape("   ", " i ", "ii ");
			recipe.setIngredient('i', Material.IRON_INGOT);
		} else if ( item.equals(muzzle) ) {
			recipe.shape("   ", " i ", "   ");
			recipe.setIngredient('i', Material.IRON_INGOT);
		} else if ( item.equals(crossbowStock) ) {
			recipe.shape("   ", "www", "w  ");
			recipe.setIngredient('w', Material.WOOD);
		} else if ( item.equals(rocketLauncher) ) {
			recipe.shape("   ", "666", "33 ");
			recipe.setIngredient('6', Material.DIAMOND_HOE);
			recipe.setIngredient('3', Material.DIAMOND_HOE);
		} else if ( item.equals(rpgAmmo) ) {
			recipe.shape("dgd", "dgd", "dbd");
			recipe.setIngredient('g', Material.SULPHUR);
			recipe.setIngredient('b', Material.BLAZE_POWDER);
			recipe.setIngredient('d', Material.DIAMOND);
		}
		
		return recipe;
		
	}
	
	public int getTotal() {
		
		return rTotal;
		
	}
	
}
