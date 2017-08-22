// =========================================================================
// |GUNSMITH v1.3 (WarZone) | for Minecraft v1.12
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

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class GunSmith extends JavaPlugin implements Listener {
	
	public static String VERSION = "1.3 (WarZone)";
	
	GSListener listener;
	
	String language;
	ArrayList<String> languages = new ArrayList<String>();
	Messages messenger;
	
	GunSmithGUI gui = new GunSmithGUI(language);
	
	boolean glassBreak = false;
	boolean silentMode = false;
	boolean guiEnabled = true;
	boolean opRequired = false;
	boolean explosions = false;
	boolean permissions = false;
	
    @Override
    public void onEnable() {
    	
    	getLogger().info("[GUNSMITH] Loading...");
    	
    	PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
		
		//Copies the default config.yml from within the .jar if "plugins/config.yml" does not exist
		this.getConfig().options().copyDefaults(true);
		
		this.language = getConfig().getString("language");
		getLogger().info( ChatColor.RED + "[GUNSMITH] Language: " + language.toUpperCase() );
		
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
    	getLogger().info("[GUNSMITH] Silent mode: " + silentMode );
    	
    	this.guiEnabled = getConfig().getBoolean("guiEnabled");
    	getLogger().info("[GUNSMITH] GUI enabled: " + guiEnabled );
    	
    	this.opRequired = getConfig().getBoolean("opRequired");
    	getLogger().info("[GUNSMITH] OP requirement enabled: " + opRequired );
    	
    	this.explosions = getConfig().getBoolean("explosions");
    	getLogger().info("[GUNSMITH] Explosions enabled: " + opRequired );
    	
    	this.permissions = getConfig().getBoolean("permissions");
    	getLogger().info("[GUNSMITH] Permissions settings enabled: " + permissions );
    	
    	getLogger().info("[GUNSMITH] Finished loading.");
			
    }
    
    @Override
    public void onDisable() {
        getLogger().info("[GUNSMITH] GunSmith has been disabled.");
    }
    
    public void msg(Player player, String cmd) {
    	messenger.makeMsg(player, cmd);
    }
    
    public void consoleMsg(String cmd) {
    	messenger.makeConsoleMsg(cmd);
    }
    
    public void setLanguage(String language) {
    	this.language = language;
    	getConfig().set("language", language);
    	saveConfig();
    	listener.setLanguage(language);
    }
    
    public void silencer(boolean silentMode) {
    	this.silentMode = silentMode;
    	getConfig().set("silentMode", silentMode);
    	saveConfig();
    	messenger.silence(silentMode);
    }
    
    public void setGlassBreak(boolean glassBreak) {
    	this.glassBreak = glassBreak;
    	getConfig().set("silentMode", glassBreak);
    	saveConfig();
    	listener.setGlassBreak(glassBreak);
    }
    
    public void setExplosions(boolean explosions) {
    	this.explosions = explosions;
    	getConfig().set("silentMode", explosions);
    	saveConfig();
    	listener.setExplosions(explosions);
    }
    
    //GunSmith commands
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player player;
		boolean isPlayer;
		
		if (sender instanceof Player) {
			player = (Player) sender;
			isPlayer = true;
		} else {
			player = Bukkit.getServer().getPlayerExact("Octopus__");
			isPlayer = false;
		}
		
		String command = cmd.getName();
		
		switch (command) {
		
			//Command: guns
			case "guns":
				
				switch (args.length) {
				
					case 0:
						
						if ( isPlayer ) {
							
							if ( opRequired && !( player.isOp() ) ) {
								msg(player, "errorIllegalCommand");
							} else if ( permissions && !player.hasPermission("gunsmith.guns") ) {
								msg(player, "errorPermissions");
							} else if ( guiEnabled ) {
								GunSmithGUI.openGSGUI(player);
							} else {
								msg(player, "errorGUINotEnabled");
							}
							
						} else {
							consoleMsg("errorPlayerCommand");
						}
						
						return true;
						
					case 1:
						
						switch ( args[0].toLowerCase() ) {
						
						  //Command: version     
			    			case "version":
			    			
							if ( !isPlayer ) {
								consoleMsg("cmdVersion");
							} else {
								msg(player, "cmdVersion");
							}
							
			                return true;
					
						}
						
					case 2:
						
						//Check if sender is a player and if that player has OP perms
						if (isPlayer) {
							
							if ( !player.isOp() ) {
								msg(player, "errorIllegalCommand");
								return true;
							}
							
						}
						
						//Command handling switch
						switch ( args[0].toLowerCase() ) {
						
							case "language":
								
								String lang = args[1].toLowerCase();
								
								//Language command handling
								if ( languages.contains( lang ) ) {
									
									setLanguage(lang);
									
									if ( !isPlayer ) {
										consoleMsg("cmdLanguageSet");
									} else {
										msg(player, "cmdLang");
									}
									
									return true;
								
								//Language command error handling
								} else {
									
									if ( !isPlayer ) {
										consoleMsg("errorLanguageSet");
									} else {
										msg(player, "errorLangNotFound");
									}
									
									return true;
								}
								
							case "glassbreak":
							case "glassBreak":
									
								//Glassbreak command handling
								switch ( args[1].toLowerCase() ) {
								
									case "true":
									case "enable":
									case "enabled":
									case "on":
									case "cierto":
										this.glassBreak = true;
										setGlassBreak(true);
										
										if ( !isPlayer ) {
											consoleMsg("cmdGlassBreakOn");
										} else {
											msg(player, "cmdGlassBreakOn");
										}
										
										return true;
										
									case "false":
									case "disable":
									case "disabled":
									case "off":
									case "falso":
										this.glassBreak = false;
										setGlassBreak(false);
										
										if ( !isPlayer ) {
											consoleMsg("cmdGlassBreakOff");
										} else {
											msg(player, "cmdGlassBreakOff");
										}
										
										return true;
									
									//Glassbreak command error handling
									default: 
										
										if ( !isPlayer ) {
											consoleMsg("errorCommandFormat");
										} else {
											msg(player, "errorGlassBreakFormat");
										}
										
										return true;
								
								}
								
							case "silentmode":
							case "silentMode":
								
								//Silentmode command handling
								switch ( args[1].toLowerCase() ) {
								
									case "true":
									case "enable":
									case "enabled":
									case "on":
									case "cierto":
										this.silentMode = true;
										silencer(true);
										
										if ( !isPlayer ) {
											consoleMsg("cmdSilentModeOn");
										} else {
											msg(player, "cmdSilentOn");
										}
										
										return true;
										
									case "false":
									case "disable":
									case "disabled":
									case "off":
									case "falso":
										this.silentMode = false;
										silencer(false);
										
										if ( !isPlayer ) {
											consoleMsg("cmdSilentModeOff");
										} else {
											msg(player, "cmdSilentOff");
										}
										
										return true;
								
								  //Silentmode command error handling
									default: 
										
										if ( !isPlayer ) {
											consoleMsg("errorCommandFormat");
										} else {
											msg(player, "errorSilentModeFormat");
										}
										
										return true;
								
								}
								
							case "gui":
							case "GUI": 
								
								//GUI command handling	
								switch ( args[1].toLowerCase() ) {
								
									case "true":
									case "enable":
									case "enabled":
									case "on":
									case "cierto":
										this.guiEnabled = true;
										getConfig().set("guiEnabled", true);
										saveConfig();
										
										if ( !isPlayer ) {
											consoleMsg("cmdGUIEnabled");
										} else {
											msg(player, "cmdGUIEnabled");
										}
										
										return true;
										
									case "false":
									case "disable":
									case "disabled":
									case "off":
									case "falso":
										this.guiEnabled = false;
										getConfig().set("guiEnabled", false);
										saveConfig();
	
										if ( !isPlayer ) {
											consoleMsg("cmdGUIDisabled");
										} else {
											msg(player, "cmdGUIDisabled");
										}
										
										return true;
								
								  //GUI command error handling
									default: 
										
										if ( !isPlayer ) {
											consoleMsg("errorCommandFormat");
										} else {
											msg(player, "errorGUIToggleFormat");
										}
										
										return true;
								
								}
								
						  //Command: opReq
			        	    case "opRequired":
			        	    case "oprequired":
			        	    case "opReq":
			        	    case "opreq":
			        			  
			        	    	if ( args.length == 2 ) {
			        	    		
			        	    		switch ( args[1].toLowerCase() ) {
			        	    			case "on":
			        	    			case "cierto":
			        	    			case "enable":
			        	    			case "enabled":
			        	    			case "true":
			        	    				this.opRequired = true;
											getConfig().set("opRequired", true);
											saveConfig();
											
											if ( !isPlayer ) {
												consoleMsg("cmdOpReqEnabled");
											} else {
												msg(player, "cmdOpReqEnabled");
											}
											
			        	    				return true;
			        	    				
			        	    			case "off":
			        	    			case "disable":
			        	    			case "disabled":
			        	    			case "false":
			        	    			case "falso":
			        	    				this.opRequired = false;
											getConfig().set("opRequired", false);
											saveConfig();
											
											if ( !isPlayer ) {
												consoleMsg("cmdOpReqDisabled");
											} else {
												msg(player, "cmdOpReqDisabled");
											}
											
			        	    				return true;
			        	    				
			        	    			default:
			        	    				if ( !isPlayer ) {
			        	    					consoleMsg("errorArgumentFormat");
			        	    				} else {
			        	    					msg(player, "errorOpReqFormat");
			        	    				}
			        	    				
			        	        	    	return true;
			        	        	    	
			        	    		}
			        	    		
			        	    	} else {
			        	    		
			        	    		if ( !isPlayer ) {
	        	    					consoleMsg("errorArgumentFormat");
	        	    				} else {
	        	    					msg(player, "errorOpReqFormat");
	        	    				}
			        	    		
	        	        	    	return true;
	        	        	    	
			        	    	}
			        	    	
			        	  //Command: perms
			        	    case "perms":
			        	    case "permissions":
			        	    case "perm":
			        	    case "permission":
			        			  
			        	    	if ( args.length == 2 ) {
			        	    		
			        	    		switch ( args[1].toLowerCase() ) {
			        	    			case "on":
			        	    			case "cierto":
			        	    			case "enable":
			        	    			case "enabled":
			        	    			case "true":
			        	    				this.permissions = true;
											getConfig().set("permissions", true);
											saveConfig();
											
											if ( !isPlayer ) {
												consoleMsg("cmdPermsEnabled");
											} else {
												msg(player, "cmdPermsEnabled");
											}
											
			        	    				return true;
			        	    				
			        	    			case "off":
			        	    			case "disable":
			        	    			case "disabled":
			        	    			case "false":
			        	    			case "falso":
			        	    				this.permissions = false;
											getConfig().set("permissions", false);
											saveConfig();
											
											if ( !isPlayer ) {
												consoleMsg("cmdPermsDisabled");
											} else {
												msg(player, "cmdPermsDisabled");
											}
											
			        	    				return true;
			        	    				
			        	    			default:
			        	    				if ( !isPlayer ) {
			        	    					consoleMsg("errorArgumentFormat");
			        	    				} else {
			        	    					msg(player, "errorPermsFormat");
			        	    				}
			        	    				
			        	        	    	return true;
			        	        	    	
			        	    		}
			        	    		
			        	    	} else {
			        	    		
			        	    		if ( !isPlayer ) {
	        	    					consoleMsg("errorArgumentFormat");
	        	    				} else {
	        	    					msg(player, "errorPermsFormat");
	        	    				}
			        	    		
	        	        	    	return true;
	        	        	    	
			        	    	}
			        	    	
			        	  //Command: explosions
			        	    case "explosions":
			        	    case "explosion":
			        	    case "explosives":
			        			  
			        	    	if ( args.length == 2 ) {
			        	    		
			        	    		switch ( args[1].toLowerCase() ) {
			        	    			case "on":
			        	    			case "cierto":
			        	    			case "enable":
			        	    			case "enabled":
			        	    			case "true":
			        	    				this.explosions = true;
			        	    				setExplosions(true);
											
											if ( !isPlayer ) {
												consoleMsg("cmdExplosionsEnabled");
											} else {
												msg(player, "cmdExplosionsEnabled");
											}
											
			        	    				return true;
			        	    				
			        	    			case "off":
			        	    			case "disable":
			        	    			case "disabled":
			        	    			case "false":
			        	    			case "falso":
			        	    				this.explosions = false;
			        	    				setExplosions(false);
											
											if ( !isPlayer ) {
												consoleMsg("cmdExplosionsDisabled");
											} else {
												msg(player, "cmdExplosionsDisabled");
											}
											
			        	    				return true;
			        	    				
			        	    			default:
			        	    				if ( !isPlayer ) {
			        	    					consoleMsg("errorArgumentFormat");
			        	    				} else {
			        	    					msg(player, "errorExplosionsFormat");
			        	    				}
			        	    				
			        	        	    	return true;
			        	        	    	
			        	    		}
			        	    		
			        	    	} else {
			        	    		
			        	    		if ( !isPlayer ) {
	        	    					consoleMsg("errorArgumentFormat");
	        	    				} else {
	        	    					msg(player, "errorExplosionsFormat");
	        	    				}
			        	    		
	        	        	    	return true;
	        	        	    	
			        	    	}
			        	    	
							}
							
					default:
						if (isPlayer) {
							msg(player, "errorIllegalCommand");
							return true;
						} else {
							consoleMsg("errorCommandFormat");
						}
						
				}
	                
	        //Command: giveGun <gunName> <player?>
			case "giveGun":
			case "givegun":
				
				switch (args.length) {
				
					case 1:
					case 2:
						switch (args[0]) {
						
							default:
								if (isPlayer) {
									
									if ( player.isOp() ) {
										
										//Find target of command to give gun
										Player target;
										
										//1 arg = self
										if (args.length < 2) {
											target = player;
										//2 args = another player
										} else {
											target = getServer().getPlayer(args[1]);
										}
										
										//Give gun to player
										try {
											
											return new ItemSmith(language).giveGun( args[0], target );
											
										} catch (NullPointerException npe) {
											
											if (isPlayer) {
												msg(player, "errorPlayerNotFound");
											} else {
												consoleMsg("errorPlayerNotFound");
											}
											
											return true;
											
										}
										
									} else {
										msg(player, "errorIllegalCommand");
									}
									
								} else {
									consoleMsg("errorPlayerCommand");
									return true;
								}
							
						}
						
					default:
						
						if (isPlayer) {
							msg(player, "errorGunFormat");
						} else {
							consoleMsg("errorCommandFormat");
						}
						
						return true;
				
				}
	        
	        //Command: giveAmmo <ammoName>
			case "giveAmmo":
			case "giveammo":
				
				switch (args.length) {
				
					case 1:
						switch (args[0]) {
						
							default:
								if (isPlayer) {
									
									if ( player.isOp() ) {
										new ItemSmith(language).giveAmmo(args[0], player);
									} else {
										msg(player, "errorIllegalCommand");
									}
									
								} else {
									consoleMsg("errorPlayerCommand");
									return true;
								}
							
						}
						
					case 2:
						switch (args[0]) {
						
							default:
								try {
									
									if ( player.isOp() ) {
										return new ItemSmith(language).giveAmmo( args[0], getServer().getPlayer(args[1]) );
									} else {
										msg(player, "errorIllegalCommand");
									}
									
								} catch (NullPointerException npe) {
									
									if (isPlayer) {
										
										msg(player, "errorPlayerNotFound");
										
									} else {
										consoleMsg("errorPlayerNotFound");
									}
									
									return true;
									
								} catch (ArrayIndexOutOfBoundsException aie) {
									
									if (isPlayer) {
										
										msg(player, "errorIllegalCommand");
										
									} else {
										consoleMsg("errorArgumentFormat");
									}
									
									return true;
									
								}
						
						}
						
					default:
						
						if (isPlayer) {
							
							msg(player, "errorAmmoFormat");
							
						} else {
							consoleMsg("errorCommandFormat");
						}
						
						return true;
			
				}
				
  			//Command: giveGrenade
  			case "giveGrenade":
  			case "givegrenade":
  				
  				switch (args.length) {
				
				case 1:
					
					switch ( args[0].toLowerCase() ) {
					
						case "frag":
							
							if (isPlayer) {
								
								if ( player.isOp() ) {
									player.getInventory().addItem( new ItemSmith(language).makeGrenade( args[0] ) );
								} else {
									msg(player, "errorIllegalCommand");
								}
								
							} else {
								consoleMsg("errorPlayerCommand");
							}
							
							return true;
							
					}	
					
				case 2:
					
					switch ( args[0].toLowerCase() ) {
					
					case "frag":
							
							try {
								
								if ( isPlayer ) {
									if ( player.isOp() ) {
										Player receiver = (Player) getServer().getPlayer(args[1]);
										receiver.getInventory().addItem( new ItemSmith(language).makeGrenade( args[0] ) );
									} else {
										msg(player, "errorIllegalCommand");
									}
								} else {
									consoleMsg("errorPlayerCommand");
								}
								
							} catch (NullPointerException npe) {
								
								if (isPlayer) {
									msg(player, "errorPlayerNotFound");
								} else {
									consoleMsg("errorPlayerNotFound");
								}
								
							}
							
							return true;
							
					}
					
				default:
					if (isPlayer) {
						msg(player, "errorGrenadeFormat");
					} else {
						consoleMsg("errorCommandFormat");
					}
					
					return true;
  				
  				}
	  			
  			//Command: giveArmor
  			case "giveArmor":
  			case "givearmor":
  				
	  				switch (args.length) {
					
					case 1:
						
						switch ( args[0].toLowerCase() ) {
						
							case "pvthelm":
							case "pvtchest":
							case "pvtlegs":
							case "pvtboots":
								
								if (isPlayer) {
									
									if ( player.isOp() ) {
										player.getInventory().addItem( new ItemSmith(language).makeArmor( args[0] ) );
									} else {
										msg(player, "errorIllegalCommand");
									}
									
								} else {
									consoleMsg("errorPlayerCommand");
								}
								
								return true;
								
						}	
						
					case 2:
						
						switch ( args[0].toLowerCase() ) {
						
						case "pvthelm":
						case "pvtchest":
						case "pvtlegs":
						case "pvtboots":
								
								try {
									
									if ( isPlayer ) {
										if ( player.isOp() ) {
											Player receiver = (Player) getServer().getPlayer(args[1]);
											receiver.getInventory().addItem( new ItemSmith(language).makeArmor( args[0] ) );
										} else {
											msg(player, "errorIllegalCommand");
										}
									} else {
										consoleMsg("errorPlayerCommand");
									}
									
								} catch (NullPointerException npe) {
									if (isPlayer) {
										msg(player, "errorPlayerNotFound");
									} else {
										consoleMsg("errorPlayerNotFound");
									}
								}
								
								return true;
								
						}
						
					default:
						if (isPlayer) {
							msg(player, "errorArmorFormat");
						} else {
							consoleMsg("errorCommandFormat");
						}
						
						return true;
				
				}
	        
			default:
				
				if (isPlayer) {
					msg(player, "errorIllegalCommand");
				} else {
					consoleMsg("errorCommandFormat");
				}
	        	
	        	return true;
		
		}
		
	}
	
		
}
