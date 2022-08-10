package world.axe.axecore.storage.mysql.function;

import world.axe.axecore.AXECore;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

public class MySQLTable {

    private Connection connection;
    private String statement;
    private HashMap<String, String> requirements;
    private String tableName;
    private String[] values;

    public MySQLTable() {
        requirements = new HashMap<>();
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
        for(String value : values) {
            if(requirements.size() > 1) {
                final int[] i = {0};
                requirements.forEach((s, s2) -> {
                    i[0] = i[0] + 1;
                    if(!(i[0] == requirements.size())) {
                        statement = statement + value + " TEXT, ";
                    } else {
                        statement = statement + value + " TEXT);";
                    }
                });
            }
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
