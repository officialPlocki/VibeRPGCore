package eu.viberpg.core.storage.mysql.function;

import eu.viberpg.core.Core;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQLTable {

    private final StringBuilder statement = new StringBuilder();
    private String tableName;
    private String[] values;

    /**
     * It creates a table if it doesn't exist, and if it does, it doesn't do anything
     *
     * @param tableName The name of the table you want to create.
     */
    public void prepare(String tableName, String... values) {
        statement.append("CREATE TABLE IF NOT EXISTS ").append(tableName).append("(");
        final int[] i = {0};
        for(String value : values) {
            if(!(i[0] == (values.length - 1))) {
                statement.append(value).append(" TEXT, ");
            } else {
                statement.append(value).append(" TEXT);");
            }
            i[0] = i[0] + 1;
        }
        this.values = values;
        this.tableName = tableName;
    }

    /**
     * It executes the statement and returns an object that contains the table name and values
     *
     * @return An anonymous class that implements the fin interface.
     */
    public MySQLTable.fin build() {
        try(Connection connection = Core.getDriver().getDataSource().getConnection()) {
            connection.prepareStatement(statement.toString()).executeUpdate();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(statement.toString());
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
