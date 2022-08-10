package world.axe.axecore.storage.mysql;

import world.axe.axecore.storage.mysql.function.MySQLInsert;
import world.axe.axecore.storage.mysql.function.MySQLPush;
import world.axe.axecore.storage.mysql.function.MySQLRequest;
import world.axe.axecore.storage.mysql.function.MySQLTable;

public class MySQLTask {

    /**
     * This function returns a MySQLRequest object.
     *
     * @return A new instance of the MySQLRequest class.
     */
    public MySQLRequest ask() {
        return new MySQLRequest();
    }

    /**
     * This function returns a MySQLTable object.
     *
     * @return A new instance of the MySQLTable class.
     */
    public MySQLTable buildTable() {
        return new MySQLTable();
    }

    /**
     * It returns a new instance of the MySQLInsert class
     *
     * @return A new instance of the MySQLInsert class.
     */
    public MySQLInsert insert() {
        return new MySQLInsert();
    }

    /**
     * This function returns a new MySQLPush object.
     *
     * @return A new instance of the MySQLPush class.
     */
    public MySQLPush update() {
        return new MySQLPush();
    }

}
