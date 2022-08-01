package world.axe.axecore;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import world.axe.axecore.util.FileProvider;
import world.axe.axecore.util.MySQLDriver;
import world.axe.axecore.util.Translator;

public final class AXECore extends JavaPlugin {

    private static MySQLDriver driver;
    private static FileProvider config;

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
            yml.set("tp.url", "https://localhost/pack.zip");
            config.save();
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        driver = new MySQLDriver();
        new Translator();
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getDriver().close();
    }

    public static MySQLDriver getDriver() {
        return driver;
    }

    public static FileProvider getConfigurationProvider() {
        return config;
    }

}
