package com.kraken.gunsmith;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.BlockIterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.WeakHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;

public class GSListener implements Listener {
	
	GunSmith plugin;
	public final static WeakHashMap<Entity, EntityData> shotprojectiledata = new WeakHashMap<Entity, EntityData>();
	public final static WeakHashMap<Entity, Location> shotHitLoc = new WeakHashMap<Entity, Location>();
	ArrayList<Player> cooldown = new ArrayList<Player>();
	ArrayList<Player> orbitalCooldown = new ArrayList<Player>();
	String language;
	boolean glassBreak;
	boolean explosions;
	
	ItemStack pistol = new ItemStack( new ItemSmith(language).makeGun("pistol", 1) );
	ItemStack sniper = new ItemStack( new ItemSmith(language).makeGun("sniperRifle", 1) );
	ItemStack br = new ItemStack( new ItemSmith(language).makeGun("battleRifle", 1) );
	ItemStack lmg = new ItemStack( new ItemSmith(language).makeGun("lightMachineGun", 1) );
	ItemStack bow = new ItemStack( new ItemSmith(language).makeGun("crossbow", 1) );
	ItemStack rocketLauncher = new ItemStack( new ItemSmith(language).makeGun("rocketLauncher", 1) );
	ItemStack shotgun = new ItemStack( new ItemSmith(language).makeGun("shotgun", 1) );
	ItemStack ar = new ItemStack( new ItemSmith(language).makeGun("assaultRifle", 1) );
	ItemStack hmg = new ItemStack( new ItemSmith(language).makeGun("heavyMachineGun", 1) );
	ItemStack orbital = new ItemStack( new ItemSmith(language).makeGun("orbital", 1) );
	ItemStack frag = new ItemStack( new ItemSmith(language).makeGrenade("frag") );
	ItemStack pvtHelm = new ItemStack( new ItemSmith(language).makeArmor("pvtHelm") );
	ItemStack pvtChest = new ItemStack( new ItemSmith(language).makeArmor("pvtChest") );
	ItemStack pvtLegs = new ItemStack( new ItemSmith(language).makeArmor("pvtLegs") );
	ItemStack pvtBoots = new ItemStack( new ItemSmith(language).makeArmor("pvtBoots") );
	
    public GSListener(GunSmith plugin, String language) {
  	  
  	  plugin.getServer().getPluginManager().registerEvents(this, plugin);
  	  this.plugin = plugin;
  	  this.language = language;
  	  
    }
    
    public void setLanguage(String language) {
    	this.language = language;
    }
    
    public void setGlassBreak(Boolean glassBreak) {
    	this.glassBreak = glassBreak;
    }
    
    public void setExplosions(Boolean explosions) {
    	this.explosions = explosions;
    }
	
	@EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
    	
    	Player player = e.getPlayer();
    	ItemStack item = player.getInventory().getItemInMainHand();
    	
    	ArrayList<Integer> types = new ArrayList<Integer>();
		types.addAll(Arrays.asList(601, 602, 603, 604, 605, 607, 608, 609, 610));
		
		Short durability = item.getDurability();
    	
	  	//BulletShot
    		//On right click...
    		if ( e.getAction() != Action.LEFT_CLICK_AIR 
    				&& e.getAction() != Action.LEFT_CLICK_BLOCK
    				&& item != null && !cooldown.contains( player ) ) {
    			
    			//The Materials correspond to the item the gun is based on
    			if ( item.getType().equals(Material.DIAMOND_HOE) && types.contains( durability.intValue() ) ) {
    				
					Messages msg = new Messages(language);
					
					//Check is player has "shoot" permission
					if ( !player.hasPermission("gunsmith.shoot") ) {
						msg.makeMsg(player, "errorIllegalCommand");
						return;
					}
					
					//Check is player has "explosives" permission
					if ( item.equals(rocketLauncher) && !player.hasPermission("gunsmith.explosives") ) {
						msg.makeMsg(player, "errorIllegalCommand");
						return;
					}
					
					//Check is player has "orbital" permission
					if ( item.equals(orbital) && !player.hasPermission("orbital") ) {
						msg.makeMsg(player, "errorIllegalCommand");
						return;
					}
    				
    				//Check if player has proper ammunition
    				if ( hasAmmo(player, item)  || item.equals(orbital) ) {
    					
    					GunShot shot = new GunShot( player, item, glassBreak );
    					Bukkit.getServer().getPluginManager().callEvent(shot);
				    	
    					if ( !item.equals(bow) ) {
    						shotprojectiledata.put(shot.getProjectile(), shot.getProjectileData());
    					}
				    	
    					//Cooldown scheduling
				    	if (!item.equals(orbital)) {
					    	cooldown.add(player);;
    					} else {
    				    	orbitalCooldown.add(player);
    					}
				    	
				    	Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				    		
				    		public void run() {
				    			
				    			if (!item.equals(orbital)) {
				    				cooldown.remove(player);
				    			} else {
				    				orbitalCooldown.remove(player);
				    			}
				    			
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
    				
    				e.setCancelled(true);
			    	
    			} else if ( item.equals(frag) ) {
    				
    				//Check if player has proper ammunition
    				if (hasGrenade(player, item)) {
    				
    					GunShot shot = new GunShot( player, frag, glassBreak );
    					Bukkit.getServer().getPluginManager().callEvent(shot);
				    	
    					shotprojectiledata.put(shot.getProjectile(), shot.getProjectileData());
    					
				    	cooldown.add(player);
				    	Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				    		public void run() {
				    			cooldown.remove(player);
				    		}
				    	}, shot.findCooldown(item));
				    	
    				} else {
    					
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | You are out of grenades.");
    					
    				}
    				
    			}
    			
    		}
    		
    	}
	    //End of BulletShot

	@EventHandler
	public void onProjectileHit(ProjectileHitEvent e) {
		
		Entity entity = e.getEntity();
		EntityData eventdata = shotprojectiledata.get(entity); 
		
		if ( shotprojectiledata.containsKey(entity) && ( eventdata.getGun().equals( rocketLauncher ) 
				|| eventdata.getGun().equals( frag ) || eventdata.getGun().equals( orbital ) ) ) {
			
		    BlockIterator iterator = new BlockIterator(entity.getWorld(), entity.getLocation().toVector(), entity.getVelocity().normalize(), 0.0D, 4);
		    Block hitBlock = null;
		    
		    while ( iterator.hasNext() ) {
		    	
		        hitBlock = iterator.next();
		         
		        if ( !hitBlock.getType().equals(Material.AIR) ) {
		        	break;
		        }
			        
		    }
		         
	        if ( eventdata.getGun().equals( rocketLauncher ) ) {
	        	
	        	hitBlock.getWorld().createExplosion( hitBlock.getLocation().getX(), 
	        											hitBlock.getLocation().getY(), 
	        											hitBlock.getLocation().getZ(), 
	        											8.0F, explosions, explosions );
	        
	        } else if ( eventdata.getGun().equals( frag ) ) {
	        	
	        	shotHitLoc.put(entity, hitBlock.getLocation());
	        	
		    	Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		    		
		    		public void run() {
		    			Location hit = shotHitLoc.get(entity);
		    			hit.getWorld().createExplosion( hit.getBlock().getX(), 
															hit.getBlock().getY(), 
															hit.getBlock().getZ(), 
															6.0F, explosions, explosions );
		    		}
		    		
		    	}, 40);
	        	
	        } else if ( eventdata.getGun().equals( orbital ) ) {
	        	
	        	shotHitLoc.put(entity, hitBlock.getLocation());
	        	
		    	Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		    		
					public void run() {
		    			Location hit = shotHitLoc.get(entity);
		    			
			        	Location newLoc = null;
			        	boolean isNotAir = false;
			        	int y = 255;
			        	while (isNotAir == false) {
			        		if (y < 0) {
			        			break;
			        		}
			        		newLoc = new Location(hit.getWorld(), hit.getX(), y, hit.getZ());
			        		if (newLoc.getBlock().getType() != Material.AIR) {
			        			isNotAir = true;
			        		} else y--;
			        	}
			        	
			        	hit.getWorld().strikeLightning(hit);
			        	newLoc.getWorld().createExplosion( newLoc.getX(), newLoc.getY(), newLoc.getZ(), 9.0F, explosions, explosions );
			        	newLoc.add(2, 0, 2).getWorld().createExplosion( newLoc.getX(), newLoc.getY(), newLoc.getZ(), 6.0F, explosions, explosions );
			        	newLoc.add(-3, 0, 3).getWorld().createExplosion( newLoc.getX(), newLoc.getY(), newLoc.getZ(), 3.0F, explosions, explosions );
			        	
		    		}
		    		
		    	}, 126);
	        	
	        	
	        }
		
		}
		
	}

	@EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
		
		//check if the damager is a snowball
        if ( event.getDamager() instanceof Snowball ) { 
        	
        	//verify it is a gunshot (i.e., snowball shout out by the gun)
            if (shotprojectiledata.containsKey(event.getDamager())) { 
            	
            	//get data stored about the projectile
                EntityData eventdata = shotprojectiledata.get(event.getDamager()); 

                if ( eventdata.getGun().equals( rocketLauncher ) ) {
                	
                	event.getEntity().getWorld().createExplosion( event.getEntity().getLocation().getX(), 
                													event.getEntity().getLocation().getY(), 
                													event.getEntity().getLocation().getZ(), 
                													8.0F, explosions, explosions );
                	
                } else if ( eventdata.getGun().equals( orbital ) ) {
                	
                	shotHitLoc.put(event.getDamager(), event.getEntity().getLocation());
		        	
			    	Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			    		
						public void run() {
			    			Location hit = shotHitLoc.get(event.getDamager());
			    			
				        	Location newLoc = null;
				        	boolean isNotAir = false;
				        	int y = 255;
				        	while (isNotAir == false) {
				        		newLoc = new Location(hit.getWorld(), hit.getX(), y, hit.getZ());
				        		if (newLoc.getBlock().getType() != Material.AIR) {
				        			isNotAir = true;
				        		} else y--;
				        	}
				        	
				        	hit.getWorld().strikeLightning(hit);
				        	newLoc.getWorld().createExplosion( newLoc.getX(), newLoc.getY(), newLoc.getZ(), 9.0F, explosions, explosions );
				        	newLoc.add(2, 0, 2).getWorld().createExplosion( newLoc.getX(), newLoc.getY(), newLoc.getZ(), 6.0F, explosions, explosions );
				        	newLoc.add(-3, 0, 3).getWorld().createExplosion( newLoc.getX(), newLoc.getY(), newLoc.getZ(), 3.0F, explosions, explosions );
				        	
			    		}
			    		
			    	}, 126);
                
                //check if the event is outside of the range AND target is not the shooter
                } else if ( event.getEntity().getLocation().distance(eventdata.getFiredFrom()) <= eventdata.getRange()
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
		}  else if ( gun.equals(rocketLauncher) ) {
			return "Rocket Launcher";
		} else if ( gun.equals(frag) ) {
			return "Frag Grenade";
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
	
	//Checks if player has grenades
	public boolean hasGrenade(Player player, ItemStack grenade) {
		
		Inventory inv = player.getInventory();
		String grenadeName = getAmmoFor(grenade);
		
		for (ItemStack item : inv) {
			
			if ( item != null && item.hasItemMeta() ) {
			
				ItemMeta im = item.getItemMeta();
				
				if ( !im.equals(null) && im.hasLore() ) {
					
					List<String> lore = im.getLore();
					
					if ( lore.toString().contains("Equipment | " + grenadeName) ) {
						//Ammo was found
						if (item.getAmount() > 1) {
							item.setAmount(item.getAmount() - 1);
							return true;
						} else {
							inv.remove(item);
							return true;
						}
						
					}
					
				}
			
			}
			
		}
		
		//Grenades not found
		return false;
		
	}

	//GUI handling
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		
		ItemSmith smithy = new ItemSmith(language);
		
		ArrayList<Integer> types = new ArrayList<Integer>();
		types.addAll(Arrays.asList(601, 602, 603, 604, 605, 607, 608, 609, 610));
		
		Player player = (Player) e.getWhoClicked();
		Inventory inventory = e.getInventory();
		
		if ( inventory.getName().equals("GunSmith GUI") && e.getSlotType() != SlotType.OUTSIDE ) {
			
			ItemStack clicked = e.getCurrentItem();
			Short clickedType = 0;
			clickedType = clicked.getDurability();
			String clickedLore = new String();
			
			if ( clicked.hasItemMeta() ) {
				clickedLore = clicked.getItemMeta().getLore().toString();
			}
			
			//NOTE: Change ArrayList of durabilities to HashMap of durability-gun pairs for better code
			if ( types.contains( clickedType.intValue() ) ) {
				
				if ( clickedType == (short) 601 ) {
					player.getInventory().addItem( smithy.makeGun("pistol", 1) );
				} else if ( clickedType == (short) 602 ) {
					player.getInventory().addItem( smithy.makeGun("sniper", 1) );
				} else if ( clickedType == (short) 603 ) {
					player.getInventory().addItem( smithy.makeGun("br", 1) );
				} else if ( clickedType == (short) 604 ) {
					player.getInventory().addItem( smithy.makeGun("lmg", 1) );
				} else if ( clickedType == (short) 605 ) {
					player.getInventory().addItem( smithy.makeGun("bow", 1) );
				} else if ( clickedType == (short) 607 ) {
					player.getInventory().addItem( smithy.makeGun("rocketLauncher", 1) );
				} else if ( clickedType == (short) 608 ) {
					player.getInventory().addItem( smithy.makeGun("shotgun", 1) );
				} else if ( clickedType == (short) 609 ) {
					player.getInventory().addItem( smithy.makeGun("ar", 1) );
				} else if ( clickedType == (short) 610 ) {
					player.getInventory().addItem( smithy.makeGun("hmg", 1) );
				}
				
				e.setCancelled(true);
				
			} else if ( clickedLore.contains("Ammunition") ) {
				
				if ( clickedLore.contains("Pistol") ) {
					smithy.giveAmmo("pistol", player);
				} else if ( clickedLore.contains("Sniper Rifle") ) {
					smithy.giveAmmo("sniper", player);
				} else if ( clickedLore.contains("Battle Rifle") ) {
					smithy.giveAmmo("br", player);
				} else if ( clickedLore.contains("LMG") ) {
					smithy.giveAmmo("lmg", player);
				} else if ( clickedLore.contains("Crossbow") ) {
					smithy.giveAmmo("bow", player);
				} else if ( clickedLore.contains("Rocket Launcher") ) {
					smithy.giveAmmo("rocketLauncher", player);
				} else if ( clickedLore.contains("Shotgun") ) {
					smithy.giveAmmo("shotgun", player);
				} else if ( clickedLore.contains("Assault Rifle") ) {
					smithy.giveAmmo("ar", player);
				} else if ( clickedLore.contains("HMG") ) {
					smithy.giveAmmo("hmg", player);
				} 
				
				e.setCancelled(true);
				
			} else {
				e.setCancelled(true);
			}
			
		}
		
	}
		
}
