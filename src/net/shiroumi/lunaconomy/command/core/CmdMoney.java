package net.shiroumi.lunaconomy.command.core;

import net.shiroumi.lunaconomy.command.BaseCommand;
import net.shiroumi.lunaconomy.command.EnumCommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CmdMoney extends BaseCommand {

	public CmdMoney() {
		super(EnumCommand.money);
	}

	@Override
	public boolean execute(CommandSender par1Sender, Command par2Command,
			String par3Args, String[] par4Args) {
		
		return false;
	}

}
