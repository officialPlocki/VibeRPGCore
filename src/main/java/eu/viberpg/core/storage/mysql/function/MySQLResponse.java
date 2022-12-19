package eu.viberpg.core.storage.mysql.function;

import java.util.HashMap;

public class MySQLResponse {

    private final HashMap<String, String> values;

    public MySQLResponse(HashMap<String, String> values) {
        this.values = values;
    }

    public String getString(String column) {
        return values.get(column);
    }

    public double getDouble(String column) {
        return Double.parseDouble(values.get(column));
    }

    public int getInt(String column) {
        return Integer.parseInt(values.get(column));
    }

    public boolean getBoolean(String column) {
        return Boolean.parseBoolean(values.get(column));
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public Object get(String column) {
        return values.get(column);
    }

    public HashMap<String, String> raw() {
        return values;
    }

    @Override
    public String toString() {
        return values.toString();
    }
}
