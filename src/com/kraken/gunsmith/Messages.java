package com.kraken.gunsmith;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Messages {

	GunSmith plugin;
	String language;
	String VERSION;
	boolean silentMode;
	
    File msgFile;
    FileConfiguration msgFileConfig;
	
    //Constructor
	public Messages(GunSmith plugin, String language) {
		
		  this.plugin = plugin;
          this.silentMode = plugin.getConfig().getBoolean("silentMode");
          this.VERSION = GunSmith.VERSION;
          
          setLanguage(language);
          
    }
	
	//Silent mode settings
	public void silence(boolean setting) {
		this.silentMode = setting;
	}
	
	//Language settings
	public void setLanguage (String language) {
		this.language = language.toLowerCase();
		msgFile = new File("plugins/GunSmith/lang/", language.toLowerCase() + ".yml");
		msgFileConfig = YamlConfiguration.loadConfiguration(msgFile);
	}
	
	//Console messages
	public void makeConsoleMsg(String msg) {
		
		if (this.silentMode) {
			return;
		}
		
		switch (msg) {					
				
			case "cmdVersion":
				System.out.println("v" + VERSION);
				break;
				
			default:
				System.out.println( msgFileConfig.getString("console." + msg) );
				break;
				
		}
		
	}
	
	//Player messages (in-game)
	public void makeMsg(Player player, String msg) {
		
		if (this.silentMode && !msg.equals("errorSilentMode") && !msg.equals("cmdSilentOn") && !msg.equals("cmdSilentOff")) {
			return;
		}
		
		switch (msg) {
		
			case "cmdVersion":
				player.sendMessage( ChatColor.translateAlternateColorCodes('&', msgFileConfig.getString("player." + msg) + VERSION) );
				break;
				
			default:
				player.sendMessage( ChatColor.translateAlternateColorCodes('&', msgFileConfig.getString("player." + msg)) );
				break;
		
		}
	
	}
		
}
