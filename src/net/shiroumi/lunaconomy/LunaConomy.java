package net.shiroumi.lunaconomy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import net.shiroumi.lunaconomy.internal.Banking;
import net.shiroumi.lunaconomy.internal.Currency;
import net.shiroumi.lunaconomy.internal.MCCom;
import net.shiroumi.lunaconomy.internal.MCLang;
import net.shiroumi.lunaconomy.internal.Settings;
import net.shiroumi.lunaconomy.internal.commands.ChatExecutor;
import net.shiroumi.lunaconomy.internal.commands.Interest;
import net.shiroumi.lunaconomy.internal.commands.Tax;
import net.shiroumi.lunaconomy.internal.gui.GUI;
import net.shiroumi.lunaconomy.internal.listeners.MCListener;
import net.shiroumi.lunaconomy.internal.util.IOH;
import net.shiroumi.lunaconomy.internal.util.Update;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A simple, mid-weight Economy Bukkit Plugin.
 * 
 * @author Laica-Lunasys (Origin by MjolnirCommando)
 * @version 1.1
 */
public class LunaConomy extends JavaPlugin
{
    /**
     * Holds the main directory of the LunaConomy data files.
     */
    public static String     maindir = "plugins/LunaConomy/";

    /**
     * LunaConomy shortcut to plugin.
     */
    public static LunaConomy plugin;

    /**
     * The id of the Bukkit build which is currently running.
     */
    public static String bukkitVersion = "";
    
    private static String    version;

    public void onEnable()
    {
        plugin = this;
        
        PluginDescriptionFile pdfFile = this.getDescription();
        version = pdfFile.getVersion();
        
        IOH.loadLog();
        
        IOH.log("LunaConomyを起動しています....", IOH.INFO);

        if (checkVersion())
        {
            onDisable();
            return;
        }

        new File(maindir).mkdir();

        load();



        getServer().getPluginManager().registerEvents(new MCListener(), this);

        ChatExecutor executor = new ChatExecutor(this);

        getCommand("lco").setExecutor(executor);
        getCommand("money").setExecutor(executor);
        getCommand("lcob").setExecutor(executor);

        if (Settings.autosaveInterval > 0)
        {
            this.getServer().getScheduler()
                    .scheduleSyncRepeatingTask(this, new Runnable()
                    {
                        public void run()
                        {
                            IOH.log("ファイルを自動セーブ....", IOH.INFO);

                            save();

                            IOH.log("自動セーブに成功", IOH.INFO);
                        }
                    }, 20, Settings.autosaveInterval * 20);
        }
        else
        {
            IOH.log("自動セーブを無効化", IOH.INFO);
        }

        if (Settings.interestInterval > 0)
        {
            this.getServer().getScheduler()
                    .scheduleSyncRepeatingTask(this, new Runnable()
                    {
                        public void run()
                        {
                            IOH.log("インタレストを取得しています....", IOH.INFO);

                            if (Settings.interestMode.equalsIgnoreCase("none"))
                            {
                                IOH.log("インタレストモードを設定しました: \"none\". "
                                        + "サーバーのパフォーマンスを向上させるには、インターバルを0に設定することを推奨します",
                                        IOH.IMPORTANT);
                            }
                            else if (Settings.interestMode
                                    .equalsIgnoreCase("fixed"))
                            {
                                IOH.log("インタレストモードを FIXED に変更.", IOH.INFO);
                                Interest.fixed();
                            }
                            else if (Settings.interestMode
                                    .equalsIgnoreCase("percent"))
                            {
                                IOH.log("インタレストモードを PERCENTに変更.",
                                        IOH.INFO);
                                Interest.percent();
                            }
                            else
                            {
                                IOH.log("インタレストモードを同定することができませんでした。",
                                        IOH.IMPORTANT);
                            }

                            IOH.log("インタレストの付与に成功！", IOH.INFO);
                        }
                    }, 20, Settings.interestInterval * 20);
        }
        else
        {
            IOH.log("インタレストを無効化", IOH.INFO);
        }

        if (Settings.taxInterval > 0)
        {
            this.getServer().getScheduler()
                    .scheduleSyncRepeatingTask(this, new Runnable()
                    {
                        public void run()
                        {
                            IOH.log("課税...", IOH.INFO);

                            if (Settings.taxMode.equalsIgnoreCase("none"))
                            {
                                IOH.log("課税モードを設定 \"none\". "
                                        + "サーバーのパフォーマンスを向上させるには、インターバルを0に設定することを推奨します",
                                        IOH.IMPORTANT);
                            }
                            else if (Settings.taxMode.equalsIgnoreCase("fixed"))
                            {
                                IOH.log("課税モードを FIXED に設定", IOH.INFO);
                                Tax.fixed();
                            }
                            else if (Settings.taxMode
                                    .equalsIgnoreCase("percent"))
                            {
                                IOH.log("課税モードを PERCENT に設定", IOH.INFO);
                                Tax.percent();
                            }
                            else
                            {
                                IOH.log("インタレストモードを同定することができませんでした。",
                                        IOH.IMPORTANT);
                            }

                            IOH.log("課税完了", IOH.INFO);
                        }
                    }, 20, Settings.taxInterval * 20);
        }
        else
        {
            IOH.log("Tax を 無効にしました。", IOH.INFO);
        }

        if (Settings.gui)
        {
            IOH.log("Creating GUI...", IOH.INFO);
            new GUI();
        }
        else
        {
            IOH.log("GUI を 無効にしました。", IOH.INFO);
        }

        IOH.log("Checking for updates...", IOH.INFO);

        if (Update.check())
        {
            IOH.log("Updates available. Check http://dev.bukkit.org/server-mods/LunaConomy/ for the update.",
                    IOH.VERY_IMPORTANT);
        }
        else
        {
            IOH.log("No updates available. LunaConomy is up to date!", IOH.INFO);
        }

        File f;

        if (Settings.migrate.equalsIgnoreCase("iconomy"))
        {

            f = new File(maindir + "../iConomy/accounts.mini");

            if (f.exists())
            {
                File n = new File(maindir + "accounts.yml.convert");

                if (!n.exists())
                {
                    IOH.log("LunaConomy has found an iConomy accounts file. LunaConomy will convert it and save it to the LunaConomy folder as \"accounts.yml.convert\".",
                            IOH.VERY_IMPORTANT);

                    try
                    {
                        YamlConfiguration accounts = new YamlConfiguration();
                        accounts.options()
                                .header("=== LunaConomy Accounts ===\n\n    Do not edit!\n");

                        Scanner in = new Scanner(f);

                        int linenum = 0;

                        while (in.hasNextLine())
                        {
                            String line = in.nextLine();
                            linenum += 1;

                            if (line != "")
                            {
                                String[] args = line.split(" ");

                                try
                                {
                                    accounts.set("Accounts." + args[0]
                                            + ".Balance", Double
                                            .parseDouble(args[1].replace(
                                                    "balance:", "")));
                                    accounts.set("Accounts." + args[0]
                                            + ".Status", "NORMAL");
                                    accounts.set("Accounts." + args[0]
                                            + ".Currency",
                                            MCCom.getDefaultCurrency());
                                }
                                catch (Exception e)
                                {
                                    IOH.error("Parse Data (Line " + linenum
                                            + "): \"" + line
                                            + "\"\nParse Error", e);
                                }
                            }
                        }

                        in.close();

                        accounts.save(n);
                    }
                    catch (FileNotFoundException e)
                    {
                        IOH.error("FileNotFoundException", e);
                    }
                    catch (IOException e)
                    {
                        IOH.error("IOException", e);
                    }
                }
                else
                {
                    IOH.log("LunaConomy could NOT find an iConomy accounts file. LunaConomy can NOT convert files.",
                            IOH.VERY_IMPORTANT);
                }
            }

        }
        else if (Settings.migrate.equalsIgnoreCase("mysql"))
        {

            f = new File(maindir + "accounts.yml");

            if (f.exists() && Settings.dbtype.equalsIgnoreCase("mysql"))
            {
                IOH.log("LunaConomy will now migrate your account data from your \"accounts.yml\" file to your MySQL database.",
                        IOH.VERY_IMPORTANT);

                YamlConfiguration accounts = YamlConfiguration
                        .loadConfiguration(f);

                Object[] t = accounts.getConfigurationSection("Accounts").getKeys(true).toArray();

                ArrayList<String> accountlist = new ArrayList<String>();

                for (int i = 0; t.length > i; i++)
                {
                    String[] parent = t[i].toString().replace(".", "-")
                            .split("-");

                    if (parent.length == 1)
                    {
                        accountlist.add(parent[0]);
                    }
                }

                Connection con = null;
                String driver = "com.mysql.jdbc.Driver";

                try
                {
                    Class.forName(driver).newInstance();
                    con = DriverManager.getConnection("jdbc:mysql://"
                            + Settings.dburl + Settings.dbname,
                            Settings.dbuser, Settings.dbpass);

                    try
                    {
                        Statement st = con.createStatement();
                        String com = "SELECT * FROM LunaConomy_accounts WHERE id = '1'";
                        st.execute(com);
                    }
                    catch (Exception e)
                    {
                        Statement st = con.createStatement();
                        String com = "CREATE TABLE LunaConomy_accounts(id int NOT NULL AUTO_INCREMENT, account text NOT NULL, balance double NOT NULL, currency text NOT NULL, status text NOT NULL, PRIMARY KEY (id) )";
                        st.execute(com);
                    }

                    for (int i = 0; accountlist.size() > i; i++)
                    {
                        Statement st = con.createStatement();
                        String com = "INSERT INTO LunaConomy_accounts(account, balance, currency, status) VALUES ('"
                                + accountlist.get(i)
                                + "', "
                                + accounts.get("Accounts." + accountlist.get(i) + ".Balance").toString()
                                + ", '"
                                + accounts.get("Accounts." + accountlist.get(i) + ".Currency").toString()
                                + "', 'NORMAL')";
                        st.execute(com);
                    }
                }
                catch (Exception e)
                {
                    IOH.error("MySQL Error", e);
                }
            }
            else
            {
                IOH.log("LunaConomy could not find a valid accounts file to migrate from.",
                        IOH.VERY_IMPORTANT);
            }
        }

        Bukkit.getServer().getServicesManager()
                .register(LunaConomy.class, this, getPlugin(), null);

        IOH.log("Version " + pdfFile.getVersion()
                + " by Laica-Lunasys (Origin by MjolnirCommando) is enabled!", IOH.IMPORTANT);

    }

    public void onDisable()
    {
        IOH.log("Disabling LunaConomy...", IOH.INFO);

        if (Settings.gui)
        {
            GUI.window.setVisible(false);
        }

        save();
        IOH.log("LunaConomy is disabled.", IOH.IMPORTANT);
    }

    private static void load()
    {
        Settings.load();
        MCCom.initialize();
        MCCom.getAccounting().load();
        Banking.load();
        Currency.load();
        MCLang.langFile = new File(LunaConomy.maindir + "lang/" + "lang-"
                + Settings.lang + ".yml");
        MCLang.load();
    }

    /**
     * Reloads LunaConomy's Resources.
     */
    public static void reload()
    {
        MCCom.getAccounting().reload();
        Banking.reload();
        Currency.reload();
        Settings.reload();
        MCLang.reload();
    }

    /**
     * Saves LunaConomy's Resources.
     */
    public static void save()
    {
        MCCom.getAccounting().save();
        Banking.save();
        Currency.save();
        Settings.save();
        MCLang.save();
        IOH.saveLog();
    }

    /**
     * Returns the current LunaConomy version.
     * 
     * @return String
     */
    public static String getVersion()
    {
        return version;
    }

    /**
     * Returns the current plugin name.
     * 
     * @return LunaConomy
     */
    public static String getPluginName()
    {
        return "LunaConomy";
    }

    /**
     * Returns LunaConomy.
     * 
     * @return LunaConomy
     */
    public LunaConomy getPlugin()
    {
        return this;
    }

    private boolean checkVersion()
    {
        String v = Bukkit.getServer().getVersion();
        v = v.substring(v.length() - 20, v.length() - 16);
        int version = 0;
        try
        {
            version = Integer.parseInt(v);
        }
        catch (NumberFormatException e)
        {
            IOH.log("Found non-Bukkit server; Unable to check server version.",
                    IOH.VERY_IMPORTANT);
            bukkitVersion = "Tekkit?";
            return false;
        }
        
        bukkitVersion = v;

        if (version >= 2586)
        {
            IOH.log("Found CraftBukkit [" + v + "]. It is compatible!",
                    IOH.INFO);
            return false;
        }
        else
        {
            IOH.log("Found CraftBukkit [" + v
                    + "]. It is not compatible! LunaConomy will now disable!",
                    IOH.VERY_IMPORTANT);
            return true;
        }
    }
}
