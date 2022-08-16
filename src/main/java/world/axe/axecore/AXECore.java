package world.axe.axecore;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import world.axe.axecore.command.LanguageCommand;
import world.axe.axecore.command.ProfileCommand;
import world.axe.axecore.command.SettingsCommand;
import world.axe.axecore.command.VoicePackCommand;
import world.axe.axecore.custom.ItemModifier;
import world.axe.axecore.listener.*;
import world.axe.axecore.player.RankManager;
import world.axe.axecore.protocol.ProtocolHandler;
import world.axe.axecore.storage.FileProvider;
import world.axe.axecore.storage.MySQLDriver;
import world.axe.axecore.util.AudioUtil;
import world.axe.axecore.util.TranslationUtil;

public final class AXECore extends JavaPlugin {

    // @todo vault money integration

    private static MySQLDriver driver;
    private static FileProvider config;
    private static RankManager ranks;

    private static AudioUtil audio;

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
            yml.set("tp.sha-1", "MD-5");
            yml.set("tp.url", "https://localhost/pack.zip");
            config.save();
            new ItemModifier();
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        driver = new MySQLDriver();
        if(driver.getDataSource().isRunning()) {
            new TranslationUtil();
            new ProtocolHandler(this).init();
            ranks = new RankManager();
            Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
            Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
            audio = new AudioUtil(this, true);
            Bukkit.getPluginManager().registerEvents(new AudioListener(), this);
            Bukkit.getPluginManager().registerEvents(new LanguageListener(), this);
            Bukkit.getPluginManager().registerEvents(new SettingsListener(), this);
            Bukkit.getPluginManager().registerEvents(new VoicePackListener(), this);
            Bukkit.getPluginManager().registerEvents(new ProfileListener(), this);
            getCommand("language").setExecutor(new LanguageCommand());
            getCommand("settings").setExecutor(new SettingsCommand());
            getCommand("voicepack").setExecutor(new VoicePackCommand());
            getCommand("profiles").setExecutor(new ProfileCommand());
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getDriver().close();
    }

    public static AudioUtil getAudio() {
        return audio;
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
