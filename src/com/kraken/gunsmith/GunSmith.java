// =========================================================================
// |GUNSMITH v0.7
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
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.WeakHashMap;

import org.bukkit.ChatColor;

public class GunSmith extends JavaPlugin implements Listener {
	
	GSListener listener;
	WeakHashMap<String, Boolean> packs = new WeakHashMap<String, Boolean>();
	String language;
	ArrayList<String> languages = new ArrayList<String>();
	Boolean glassBreak = false;
	
    @Override
    public void onEnable() {
    	
    	getLogger().info("GunSmith has been enabled.");
    	PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
		
		//Copies the default config.yml from within the .jar if "plugins/config.yml" does not exist
		this.getConfig().options().copyDefaults(true);
	    saveDefaultConfig();
		
		getPacks();
		
		this.language = getConfig().getString("language");
		
		languages.add("english");
		languages.add("spanish");
		
		listener = new GSListener(this, language);
		
		RecipeSmith recipes = new RecipeSmith(language);
		
		for (int n = 0; n < recipes.getTotal(); n++) {
			getServer().addRecipe( recipes.getRecipe(n) );
		}
			
    }
    
    @Override
    public void onDisable() {
    	
        getLogger().info("GunSmith has been disabled.");
        
    }
    
    public void msg(Player player, String cmd) {
    	new Messages(language).makeMsg(player, cmd);
    }
    
    public void setLanguage() {
    	listener.loadLanguage(language);
    }
    
    public void setGlassBreak() {
    	listener.loadGlassBreak(glassBreak);
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
				
				switch (args.length) {
				
					case 0:
						msg(player, "cmdGuns");
						return true;
						
					case 2:
						switch ( args[0].toLowerCase() ) {
						
							case "language":
								if ( player.isOp() ) {
									
									if ( languages.contains( args[1].toLowerCase() ) ) {
										this.language = args[1].toLowerCase();
										setLanguage();
										getConfig().set("language", args[1].toLowerCase());
										saveConfig();
										msg(player, "cmdLang");
										return true;
										
									} else {
										msg(player, "errorLangNotFound");
										return true;
									}
									
								}
								
							case "glassbreak":
								if ( player.isOp() ) {
									
									switch ( args[1].toLowerCase() ) {
									
										case "true":
											this.glassBreak = true;
											setGlassBreak();
											getConfig().set("glassBreak", true);
											saveConfig();
											msg(player, "cmdGlassBreakOn");
											return true;
											
										case "false":
											this.glassBreak = false;
											setGlassBreak();
											getConfig().set("glassBreak", false);
											saveConfig();
											msg(player, "cmdGlassBreakOff");
											return true;
											
										default: 
											msg(player, "errorGlassBreakFormat");
											return true;
									
									}
									
								}
							
							}
							
					default:
						msg(player, "errorIllegalCommand");
						return true;
						
				}
	        	
	        
	        //Command: giveGun <gunName>
			case "giveGun":
			case "givegun":
				
				switch (args.length) {
				
					case 1:
						switch (args[0]) {
						
							case "rpg":
								if ( !packs.get("WarZone") ) {
									msg(player, "errorWarZoneNotFound");
									return true;
								}
							case "hammerOfDawn":
							case "orbital":
								if ( !packs.get("WarZone") ) {
									msg(player, "errorWarZoneNotFound");
									return true;
								}
							default:
								return new ItemSmith(language).giveGun(args, player);
							
						}
					case 2:
						switch (args[0]) {
						
							case "rpg":
								if ( !packs.get("WarZone") ) {
									msg(player, "errorWarZoneNotFound");
									return true;
								}
							case "hammerOfDawn":
							case "orbital":
								if ( !packs.get("WarZone") ) {
									msg(player, "errorWarZoneNotFound");
									return true;
								}
							default:
								try {
									return new ItemSmith(language).giveGun( args, getServer().getPlayer(args[1]) );
								} catch (NullPointerException npe) {
									msg(player, "errorPlayerNotFound");
									return true;
								}
								
						}
					default:
						msg(player, "errorGunFormat");
						return true;
				
				}
	        
	        //Command: giveAmmo <ammoName>
			case "giveAmmo":
			case "giveammo":
				
				switch (args.length) {
				
				case 1:
					switch (args[0]) {
					
						case "rpg":
							if ( !packs.get("WarZone") ) {
								msg(player, "errorWarZoneNotFound");
								return true;
							}
						default:
							new ItemSmith(language).giveAmmo(args, player);
							return true;
						
					}
				case 2:
					switch (args[0]) {
					
						case "rpg":
							if ( !packs.get("WarZone") ) {
								msg(player, "errorWarZoneNotFound");
								return true;
							}
						default:
							try {
								return new ItemSmith(language).giveAmmo( args, getServer().getPlayer(args[1]) );
							} catch (NullPointerException npe) {
								msg(player, "errorPlayerNotFound");
								return true;
							}
					
					}
				default:
					msg(player, "errorAmmoFormat");
					return true;
			
				}
	        
	        //Command: getStat <gunName> <stat>
			case "getStat":
			case "getstat":
					
		        if ( args.length == 2 && !args[0].equalsIgnoreCase("rpg") ) {
		        	
		        	String statName = args[1];
		        	String gunName = new GunStats().getName(args[0]);
		        	int stat = -1;
		        	
		        	if ( statName.equalsIgnoreCase("range") ) {
		        		stat = new GunStats().findRange(args[0]);
		        	} else if ( statName.equalsIgnoreCase("cooldown") ) {
		        		stat = new GunStats().findCooldown(args[0]);
		        	}  else {
		        		msg(player, "errorStatType");
		        		return true;
		        	}
		        	
		        	if (stat >= 0) {
		        		
		        		player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | " + gunName + ", " + statName + ": " + stat);
		        		return true;
		        		
		        	} else {
			        	
		        		msg(player, "errorStatFormat");
			        	return true;
			        	
			        }
		        	
		        } else {
		        	
		        	msg(player, "errorStatFormat");
		        	return true;
		        	
		        }
				
			default:
				msg(player, "errorIllegalCommand");
	        	return true;
		
		}
		
	}
	
	public void getPacks() {
		
    	File packsFile = new File("plugins/GunSmith", "packs.yml");
	  	FileConfiguration packsConfig = YamlConfiguration.loadConfiguration(packsFile);
	  	packsConfig.set("default.enabled", true);
	  	
	  	try {
			packsConfig.save(packsFile);
		} catch (IOException e) {
			//No need to fuss!
		}
	  	
		Boolean isEnabled = packsConfig.getBoolean("WarZone.enabled");
		if (isEnabled != null) {
			packs.put("WarZone", isEnabled);
		}
			
	}
	
		
}
