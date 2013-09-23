package net.shiroumi.lunaconomy;

import net.shiroumi.lunaconomy.Configuration.ConfigurationManager;

/**
 * @author squarep
 */
public final class i18n {

	private static ConfigurationManager msg;

	/**
	 * Translate String
	 * @param par1Str Target String
	 * @return Translated String
	 */
	public static String _(final String par1Str){
		return msg.getString(par1Str);
	}

	/**
	 * Initialize the i18n Configuration
	 * @param isForce Force Configuration
	 */
	public static void initialize(boolean isForce){
		if(msg == null || isForce){
			msg = new ConfigurationManager(LunaConomyCore.getInstance(),"lang/" + LunaConomyCore.getLang() + ".yml");
		}
	}
	static {
		initialize(false);
	}
}
