package world.axe.axecore.storage.mysql.function;

import world.axe.axecore.AXECore;
import world.axe.axecore.storage.mysql.MySQLTask;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

public class MySQLInsert {

    private Connection connection;
    private String statement;
    private HashMap<String, String> requirements;

    public MySQLInsert() {
        requirements = new HashMap<>();
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
           statement = "INSERT INTO " + information.getTableName() + "(";
           for(String value : information.getValues()) {
               if(requirements.size() > 1) {
                   final int[] i = {0};
                   requirements.forEach((s, s2) -> {
                       i[0] = i[0] + 1;
                       if(!(i[0] == requirements.size())) {
                           statement = statement + value + ", ";
                       } else {
                           statement = statement + value + ") VALUES (";
                       }
                   });
               }
           }
           for(Object value : values) {
               if(requirements.size() > 1) {
                   final int[] i = {0};
                   requirements.forEach((s, s2) -> {
                       i[0] = i[0] + 1;
                       if(!(i[0] == requirements.size())) {
                           statement = statement + "`" + value + "`" + ", ";
                       } else {
                           statement = statement + "`" + value + "`" + ");";
                       }
                   });
               }
           }
       }
    }

    /**
     * It executes a SQL statement.
     */
    public void execute() {
        try {
            connection.prepareStatement(statement).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
