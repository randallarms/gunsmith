package com.kraken.gunsmith;

import java.util.WeakHashMap;

import org.bukkit.entity.Player;

public class ConsoleCommands {
	
	  GunSmith plugin;
	  String VERSION;
	  
	  String language;
	  Messages messenger;
	  
	  //Options
	  WeakHashMap<String, Boolean> options = new WeakHashMap<>();
	
  //Constructor
	public ConsoleCommands(GunSmith plugin) {
		
		this.plugin = plugin;
		this.VERSION = GunSmith.VERSION;
		this.options = plugin.options;
		
		this.language = plugin.language;
		this.messenger = new Messages(plugin, language);
	
	}
	
  //Commands
	public boolean execute(boolean isPlayer, Player player, String command, String[] args) {
		
		switch (command) {
		
			//Command: guns
			case "guns":
				
				switch (args.length) {
				
					case 0:
						
						if ( isPlayer ) {
							
							if ( options.get("opRequired") && !( player.isOp() ) ) {
								plugin.msg(player, "errorIllegalCommand");
							} else if ( options.get("permissions") && !player.hasPermission("gunsmith.guns") ) {
								plugin.msg(player, "errorPermissions");
							} else if ( options.get("guiEnabled") ) {
								GunSmithGUI.openGSGUI(player);
							} else {
								plugin.msg(player, "errorGUINotEnabled");
							}
							
						} else {
							plugin.consoleMsg("errorPlayerCommand");
						}
						
						return true;
						
					case 1:
						
						switch ( args[0].toLowerCase() ) {
						
						  //Command: version     
			    			case "version":
			    			
								if ( !isPlayer ) {
									plugin.consoleMsg("cmdVersion");
								} else {
									plugin.msg(player, "cmdVersion");
								}
								
				                return true;
			                
			                default:
					
								if ( !isPlayer ) {
									plugin.consoleMsg("errorCommandFormat");
								} else {
									plugin.msg(player, "errorIllegalCommand");
								}
								
				                return true;
			                	
						}
						
					case 2:
						
						//Check if sender is a player and if that player has OP perms
						if (isPlayer) {
							
							if ( !player.isOp() ) {
								plugin.msg(player, "errorIllegalCommand");
								return true;
							}
							
						}
						
						//Command handling switch
						switch ( args[0].toLowerCase() ) {
						
							case "language":
							case "lang":
								
								String lang = args[1].toLowerCase();
								
								//Language command handling
								if ( plugin.languages.contains( lang ) ) {
									
									plugin.setLanguage(lang);
									
									if ( !isPlayer ) {
										plugin.consoleMsg("cmdLanguageSet");
									} else {
										plugin.msg(player, "cmdLang");
									}
									
									return true;
								
								//Language command error handling
								} else {
									
									if ( !isPlayer ) {
										plugin.consoleMsg("errorLanguageSet");
									} else {
										plugin.msg(player, "errorLangNotFound");
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
										plugin.setOption("glassBreak", true);
										
										if ( !isPlayer ) {
											plugin.consoleMsg("cmdGlassBreakOn");
										} else {
											plugin.msg(player, "cmdGlassBreakOn");
										}
										
										return true;
										
									case "false":
									case "disable":
									case "disabled":
									case "off":
									case "falso":
										plugin.setOption("glassBreak", false);
										
										if ( !isPlayer ) {
											plugin.consoleMsg("cmdGlassBreakOff");
										} else {
											plugin.msg(player, "cmdGlassBreakOff");
										}
										
										return true;
									
									//Glassbreak command error handling
									default: 
										
										if ( !isPlayer ) {
											plugin.consoleMsg("errorCommandFormat");
										} else {
											plugin.msg(player, "errorGlassBreakFormat");
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
										plugin.setOption("silentMode", true);
										plugin.silencer(true);
										
										if ( !isPlayer ) {
											plugin.consoleMsg("cmdSilentModeOn");
										} else {
											plugin.msg(player, "cmdSilentOn");
										}
										
										return true;
										
									case "false":
									case "disable":
									case "disabled":
									case "off":
									case "falso":
										plugin.setOption("silentMode", false);
										plugin.silencer(false);
										
										if ( !isPlayer ) {
											plugin.consoleMsg("cmdSilentModeOff");
										} else {
											plugin.msg(player, "cmdSilentOff");
										}
										
										return true;
								
								  //Silentmode command error handling
									default: 
										
										if ( !isPlayer ) {
											plugin.consoleMsg("errorCommandFormat");
										} else {
											plugin.msg(player, "errorSilentModeFormat");
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
										plugin.setOption("guiEnabled", true);
										
										if ( !isPlayer ) {
											plugin.consoleMsg("cmdGUIEnabled");
										} else {
											plugin.msg(player, "cmdGUIEnabled");
										}
										
										return true;
										
									case "false":
									case "disable":
									case "disabled":
									case "off":
									case "falso":
										plugin.setOption("guiEnabled", false);
	
										if ( !isPlayer ) {
											plugin.consoleMsg("cmdGUIDisabled");
										} else {
											plugin.msg(player, "cmdGUIDisabled");
										}
										
										return true;
								
									default: 
										
										if ( !isPlayer ) {
											plugin.consoleMsg("errorCommandFormat");
										} else {
											plugin.msg(player, "errorGUIToggleFormat");
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
			        	    				plugin.setOption("opRequired", true);
											
											if ( !isPlayer ) {
												plugin.consoleMsg("cmdOpReqEnabled");
											} else {
												plugin.msg(player, "cmdOpReqEnabled");
											}
											
			        	    				return true;
			        	    				
			        	    			case "off":
			        	    			case "disable":
			        	    			case "disabled":
			        	    			case "false":
			        	    			case "falso":
			        	    				plugin.setOption("opRequired", false);
											
											if ( !isPlayer ) {
												plugin.consoleMsg("cmdOpReqDisabled");
											} else {
												plugin.msg(player, "cmdOpReqDisabled");
											}
											
			        	    				return true;
			        	    				
			        	    			default:
			        	    				if ( !isPlayer ) {
			        	    					plugin.consoleMsg("errorArgumentFormat");
			        	    				} else {
			        	    					plugin.msg(player, "errorOpReqFormat");
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
			        	    				plugin.setOption("permissions", true);
											
											if ( !isPlayer ) {
												plugin.consoleMsg("cmdPermsEnabled");
											} else {
												plugin.msg(player, "cmdPermsEnabled");
											}
											
			        	    				return true;
			        	    				
			        	    			case "off":
			        	    			case "disable":
			        	    			case "disabled":
			        	    			case "false":
			        	    			case "falso":
			        	    				plugin.setOption("permissions", false);
											
											if ( !isPlayer ) {
												plugin.consoleMsg("cmdPermsDisabled");
											} else {
												plugin.msg(player, "cmdPermsDisabled");
											}
											
			        	    				return true;
			        	    				
			        	    			default:
			        	    				if ( !isPlayer ) {
			        	    					plugin.consoleMsg("errorArgumentFormat");
			        	    				} else {
			        	    					plugin.msg(player, "errorPermsFormat");
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
		        	    				plugin.setOption("crafting", true);
										
										if ( !isPlayer ) {
											plugin.consoleMsg("cmdCraftingEnabled");
										} else {
											plugin.msg(player, "cmdCraftingEnabled");
										}
										
		        	    				return true;
		        	    				
		        	    			case "off":
		        	    			case "disable":
		        	    			case "disabled":
		        	    			case "false":
		        	    			case "falso":
		        	    				plugin.setOption("crafting", false);
		        	    				
										if ( !isPlayer ) {
											plugin.consoleMsg("cmdCraftingDisabled");
										} else {
											plugin.msg(player, "cmdCraftingDisabled");
										}
										
		        	    				return true;
		        	    				
		        	    			default:
		        	    				if ( !isPlayer ) {
		        	    					plugin.consoleMsg("errorArgumentFormat");
		        	    				} else {
		        	    					plugin.msg(player, "errorCraftingFormat");
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
		        	    				plugin.setOption("particles", true);
										
										if ( !isPlayer ) {
											plugin.consoleMsg("cmdParticlesEnabled");
										} else {
											plugin.msg(player, "cmdParticlesEnabled");
										}
										
		        	    				return true;
		        	    				
		        	    			case "off":
		        	    			case "disable":
		        	    			case "disabled":
		        	    			case "false":
		        	    			case "falso":
		        	    				plugin.setOption("particles", false);
		        	    				
										if ( !isPlayer ) {
											plugin.consoleMsg("cmdParticlesDisabled");
										} else {
											plugin.msg(player, "cmdParticlesDisabled");
										}
										
		        	    				return true;
		        	    				
		        	    			default:
		        	    				if ( !isPlayer ) {
		        	    					plugin.consoleMsg("errorArgumentFormat");
		        	    				} else {
		        	    					plugin.msg(player, "errorParticlesFormat");
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
		        	    				plugin.setOption("explosions", true);
										
										if ( !isPlayer ) {
											plugin.consoleMsg("cmdExplosionsEnabled");
										} else {
											plugin.msg(player, "cmdExplosionsEnabled");
										}
										
		        	    				return true;
		        	    				
		        	    			case "off":
		        	    			case "disable":
		        	    			case "disabled":
		        	    			case "false":
		        	    			case "falso":
		        	    				plugin.setOption("explosions", false);
										
										if ( !isPlayer ) {
											plugin.consoleMsg("cmdExplosionsDisabled");
										} else {
											plugin.msg(player, "cmdExplosionsDisabled");
										}
										
		        	    				return true;
		        	    				
		        	    			default:
		        	    				if ( !isPlayer ) {
		        	    					plugin.consoleMsg("errorArgumentFormat");
		        	    				} else {
		        	    					plugin.msg(player, "errorExplosionsFormat");
		        	    				}
		        	    				
		        	        	    	return true;
		        	        	    	
		        	    		}
			        	    	
						}
								
					default:
						if (isPlayer) {
							plugin.msg(player, "errorIllegalCommand");
							return true;
						} else {
							plugin.consoleMsg("errorCommandFormat");
						}
						
			}
	                
	        //Command: giveGun <gunName/id> <player?>
			case "giveGun":
			case "givegun":
				
				switch (args.length) {
				
					case 1:
					case 2:
						
						//Find target of command to give gun
						Player target;
						
						//1 arg = self
						if (args.length == 1) {
							if ( isPlayer && player.isOp() ) {
								target = player;
							} else {
								plugin.consoleMsg("errorPlayerCommand");
								return true;
							}
							
						//2 args = another player
						} else if (args.length == 2) {
							target = plugin.getServer().getPlayer(args[1]);
							
						//0, 3+ args = argument format error
						} else {
							
							if ( isPlayer && player.isOp() ) {
								plugin.msg(player, "errorGunFormat");
							} else {
								plugin.consoleMsg("errorArgumentFormat");
							}
							
							return true;
							
						}
						
						//Give gun to player
						try {
							
							boolean success = new ItemSmith(language).giveGun( args[0], target );
							
							if ( success ) {
					    		plugin.msg(target, "cmdGiveGun");
					    	} else {
					    		plugin.msg(target, "errorGunFormat");
					    	}
							
						} catch (NullPointerException npe) {
							
							if (isPlayer) {
								plugin.msg(player, "errorGunFormat");
							} else {
								plugin.consoleMsg("errorGunFormat");
							}
							
						}
						
						return true;
						
					default:
						
						if (isPlayer) {
							plugin.msg(player, "errorGunFormat");
						} else {
							plugin.consoleMsg("errorCommandFormat");
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
								plugin.consoleMsg("errorPlayerCommand");
								return true;
							}
						//2 args = another player
						} else {
							target = plugin.getServer().getPlayer(args[1]);
						}
						
						//Give ammo
						try {
							
							boolean success = new ItemSmith(language).giveAmmo(args[0], target, 64);
							
							if ( success && !options.get("silentMode") ) {
								plugin.msg(target, "cmdGiveAmmo");
					    	}
							
							return success;
							
						} catch (NullPointerException npe) {
							
							if (isPlayer) {
								plugin.msg(player, "errorPlayerNotFound");
							} else {
								plugin.consoleMsg("errorPlayerNotFound");
							}
							
							return true;
							
						} catch (ArrayIndexOutOfBoundsException aie) {
							
							if (isPlayer) {
								plugin.msg(player, "errorIllegalCommand");
							} else {
								plugin.consoleMsg("errorArgumentFormat");
							}
							
							return true;
							
						}
						
					default:
						
						if (isPlayer) {
							plugin.msg(player, "errorAmmoFormat");
						} else {
							plugin.consoleMsg("errorCommandFormat");
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
									plugin.msg(player, "errorIllegalCommand");
								}
								
							} else {
								plugin.consoleMsg("errorPlayerCommand");
							}
							
							return true;
							
					}	
					
				case 2:
					
					switch ( args[0].toLowerCase() ) {
					
					case "frag":
							
							try {
								
								if ( isPlayer ) {
									if ( player.isOp() ) {
										Player receiver = (Player) plugin.getServer().getPlayer(args[1]);
										receiver.getInventory().addItem( new ItemSmith(language).makeGrenade( args[0] ) );
									} else {
										plugin.msg(player, "errorIllegalCommand");
									}
								} else {
									plugin.consoleMsg("errorPlayerCommand");
								}
								
							} catch (NullPointerException npe) {
								
								if (isPlayer) {
									plugin.msg(player, "errorPlayerNotFound");
								} else {
									plugin.consoleMsg("errorPlayerNotFound");
								}
								
							}
							
							return true;
							
					}
					
				default:
					if (isPlayer) {
						plugin.msg(player, "errorGrenadeFormat");
					} else {
						plugin.consoleMsg("errorCommandFormat");
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
										plugin.msg(player, "errorIllegalCommand");
									}
									
								} else {
									plugin.consoleMsg("errorPlayerCommand");
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
											Player receiver = (Player) plugin.getServer().getPlayer(args[1]);
											receiver.getInventory().addItem( new ItemSmith(language).makeArmor( args[0] ) );
										} else {
											plugin.msg(player, "errorIllegalCommand");
										}
									} else {
										plugin.consoleMsg("errorPlayerCommand");
									}
									
								} catch (NullPointerException npe) {
									if (isPlayer) {
										plugin.msg(player, "errorPlayerNotFound");
									} else {
										plugin.consoleMsg("errorPlayerNotFound");
									}
								}
								
								return true;
								
						}
						
					default:
						if (isPlayer) {
							plugin.msg(player, "errorArmorFormat");
						} else {
							plugin.consoleMsg("errorCommandFormat");
						}
						
						return true;
				
				}
	        
			default:
				
				if (isPlayer) {
					plugin.msg(player, "errorIllegalCommand");
				} else {
					plugin.consoleMsg("errorCommandFormat");
				}
	        	
	        	return true;
		
		}
		
	}
	
}
