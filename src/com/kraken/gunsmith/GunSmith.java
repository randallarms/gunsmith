// =========================================================================
// |GUNSMITH v1.7 (EpiCenter) | for Minecraft v1.12
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

import java.io.File;
import java.util.ArrayList;
import java.util.WeakHashMap;

import org.bukkit.Bukkit;

public class GunSmith extends JavaPlugin implements Listener {
	
	//Lang vars
	public static String VERSION = "1.7 (EpiCenter)";
	String language;
	ArrayList<String> languages = new ArrayList<String>();
	Messages messenger;
	
	//Class vars
	GSListener listener;
	RecipeSmith recipes;
	GunSmithGUI gui = new GunSmithGUI(language);
	
	//Options
	WeakHashMap<String, Boolean> options = new WeakHashMap<>();
	
	//Enable
    @Override
    public void onEnable() {
    	
    	getLogger().info("Loading options...");
		
		//Copies the default config.yml from within the .jar if "plugins/config.yml" does not exist
		this.getConfig().options().copyDefaults(true);
		
		//Language/Messages handler class construction
		languages.add("english");
		languages.add("spanish");
		languages.add("chinese");
		loadMessageFiles();
		this.language = getConfig().getString("language");
		this.messenger = new Messages(this, "english");
		
		//General plugin management
    	PluginManager pm = getServer().getPluginManager();
    	listener = new GSListener(this, language);
		pm.registerEvents(listener, this);
		
		//Language setting
		setLanguage(language);
		
	    //Loading default settings into options
    	setOption( "guiEnabled", getConfig().getBoolean("guiEnabled") );
    	setOption( "opRequired", getConfig().getBoolean("opRequired") );
    	setOption( "explosions", getConfig().getBoolean("explosions") );
    	setOption( "permissions", getConfig().getBoolean("permissions") );
    	setOption( "glassBreak", getConfig().getBoolean("glassBreak") );
    	setOption( "crafting", getConfig().getBoolean("crafting") );
    	setOption( "particles", getConfig().getBoolean("particles") );
    	setOption( "silentMode", getConfig().getBoolean("silentMode") );
    	silencer( options.get("silentMode") );
    	
		//Custom recipes
		recipes = new RecipeSmith(language);
		if ( options.get("crafting") ) {
			addRecipes();
		}
    	
    	getLogger().info("Finished loading!");
			
    }
    
    //Disable
    @Override
    public void onDisable() {
        getLogger().info("Disabling...");
    }
    
    //Messages
    public void msg(Player player, String cmd) {
    	messenger.makeMsg(player, cmd);
    }
    
    public void consoleMsg(String cmd) {
    	messenger.makeConsoleMsg(cmd);
    }
    
    //Setting methods
    //Options setting
    public void setOption(String option, boolean setting) {
    	getConfig().set(option, setting);
    	saveConfig();
    	options.put(option, setting);
    	listener.setOption(option, setting);
    	getLogger().info(option + " setting: " + setting );
    }
    
    //Language setting
    public void setLanguage(String language) {
    	this.language = language;
    	getConfig().set("language", language);
    	saveConfig();
    	listener.setLanguage(language);
    	messenger.setLanguage(language);
    	getLogger().info( "Language: " + language.toUpperCase() );
    }
    
	public void loadMessageFiles() {
		
		for (String lang : languages) {
			
		    File msgFile = new File("plugins/GunSmith/lang/", lang.toLowerCase() + ".yml");
	        
		    if ( !msgFile.exists() ) {
		    	saveResource("lang/" + lang.toLowerCase() + ".yml", false);
		    }
		    
		}
		
    }
    
    //Recipe setting
    public void addRecipes() {
		for (int n = 0; n < recipes.getTotal(); n++) {
			getServer().addRecipe( recipes.getRecipe(n) );
		}
    }
    
    //Silent mode setting
    public void silencer(boolean silentMode) {
    	messenger.silence(silentMode);
    }
    
    //GunSmith commands
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player player;
		boolean isPlayer = sender instanceof Player;
		
		this.messenger = new Messages(this, language);
		
		if (isPlayer) {
			player = (Player) sender;
		} else {
			player = Bukkit.getServer().getPlayerExact("Octopus__");
		}
		
		String command = cmd.getName();
		
		switch (command) {
		
			//Command: guns
			case "guns":
				
				switch (args.length) {
				
					case 0:
						
						if ( isPlayer ) {
							
							if ( options.get("opRequired") && !( player.isOp() ) ) {
								msg(player, "errorIllegalCommand");
							} else if ( options.get("permissions") && !player.hasPermission("gunsmith.guns") ) {
								msg(player, "errorPermissions");
							} else if ( options.get("guiEnabled") ) {
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
			                
			                default:
					
								if ( !isPlayer ) {
									consoleMsg("errorCommandFormat");
								} else {
									msg(player, "errorIllegalCommand");
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
							case "lang":
								
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
									
								//Glassbreak command handling
								switch ( args[1].toLowerCase() ) {
								
									case "true":
									case "enable":
									case "enabled":
									case "on":
									case "cierto":
										setOption("glassBreak", true);
										
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
										setOption("glassBreak", false);
										
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
								
							//Command: guns silentMode
							case "silentmode":
								
								switch ( args[1].toLowerCase() ) {
								
									case "true":
									case "enable":
									case "enabled":
									case "on":
									case "cierto":
										setOption("silentMode", true);
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
										setOption("silentMode", false);
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
							
						    //Command: guns GUI
							case "gui":
								
								switch ( args[1].toLowerCase() ) {
								
									case "true":
									case "enable":
									case "enabled":
									case "on":
									case "cierto":
										setOption("guiEnabled", true);
										
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
										setOption("guiEnabled", false);
	
										if ( !isPlayer ) {
											consoleMsg("cmdGUIDisabled");
										} else {
											msg(player, "cmdGUIDisabled");
										}
										
										return true;
								
									default: 
										
										if ( !isPlayer ) {
											consoleMsg("errorCommandFormat");
										} else {
											msg(player, "errorGUIToggleFormat");
										}
										
										return true;
								
								}
								
						    //Command: guns opReq
			        	    case "oprequired":
			        	    case "opreq":
			        	    		
			        	    		switch ( args[1].toLowerCase() ) {
			        	    		
			        	    			case "on":
			        	    			case "cierto":
			        	    			case "enable":
			        	    			case "enabled":
			        	    			case "true":
			        	    				setOption("opRequired", true);
											
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
			        	    				setOption("opRequired", false);
											
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
			        	    	
			        	    //Command: guns perms
			        	    case "perms":
			        	    case "permissions":
			        	    case "perm":
			        	    case "permission":
			        	    		
			        	    		switch ( args[1].toLowerCase() ) {
			        	    		
			        	    			case "on":
			        	    			case "cierto":
			        	    			case "enable":
			        	    			case "enabled":
			        	    			case "true":
			        	    				setOption("permissions", true);
											
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
			        	    				setOption("permissions", false);
											
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
			        	    		
			        	    //Command: guns crafting
			        	    case "crafting":
			        	    case "craft":
			        	    	
		        	    		switch ( args[1].toLowerCase() ) {
		        	    		
		        	    			case "on":
		        	    			case "cierto":
		        	    			case "enable":
		        	    			case "enabled":
		        	    			case "true":
		        	    				setOption("crafting", true);
										
										if ( !isPlayer ) {
											consoleMsg("cmdCraftingEnabled");
										} else {
											msg(player, "cmdCraftingEnabled");
										}
										
		        	    				return true;
		        	    				
		        	    			case "off":
		        	    			case "disable":
		        	    			case "disabled":
		        	    			case "false":
		        	    			case "falso":
		        	    				setOption("crafting", false);
		        	    				
										if ( !isPlayer ) {
											consoleMsg("cmdCraftingDisabled");
										} else {
											msg(player, "cmdCraftingDisabled");
										}
										
		        	    				return true;
		        	    				
		        	    			default:
		        	    				if ( !isPlayer ) {
		        	    					consoleMsg("errorArgumentFormat");
		        	    				} else {
		        	    					msg(player, "errorCraftingFormat");
		        	    				}
		        	    				
		        	        	    	return true;
		        	        	    	
		        	    		}
			        	    
		        	    	//Command: guns particles
			        	    case "particles":
			        	    case "smoke":
			        	    case "trail":
			        	    	
		        	    		switch ( args[1].toLowerCase() ) {
		        	    		
		        	    			case "on":
		        	    			case "cierto":
		        	    			case "enable":
		        	    			case "enabled":
		        	    			case "true":
		        	    				setOption("particles", true);
										
										if ( !isPlayer ) {
											consoleMsg("cmdParticlesEnabled");
										} else {
											msg(player, "cmdParticlesEnabled");
										}
										
		        	    				return true;
		        	    				
		        	    			case "off":
		        	    			case "disable":
		        	    			case "disabled":
		        	    			case "false":
		        	    			case "falso":
		        	    				setOption("particles", false);
		        	    				
										if ( !isPlayer ) {
											consoleMsg("cmdParticlesDisabled");
										} else {
											msg(player, "cmdParticlesDisabled");
										}
										
		        	    				return true;
		        	    				
		        	    			default:
		        	    				if ( !isPlayer ) {
		        	    					consoleMsg("errorArgumentFormat");
		        	    				} else {
		        	    					msg(player, "errorParticlesFormat");
		        	    				}
		        	    				
		        	        	    	return true;
		        	        	    	
		        	    		}
		        	    		
			        	    //Command: guns explosions
			        	    case "explosions":
			        	    case "explosion":
			        	    case "explosives":
			        			  
		        	    		switch ( args[1].toLowerCase() ) {
		        	    		
		        	    			case "on":
		        	    			case "cierto":
		        	    			case "enable":
		        	    			case "enabled":
		        	    			case "true":
		        	    				setOption("explosions", true);
										
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
		        	    				setOption("explosions", false);
										
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
						
						//Find target of command to give gun
						Player target;
						
						//1 arg = self
						if (args.length < 2) {
							if ( isPlayer && player.isOp() ) {
								target = player;
							} else {
								consoleMsg("errorPlayerCommand");
								return true;
							}
						//2 args = another player
						} else {
							target = getServer().getPlayer(args[1]);
						}
						
						//Give gun to player
						try {
							
							boolean success = new ItemSmith(language).giveGun( args[0], target, 1 );
							
							if ( success && !options.get("silentMode") ) {
					    		new Messages(this, language).makeMsg(target, "cmdGiveGun");
					    	}
							
							return success;
							
						} catch (NullPointerException npe) {
							
							if (isPlayer) {
								msg(player, "errorPlayerNotFound");
							} else {
								consoleMsg("errorPlayerNotFound");
							}
							
							return true;
							
						}
						
					default:
						
						if (isPlayer) {
							msg(player, "errorGunFormat");
						} else {
							consoleMsg("errorCommandFormat");
						}
						
						return true;
				
				}
		        
	        //Command: giveAmmo <ammoName> <player?>
			case "giveAmmo":
			case "giveammo":
				
				switch (args.length) {
				
					case 1:
					case 2:
						
						//Find target of command to give gun
						Player target;
						
						//1 arg = self
						if (args.length < 2) {
							if ( isPlayer && player.isOp() ) {
								target = player;
							} else {
								consoleMsg("errorPlayerCommand");
								return true;
							}
						//2 args = another player
						} else {
							target = getServer().getPlayer(args[1]);
						}
						
						//Give ammo
						try {
							
							boolean success = new ItemSmith(language).giveAmmo(args[0], target, 64);
							
							if ( success && !options.get("silentMode") ) {
								new Messages(this, language).makeMsg(target, "cmdGiveAmmo");
					    	}
							
							return success;
							
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
