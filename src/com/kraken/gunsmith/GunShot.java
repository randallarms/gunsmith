package com.kraken.gunsmith;

import java.io.File;
import java.util.List;
import java.util.WeakHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
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
	ItemStack grenade = new ItemStack( new ItemSmith(language).makeGrenade("frag") );
	
    File gunsFile = new File("plugins/GunSmith", "guns.yml");
    FileConfiguration gunsConfig = YamlConfiguration.loadConfiguration(gunsFile);
	WeakHashMap<Integer, ItemStack> guns = new ItemSmith(language).listGuns();

	public GunShot(Player player, ItemStack gun, GunSmith plugin) {
		
		if ( gun.equals(bow) ) {
			player.launchProjectile(Arrow.class);
		} else {
			
			Location location = player.getEyeLocation();
			
			Integer range = findStat(gun, "range");
			//Hard-coded range limit for safety
			if (range > 500) {
				range = 500;
			}
				
			Double damage = Double.valueOf( findStat(gun, "dmg") );
			//Hard-coded damage limit for safety
			if (damage > 100D) {
				damage = 100D;
			}
			
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
			shotprojectiledata.put(projectile, data);
			
			//Visual + audio effects (smoke trail, gunshot blast)
			shotEffects(player, location, gun, plugin.options.get("glassBreak"), plugin.options.get("particles"));
			  
			    //Cancel snowball packet
			for (Player p : Bukkit.getOnlinePlayers()) {
			    ((CraftPlayer)p).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(((CraftSnowball) projectile).getHandle().getId()));
			}
	      
		}
          
    }
	
	public void shotEffects(Player player, Location location, ItemStack gun, boolean glassBreak, boolean particles) {
		
		Integer range = findStat(gun, "range");
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
        		
        		//Particle generation for particle trails
        		if ( gun.equals(rocketLauncher) ) {
        			player.getWorld().spawnParticle(Particle.CRIT, blockToAdd, 1);
        		} else if ( gun.equals(shotgun) ) {
        			player.getWorld().spawnParticle(Particle.END_ROD, blockToAdd, 2);
        			player.getWorld().spawnParticle(Particle.SPELL, blockToAdd, 1);
        		} else {
        			player.getWorld().spawnParticle(Particle.SPELL, blockToAdd, 2);
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
	            				target.damage( findStat(gun, "dmg") - 2D );
	            			}
	            			
	            		}
	            		
	            	}
	            	
				} 
	            
	            if ( bCount > 14 ) {
	            		
	            	for (LivingEntity entity : entities) {
	            		
	            		if (entity.getLocation().distance(blockToAdd) < 3.5) {
	            			
	            			LivingEntity target = (LivingEntity) entity;
	            			if ( !( target instanceof HumanEntity && !entity.getWorld().getPlayers().contains(entity) ) ) { 
	            				target.damage( findStat(gun, "dmg") - 4D );
	            			}
	            		
	            		}
	            		
	            	}
	            	
				}
            
            }
            
        }
		
	}
	
	public int findStat(ItemStack gun, String stat) {
		
		int statNum = 1;
		
		if ( guns.containsValue(gun) ) {
		    statNum = gunsConfig.getInt(gun.getDurability() + "." + stat);
		}
		
		return statNum;
	      
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
