package net.shiroumi.lunaconomy.internal.listeners;

import net.shiroumi.lunaconomy.LunaConomy;
import net.shiroumi.lunaconomy.internal.MCCom;
import net.shiroumi.lunaconomy.internal.MCLang;
import net.shiroumi.lunaconomy.internal.Settings;
import net.shiroumi.lunaconomy.internal.util.Update;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Listens for players' actions.
 * 
 * @author Laica-Lunasys (Origin by MjolnirCommando)
 */
public class MCListener implements Listener
{
    /**
	 * LunaConomy shortcut to plugin.
	 */
	public static LunaConomy	plugin;
	
	/**
	 * Fires when player joins the game.
	 * 
	 * @param event
	 */
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		
		if (! (MCCom.exists(player.getName())))
        {
		    if (Settings.iconomy)
            {
                if (MCCom.exists(player.getName().toLowerCase()))
                {
                    MCCom.rename(player.getName().toLowerCase(), player.getName());
                    onPlayerJoin(event);
                    return;
                }
                else
                {
                    if (player.hasPermission("LunaConomy.account.have"))
                    {
                        MCCom.create(player.getName());
                        player.sendMessage(MCLang.tag + MCLang.messageWelcomeAccountCreated);
                    }
                    else
                    {
                        player.sendMessage(MCLang.tag + MCLang.errorPermissionHaveAccount);
                        player.sendMessage(" ");
                        return;
                    }
                }
            }
		    else
		    {
                if (player.hasPermission("LunaConomy.account.have"))
                {
                    MCCom.create(player.getName());
                    player.sendMessage(MCLang.tag + MCLang.messageWelcomeAccountCreated);
                }
                else
                {
                    player.sendMessage(MCLang.tag + MCLang.errorPermissionHaveAccount);
                    player.sendMessage(" ");
                    return;
                }
		    }
        }
		
        if (MCLang.displayWelcome)
        {
            String[] args = {player.getName(), MCCom.getCurrency(player.getName()) + "", MCCom.getBalance(player.getName()) + ""};
            
            player.sendMessage(MCLang.tag + MCLang.parse(MCLang.welcomeMessage, args));
            player.sendMessage(" ");
        }
        
        if (Settings.ops && player.isOp() && Update.check())
        {
            player.sendMessage(MCLang.tag + MCLang.warnOp);
            player.sendMessage(" ");
        }
	}
}
