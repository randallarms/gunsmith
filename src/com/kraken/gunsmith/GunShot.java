package com.kraken.gunsmith;

import java.util.WeakHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftSnowball;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.util.BlockIterator;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import net.minecraft.server.v1_9_R1.PacketPlayOutEntityDestroy;

public class GunShot extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	public final static WeakHashMap<Entity, EntityData> shotprojectiledata = new WeakHashMap<Entity, EntityData>();
	Snowball projectile;
	EntityData data;

	public GunShot(Player player, Material gun) {

		if ( !gun.equals(Material.FLINT) ) {
			
			Location location = player.getEyeLocation();
	        BlockIterator blocksToAdd = new BlockIterator( location, -0.75D, findRange(gun) );
	        Location blockToAdd;
	        
	        projectile = player.launchProjectile(Snowball.class);
	        projectile.setVelocity(player.getLocation().getDirection().multiply(10.0D));
	        //Controls shooter identity, location, range, and damage
	        	data = new EntityData(player, projectile.getLocation(), 75, 10D);
	        //^^^
	        shotprojectiledata.put(projectile, data);
	        
	        while(blocksToAdd.hasNext()) {
	            blockToAdd = blocksToAdd.next().getLocation();
	            Material b = blockToAdd.getBlock().getType();
            	if ( b.equals(Material.GLASS)
            			|| b.equals(Material.THIN_GLASS)
            			|| b.equals(Material.STAINED_GLASS)
            			|| b.equals(Material.STAINED_GLASS_PANE) ) {
            		blockToAdd.getBlock().setType(Material.AIR);
            	} else if (b.isOccluding() || !shotprojectiledata.containsKey(projectile)) {
	            	break;
	            }
	            player.getWorld().playEffect(blockToAdd, Effect.STEP_SOUND, Material.BEACON);
	            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_LARGE_BLAST, (float) 0.1, (float) 0.5);
	        }
	        
	      for (Player p : Bukkit.getOnlinePlayers()) {
	          ((CraftPlayer)p).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(((CraftSnowball) projectile).getHandle().getId()));
	      }
	      
	  }
	      
      else {
    	  
    	  player.launchProjectile(Arrow.class);
    	  
      }
          
    }
	
	public int findRange(Material m) {

		if ( m.equals( Material.FEATHER) ) {
			return 100;
		} else if ( m.equals( Material.GOLD_AXE ) || m.equals( Material.WOOD_HOE ) ) {
			return 50;
		} else if ( m.equals( Material.DIAMOND_PICKAXE ) ) {
			return 40;
		} else if ( m.equals( Material.FLINT ) ) {
			return 30;
		} else {
			return 50;
		}
	      
	}
	
	public int findCooldown(Material m) {

		if ( m.equals( Material.FEATHER ) ) {
			return 20;
		} else if ( m.equals( Material.FLINT ) ) {
			return 30;
		} else if ( m.equals( Material.WOOD_HOE ) ) {
			return 10;
		} else if ( m.equals( Material.GOLD_AXE ) ) {
			return 5;
		} else if ( m.equals( Material.DIAMOND_PICKAXE ) ) {
			return 2;
		} else {
			return 5;
		}
	      
	}
	
    public WeakHashMap<Entity, EntityData> getShotProjectileData() {
        return shotprojectiledata;
    }
    
    public Entity getProjectile() {
        return projectile;
    }
    
    public EntityData getProjectileData() {
        return data;
    }
	
    @Override
    public HandlerList getHandlers() {
     
    return handlers;
    }
     
    public static HandlerList getHandlerList() {
    return handlers;
    }
	
}
