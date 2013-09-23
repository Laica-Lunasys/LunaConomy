package net.shiroumi.lunaconomy;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import net.shiroumi.lunaconomy.Configuration.ConfigurationManager;
import net.shiroumi.lunaconomy.command.CommandRegister;
import net.shiroumi.lunaconomy.command.core.CmdMoney;

import org.bukkit.plugin.java.JavaPlugin;

public class LunaConomyCore extends JavaPlugin {
	public static LunaConomyCore Instance;
	public static Logger log;
	private static ConfigurationManager cfg;
	public static String Name = "LunaConomy";
	public LunaConomyCore() {
		Instance = this;
	}
	@Override
	public void onEnable(){
		log = this.getLogger();
		cfg = new ConfigurationManager(this);
		MoneyController.load(new File(cfg.getString("MoneyDataFile")));
		CommandRegister.Register(new CmdMoney());
		log.info("Enabled " + Name + "!");
	}
	@Override
	public void onDisable(){
		try {
			MoneyController.save(new File(cfg.getString("MoneyDataFile")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static LunaConomyCore getInstance(){
		System.out.println("getInstance : " + Instance);
		System.out.println("getInstance : " + log);
		return Instance;
	}
	
	public File getPluginJarFile(){
		return this.getFile();
	}
	
	public static String getLang(){
		return cfg.getString("lang");
	}
}
