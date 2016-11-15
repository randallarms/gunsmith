package com.kraken.gunsmith;

import java.util.List;
import java.util.WeakHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftSnowball;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import net.minecraft.server.v1_9_R1.PacketPlayOutEntityDestroy;

public class GunShot extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	public final static WeakHashMap<Entity, EntityData> shotprojectiledata = new WeakHashMap<Entity, EntityData>();
	Snowball projectile;
	EntityData data;

	public GunShot(Player player, Material gun, Boolean glassBreakEnabled) {

		if ( !gun.equals(Material.FLINT) ) { //Not Crossbow
			
			Location location = player.getEyeLocation();
			Integer range = findRange(gun);
	        Double damage = findDamage(gun);
	        Vector velocity = player.getLocation().getDirection().multiply(10.0D);
	        
	        projectile = player.launchProjectile(Snowball.class);
	        projectile.setVelocity(velocity);
	        
	        //Controls shooter identity, location, range, and damage
	        	data = new EntityData(player, projectile.getLocation(), range, damage);
	        //^^^
	        	
	        shotprojectiledata.put(projectile, data);
	        
	      //Visual + audio effects (smoke trail, gunshot blast)
			shotEffects(player, location, gun, glassBreakEnabled);
	      
	      //Cancel snowball packet
	      for (Player p : Bukkit.getOnlinePlayers()) {
	          ((CraftPlayer)p).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(((CraftSnowball) projectile).getHandle().getId()));
	      }
	      
	  }
	      
      else { //Crossbow
    	  
    	  player.launchProjectile(Arrow.class);
    	  
      }
          
    }
	
	public void shotEffects(Player player, Location location, Material gun, boolean glassBreakEnabled) {
		
		Integer range = findRange(gun);
		BlockIterator blocksToAdd = new BlockIterator( location, -0.75D, range );
        Location blockToAdd;
		int bCount = 0;
		List<Entity> entities = player.getWorld().getEntities();
        
		while ( blocksToAdd.hasNext() ) {
        	
			bCount++;
			
            blockToAdd = blocksToAdd.next().getLocation();
            Material b = blockToAdd.getBlock().getType();
            
        	if ( b.equals(Material.GLASS)
        			|| b.equals(Material.THIN_GLASS)
        			|| b.equals(Material.STAINED_GLASS)
        			|| b.equals(Material.STAINED_GLASS_PANE) ) {
        		if (glassBreakEnabled) {
        			blockToAdd.getBlock().setType(Material.AIR);
        		} else {
        			break;
        		}
        	} else if ( b.isOccluding() || !shotprojectiledata.containsKey(projectile) ) {
            	break;
            }
        	
        	player.getWorld().playEffect(blockToAdd, Effect.STEP_SOUND, Material.WEB);
        	if ( gun.equals(Material.GOLD_SPADE) && bCount == 1 ) {
                fwEffect(blockToAdd.add(player.getLocation().getDirection().multiply(2)).add(0, 1, 0));
        	}
        	
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_LARGE_BLAST, (float) 0.1, (float) 0.5);
            
          //Buckshot
            if ( gun.equals(Material.GOLD_SPADE) ) {
            	
	            if ( bCount > 7 ) {
	            	
	            	for (Entity entity : entities) {
	            		
	            		if (entity.getLocation().distance(blockToAdd) < 2.5 && entity instanceof LivingEntity) {
	            			
	            			LivingEntity target = (LivingEntity) entity;
	            			target.damage( findDamage(gun) - 2D );
	            			
	            		}
	            		
	            	}
	            	
				} 
	            
	            if ( bCount > 14 ) {
	            		
	            	for (Entity entity : entities) {
	            		
	            		if (entity.getLocation().distance(blockToAdd) < 3.5 && entity instanceof LivingEntity) {
	            			
	            			LivingEntity target = (LivingEntity) entity;
	            			target.damage( findDamage(gun) - 4D );
	            			
	            		}
	            		
	            	}
	            	
				}
            
            }
            
        }
		
	}
	
	public double findDamage(Material m) {

		if ( m.equals( Material.FEATHER ) ) { //Sniper
			return 7D;
		} else if ( m.equals( Material.FLINT ) ) { //Crossbow
			return 2D;
		} else if ( m.equals( Material.GOLD_AXE )  ) { //Pistol
			return 3D;
		} else if ( m.equals( Material.WOOD_HOE ) || m.equals( Material.WOOD_PICKAXE ) ) { //BR, AR
			return 4.5D;
		} else if ( m.equals( Material.DIAMOND_PICKAXE ) || m.equals( Material.GOLD_PICKAXE ) ) { //LMG, HMG
			return 5D;
		} else if ( m.equals( Material.GOLD_SPADE ) ) { //Shotgun
			return 10D;
		} else {
			return 3D;
		}
	      
	}
	
	public int findRange(Material m) {

		if ( m.equals( Material.FEATHER) ) { //Sniper
			return 100;
		} else if ( m.equals( Material.GOLD_AXE ) || m.equals( Material.WOOD_HOE ) ) { //Pistol, BR
			return 50;
		} else if ( m.equals( Material.DIAMOND_PICKAXE ) || m.equals( Material.GOLD_PICKAXE ) || m.equals( Material.WOOD_PICKAXE ) ) { //LMG, HMG, AR
			return 40;
		} else if ( m.equals( Material.FLINT ) ) { //Crossbow
			return 30;
		} else if ( m.equals( Material.GOLD_SPADE ) ) { //Shotgun
			return 20;
		} else {
			return 50;
		}
	      
	}
	
	public int findCooldown(Material m) {

		if ( m.equals( Material.FEATHER ) ) { //Sniper
			return 20;
		} else if ( m.equals( Material.FLINT ) ) { //Crossbow
			return 30;
		} else if ( m.equals( Material.GOLD_SPADE ) ) { //Shotgun
			return 20;
		} else if ( m.equals( Material.WOOD_HOE ) ) { //BR
			return 10;
		} else if ( m.equals( Material.GOLD_AXE ) || m.equals( Material.WOOD_PICKAXE ) ) { //Pistol, AR
			return 5;
		} else if ( m.equals( Material.DIAMOND_PICKAXE ) || m.equals( Material.GOLD_PICKAXE ) ) { //LMG, HMG
			return 2;
		} else {
			return 5;
		}
	      
	}
	
    public static void fwEffect(Location location) {
    	
        FireworkEffect effect = FireworkEffect.builder().withColor(Color.GRAY).with(FireworkEffect.Type.BALL).build();
        FireworkEffectPlayer fireworkEffect = new FireworkEffectPlayer();
        
        try {
			fireworkEffect.playFirework(location.getWorld(), location, effect);
		} catch (Exception e) {
			//No need to fuss!
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
