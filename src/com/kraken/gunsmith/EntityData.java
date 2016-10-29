package com.kraken.gunsmith;

import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EntityData extends Event implements Cancellable {
	
	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled;
	
    private Location firedfrom;
    private Integer range;
    private Double damage;

    //constructor
    public EntityData(Location loc, Integer range, Double damage) {
        this.firedfrom = loc;
        this.range = range;
        this.damage = damage;
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
