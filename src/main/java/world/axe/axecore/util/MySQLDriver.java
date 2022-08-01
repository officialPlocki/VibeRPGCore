package world.axe.axecore.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.configuration.file.YamlConfiguration;
import world.axe.axecore.AXECore;

public class MySQLDriver {

    private final HikariDataSource source;

    public MySQLDriver() {
        YamlConfiguration yml = AXECore.getConfigurationProvider().getConfiguration();
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + yml.getString("mysql.host") + ":3306/" + yml.getString("mysql.database"));
        config.setUsername(yml.getString("mysql.user"));
        config.setPassword(yml.getString("mysql.password"));
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        source = new HikariDataSource(config);
    }

    public HikariDataSource getDataSource() {
        return source;
    }

    public void close() {
        source.close();
    }

}
