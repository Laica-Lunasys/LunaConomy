package net.shiroumi.lunaconomy.command.core;

import net.shiroumi.lunaconomy.MoneyController;
import net.shiroumi.lunaconomy.i18n;
import net.shiroumi.lunaconomy.Util.Util;
import net.shiroumi.lunaconomy.command.BaseCommand;
import net.shiroumi.lunaconomy.command.EnumCommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdMoney extends BaseCommand {

	public CmdMoney() {
		super(EnumCommand.money);
	}

	@Override
	public boolean execute(CommandSender par1Sender, Command par2Command,
			String par3Args, String[] par4Args) {
		if(par1Sender instanceof Player){
			String player = par1Sender.getName();
			if(par4Args.length > 0){
				player = par4Args[0];
			}
			Util.Message(par1Sender, i18n._("ShowMoney"), new String[][]{
				{"%player", player},
				{"%amount", String.valueOf(MoneyController.getMoney(player))}
			});
		}
		return false;
	}
}
