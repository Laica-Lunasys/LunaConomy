package net.shiroumi.lunaconomy;

import org.bukkit.plugin.Plugin;

/**
 * @author squarep
 */
public class PluginFeatures<T> {
	private final T featurePlugin;
	private final String PluginName;

	private PluginFeatures(T par1Instance, String par2Name){
		featurePlugin = par1Instance;
		PluginName = par2Name;
	}
	@SuppressWarnings({ "unchecked", "unused" })
	public static <T> PluginFeatures<T> register(String par1PluginName){
		Plugin var1 = null;
		Class<T> cls = null;
		if((var1 = LunaConomyCore.getInstance().
				getServer().getPluginManager().getPlugin(par1PluginName)) != null){
			try {
				cls = (Class<T>) Class.forName(var1.getDescription().getMain());
			} catch (ClassNotFoundException e) {
			}
			return new PluginFeatures<T>((T)var1, par1PluginName);
		}
		return null;
	}

	public boolean isEnable(){
		return featurePlugin != null &&((Plugin)featurePlugin).isEnabled();
	}

	public String getPluginName(){
		return PluginName;
	}

	public T getInstance(){
		return featurePlugin;
	}
}
