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
    
  //Angelic commands
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player player = (Player) sender;
		
    	//Command: guns
        if (cmd.getName().equalsIgnoreCase("guns") && sender instanceof Player) {
        	player.sendMessage(ChatColor.RED + "[GUNS!]" + ChatColor.GRAY + " | Shoot things.");
        	return true;
        }
        
        //Command: gun
        if (cmd.getName().equalsIgnoreCase("gun") && sender instanceof Player) {
        	
        	ItemStack gunItem;
        	
        	if (args.length == 1 && args[0].equalsIgnoreCase("sniper")) {
        		
        		ItemSmith smithy = new ItemSmith();
            	gunItem = smithy.makeGun(player, "sniperRifle");
            	
        	} else if (args.length == 1 && args[0].equalsIgnoreCase("br")) {
        		
        		ItemSmith smithy = new ItemSmith();
            	gunItem = smithy.makeGun(player, "battleRifle");
            	
        	} else {
        		
        		ItemSmith smithy = new ItemSmith();
        		gunItem = smithy.makeGun(player, "pistol");	
        		
    		}
        	
            player.sendMessage(ChatColor.RED + "[GUNS!]" + ChatColor.GRAY + " | Pew pew!");
            player.getInventory().addItem(new ItemStack(gunItem));
            
            return true;
            
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
    			if (m.equals(Material.FEATHER) || m.equals(Material.WOOD_HOE)) {
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
			
			if (event.getDamager() instanceof Player) {
				
			}
			
	        if (event.getDamager() instanceof Snowball) { //check if the damager is a snowball
	        	
	            if (shotprojectiledata.containsKey(event.getDamager())) { //verify it is a move (i.e., snowball shout out by the move)
	            	
	                EntityData eventdata = shotprojectiledata.get(event.getDamager()); //get data stored about the projectile
	                
	                if ( event.getEntity().getLocation().distance(eventdata.getFiredFrom()) <= eventdata.getRange() ) { //check if the event is outside of the range
	                	
	                	Projectile bullet = (Projectile) event.getDamager();
	                	
	                	//Headshot code
		            	double projectileHeight = bullet.getLocation().getY();
		            	System.out.println(projectileHeight);
		                double playerBodyHeight = event.getEntity().getLocation().getY() + 1.35;
		                System.out.println(playerBodyHeight);
		            	
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
