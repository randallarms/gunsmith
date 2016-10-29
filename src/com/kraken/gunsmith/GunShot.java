package com.kraken.gunsmith;

import java.util.WeakHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftSnowball;
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

		Location location = player.getEyeLocation();
        BlockIterator blocksToAdd = new BlockIterator( location, -0.75D, findRange(gun) );
        Location blockToAdd;
        projectile = player.launchProjectile(Snowball.class);
        projectile.setVelocity(player.getLocation().getDirection().multiply(10.0D));
        data = new EntityData(projectile.getLocation(), 75, 10D);
        shotprojectiledata.put(projectile, data);
        while(blocksToAdd.hasNext()) {
            blockToAdd = blocksToAdd.next().getLocation();
            Material b = blockToAdd.getBlock().getType();
            if (b.isOccluding() || !shotprojectiledata.containsKey(projectile)) {
            	break;
            }
            player.getWorld().playEffect(blockToAdd, Effect.STEP_SOUND, Material.BEACON);
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_LARGE_BLAST, (float) 0.1, (float) 0.5);
        }
      for (Player p : Bukkit.getOnlinePlayers()) {
          ((CraftPlayer)p).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(((CraftSnowball) projectile).getHandle().getId()));
      }
          
    }
	
	public int findRange(Material m) {

		if ( m.equals(Material.FEATHER) ) {
			return 100;
		} else {
			return 50;
		}
	      
	}
	
	public int findCooldown(Material m) {

		if ( m.equals(Material.FEATHER) ) {
			return 20;
		} else if ( m.equals(Material.WOOD_HOE) ) {
			return 10;
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
