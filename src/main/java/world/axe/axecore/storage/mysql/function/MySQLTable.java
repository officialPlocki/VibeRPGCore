package world.axe.axecore.storage.mysql.function;

import org.bukkit.Bukkit;
import world.axe.axecore.AXECore;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQLTable {

    private Connection connection;
    private String statement;
    private String tableName;
    private String[] values;

    public MySQLTable() {
        try {
            connection = AXECore.getDriver().getDataSource().getConnection();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * It creates a table if it doesn't exist, and if it does, it doesn't do anything
     *
     * @param tableName The name of the table you want to create.
     * @return The MySQLTable class.
     */
    public MySQLTable prepare(String tableName, String... values) {
        statement = "CREATE TABLE IF NOT EXISTS " + tableName + "(";
        final int[] i = {0};
        for(String value : values) {
            if(!(i[0] == (values.length - 1))) {
                statement = statement + value + " TEXT, ";
            } else {
                statement = statement + value + " TEXT);";
            }
            i[0] = i[0] + 1;
        }
        this.values = values;
        this.tableName = tableName;
        return this;
    }

    /**
     * It executes the statement and returns an object that contains the table name and values
     *
     * @return An anonymous class that implements the fin interface.
     */
    public MySQLTable.fin build() {
        try {
            connection.prepareStatement(statement).executeUpdate();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(statement);
            throw new RuntimeException(e);
        }
        return new fin() {
            @Override
            public String getTableName() {
                return tableName;
            }

            @Override
            public String[] getValues() {
                return values;
            }
        };
    }

    public interface fin {

        /**
         * Returns the name of the table
         *
         * @return The name of the table.
         */
        String getTableName();

        /**
         * Returns an array of all the values in the map
         *
         * @return An array of strings.
         */
        String[] getValues();

    }

}
