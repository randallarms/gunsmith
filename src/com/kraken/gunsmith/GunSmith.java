// =========================================================================
// |GUNSMITH v1.9.3 (EpiCenter) | for Minecraft v1.12
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
	public static String VERSION = "1.9.3 (EpiCenter)";
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
		
		//Copies the default config.yml from within the .jar if "plugins/GunSmith/config.yml" does not exist
		getConfig().options().copyDefaults(true);
		
    	//Copies the default guns.yml from within the .jar if "plugins/GunSmith/guns.yml" does not exist
    	saveResource("guns.yml", false);
		
		//Language/Messages handler class construction
		languages.add("english");
		languages.add("spanish");
		languages.add("chinese");
		loadMessageFiles();
		language = getConfig().getString("language");
		messenger = new Messages(this, "english");
		
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

		//Command handling
		ConsoleCommands commands = new ConsoleCommands(this);
		String command = cmd.getName();
		
		//Player handling
		Player player;
		boolean isPlayer = sender instanceof Player;
		
		if (isPlayer) {
			player = (Player) sender;
		} else {
			player = Bukkit.getServer().getPlayerExact("Octopus__");
		}
		
		//Execute command & return
		return commands.execute(isPlayer, player, command, args);
		
	}
	
		
}
