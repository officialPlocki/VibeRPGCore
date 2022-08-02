package world.axe.axecore.util;
import java.sql.ResultSet;

import org.json.JSONObject;
import world.axe.axecore.AXECore;
import world.axe.axecore.player.AXEPlayer;
import world.axe.axecore.player.Languages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Translator {

    public Translator() {
        try(Connection connection = AXECore.getDriver().getDataSource().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS translations(keyA TEXT, english TEXT, german TEXT, french TEXT);");
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void define(String german, String keyA) {
        try (Connection connection = AXECore.getDriver().getDataSource().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT german FROM translations WHERE keyA = ?;");
            statement.setString(1, keyA);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                String english = getTranslation(german, "en");
                String french = getTranslation(german, "fr");
                PreparedStatement insert = connection.prepareStatement("INSERT INTO translations(keyA,english,german,french) VALUES (?,?,?,?);");
                insert.setString(1, keyA);
                insert.setString(2, english);
                insert.setString(3, german);
                insert.setString(4, french);
                insert.executeUpdate();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String keyA(String keyA, AXEPlayer player, String... replacements) {
        try(Connection connection = AXECore.getDriver().getDataSource().getConnection()) {
            String lang;
            if(player.getLanguage().equals(Languages.DE)) {
                lang = "german";
            } else if(player.getLanguage().equals(Languages.FR)) {
                lang = "french";
            } else {
                lang = "english";
            }
            PreparedStatement statement = connection.prepareStatement("SELECT " + lang + " FROM translations WHERE keyA = ?;");
            statement.setString(1, keyA);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                String str = resultSet.getString(lang);
                int i = 1;
                for(String replacement : replacements) {
                    str = str.replaceFirst("/rep" + i, replacement);
                    i = i + 1;
                }
                return str;
            } else {
                return "The keyA or language string is not defined.";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "An error occurred at MySQL.";
    }

    private String getTranslation(String text, String targetLanguage) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://192.168.178.33:5000/translate").openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        OutputStream out = connection.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
        JSONObject object = new JSONObject();
        object.put("q", text);
        object.put("source", "de");
        object.put("target", targetLanguage);
        object.put("format", "text");
        writer.flush();
        writer.close();
        out.close();
        InputStream input = connection.getInputStream();
        String string = new JSONObject(new String(input.readAllBytes())).getString("translatedText");
        input.close();
        return string;
    }

}
