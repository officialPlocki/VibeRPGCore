package eu.viberpg.core.storage.mysql.function;

import eu.viberpg.core.Core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;

public class MySQLRequest {

    private String statement;
    private final HashMap<String, String> requirements;

    public MySQLRequest() {
        requirements = new HashMap<>();
    }

    /**
     * Prepare a SELECT statement.
     *
     * @param select The columns you want to select.
     * @param table  The table you want to select from
     */
    public void prepare(String select, String table) {
        statement = "SELECT " + select + " FROM " + table;
    }

    /**
     * It adds a requirement to the request
     *
     * @param value       The column name
     * @param requirement The value of the requirement.
     */
    public void addRequirement(String value, Object requirement) {
        requirements.put(value, requirement.toString());
    }

    /**
     * It executes the statement and returns the result
     *
     * @return A MySQLResult object.
     */
    public MySQLResponse execute() {
        if(!requirements.isEmpty()) {
            if(requirements.size() > 1) {
                final int[] i = {0};
                requirements.forEach((s, s2) -> {
                    if(!(i[0] == (requirements.size() - 1))) {
                        if(i[0] == 0) {
                            statement = statement + " WHERE ";
                        }
                        statement = statement + s + " = '" + s2 + "' AND ";
                    } else {
                        if(i[0] == 0) {
                            statement = statement + " WHERE ";
                        }
                        statement = statement + s + " = '" + s2 + "';";
                    }
                    i[0] = i[0] + 1;
                });
            }
        }
        try(Connection connection = Core.getDriver().getDataSource().getConnection()) {
            PreparedStatement exec = connection.prepareStatement(statement);
            ResultSet result = exec.executeQuery();
            ResultSetMetaData data = result.getMetaData();
            HashMap<String, String> values = new HashMap<>();
            while (result.next()) {
                for(int i = 1; i <= data.getColumnCount(); i++) {
                    values.put(data.getColumnName(i), result.getString(i));
                }
            }
            return new MySQLResponse(values);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new MySQLResponse(null);
    }

}
