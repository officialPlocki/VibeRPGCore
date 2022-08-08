package world.axe.axecore;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import world.axe.axecore.custom.ItemModifier;
import world.axe.axecore.listener.PlayerListener;
import world.axe.axecore.player.RankManager;
import world.axe.axecore.protocol.ProtocolHandler;
import world.axe.axecore.storage.FileProvider;
import world.axe.axecore.storage.MySQLDriver;
import world.axe.axecore.util.TranslationUtil;

public final class AXECore extends JavaPlugin {

    private static MySQLDriver driver;
    private static FileProvider config;
    private static RankManager ranks;

    @Override
    public void onEnable() {
        // Plugin startup logic
        config = new FileProvider("config.yml");
        YamlConfiguration yml = config.getConfiguration();
        if(!yml.isSet("version")) {
            yml.set("version", "1.0");
            yml.set("mysql.host", "localhost");
            yml.set("mysql.user", "root");
            yml.set("mysql.password", "password");
            yml.set("mysql.database", "database");
            yml.set("tp.sha-1", "SHA-1");
            yml.set("tp.url", "https://localhost/pack.zip");
            config.save();
            new ItemModifier();
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        driver = new MySQLDriver();
        new TranslationUtil();
        new ProtocolHandler(this).init();
        ranks = new RankManager();
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getDriver().close();
    }

    public static RankManager getRanks() {
        return ranks;
    }

    public static MySQLDriver getDriver() {
        return driver;
    }

    public static FileProvider getConfigurationProvider() {
        return config;
    }

}
