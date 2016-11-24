package com.kraken.gunsmith;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;

public class GSListener implements Listener {
	
	public final static WeakHashMap<Entity, EntityData> shotprojectiledata = new WeakHashMap<Entity, EntityData>();
	GunSmith plugin;
	ArrayList<Player> cooldown = new ArrayList<Player>();
	String language;
	Boolean glassBreak;
	
	ItemStack pistol = new ItemStack( new ItemSmith(language).makeGun("pistol", 1) );
	ItemStack sniper = new ItemStack( new ItemSmith(language).makeGun("sniperRifle", 1) );
	ItemStack br = new ItemStack( new ItemSmith(language).makeGun("battleRifle", 1) );
	ItemStack lmg = new ItemStack( new ItemSmith(language).makeGun("lightMachineGun", 1) );
	ItemStack bow = new ItemStack( new ItemSmith(language).makeGun("crossbow", 1) );
	ItemStack rocketLauncher = new ItemStack( new ItemSmith(language).makeGun("rocketLauncher", 1) );
	ItemStack shotgun = new ItemStack( new ItemSmith(language).makeGun("shotgun", 1) );
	ItemStack ar = new ItemStack( new ItemSmith(language).makeGun("assaultRifle", 1) );
	ItemStack hmg = new ItemStack( new ItemSmith(language).makeGun("heavyMachineGun", 1) );
	
    public GSListener(GunSmith plugin, String language) {
  	  
  	  plugin.getServer().getPluginManager().registerEvents(this, plugin);
  	  this.plugin = plugin;
  	  this.language = language;
  	  glassBreak = plugin.getConfig().getBoolean("glassBreak");
  	  
    }
    
    public void loadLanguage(String language) {
    	this.language = language;
    }
    
    public void loadGlassBreak(Boolean glassBreak) {
    	this.glassBreak = glassBreak;
    }
	
	@EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
    	
    	Player player = e.getPlayer();
    	ItemStack item = player.getInventory().getItemInMainHand();
    	
	  	//BulletShot
    		//On right click...
    		if ( e.getAction() != Action.LEFT_CLICK_AIR 
    				&& e.getAction() != Action.LEFT_CLICK_BLOCK
    				&& item != null && !cooldown.contains( player ) ) {
    			
    			//The Materials correspond to the item the gun is based on
    			if ( item.getType().equals(Material.DIAMOND_HOE) ) {
    				
    				//Check if player has proper ammunition
    				if (hasAmmo(player, item)) {
    				
    					GunShot shot = new GunShot(player, item, glassBreak);
    					Bukkit.getServer().getPluginManager().callEvent(shot);
				    	
    					if ( !item.equals(bow) ) {
    						shotprojectiledata.put(shot.getProjectile(), shot.getProjectileData());
    					}
				    	
				    	cooldown.add(player);
				    	Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				    		public void run() {
				    			cooldown.remove(player);
				    		}
				    	}, shot.findCooldown(item));
				    	
    				} else {
    					
    					new Messages(language).makeMsg(player, "errorNoAmmoFound");
    					if ( item.equals(pistol) ) {
    						player.getWorld().playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, (float) 0.1, (float) 0.7);
    					} else {
    						player.getWorld().playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, (float) 0.2, (float) 0.5);
    					}
    					
    				}
			    	
    			}
    		}
    		
    	}
	    //End of BulletShot
	
		@EventHandler
	    public void onHit(EntityDamageByEntityEvent event) {
			
			//check if the damager is a snowball
	        if ( event.getDamager() instanceof Snowball ) { 
	        	
	        	//verify it is a gunshot (i.e., snowball shout out by the gun)
	            if (shotprojectiledata.containsKey(event.getDamager())) { 
	            	
	            	//get data stored about the projectile
	                EntityData eventdata = shotprojectiledata.get(event.getDamager()); 
	              
	                //check if the event is outside of the range AND target is not the shooter
	                if ( event.getEntity().getLocation().distance(eventdata.getFiredFrom()) <= eventdata.getRange()
	                		&& !eventdata.getPlayer().equals( event.getEntity() ) ) { 
	                	
	                	Projectile bullet = (Projectile) event.getDamager();
	                	
	                	//Headshot code
		            	double projectileHeight = bullet.getLocation().getY();
		                double playerBodyHeight = event.getEntity().getLocation().getY() + 1.35;
		            	
		                if (projectileHeight > playerBodyHeight) {
		                	event.setDamage( (double) (eventdata.getDamage() * 2) ); // double damage
		                } else {
		                	event.setDamage(eventdata.getDamage()); // regular damage
		                }
		                
	                    shotprojectiledata.remove(event.getDamager()); //remove the projectile
	                    
	                } else {
	                	event.setCancelled(true);
	                }
	                
	            }
	            
	        }
	        
	    }
		
		//Checks for the name of the ammo used for a particular gun
		public String getAmmoFor(ItemStack gun) {
			
			if ( gun.equals(sniper) ) {
				return "Sniper Rifle";
			} else if ( gun.equals(br) ) {
				return "Battle Rifle";
			} else if ( gun.equals(pistol) ) {
				return "Pistol";
			} else if ( gun.equals(lmg) ) {
				return "LMG";
			} else if ( gun.equals(bow) ) {
				return "Crossbow";
			} else if ( gun.equals(shotgun) ) {
				return "Shotgun";
			} else if ( gun.equals(ar) ) {
				return "Assault Rifle";
			} else if ( gun.equals(hmg) ) {
				return "HMG";
			} else {
				return "null";
			}
			
		}
		
		//Checks if player has ammunition of a certain type
		public boolean hasAmmo(Player player, ItemStack gun) {
			
			Inventory inv = player.getInventory();
			String ammo = getAmmoFor(gun);
			
			for (ItemStack item : inv) {
				
				if (item != null && item.hasItemMeta()) {
				
					ItemMeta im = item.getItemMeta();
					
					if (!im.equals(null) && im.hasLore()) {
						
						List<String> lore = im.getLore();
						
						if ( lore.toString().contains("Ammunition | " + ammo) ) {
							//Ammo was found
							if (item.getAmount() > 1) {
								item.setAmount(item.getAmount() - 1);
							} else {
								inv.remove(item);
							}
							return true;
						}
						
					}
				
				}
				
			}
			
			//Ammo was not found
			return false;
			
		}
		
}
