package world.axe.axecore.storage.mysql.function;

import org.bukkit.Bukkit;
import world.axe.axecore.AXECore;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

public class MySQLPush {

    private Connection connection;
    private String statement;
    private final HashMap<String, String> requirements;

    public MySQLPush() {
        this.requirements = new HashMap<>();
        try {
            this.connection = AXECore.getDriver().getDataSource().getConnection();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Prepare an update statement.
     *
     * @param table The table you want to update
     * @param param The parameter you want to update.
     * @param value The value that it should be changed to
     * @return The MySQLPush object.
     */
    public MySQLPush prepare(String table, String param, Object value) {
        statement = "UPDATE " + table + " SET " + param + " = '" + value + "'";
        return this;
    }

    /**
     * It adds a requirement to the push
     *
     * @param value The value to be pushed to the database.
     * @param requirement The requirement to be added.
     * @return The MySQLPush object
     */
    public MySQLPush addRequirement(String value, Object requirement) {
        requirements.put(value, requirement.toString());
        return this;
    }

    /**
     * It takes the statement and the requirements and combines them into one statement
     */
    public void execute() {
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
        try {
            connection.prepareStatement(statement).executeUpdate();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(statement);
            e.printStackTrace();
        }
    }

}
