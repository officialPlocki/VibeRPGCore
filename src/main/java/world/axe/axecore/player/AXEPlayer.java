package world.axe.axecore.player;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import world.axe.axecore.AXECore;
import world.axe.axecore.economy.Money;
import world.axe.axecore.economy.MoneyAPI;
import world.axe.axecore.manager.ResourcepackManager;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class AXEPlayer {

    private final Object player;

    private final boolean online;

    public AXEPlayer(Player player) {
        this.player = player;
        online = true;
        try (Connection connection = AXECore.getDriver().getDataSource().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS axePlayer(TEXT language, TEXT firstJoin, TEXT lastJoin, TEXT joins, TEXT lastIP, TEXT ips, TEXT uuid);");
            statement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try (Connection connection = AXECore.getDriver().getDataSource().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM axePlayer WHERE uuid = ?");
            statement.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                PreparedStatement insert = connection.prepareStatement("INSERT INTO axePlayer(language,firstJoin,lastJoin,joins,lastIP,ips,uuid) VALUES (?,?,?,?,?,?,?,?);");
                insert.setString(1, Languages.EN.name());
                insert.setString(2, "" + System.currentTimeMillis());
                insert.setString(3, "" + System.currentTimeMillis());
                insert.setString(4, String.join(", ", Lists.newArrayList(System.currentTimeMillis() + "")));
                insert.setString(5, Objects.requireNonNull(player.getAddress()).getHostName());
                insert.setString(6, String.join(", ", Lists.newArrayList(player.getAddress().getHostName())));
                insert.setString(7, player.getUniqueId().toString());
                insert.executeUpdate();
                new MoneyAPI(this, Money.GEMS);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public AXEPlayer(OfflinePlayer player) {
        this.player = player;
        online = false;
        try (Connection connection = AXECore.getDriver().getDataSource().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS axePlayer(TEXT language, TEXT firstJoin, TEXT lastJoin, TEXT joins, TEXT lastIP, TEXT ips, TEXT uuid);");
            statement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try (Connection connection = AXECore.getDriver().getDataSource().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM axePlayer WHERE uuid = ?");
            statement.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                PreparedStatement insert = connection.prepareStatement("INSERT INTO axePlayer(language,firstJoin,lastJoin,joins,lastIP,ips,uuid) VALUES (?,?,?,?,?,?,?,?);");
                insert.setString(1, Languages.EN.name());
                insert.setString(2, "" + System.currentTimeMillis());
                insert.setString(3, "" + System.currentTimeMillis());
                insert.setString(4, String.join(",", Lists.newArrayList(System.currentTimeMillis() + "")));
                insert.setString(5, "not joined yet " + UUID.randomUUID());
                insert.setString(6, String.join(",", new ArrayList<String>()));
                insert.setString(7, player.getUniqueId().toString());
                insert.executeUpdate();
                new MoneyAPI(this, Money.GEMS);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public AXEPlayer(String uuid) {
        if(Objects.requireNonNull(Bukkit.getPlayer(UUID.fromString(uuid))).isOnline()) {
            player = Bukkit.getPlayer(uuid);
            online = true;
            try (Connection connection = AXECore.getDriver().getDataSource().getConnection()) {
                PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS axePlayer(TEXT language, TEXT firstJoin, TEXT lastJoin, TEXT joins, TEXT lastIP, TEXT ips, TEXT uuid);");
                statement.executeUpdate();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            try (Connection connection = AXECore.getDriver().getDataSource().getConnection()) {
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM axePlayer WHERE uuid = ?");
                statement.setString(1, uuid);
                ResultSet resultSet = statement.executeQuery();
                if (!resultSet.next()) {
                    PreparedStatement insert = connection.prepareStatement("INSERT INTO axePlayer(language,firstJoin,lastJoin,joins,lastIP,ips,uuid) VALUES (?,?,?,?,?,?,?,?);");
                    insert.setString(1, Languages.EN.name());
                    insert.setString(2, "" + System.currentTimeMillis());
                    insert.setString(3, "" + System.currentTimeMillis());
                    insert.setString(4, String.join(", ", Lists.newArrayList(System.currentTimeMillis() + "")));
                    insert.setString(5, Objects.requireNonNull(Objects.requireNonNull(Bukkit.getPlayer(UUID.fromString(uuid))).getAddress()).getHostName());
                    insert.setString(6, String.join(", ", Lists.newArrayList(Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(Bukkit.getPlayer(UUID.fromString(uuid))).getAddress()).getHostName()))));
                    insert.setString(7, uuid);
                    insert.executeUpdate();
                    new MoneyAPI(this, Money.GEMS);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            player = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
            online = false;
            try (Connection connection = AXECore.getDriver().getDataSource().getConnection()) {
                PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS axePlayer(TEXT language, TEXT firstJoin, TEXT uuid);");
                statement.executeUpdate();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            try (Connection connection = AXECore.getDriver().getDataSource().getConnection()) {
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM axePlayer WHERE uuid = ?");
                statement.setString(1, uuid);
                ResultSet resultSet = statement.executeQuery();
                if (!resultSet.next()) {
                    PreparedStatement insert = connection.prepareStatement("INSERT INTO axePlayer(language,firstJoin,uuid) VALUES (?,?,?);");
                    insert.setString(1, Languages.EN.name());
                    insert.setString(2, "" + System.currentTimeMillis());
                    insert.setString(3, uuid);
                    insert.executeUpdate();
                    new MoneyAPI(this, Money.GEMS);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void sendResourcePack() {
        new ResourcepackManager(this).send();
    }

    public boolean isOnline() {
        return online;
    }

    public OfflinePlayer getBukkitOfflinePlayer() {
        return (OfflinePlayer) player;
    }

    public void setLanguage(Languages language) {
        try (Connection connection = AXECore.getDriver().getDataSource().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE axePlayer SET language = ? WHERE uuid = ?;");
            statement.setString(1, language.name());
            statement.setString(2, getUUID());
            statement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Languages getLanguage() {
        try (Connection connection = AXECore.getDriver().getDataSource().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT language FROM axePlayer WHERE uuid = ?");
            statement.setString(1, getUUID());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Languages.valueOf(resultSet.getString("language"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Languages.EN;
    }

    public Player getBukkitPlayer() {
        return (Player) player;
    }

    public String getName() {
        if(!isOnline()) {
            return getBukkitOfflinePlayer().getName();
        } else {
            return getBukkitPlayer().getName();
        }
    }

    public String getUUID() {
        if(!isOnline()) {
            return getBukkitOfflinePlayer().getUniqueId().toString();
        } else {
            return getBukkitPlayer().getUniqueId().toString();
        }
    }

    public void connect(String server) {
        if(isOnline()) {
            try {
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(byteArray);
                out.writeUTF("Connect");
                out.writeUTF(server);
                ((Player)player).sendPluginMessage(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("AXECore")), "BungeeCord", byteArray.toByteArray());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
