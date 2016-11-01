package com.kraken.gunsmith;

public class GunStats {

	public GunStats() {
          
    }
	
	public String getName(String gunName) {

		if ( gunName.equalsIgnoreCase("sniper") ) {
			return "Sniper Rifle";
		} else if ( gunName.equalsIgnoreCase("br") ||  gunName.equalsIgnoreCase("battleRifle") ) {
			return "Battle Rifle";
		} else if ( gunName.equalsIgnoreCase("pistol") ) {
			return "Pistol";
		} else if ( gunName.equalsIgnoreCase("lmg") || gunName.equalsIgnoreCase("lightMachineGun") ) {
			return "Light Machine Gun";
		} else {
			return "";
		}
	      
	}
	
	public int findRange(String gunName) {

		if ( gunName.equalsIgnoreCase("sniper") ) {
			return 100;
		} else if ( gunName.equalsIgnoreCase("br") ||  gunName.equalsIgnoreCase("battleRifle")
				|| gunName.equalsIgnoreCase("pistol") ) {
			return 50;
		} else if ( gunName.equalsIgnoreCase("lmg") || gunName.equalsIgnoreCase("lightMachineGun") ) {
			return 40;
		} else {
			return -1;
		}
	      
	}
	
	public int findCooldown(String gunName) {

		if ( gunName.equalsIgnoreCase("sniper") ) {
			return 20;
		} else if ( gunName.equalsIgnoreCase("battleRifle") || gunName.equalsIgnoreCase("br") ) {
			return 10;
		} else if ( gunName.equalsIgnoreCase("pistol") ) {
			return 5;
		} else if ( gunName.equalsIgnoreCase("lmg") || gunName.equalsIgnoreCase("lightMachineGun") ) {
			return 2;
		} else {
			return -1;
		}
	      
	}
	
}
