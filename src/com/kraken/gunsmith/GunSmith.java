// =========================================================================
// |GUNSMITH v0.9 | for Minecraft v1.12
// | by Kraken | https://www.spigotmc.org/members/kraken_.287802/
// | code inspired by various Bukkit & Spigot devs -- thank you.
// | Special mention: codename_B (FireworkEffectPlayer)
// |
// | Always free & open-source! If the main plugin is being sold/re-branded,
// | please let me know on the SpigotMC site, or wherever you can. Thanks!
// | Source code: https://github.com/randallarms/gunsmith
// =========================================================================

package com.kraken.gunsmith;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

import org.bukkit.ChatColor;

public class GunSmith extends JavaPlugin implements Listener {
	
	public String VERSION = "0.9";
	
	GSListener listener;
	String language;
	ArrayList<String> languages = new ArrayList<String>();
	Boolean glassBreak = false;
	Boolean silentMode = false;
	Messages messenger;
	
    @Override
    public void onEnable() {
    	
    	getLogger().info("Loading...");
    	PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
		
		//Copies the default config.yml from within the .jar if "plugins/config.yml" does not exist
		this.getConfig().options().copyDefaults(true);
		
		this.language = getConfig().getString("language");
		getLogger().info(ChatColor.RED + "Language: " + language.toUpperCase());
		
		this.messenger = new Messages(language);
		
		languages.add("english");
		languages.add("spanish");
		
		listener = new GSListener(this, language);
		
		RecipeSmith recipes = new RecipeSmith(language);
		
		for (int n = 0; n < recipes.getTotal(); n++) {
			getServer().addRecipe( recipes.getRecipe(n) );
		}
	    	
    	this.silentMode = getConfig().getBoolean("silentMode");
    	silencer(silentMode);
    	getLogger().info("Silent mode: " + silentMode.toString().toUpperCase() );
			
    }
    
    @Override
    public void onDisable() {
    	
        getLogger().info("GunSmith has been disabled.");
        
    }
    
    public void msg(Player player, String cmd) {
    	messenger.makeMsg(player, cmd);
    }
    
    public void setLanguage() {
    	listener.loadLanguage(language);
    }
    
    public void silencer(boolean setting) {
    	getConfig().set("silentMode", setting);
    	saveConfig();
    	messenger.silence(setting);
    }
    
    public void setGlassBreak() {
    	listener.loadGlassBreak(glassBreak);
    }
    
    //GunSmith commands
	@SuppressWarnings("deprecation")
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
										case "enable":
										case "enabled":
										case "on":
											this.glassBreak = true;
											setGlassBreak();
											getConfig().set("glassBreak", true);
											saveConfig();
											msg(player, "cmdGlassBreakOn");
											return true;
											
										case "false":
										case "disable":
										case "disabled":
										case "off":
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
								
							case "silentmode":
								if ( player.isOp() ) {
									
									switch ( args[1].toLowerCase() ) {
									
									case "true":
									case "enable":
									case "enabled":
									case "on":
										this.silentMode = true;
										silencer(true);
										getConfig().set("silentMode", true);
										saveConfig();
										msg(player, "cmdSilentOn");
										return true;
										
									case "false":
									case "disable":
									case "disabled":
									case "off":
										this.silentMode = false;
										silencer(false);
										getConfig().set("silentMode", false);
										saveConfig();
										msg(player, "cmdSilentOff");
										return true;
										
									default: 
										msg(player, "errorSilentModeFormat");
										return true;
									
									}
									
								}
							
							}
							
					default:
						msg(player, "errorIllegalCommand");
						return true;
						
				}
			
				
	        //Command: version        
	    		case "version":
	    			
	    			player.sendMessage(ChatColor.GRAY + "CURRENT: GunSmith v" + VERSION + " (beta)");
	                return true;
	        
	                
	        //Command: giveGun <gunName>
			case "giveGun":
			case "givegun":
				
				switch (args.length) {
				
					case 1:
						switch (args[0]) {
						
							case "rpg":
							case "hammerOfDawn":
							case "orbital":
							default:
								return new ItemSmith(language).giveGun(args, player);
							
						}
					case 2:
						switch (args[0]) {
						
							case "rpg":
							case "hammerOfDawn":
							case "orbital":
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
						default:
							new ItemSmith(language).giveAmmo(args, player);
							return true;
						
					}
				case 2:
					switch (args[0]) {
					
						case "rpg":
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
	
		
}
