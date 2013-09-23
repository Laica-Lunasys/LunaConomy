package net.shiroumi.lunaconomy.command;

import net.shiroumi.lunaconomy.LunaConomyCore;

public final class CommandRegister {
	public static void Register(BaseCommand par1Command){
		LunaConomyCore.getInstance().getServer().getPluginCommand(par1Command.getCmd()).setExecutor(par1Command);
	}
}
