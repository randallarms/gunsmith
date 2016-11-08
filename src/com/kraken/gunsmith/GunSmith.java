// =========================================================================
// |GUNSMITH v0.5
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
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.ChatColor;

public class GunSmith extends JavaPlugin implements Listener {
	
	GSListener listener;
	
    @Override
    public void onEnable() {
    	
    	getLogger().info("GunSmith has been enabled.");
    	PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
		listener = new GSListener(this);
		
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

		String command = cmd.getName();
		Player player = (Player) sender;
		
		if ( !(sender instanceof Player) ) {
			
			return false;
			
		}
		
		switch (command) {
		
			//Command: guns
			case "guns":
				
	        	player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Type \"/givegun <gunName> to give yourself a gun.\"");
	        	player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Names: " 
	        					+ ChatColor.GREEN + "sniper" + ChatColor.GRAY + " | "
	        					+ ChatColor.GREEN + "br" + ChatColor.GRAY + "/" + ChatColor.GREEN + "battleRifle" + ChatColor.GRAY + " | "
	        					+ ChatColor.GREEN + "lmg" + ChatColor.GRAY + "/" + ChatColor.GREEN + "lightMachineGun" + ChatColor.GRAY + " | "
	        					+ ChatColor.GREEN + "pistol" + ChatColor.GRAY + " | "
	        					+ ChatColor.GREEN + "bow");
	        	return true;
	        
	        //Command: giveGun <gunName>
			case "giveGun":
			case "givegun":
				
				switch (args.length) {
				
					case 1:
						return new ItemSmith().giveGun(args, player);
					case 2:
						try {
							return new ItemSmith().giveGun( args, getServer().getPlayer(args[1]) );
						} catch (NullPointerException npe) {
							player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Player not found!");
							return true;
						}
					default:
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Unrecognized format. Use \"/giveGun <gunName> {player}\"");
						return true;
				
				}
	        
	        //Command: giveAmmo <ammoName>
			case "giveAmmo":
			case "giveammo":
				
				switch (args.length) {
				
				case 1:
					return new ItemSmith().giveAmmo(args, player);
				case 2:
					try {
						return new ItemSmith().giveAmmo( args, getServer().getPlayer(args[1]) );
					} catch (NullPointerException npe) {
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Player not found!");
						return true;
					}
				default:
					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Unrecognized format. Use \"/giveAmmo <gunName> {player}\"");
					return true;
			
				}
	        
	        //Command: getStat <gunName> <stat>
			case "getStat":
			case "getstat":
				
		        if (args.length == 2) {
		        	
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
		        
			default:
				player.sendMessage(ChatColor.RED + "Your command was not recognized, or you have insufficient permissions.");
	        	return true;
		
		}
		
	}
		
}
