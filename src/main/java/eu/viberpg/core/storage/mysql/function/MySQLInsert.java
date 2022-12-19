package eu.viberpg.core.storage.mysql.function;

import eu.viberpg.core.Core;
import eu.viberpg.core.storage.mysql.MySQLTask;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQLInsert {

    private StringBuilder statement;

    public MySQLInsert() {
        statement = new StringBuilder();
    }

    /**
     * It creates a statement that will insert a row into a table if the row doesn't already exist
     *
     * @param information This is the enum that contains the table name and the values that are required.
     */
    public void prepare(MySQLTable.fin information, Object... values) {
       MySQLRequest request = new MySQLTask().ask();
       request.prepare("*", information.getTableName());
       request.addRequirement(information.getValues()[0], values[0]);
       if(request.execute().isEmpty()) {
           statement.append("INSERT INTO ").append(information.getTableName()).append("( ");
           final int[] i = {0};
           for(String value : information.getValues()) {
               if(!(i[0] == (values.length - 1))) {
                   statement.append(value).append(", ");
               } else {
                   statement.append(value).append(") VALUES (");
               }
               i[0] = i[0] + 1;
           }
           i[0] = 0;
           for(Object value : values) {
               if(!(i[0] == (values.length - 1))) {
                   statement.append(" '").append(value).append("' ").append(", ");
               } else {
                   statement.append(" '").append(value).append("' ").append(");");
               }
               i[0] = i[0] + 1;
           }
       } else {
           statement = new StringBuilder("empty");
       }
    }

    /**
     * It executes a SQL statement.
     */
    public void execute() {
        if(statement.toString().equalsIgnoreCase("empty")) return;
        try(Connection connection = Core.getDriver().getDataSource().getConnection()) {
            connection.prepareStatement(statement.toString()).executeLargeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
