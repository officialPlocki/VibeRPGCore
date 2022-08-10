package world.axe.axecore.storage.mysql.function;

import world.axe.axecore.AXECore;

import java.sql.*;
import java.util.HashMap;

public class MySQLRequest {

    private Connection connection;
    private String statement;
    private HashMap<String, String> requirements;

    public MySQLRequest() {
        requirements = new HashMap<>();
        try {
            connection = AXECore.getDriver().getDataSource().getConnection();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Prepare a SELECT statement.
     *
     * @param select The columns you want to select.
     * @param table The table you want to select from
     * @return The MySQLRequest object.
     */
    public MySQLRequest prepare(String select, String table) {
        statement = "SELECT " + select + " FROM " + table;
        return this;
    }

    /**
     * It adds a requirement to the request
     *
     * @param value The column name
     * @param requirement The value of the requirement.
     * @return The MySQLRequest object.
     */
    public MySQLRequest addRequirement(String value, Object requirement) {
        requirements.put(value, requirement.toString());
        return this;
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
                    i[0] = i[0] + 1;
                    if(!(i[0] == requirements.size())) {
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
                });
            }
        }
        try {
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
