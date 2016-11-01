// ========================================================================
// |GUNSMITH v0.1.3
// | by Kraken | https://www.spigotmc.org/members/kraken_.287802/
// | code inspired by various Bukkit & Spigot devs -- thank you. 
// |
// | Always free & open-source! If this plugin is being sold or re-branded,
// | please let me know on the SpigotMC site, or wherever you can. Thanks!
// | Source code: https://github.com/randallarms/gunsmith
// ========================================================================

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
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
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
        	player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Gun names: " 
        					+ ChatColor.GREEN + "sniper" + ChatColor.GRAY + " | "
        					+ ChatColor.GREEN + "br" + ChatColor.GRAY + "/" + ChatColor.GREEN + "battleRifle" + ChatColor.GRAY + " | "
        					+ ChatColor.GREEN + "lmg" + ChatColor.GRAY + "/" + ChatColor.GREEN + "lightMachineGun" + ChatColor.GRAY + " | "
        					+ ChatColor.GREEN + "pistol" + ChatColor.GRAY + " | ");
        	return true;
        }
        
        //Command: giveGun <gunName>
        if (cmd.getName().equalsIgnoreCase("giveGun") && sender instanceof Player) {
        	
        	return new ItemSmith().giveGun(args, player);
            
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
	
	@SuppressWarnings("deprecation")
	@EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
    	
    	Player player = e.getPlayer();
    	ItemStack item = player.getInventory().getItemInHand();
    	
	  	//BulletShot
    		//On right click...
    		if ( e.getAction() != Action.LEFT_CLICK_AIR && item != null && !cooldown.contains( player ) ) {
    			
    			Material m = item.getType();
    			
    			//The Materials correspond to the item the gun is based on
    			if ( m.equals(Material.FEATHER) || m.equals(Material.WOOD_HOE) 
    					|| m.equals(Material.GOLD_AXE) || m.equals(Material.DIAMOND_PICKAXE) ) {
	    			GunShot shot = new GunShot(player, item.getType());
			    	Bukkit.getServer().getPluginManager().callEvent(shot);
			    	shotprojectiledata.put(shot.getProjectile(), shot.getProjectileData());
			    	cooldown.add(player);
			    	Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			    		public void run() {
			    			cooldown.remove(player);
			    		}
			    	}, shot.findCooldown(m));
    			}
    		}
    		
    	}
	    //End of BulletShot
	 
		@EventHandler
	    public void onHit(EntityDamageByEntityEvent event) {
			
	        if (event.getDamager() instanceof Snowball) { //check if the damager is a snowball
	        	
	            if (shotprojectiledata.containsKey(event.getDamager())) { //verify it is a gunshot (i.e., snowball shout out by the gun)
	            	
	                EntityData eventdata = shotprojectiledata.get(event.getDamager()); //get data stored about the projectile
	                
	                if ( event.getEntity().getLocation().distance(eventdata.getFiredFrom()) <= eventdata.getRange() ) { //check if the event is outside of the range
	                	
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
		
}
