package world.axe.axecore.storage.mysql.function;

import org.bukkit.Bukkit;
import world.axe.axecore.AXECore;
import world.axe.axecore.storage.mysql.MySQLTask;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

public class MySQLInsert {

    private Connection connection;
    private String statement;

    public MySQLInsert() {
        try {
            connection = AXECore.getDriver().getDataSource().getConnection();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * It creates a statement that will insert a row into a table if the row doesn't already exist
     *
     * @param information This is the enum that contains the table name and the values that are required.
     */
    public void prepare(MySQLTable.fin information, Object... values) {
       MySQLRequest request = new MySQLTask().ask().prepare(information.getValues()[0], information.getTableName());
       if(request.execute().isEmpty()) {
           statement = "INSERT INTO " + information.getTableName() + "( ";
           final int[] i = {0};
           for(String value : information.getValues()) {
               if(!(i[0] == (values.length - 1))) {
                   statement = statement + value + ", ";
               } else {
                   statement = statement + value + ") VALUES (";
               }
               i[0] = i[0] + 1;
           }
           i[0] = 0;
           for(Object value : values) {
               if(!(i[0] == (values.length - 1))) {
                   statement = statement + " '" + value + "' " + ", ";
               } else {
                   statement = statement + " '" + value + "' " + ");";
               }
               i[0] = i[0] + 1;
           }
       }
    }

    /**
     * It executes a SQL statement.
     */
    public void execute() {
        try {
            connection.prepareStatement(statement).executeLargeUpdate();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(statement);
            throw new RuntimeException(e);
        }
    }

}
