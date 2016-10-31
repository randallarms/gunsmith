package com.kraken.gunsmith;

public class GunStats {

	public GunStats() {
          
    }
	
	public String getName(String gunName) {

		if ( gunName.equals("sniper") ) {
			return "Sniper Rifle";
		} else if ( gunName.equals("br") ||  gunName.equals("battleRifle") ) {
			return "Battle Rifle";
		} else if ( gunName.equals("pistol") ) {
			return "Pistol";
		} else {
			return "";
		}
	      
	}
	
	public int findRange(String gunName) {

		if ( gunName.equals("sniper") ) {
			return 100;
		} else if ( gunName.equals("br") ||  gunName.equals("battleRifle")
				|| gunName.equals("pistol") ) {
			return 50;
		} else {
			return -1;
		}
	      
	}
	
	public int findCooldown(String gunName) {

		if ( gunName.equals("sniper") ) {
			return 20;
		} else if ( gunName.equals("battleRifle") || gunName.equals("br") ) {
			return 10;
		} else if ( gunName.equals("pistol") ) {
			return 5;
		} else {
			return -1;
		}
	      
	}
	
}
