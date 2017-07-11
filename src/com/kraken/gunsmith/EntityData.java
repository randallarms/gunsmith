package com.kraken.gunsmith;

import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class EntityData extends Event implements Cancellable {
	
	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled;
	
    private Location firedfrom;
    private Integer range;
    private Double damage;
    private Player player;
    private ItemStack gun;
    private Collection<? extends Player> nearby;
    
    //Constructor
    public EntityData(Player player, Location loc, Integer range, Double damage, ItemStack gun) {
        this.player = player;
    	this.firedfrom = loc;
        this.range = range;
        this.damage = damage;
        this.gun = gun;
    }
    
    public Location getFiredFrom() {
        return firedfrom;
    }
    
    public Integer getRange() {
        return range;
    }
    
    public Double getDamage() {
        return damage;
    }
    
    public Player getPlayer() {
    	return player;
    }
    
    public ItemStack getGun() {
    	return gun;
    }
    
    public Collection<? extends Player> getNearby() {
    	return nearby;
    }
    
    @Override
    public HandlerList getHandlers() {
     
    return handlers;
    }
     
    public static HandlerList getHandlerList() {
    return handlers;
    }
     
    @Override
    public boolean isCancelled() {
    return cancelled;
    }
    
    @Override
    public void setCancelled(boolean cancel) {
     
    this.cancelled = cancel;

    }

}
