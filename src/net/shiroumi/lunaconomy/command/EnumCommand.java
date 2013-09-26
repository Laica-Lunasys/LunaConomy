package net.shiroumi.lunaconomy.command;


/**
 * @author squarep
 */
public enum EnumCommand {
	money("money", "money.show", false),
	;;
	private String perm, cmd;
	private boolean isCons;
	EnumCommand(String par1Cmd, String par2Perm, boolean isConsole){
		cmd = par1Cmd;
		perm = "LunaConomy." + par2Perm;
		isCons = isConsole;
	}
	public String getCmd(){
		return cmd;
	}
	public String getPerm(){
		return perm;
	}
	public boolean isConsoleExecute(){
		return isCons;
	}
}
