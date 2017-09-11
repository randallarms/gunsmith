package com.kraken.gunsmith;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftSnowball;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_12_R1.PacketPlayOutEntityDestroy;

public class GunShot extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	public final static WeakHashMap<Entity, EntityData> shotprojectiledata = new WeakHashMap<Entity, EntityData>();
	Snowball projectile;
	EntityData data;
	String language = "English";
	
	ItemStack pistol = new ItemStack( new ItemSmith(language).makeGun("pistol", 1) );
	ItemStack sniper = new ItemStack( new ItemSmith(language).makeGun("sniperRifle", 1) );
	ItemStack br = new ItemStack( new ItemSmith(language).makeGun("battleRifle", 1) );
	ItemStack lmg = new ItemStack( new ItemSmith(language).makeGun("lightMachineGun", 1) );
	ItemStack bow = new ItemStack( new ItemSmith(language).makeGun("crossbow", 1) );
	ItemStack orbital = new ItemStack( new ItemSmith(language).makeGun("orbital", 1) );
	ItemStack rocketLauncher = new ItemStack( new ItemSmith(language).makeGun("rocketLauncher", 1) );
	ItemStack shotgun = new ItemStack( new ItemSmith(language).makeGun("shotgun", 1) );
	ItemStack ar = new ItemStack( new ItemSmith(language).makeGun("assaultRifle", 1) );
	ItemStack hmg = new ItemStack( new ItemSmith(language).makeGun("heavyMachineGun", 1) );
	ItemStack grenade = new ItemStack( new ItemSmith(language).makeGrenade("frag") );
	
	ArrayList<ItemStack> guns = new ItemSmith(language).listGuns();

	public GunShot(Player player, ItemStack gun, GunSmith plugin) {
		
		if ( !gun.equals(bow) ) {
			
			Location location = player.getEyeLocation();
			Integer range = findRange(gun);
	        Double damage = findDamage(gun);
	        Vector velocity = player.getLocation().getDirection().multiply(10.0D);
	        
	        if ( gun.equals(rocketLauncher.getType()) ) {
	        	velocity = player.getLocation().getDirection().multiply(3D);
	        } else if ( gun.equals(grenade.getType()) ) {
	        	velocity = player.getLocation().getDirection().multiply(1.5D);
	        }
	        
	        projectile = player.launchProjectile(Snowball.class);
	        projectile.setVelocity(velocity);
	        
	        //Controls shooter identity, location, range, and damage
	        	data = new EntityData(player, projectile.getLocation(), range, damage, gun);
	        //^^^
	        	
	        shotprojectiledata.put(projectile, data);
	        
	        //Visual + audio effects (smoke trail, gunshot blast)
			shotEffects(player, location, gun, plugin.options.get("glassBreak"), plugin.options.get("particles"));
	      
	        //Cancel snowball packet
	        for (Player p : Bukkit.getOnlinePlayers()) {
	            ((CraftPlayer)p).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(((CraftSnowball) projectile).getHandle().getId()));
	        }
	      
		  } else if ( gun.equals(bow) ) {
			  
			  player.launchProjectile(Arrow.class);
			  
		  } else {
		  	
		      player.getWorld().playSound(player.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, (float) 0.1, (float) 0.5);
		  	
		  }
          
    }
	
	public void shotEffects(Player player, Location location, ItemStack gun, boolean glassBreak, boolean particles) {
		
		Integer range = findRange(gun);
		BlockIterator blocksToAdd = new BlockIterator( location, -0.75D, range );
        Location blockToAdd;
		int bCount = 0;
		List<LivingEntity> entities = player.getWorld().getLivingEntities();
        
		while ( blocksToAdd.hasNext() ) {
        	
			bCount++;
			
            blockToAdd = blocksToAdd.next().getLocation();
            Material b = blockToAdd.getBlock().getType();
            
        	if ( b.equals(Material.GLASS)
        			|| b.equals(Material.THIN_GLASS)
        			|| b.equals(Material.STAINED_GLASS)
        			|| b.equals(Material.STAINED_GLASS_PANE) ) {
        		if (glassBreak) {
        			blockToAdd.getBlock().setType(Material.AIR);
        		} else {
        			break;
        		}
        	} else if ( b.isOccluding() || !shotprojectiledata.containsKey(projectile) ) {
            	break;
            }
        	
        	if (particles) {
	        	player.getWorld().playEffect(blockToAdd, Effect.STEP_SOUND, Material.WEB);
	        	if ( gun.equals(shotgun) && bCount == 1 ) {
	                fwEffect(blockToAdd.add(player.getLocation().getDirection().multiply(2)).add(0, 1, 0));
	        	}
			}
        	
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_LARGE_BLAST, (float) 0.1, (float) 0.5);
            
          //Buckshot
            if ( gun.equals(shotgun) ) {
            	
	            if ( bCount > 7 ) {
	            	
	            	for (LivingEntity entity : entities) {
	            		
	            		if (entity.getLocation().distance(blockToAdd) < 2.5) {
	            			
	            			LivingEntity target = (LivingEntity) entity;
	            			if ( !( target instanceof HumanEntity && !entity.getWorld().getPlayers().contains(entity) ) ) {
	            				target.damage( findDamage(gun) - 2D );
	            			}
	            			
	            		}
	            		
	            	}
	            	
				} 
	            
	            if ( bCount > 14 ) {
	            		
	            	for (LivingEntity entity : entities) {
	            		
	            		if (entity.getLocation().distance(blockToAdd) < 3.5) {
	            			
	            			LivingEntity target = (LivingEntity) entity;
	            			if ( !( target instanceof HumanEntity && !entity.getWorld().getPlayers().contains(entity) ) ) { 
	            				target.damage( findDamage(gun) - 4D );
	            			}
	            		
	            		}
	            		
	            	}
	            	
				}
            
            }
            
        }
		
	}
	
	public double findDamage(ItemStack gun) {
		
		if ( guns.contains(gun) ) {
			//return loaded gun dmg value
		}

		if ( gun.equals( sniper ) ) {
			return 7D;
		} else if ( gun.equals( bow ) ) {
			return 2D;
		} else if ( gun.equals( pistol )  ) {
			return 3D;
		} else if ( gun.equals( br ) || gun.equals( ar ) ) {
			return 4.5D;
		} else if ( gun.equals( lmg ) || gun.equals( hmg ) ) {
			return 5D;
		} else if ( gun.equals( shotgun ) ) {
			return 10D;
		} else if ( gun.equals( rocketLauncher ) ) {
			return 6D;
		} else if ( gun.equals( grenade ) ) {
			return 1D;
		} else if ( gun.equals( orbital ) ) {
			return 0D;
		} else {
			return 3D;
		}
	      
	}
	
	public int findRange(ItemStack gun) {
		
		if ( guns.contains(gun) ) {
			//return loaded gun range value
		}

		if ( gun.equals( sniper ) || gun.equals( orbital ) || gun.equals( rocketLauncher ) ) {
			return 125;
		} else if ( gun.equals( pistol ) || gun.equals( br ) ) {
			return 50;
		} else if ( gun.equals( lmg ) || gun.equals( hmg ) || gun.equals( ar ) ) {
			return 40;
		} else if ( gun.equals( bow ) ) {
			return 30;
		} else if ( gun.equals( shotgun ) ) {
			return 20;
		} else {
			return 50;
		}
	      
	}
	
	public int findCooldown(ItemStack gun) {

		if ( guns.contains(gun) ) {
			//return loaded gun cooldown value
		}
		
		if ( gun.equals( sniper ) ) { //Sniper
			return 20;
		} else if ( gun.equals( bow ) ) { //Crossbow
			return 30;
		} else if ( gun.equals( shotgun ) ) { //Shotgun
			return 20;
		} else if ( gun.equals( br ) ) { //BR
			return 10;
		} else if ( gun.equals( pistol ) || gun.equals( ar ) ) { //Pistol, AR
			return 5;
		} else if ( gun.equals( lmg ) || gun.equals( hmg ) ) { //LMG, HMG
			return 2;
		} else if ( gun.equals( rocketLauncher ) ) { //RPG
			return 50;
		} else if ( gun.equals( orbital ) ) { //Orbital
			return 250;
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
