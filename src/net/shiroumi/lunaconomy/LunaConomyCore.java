package net.shiroumi.lunaconomy;

import java.io.File;
import java.util.logging.Logger;

import net.shiroumi.lunaconomy.Configuration.ConfigurationManager;

import org.bukkit.plugin.java.JavaPlugin;

public class LunaConomyCore extends JavaPlugin {
	public static LunaConomyCore Instance;
	public static Logger log;
	private static ConfigurationManager cfg;
	public LunaConomyCore(){
		Instance = this;
		log = this.getLogger();
	}
	@Override
	public void onEnable(){
		cfg = new ConfigurationManager(this);
		log.info("Enabled " + this.getDescription().getName() + "!");
	}
	@Override
	public void onDisable(){
		
	}
	public static LunaConomyCore getInstance(){
		return Instance;
	}
	public File getPluginJarFile(){
		return this.getFile();
	}
	
	public static String getLang(){
		return cfg.getString("Lang");
	}
}
