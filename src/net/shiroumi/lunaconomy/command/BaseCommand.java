package net.shiroumi.lunaconomy.command;



import net.shiroumi.lunaconomy.LunaConomyCore;
import net.shiroumi.lunaconomy.i18n;
import net.shiroumi.lunaconomy.Util.Util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

/**
 * @author squarep
 */

public abstract class BaseCommand implements CommandExecutor {
	protected final EnumCommand command;
	protected BaseCommand(EnumCommand par1Cmd){
		command = par1Cmd;
	}
	public String getCmd(){
		return command.getCmd();
	}
	public String getPerm(){
		return command.getPerm();
	}
	public boolean isConsoleExecute(){
		return command.isConsoleExecute();
	}
	@Override
	public boolean onCommand(CommandSender par1Sender, Command par2Command, String par3Args, String[] par4Args){
		if(!this.isConsoleExecute() && par1Sender instanceof ConsoleCommandSender){
			Util.Message(par1Sender, i18n._("cmdplayeronly"), null);
			return false;
		}
		if(!Util.hasPerm(this.getPerm(), par1Sender, LunaConomyCore.getInstance())){
			return false;
		}
		return execute(par1Sender, par2Command, par3Args, par4Args);
	}
	abstract public boolean execute(CommandSender par1Sender, Command par2Command, String par3Args, String[] par4Args);
}
