// =========================================================================
// |GUNSMITH v0.4.1
// | by Kraken | https://www.spigotmc.org/members/kraken_.287802/
// | code inspired by various Bukkit & Spigot devs -- thank you. 
// |
// | Always free & open-source! If the main plugin is being sold/re-branded,
// | please let me know on the SpigotMC site, or wherever you can. Thanks!
// | Source code: https://github.com/randallarms/gunsmith
// | Premium packs: None
// =========================================================================

package com.kraken.gunsmith;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class GunSmith extends JavaPlugin implements Listener {
	
	public final static WeakHashMap<Entity, EntityData> shotprojectiledata = new WeakHashMap<Entity, EntityData>();
	
	ArrayList<Player> cooldown = new ArrayList<Player>();
	
    @Override
    public void onEnable() {
    	
    	getLogger().info("GunSmith has been enabled.");
    	PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
		
		RecipeSmith recipes = new RecipeSmith();
		
		for (int n = 0; n < recipes.getTotal(); n++) {
			getServer().addRecipe( recipes.getRecipe(n) );
		}
			
    }
    
    @Override
    public void onDisable() {
    	
        getLogger().info("GunSmith has been disabled.");
        
    }
    
//GunSmith commands
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player player = (Player) sender;
		
    	//Command: guns
        if (cmd.getName().equalsIgnoreCase("guns") && sender instanceof Player) {
        	player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Type \"/givegun <gunName> to give yourself a gun.\"");
        	player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Names: " 
        					+ ChatColor.GREEN + "sniper" + ChatColor.GRAY + " | "
        					+ ChatColor.GREEN + "br" + ChatColor.GRAY + "/" + ChatColor.GREEN + "battleRifle" + ChatColor.GRAY + " | "
        					+ ChatColor.GREEN + "lmg" + ChatColor.GRAY + "/" + ChatColor.GREEN + "lightMachineGun" + ChatColor.GRAY + " | "
        					+ ChatColor.GREEN + "pistol" + ChatColor.GRAY + " | "
        					+ ChatColor.GREEN + "bow");
        	return true;
        }
        
        //Command: giveGun <gunName>
        if (cmd.getName().equalsIgnoreCase("giveGun") && sender instanceof Player) {
        	
        	return new ItemSmith().giveGun(args, player);
            
        }
        
        //Command: giveAmmo <ammoName>
        if (cmd.getName().equalsIgnoreCase("giveAmmo") && sender instanceof Player) {
        	
        	if ( !new ItemSmith().giveAmmo(args, player) ) {
        		
        		player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Unrecognized ammo type.");
        		
        	}
        	
        	return true;
            
        }
        
        //Command: getStat <gunName> <stat>
        if (cmd.getName().equalsIgnoreCase("getStat") && sender instanceof Player && args.length == 2) {
        	
        	String statName = args[1];
        	String gunName = new GunStats().getName(args[0]);
        	int stat = -1;
        	
        	if ( statName.equalsIgnoreCase("range") ) {
        		stat = new GunStats().findRange(args[0]);
        	} else if ( statName.equalsIgnoreCase("cooldown") ) {
        		stat = new GunStats().findCooldown(args[0]);
        	}
        	
        	if (stat >= 0) {
        		
        		player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | " + gunName + ", " + statName + ": " + stat);
        		return true;
        		
        	}
        	
        }
    	
        player.sendMessage(ChatColor.RED + "Your command was not recognized, or you have insufficient permissions.");
        return true;
        
    }
	
	@EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
    	
    	Player player = e.getPlayer();
    	ItemStack item = player.getInventory().getItemInMainHand();
    	
	  	//BulletShot
    		//On right click...
    		if ( e.getAction() != Action.LEFT_CLICK_AIR && item != null && !cooldown.contains( player ) ) {
    			
    			Material m = item.getType();
    			
    			//The Materials correspond to the item the gun is based on
    			if ( m.equals(Material.FEATHER) || m.equals(Material.WOOD_HOE) 
    					|| m.equals(Material.GOLD_AXE) || m.equals(Material.DIAMOND_PICKAXE)
    					|| m.equals(Material.FLINT) ) {
    				
    				//Check if player has proper ammunition
    				if (hasAmmo(player, m)) {
    				
    					GunShot shot = new GunShot(player, item.getType());
    					Bukkit.getServer().getPluginManager().callEvent(shot);
				    	
    					if ( !m.equals(Material.FLINT) ) {
    						shotprojectiledata.put(shot.getProjectile(), shot.getProjectileData());
    					}
				    	
				    	cooldown.add(player);
				    	Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
				    		public void run() {
				    			cooldown.remove(player);
				    		}
				    	}, shot.findCooldown(m));
				    	
    				} else {
    					
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | You are out of ammunition.");
    					
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
		public String getAmmoFor(Material m) {
			
			if ( m.equals(Material.FEATHER) ) {
				return "Sniper Rifle";
			} else if ( m.equals(Material.WOOD_HOE) ) {
				return "Battle Rifle";
			} else if ( m.equals(Material.GOLD_AXE) ) {
				return "Pistol";
			} else if ( m.equals(Material.DIAMOND_PICKAXE) ) {
				return "LMG";
			} else if ( m.equals(Material.FLINT) ) {
				return "Crossbow";
			} else {
				return "null";
			}
			
		}
		
		//Checks if player has ammunition of a certain type
		public boolean hasAmmo(Player player, Material m) {
			
			Inventory inv = player.getInventory();
			String ammo = getAmmoFor(m);
			
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
