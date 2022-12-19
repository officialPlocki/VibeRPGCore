package eu.viberpg.core;

import eu.viberpg.core.command.*;
import eu.viberpg.core.custom.ItemModifier;
import eu.viberpg.core.listener.*;
import eu.viberpg.core.player.RankManager;
import eu.viberpg.core.protocol.ProtocolHandler;
import eu.viberpg.core.storage.FileProvider;
import eu.viberpg.core.storage.MySQLDriver;
import eu.viberpg.core.util.AudioUtil;
import eu.viberpg.core.util.TranslationUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Core extends JavaPlugin {

    // @todo vault money integration
    // @todo fix settings interaction (not working)
    // @todo fix profile change (doesn't change profile)

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
            Objects.requireNonNull(getCommand("language")).setExecutor(new LanguageCommand());
            Objects.requireNonNull(getCommand("settings")).setExecutor(new SettingsCommand());
            Objects.requireNonNull(getCommand("voicepack")).setExecutor(new VoicePackCommand());
            Objects.requireNonNull(getCommand("profiles")).setExecutor(new ProfileCommand());
            Objects.requireNonNull(getCommand("measure")).setExecutor(new MeasureCommand());
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
