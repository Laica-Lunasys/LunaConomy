package net.shiroumi.lunaconomy.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

/** @author squarep */
public class ConfigurationManager extends FileSetConfiguration {
	FileSetConfiguration cfg;
	public ConfigurationManager(JavaPlugin par1Plugin){
		super(par1Plugin, "config.yml");
		cfg = super.Instance;
	}
	public ConfigurationManager(JavaPlugin par1Plugin, String par2Fname){
		super(par1Plugin, par2Fname);
		cfg = super.Instance;
	}
}
