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

import java.io.File;
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
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class GSListener implements Listener {
	
	GunSmith plugin;
	public final static WeakHashMap<Entity, EntityData> shotprojectiledata = new WeakHashMap<Entity, EntityData>();
	public final static WeakHashMap<Entity, Location> shotHitLoc = new WeakHashMap<Entity, Location>();
	ArrayList<Player> cooldown = new ArrayList<Player>();
	ArrayList<Player> orbitalCooldown = new ArrayList<Player>();
	ArrayList<Player> justClicked = new ArrayList<Player>();
	ArrayList<Player> zoomed = new ArrayList<Player>();
	WeakHashMap<String, Boolean> options = new WeakHashMap<>();
	String language;
	
	ItemStack pistol = new ItemStack( new ItemSmith(language).makeGun(601) );
	ItemStack sniper = new ItemStack( new ItemSmith(language).makeGun(602) );
	ItemStack br = new ItemStack( new ItemSmith(language).makeGun(603) );
	ItemStack lmg = new ItemStack( new ItemSmith(language).makeGun(604) );
	ItemStack bow = new ItemStack( new ItemSmith(language).makeGun(605) );
	ItemStack orbital = new ItemStack( new ItemSmith(language).makeGun(606) );
	ItemStack rocketLauncher = new ItemStack( new ItemSmith(language).makeGun(607) );
	ItemStack shotgun = new ItemStack( new ItemSmith(language).makeGun(608) );
	ItemStack ar = new ItemStack( new ItemSmith(language).makeGun(609) );
	ItemStack hmg = new ItemStack( new ItemSmith(language).makeGun(610) );
	ItemStack frag = new ItemStack( new ItemSmith(language).makeGrenade("frag") );
	ItemStack pvtHelm = new ItemStack( new ItemSmith(language).makeArmor("pvtHelm") );
	ItemStack pvtChest = new ItemStack( new ItemSmith(language).makeArmor("pvtChest") );
	ItemStack pvtLegs = new ItemStack( new ItemSmith(language).makeArmor("pvtLegs") );
	ItemStack pvtBoots = new ItemStack( new ItemSmith(language).makeArmor("pvtBoots") );
	
	//Guns plugin file & config
    File gunsFile = new File("plugins/GunSmith", "guns.yml");
    FileConfiguration gunsConfig = YamlConfiguration.loadConfiguration(gunsFile);
    
	WeakHashMap<Integer, ItemStack> guns = new ItemSmith(language).listGuns();
	
    public GSListener(GunSmith plugin, String language) {
  	  
  	  plugin.getServer().getPluginManager().registerEvents(this, plugin);
  	  this.plugin = plugin;
  	  this.language = language;
  	  
    }
    
    public void setOption(String option, boolean setting) {
    	options.put(option, setting);
    }
    
    public void setLanguage(String language) {
    	this.language = language;
    }
    
  //Check for double-click issues, returns true if clicking too fast
    public boolean clickCheck(Player player) {
    	
		if ( justClicked.contains(player) ) {
			
			return false;
			
		} else {
			
			justClicked.add(player);
			
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
	    		public void run() {
	    			justClicked.remove(player);
	    		}
	    	}, 2);
			
			return true;
			
		}
		
    }
    
	@EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
    	
    	Player player = e.getPlayer();
    	ItemStack item = player.getInventory().getItemInMainHand();
    	
    	//Check for double-click issues
		if ( clickCheck(player) ) {
			return;
		}
    	
    	ArrayList<Integer> types = new ArrayList<Integer>();
		types.addAll(Arrays.asList(601, 602, 603, 604, 605, 607, 608, 609, 610));
		
		Short durability = item.getDurability();
		
		boolean isGun = item.getType().equals(Material.DIAMOND_HOE) && types.contains( durability.intValue() );
	
		//On right click...
		if ( e.getAction() != Action.LEFT_CLICK_AIR 
				&& e.getAction() != Action.LEFT_CLICK_BLOCK ) {
			
			//The Materials correspond to the item the gun is based on
			if ( isGun && !cooldown.contains( player ) ) {
				
				Messages msg = new Messages(plugin, language);
				
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
				if ( hasAmmo(player, item) ) {
					
					GunShot shot = new GunShot( player, item, plugin );
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
			    		
			    	}, shot.findStat(item, "cooldown") );
			    	
				} else {
					
					new Messages(plugin, language).makeMsg(player, "errorNoAmmoFound");
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
				
					GunShot shot = new GunShot( player, frag, plugin );
					Bukkit.getServer().getPluginManager().callEvent(shot);
			    	
					shotprojectiledata.put(shot.getProjectile(), shot.getProjectileData());
					
			    	cooldown.add(player);
			    	Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			    		public void run() {
			    			cooldown.remove(player);
			    		}
			    	}, shot.findStat(item, "cooldown") );
			    	
				} else {
					
					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | You are out of grenades.");
					
				}
				
			}
		
		//on left click...
		} else if ( item.equals(sniper) || item.equals(br) ) {
			
	        if ( zoomed.contains(player) ) {
	        	player.setWalkSpeed(0.2F);
	        	zoomed.remove(player);
	        } else {
	        	player.setWalkSpeed(-0.2F);
	        	zoomed.add(player);
	        }
	        
		}
		
	}

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
	        											8.0F, options.get("explosions"), options.get("explosions") );
	        
	        } else if ( eventdata.getGun().equals( frag ) ) {
	        	
	        	shotHitLoc.put(entity, hitBlock.getLocation());
	        	
		    	Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		    		
		    		public void run() {
		    			Location hit = shotHitLoc.get(entity);
		    			hit.getWorld().createExplosion( hit.getBlock().getX(), 
															hit.getBlock().getY(), 
															hit.getBlock().getZ(), 
															6.0F, options.get("explosions"), options.get("explosions") );
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
			        	newLoc.getWorld().createExplosion( newLoc.getX(), newLoc.getY(), newLoc.getZ(), 9.0F, 
			        													options.get("explosions"), options.get("explosions") );
			        	newLoc.add(2, 0, 2).getWorld().createExplosion( newLoc.getX(), newLoc.getY(), newLoc.getZ(), 6.0F, 
			        													options.get("explosions"), options.get("explosions") );
			        	newLoc.add(-3, 0, 3).getWorld().createExplosion( newLoc.getX(), newLoc.getY(), newLoc.getZ(), 3.0F, 
			        													options.get("explosions"), options.get("explosions") );
			        	
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
                													8.0F, options.get("explosions"), options.get("explosions") );
                	
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
				        	newLoc.getWorld().createExplosion( newLoc.getX(), newLoc.getY(), newLoc.getZ(), 9.0F, 
				        													options.get("explosions"), options.get("explosions") );
				        	newLoc.add(2, 0, 2).getWorld().createExplosion( newLoc.getX(), newLoc.getY(), newLoc.getZ(), 6.0F, 
				        													options.get("explosions"), options.get("explosions") );
				        	newLoc.add(-3, 0, 3).getWorld().createExplosion( newLoc.getX(), newLoc.getY(), newLoc.getZ(), 3.0F, 
				        													options.get("explosions"), options.get("explosions") );
				        	
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
	
	//Checks if player has ammunition of a certain type
	public boolean hasAmmo(Player player, ItemStack gun) {
		
		Inventory inv = player.getInventory();
		String ammo = gunsConfig.getString(gun.getDurability() + ".ammo");
		
		if ( gun.equals(orbital) ) {
			return true;
		}
		
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
		String grenadeName = "Frag Grenade";
		
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
		
    	//Check for double-click issues
		if ( clickCheck(player) ) {
			return;
		}
		
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
					player.getInventory().addItem( smithy.makeGun(601) );
				} else if ( clickedType == (short) 602 ) {
					player.getInventory().addItem( smithy.makeGun(602) );
				} else if ( clickedType == (short) 603 ) {
					player.getInventory().addItem( smithy.makeGun(603) );
				} else if ( clickedType == (short) 604 ) {
					player.getInventory().addItem( smithy.makeGun(604) );
				} else if ( clickedType == (short) 605 ) {
					player.getInventory().addItem( smithy.makeGun(605) );
				} else if ( clickedType == (short) 607 ) {
					player.getInventory().addItem( smithy.makeGun(607) );
				} else if ( clickedType == (short) 608 ) {
					player.getInventory().addItem( smithy.makeGun(608) );
				} else if ( clickedType == (short) 609 ) {
					player.getInventory().addItem( smithy.makeGun(609) );
				} else if ( clickedType == (short) 610 ) {
					player.getInventory().addItem( smithy.makeGun(610) );
				}
				
				e.setCancelled(true);
				
			} else if ( clickedLore.contains("Ammunition") ) {
				
				if ( clickedLore.contains("Pistol") ) {
					smithy.giveAmmo("pistol", player, 64);
				} else if ( clickedLore.contains("Sniper Rifle") ) {
					smithy.giveAmmo("sniper", player, 64);
				} else if ( clickedLore.contains("Battle Rifle") ) {
					smithy.giveAmmo("br", player, 64);
				} else if ( clickedLore.contains("LMG") ) {
					smithy.giveAmmo("lmg", player, 64);
				} else if ( clickedLore.contains("Crossbow") ) {
					smithy.giveAmmo("bow", player, 64);
				} else if ( clickedLore.contains("Rocket Launcher") ) {
					smithy.giveAmmo("rocketLauncher", player, 64);
				} else if ( clickedLore.contains("Shotgun") ) {
					smithy.giveAmmo("shotgun", player, 64);
				} else if ( clickedLore.contains("Assault Rifle") ) {
					smithy.giveAmmo("ar", player, 64);
				} else if ( clickedLore.contains("HMG") ) {
					smithy.giveAmmo("hmg", player, 64);
				} 
				
				e.setCancelled(true);
				
			} else {
				e.setCancelled(true);
			}
			
		}
		
	}
		
}
