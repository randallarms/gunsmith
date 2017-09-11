package com.kraken.gunsmith;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Messages {

	String language;
	String VERSION;
	
	boolean silentMode;
	
	public Messages(String language) {
          this.language = language;
          this.silentMode = Bukkit.getPluginManager().getPlugin("GunSmith").getConfig().getBoolean("silentMode");
          this.VERSION = GunSmith.VERSION;
    }
	
	public void silence(boolean setting) {
		this.silentMode = setting;
	}
	
	public void setLanguage (String language) {
		this.language = language;
	}
	
	public void makeConsoleMsg(String msg) {
		
		if (this.silentMode) {
			
			return;
			
		}
		
		switch (language) {
		
			case "Chinese":
			case "chinese":
			
				//Start of Chinese messages
				switch (msg) {
				
					case "errorPlayerCommand":
						System.out.println("只有玩家才能这么做");
						break;
						
					case "errorCommandFormat":
						System.out.println("指令输入错误");
						break;
						
					case "errorArgumentFormat":
						System.out.println("参数不正确");
						break;						
						
					case "cmdVersion":
						System.out.println("v" + VERSION);
						break;
						
					case "cmdGlassBreakOn":
						System.out.println("现在枪可以破坏玻璃");
						break;
						
					case "cmdGlassBreakOff":
						System.out.println("现在枪不能破坏玻璃");
						break;
						
					case "cmdSilentModeOn":
						System.out.println("静默模式已开启");
						break;
						
					case "cmdSilentModeOff":
						System.out.println("静默模式已关闭");
						break;
						
					case "cmdLanguageSet":
						System.out.println("语言已设置");
						break;
						
					case "errorLanguageSet":
						System.out.println("语言未找到");
						break;
						
					case "cmdGUIEnabled":
						System.out.println("GUI 已启用");
						break;
						
					case "cmdGUIDisabled":
						System.out.println("GUI 未启用");
						break;
						
					case "cmdOpReqEnabled":
						System.out.println("Op 需求已启用");
						break;
						
					case "cmdOpReqDisabled":
						System.out.println("Op 需求未启用");
						break;
						
					case "errorPlayerNotFound":
						System.out.println("玩家未找到!");
						break;
						
					case "cmdPermsEnabled":
						System.out.println("权限需求未找到. 权限: \"gunsmith.guns\"");
						break;
						
					case "cmdPermsDisabled":
						System.out.println("已禁用权限需求");
						break;
						
					case "cmdExplosionsEnabled":
						System.out.println("爆炸已启用");
						break;
						
					case "cmdExplosionsDisabled":
						System.out.println("爆炸已禁用");
						break;
						
					//Untranslated
					case "cmdCraftingEnabled":
						System.out.println("Crafting firearm recipes are enabled.");
						break;
						
					case "cmdCraftingDisabled":
						System.out.println("Crafting firearm recipes are disabled.");
						break;
						
					case "cmdParticlesEnabled":
						System.out.println("Particle smoke trails on gunshot are enabled.");
						break;
						
					case "cmdParticlesDisabled":
						System.out.println("Particle smoke trails on gunshot are disabled.");
						break;
						
					default:
						
						break;
					
			}
			break;	
			//End of Chinese messages
		
			case "Spanish":
			case "spanish":
				
				//Start of Spanish messages
				switch (msg) {
				
					case "errorPlayerCommand":
						System.out.println("Este comando es solo para juegadores.");
						break;
						
					case "errorCommandFormat":
						System.out.println("No reconocido el comando.");
						break;
						
					case "cmdVersion":
						System.out.println("v" + VERSION);
						break;
						
					case "cmdGlassBreakOn":
						System.out.println("Rompiendo los vidrios estan habilitado.");
						break;
						
					case "cmdGlassBreakOff":
						System.out.println("Rompiendo los vidrios no mas estan habilitado.");
						break;
						
					case "cmdSilentModeOn":
						System.out.println("El modo silencioso estan habilitado.");
						break;
						
					case "cmdSilentModeOff":
						System.out.println("No mas estan habilitado el modo silencioso.");
						break;
						
					case "cmdLanguageSet":
						System.out.println("El idioma ha sido establecido.");
						break;
						
					case "errorLanguageSet":
						System.out.println("No reconocido el idioma.");
						break;
						
					case "cmdGUIEnabled":
						System.out.println("El menu de armas estan habilitado.");
						break;
						
					case "cmdGUIDisabled":
						System.out.println("No mas estan habilitado el menu de armas.");
						break;
						
					case "cmdOpReqEnabled":
						System.out.println("Necesita los permisos 'op' para usar el menu de armas.");
						break;
						
					case "cmdOpReqDisabled":
						System.out.println("No mas necesita los permisos 'op' para usar el menu de armas.");
						break;
						
					case "cmdPermsEnabled":
						System.out.println("Los permisos estan necesarios. El permiso: \"gunsmith.guns\"");
						break;
						
					case "cmdPermsDisabled":
						System.out.println("No mas estan necesarios los permisos.");
						break;
						
					case "cmdExplosionsEnabled":
						System.out.println("Explosiones estan habilitado.");
						break;
						
					case "cmdExplosionsDisabled":
						System.out.println("No mas estan habilitado los explosiones.");
						break;
						
					case "cmdCraftingEnabled":
						System.out.println("El fabricacion de armas estan habilitado.");
						break;
						
					case "cmdCraftingDisabled":
						System.out.println("No mas estan habilitado el fabricacion de armas.");
						break;
						
					case "cmdParticlesEnabled":
						System.out.println("Las partículas de disparo estan habilitado.");
						break;
						
					case "cmdParticlesDisabled":
						System.out.println("No mas estan habilitado las partículas de disparo.");
						break;
						
					default:
						
						break;
						
				}
				break;
				//End of Spanish messages
				
				
			default:
				
				//Start of English messages
				switch (msg) {
				
					case "errorPlayerCommand":
						System.out.println("This is a player-only command.");
						break;
						
					case "errorCommandFormat":
						System.out.println("Command not recognized.");
						break;
						
					case "errorArgumentFormat":
						System.out.println("Unrecognized arguments.");
						break;						
						
					case "cmdVersion":
						System.out.println("v" + VERSION);
						break;
						
					case "cmdGlassBreakOn":
						System.out.println("Glass-break on gunshot is enabled.");
						break;
						
					case "cmdGlassBreakOff":
						System.out.println("Glass-break on gunshot is disabled.");
						break;
						
					case "cmdSilentModeOn":
						System.out.println("Silent mode is enabled.");
						break;
						
					case "cmdSilentModeOff":
						System.out.println("Silent mode is disabled.");
						break;
						
					case "cmdLanguageSet":
						System.out.println("Language set.");
						break;
						
					case "errorLanguageSet":
						System.out.println("Language not found.");
						break;
						
					case "cmdGUIEnabled":
						System.out.println("Guns GUI is enabled.");
						break;
						
					case "cmdGUIDisabled":
						System.out.println("Guns GUI is disabled.");
						break;
						
					case "cmdOpReqEnabled":
						System.out.println("Op requirement is enabled.");
						break;
						
					case "cmdOpReqDisabled":
						System.out.println("Op requirement is disabled.");
						break;
						
					case "errorPlayerNotFound":
						System.out.println("Player not found!");
						break;
						
					case "cmdPermsEnabled":
						System.out.println("Permissions requirement is enabled. Perm: \"gunsmith.guns\"");
						break;
						
					case "cmdPermsDisabled":
						System.out.println("Permissions requirement is disabled.");
						break;
						
					case "cmdExplosionsEnabled":
						System.out.println("Explosions are enabled.");
						break;
						
					case "cmdExplosionsDisabled":
						System.out.println("Explosions are disabled.");
						break;
						
					case "cmdCraftingEnabled":
						System.out.println("Crafting firearm recipes are enabled.");
						break;
						
					case "cmdCraftingDisabled":
						System.out.println("Crafting firearm recipes are disabled.");
						break;
						
					case "cmdParticlesEnabled":
						System.out.println("Particle smoke trails on gunshot are enabled.");
						break;
						
					case "cmdParticlesDisabled":
						System.out.println("Particle smoke trails on gunshot are disabled.");
						break;
						
					default:
						
						break;
						
				}
				break;
				//End of English messages
		
		}
		
	}
	
	public void makeMsg(Player player, String msg) {
		
		if (this.silentMode && !msg.equals("errorSilentMode") && !msg.equals("cmdSilentOn") && !msg.equals("cmdSilentOff")) {
			
			return;
			
		}
		
		switch (language) {
		
			case "Chinese":
			case "chinese":
		
			  //Start of Chinese messages
				switch (msg) {
				
					case "errorIllegalCommand":
						player.sendMessage(ChatColor.RED + "指令不正确.");
						break;
				
					case "cmdGuns":
			        	player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | 输入 \"/givegun <gunName> 来让自己获得一把枪\"");
			        	player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | 名称: " 
			        					+ ChatColor.GREEN + "sniper" + ChatColor.GRAY + " | "
			        					+ ChatColor.GREEN + "br" + ChatColor.GRAY + "/" + ChatColor.GREEN + "来复枪" + ChatColor.GRAY + " | "
			        					+ ChatColor.GREEN + "lmg" + ChatColor.GRAY + "/" + ChatColor.GREEN + "轻机枪" + ChatColor.GRAY + " | "
			        					+ ChatColor.GREEN + "pistol" + ChatColor.GRAY + " | "
			        					+ ChatColor.GREEN + "bow");
			        	break;
						
					case "errorGUINotEnabled":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | GUI 已被禁止");
						break;	
						
					case "cmdOpReqEnabled":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | 现在GUI需要OP权限才能打开");
						break;
						
					case "cmdOpReqDisabled":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | 打开GUI不再需要OP权限了");
						break;
						
					case "errorOpReqFormat":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | 请输入 \"/guns opReq <on/off>\".");
						break;
			        	
					case "cmdGiveGun":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | 砰! 砰!");
						break;
						
					case "errorGunFormat":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | 错误的格式 请使用 \"/giveGun <gunName> {player}\"");
						break;
						
					case "cmdGiveAmmo":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | 已换弹夹");
						break;
					
					case "errorAmmoFormat":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | 错误的格式 请使用 \"/giveAmmo <gunName> {player}\"");
						break;
						
					case "errorPlayerNotFound":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | 玩家未找到!");
						break;
						
					case "errorWarZoneNotFound":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | 你需要 WarZone 包才能使用那个物品!");
						break;
						
					case "errorNoAmmoFound":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | 哎呀~子弹打光了~");
						break;
						
					case "cmdLang":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | 你的语言已被设置为中文");
						break;
						
					case "errorLangNotFound":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | 语言未找到");
						break;
						
					case "errorLangFormat":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | 错误的格式 请使用 \"/guns language <languageName>\"");
						break;
						
					case "cmdGlassBreakOn":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | 现在枪会打碎玻璃");
						break;
						
					case "cmdGlassBreakOff":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | 现在枪不会打碎玻璃了");
						break;
						
					case "errorGlassBreakFormat":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | 错误的格式 请使用 \"/guns glassbreak <true/false>\"");
						break;
						
					case "cmdSilentOn":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | 静默模式已开启");
						break;
						
					case "cmdSilentOff":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | 静默模式已关闭");
						break;
						
					case "errorSilentModeFormat":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | 错误的格式 请使用 \"/guns silentMode <true/false>\"");
						break;
						
					case "cmdGUIEnabled":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | GUI 已启用");
						break;
						
					case "cmdGUIDisabled":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | GUI 已禁用");
						break;
						
					case "errorGUIToggleFormat":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | 错误的格式 请使用 \"/guns gui <on/off>\"");
						break;
					
					case "cmdExplosionsEnabled":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | 爆炸已启用");
						break;
						
					case "cmdExplosionsDisabled":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | 爆炸已禁用");
						break;
						
					case "errorExplosionsFormat":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | 输入 \"/guns explosions <on/off>\".");
						break;
						
					case "errorPermissions":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | 你没有权限这么做!");
						break;	
						
					case "cmdPermsEnabled":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | 现在使用枪械需要权限了");
						break;
						
					case "cmdPermsDisabled":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | 现在使用枪械不需要权限了");
						break;
						
					case "errorPermsFormat":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | 输入 \"/guns perms <on/off>\".");
						break;	
						
					case "errorGrenadeFormat":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | 错误的格式 请使用 \"/giveGrenade <grenadeName> {player}\"");
						break;
						
					case "errorArmorFormat":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | 错误的格式 请使用 \"/giveArmor <armorName> {player}\"");
						break;
					
					case "cmdVersion":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | GunSmith v" + VERSION );
						break;
						
					//Untranslated
					case "cmdCraftingEnabled":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Firearm crafting is enabled.");
						break;
						
					case "cmdCraftingDisabled":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Firearm crafting is disabled.");
						break;
						
					case "errorCraftingFormat":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Try entering \"/guns crafting <on/off>\".");
						break;
						
					case "cmdParticlesEnabled":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Firearm crafting is enabled.");
						break;
						
					case "cmdParticlesDisabled":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Firearm crafting is disabled.");
						break;
						
					case "errorParticlesFormat":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Try entering \"/guns crafting <on/off>\".");
						break;
						
					default:
				
						break;
					
				} 
				break;
			  // End of Chinese messages
		
			case "Spanish":
			case "spanish":
				
				//Start of Spanish messages
				switch (msg) {
				
					case "errorIllegalCommand":
						player.sendMessage(ChatColor.RED + "No reconocido el comando, o no tienes el permiso.");
						break;
				
					case "cmdGuns":
			        	player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Teclea \"/givegun <nombreDeLaArma> para darse la arma.\"");
			        	player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Los nombres: " 
			        					+ ChatColor.GREEN + "sniper" + ChatColor.GRAY + " | "
			        					+ ChatColor.GREEN + "br" + ChatColor.GRAY + "/" + ChatColor.GREEN + "battleRifle" + ChatColor.GRAY + " | "
			        					+ ChatColor.GREEN + "lmg" + ChatColor.GRAY + "/" + ChatColor.GREEN + "lightMachineGun" + ChatColor.GRAY + " | "
			        					+ ChatColor.GREEN + "pistol" + ChatColor.GRAY + " | "
			        					+ ChatColor.GREEN + "bow");
			        	break;
						
					case "cmdGiveGun":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Â¡Pew pew!");
						break;
						
					case "cmdOpReqEnabled":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Los OP permisos estan necesario ahora por el menu.");
						break;
						
					case "cmdOpReqDisabled":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Los OP permisos no estan necesario ahora por el menu.");
						break;
						
					case "errorOpReqFormat":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | No reconocido el comando. Teclea \"/guns opReq <cierto/falso>\".");
						break;
						
					case "errorGUINotEnabled":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | El GUI es discapacitado.");
						break;
						
					case "errorGunFormat":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | No reconocido el comando. Teclea \"/giveGun <arma> {jugador}\"");
						break;
						
					case "cmdGiveAmmo":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Cierra y carga.");
						break;
					
					case "errorAmmoFormat":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | No reconocido el comando. Teclea \"/giveAmmo <arma> {jugador}\"");
						break;
						
					case "errorPlayerNotFound":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | ¡No reconocido el jugador!");
						break;
						
					case "errorWarZoneNotFound":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | ¡Requeras el paquete del WarZone para usar este item!");
						break;
						
					case "errorNoAmmoFound":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | No tienes mas municion.");
						break;
						
					case "cmdLang":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Ahora el lenguaje esta espanol.");
						break;
						
					case "errorLangNotFound":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | No reconocido el nombre del lenguaje.");
						break;
						
					case "errorLangFormat":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | No reconocido el comando. Teclea \"/guns language <idioma>\"");
						break;
						
					case "cmdGlassBreakOn":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Ahora los cartuchos romperan el vidrio.");
						break;
						
					case "cmdGlassBreakOff":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | No mas pueden romper el vidrio con los cartuchos.");
						break;
						
					case "errorGlassBreakFormat":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | No reconocido el comando. Teclea \"/guns glassbreak <cierto/falso>\"");
						break;
						
					case "cmdSilentOn":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Ahora esta en modo silencio.");
						break;
						
					case "cmdSilentOff":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | No esta en modo silencio nunca mas.");
						break;
						
					case "errorSilentModeFormat":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | No reconocido el comando. Teclea \"/guns silentMode <cierto/falso>\"");
						break;
						
					case "cmdGUIEnabled":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Ahora el GUI esta habilitado.");
						break;
						
					case "cmdGUIDisabled":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | No mas el GUI esta habilitado.");
						break;
						
					case "errorGUIToggleFormat":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | No reconocido el comando. Teclea \"/guns gui <cierto/falso>\"");
						break;
						
					case "errorExplosionsFormat":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | No reconocido el comando. Teclea \"/guns explosions <cierto/falso>\"");
						break;	
						
					case "cmdExplosionsEnabled":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Ahora los explosiones estan habilitado.");
						break;
						
					case "cmdExplosionsDisabled":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | No mas los explosiones estan habilitado.");
						break;
						
					case "errorCraftingFormat":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | No reconocido el comando. Teclea \"/guns crafting <cierto/falso>\"");
						break;	
						
					case "cmdCraftingEnabled":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Ahora el fabricacion de armas estan habilitado.");
						break;
						
					case "cmdCraftingDisabled":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | No mas estan habilitado el fabricacion de armas.");
						break;
						
					case "cmdParticlesEnabled":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Las partículas de disparo estan habilitado.");
						break;
						
					case "cmdParticlesDisabled":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | No mas estan habilitado las partículas de disparo.");
						break;
						
					case "errorParticlesFormat":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | No reconocido el comando. Teclea \"/guns particles <cierto/falso>\".");
						break;
						
					case "errorPermissions":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | No tienes los permisos correctos.");
						break;	
						
					case "cmdPermsEnabled":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Los permisos estan necesario ahora por el menu.");
						break;
						
					case "cmdPermsDisabled":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Los permisos no estan necesario ahora por el menu.");
						break;
						
					case "errorPermsFormat":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | No reconocido el comando. Teclea \"/guns perms <cierto/falso>\"");
						break;
						
					case "errorGrenadeFormat":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | No reconocido el comando. Teclea \"/giveGrenade <grenada>\"");
						break;
						
					case "errorArmorFormat":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | No reconocido el comando. Teclea \"/giveArmor <armadura> {jugador}\"");
						break;
						
					case "cmdVersion":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | GunSmith v" + VERSION );
						break;
						
					default:
				
						break;
					
				} 
				break;
			  // End of Spanish messages
			
			default:
		
			  //Start of English messages
				switch (msg) {
				
					case "errorIllegalCommand":
						player.sendMessage(ChatColor.RED + "Your command was not recognized, or you have insufficient permissions.");
						break;
				
					case "cmdGuns":
			        	player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Type \"/givegun <gunName> to give yourself a gun.\"");
			        	player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Names: " 
			        					+ ChatColor.GREEN + "sniper" + ChatColor.GRAY + " | "
			        					+ ChatColor.GREEN + "br" + ChatColor.GRAY + "/" + ChatColor.GREEN + "battleRifle" + ChatColor.GRAY + " | "
			        					+ ChatColor.GREEN + "lmg" + ChatColor.GRAY + "/" + ChatColor.GREEN + "lightMachineGun" + ChatColor.GRAY + " | "
			        					+ ChatColor.GREEN + "pistol" + ChatColor.GRAY + " | "
			        					+ ChatColor.GREEN + "bow");
			        	break;
						
					case "errorGUINotEnabled":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | The GUI is disabled.");
						break;	
						
					case "cmdOpReqEnabled":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | OP perms are now required for the GS GUI.");
						break;
						
					case "cmdOpReqDisabled":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | OP perms are no longer required for the GS GUI.");
						break;
						
					case "errorOpReqFormat":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Try entering \"/guns opReq <on/off>\".");
						break;
			        	
					case "cmdGiveGun":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Pew pew!");
						break;
						
					case "errorGunFormat":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Unrecognized format. Use \"/giveGun <gunName> {player}\"");
						break;
						
					case "cmdGiveAmmo":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Lock 'n load.");
						break;
					
					case "errorAmmoFormat":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Unrecognized format. Use \"/giveAmmo <gunName> {player}\"");
						break;
						
					case "errorPlayerNotFound":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Player not found!");
						break;
						
					case "errorWarZoneNotFound":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | You need the WarZone pack to use that item!");
						break;
						
					case "errorNoAmmoFound":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | You are out of ammunition.");
						break;
						
					case "cmdLang":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Your language is now set to English.");
						break;
						
					case "errorLangNotFound":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Language name not recognized.");
						break;
						
					case "errorLangFormat":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Unrecognized format. Enter \"/guns language <languageName>\"");
						break;
						
					case "cmdGlassBreakOn":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Firearm rounds will now break glass.");
						break;
						
					case "cmdGlassBreakOff":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Firearm rounds will no longer break glass.");
						break;
						
					case "errorGlassBreakFormat":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Unrecognized format. Enter \"/guns glassbreak <true/false>\"");
						break;
						
					case "cmdSilentOn":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Silent mode is turned on.");
						break;
						
					case "cmdSilentOff":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Silent mode is turned off.");
						break;
						
					case "errorSilentModeFormat":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Unrecognized format. Enter \"/guns silentMode <true/false>\"");
						break;
						
					case "cmdGUIEnabled":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | The GUI is enabled.");
						break;
						
					case "cmdGUIDisabled":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | The GUI is disabled.");
						break;
						
					case "errorGUIToggleFormat":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Unrecognized format. Enter \"/guns gui <on/off>\"");
						break;
					
					case "cmdExplosionsEnabled":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Explosions are enabled.");
						break;
						
					case "cmdExplosionsDisabled":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Explosions are disabled.");
						break;
						
					case "errorExplosionsFormat":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Try entering \"/guns explosions <on/off>\".");
						break;
						
					case "cmdCraftingEnabled":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Firearm crafting is enabled.");
						break;
						
					case "cmdCraftingDisabled":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Firearm crafting is disabled.");
						break;
						
					case "errorCraftingFormat":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Try entering \"/guns crafting <on/off>\".");
						break;
						
					case "cmdParticlesEnabled":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Particle smoke trails from gunshot are enabled.");
						break;
						
					case "cmdParticlesDisabled":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Particle smoke trails from gunshot are disabled.");
						break;
						
					case "errorParticlesFormat":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Try entering \"/guns particles <on/off>\".");
						break;
						
					case "errorPermissions":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | You do not have the required permission to use this command!");
						break;	
						
					case "cmdPermsEnabled":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Permissions are now required to use GunSmith guns.");
						break;
						
					case "cmdPermsDisabled":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Permissions are no longer required to use GunSmith guns.");
						break;
						
					case "errorPermsFormat":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Try entering \"/guns perms <on/off>\".");
						break;	
						
					case "errorGrenadeFormat":
    					player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Unrecognized format. Use \"/giveGrenade <grenadeName> {player}\"");
						break;
						
					case "errorArmorFormat":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | Unrecognized format. Use \"/giveArmor <armorName> {player}\"");
						break;
					
					case "cmdVersion":
						player.sendMessage(ChatColor.RED + "[GS]" + ChatColor.GRAY + " | GunSmith v" + VERSION );
						break;
						
					default:
				
						break;
					
				} 
				break;
			  // End of English messages
				
		}
	
	}
		
}
