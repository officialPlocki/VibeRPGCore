package eu.viberpg.core.economy;

import eu.viberpg.core.Core;
import eu.viberpg.core.custom.LabyModDisplay;
import eu.viberpg.core.player.AXEPlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MoneyAPI {

    private final Money save;
    private final AXEPlayer player;

    public MoneyAPI(AXEPlayer player, Money save) {
        this.save = save;
        this.player = player;
        for(Money money : Money.values()) {
            try(Connection connection = Core.getDriver().getDataSource().getConnection()) {
                PreparedStatement table = connection.prepareStatement("CREATE TABLE IF NOT EXISTS playerMoney" + money.name() + "(money TEXT, uuid TEXT);");
                table.executeUpdate();
                if(connection.prepareStatement("SELECT money FROM playerMoney" + money.name() + " WHERE uuid = '" + player.getBukkitPlayer().getUniqueId() + "'").executeQuery().next()) return;
                PreparedStatement statement = connection.prepareStatement("INSERT INTO playerMoney" + money.name() + "(money,uuid) VALUES (?,?)");
                statement.setString(1, "0.0");
                statement.setString(2, player.getUUID());
                statement.executeUpdate();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        new LabyModDisplay().updateRubyDisplay(player.getBukkitPlayer(), 0);
        new LabyModDisplay().updateGemsDisplay(player.getBukkitPlayer(), 0);
    }

    public void addMoney(double money) {
        try(Connection connection = Core.getDriver().getDataSource().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT money FROM playerMoney" + save.name() + " WHERE uuid = ?");
            statement.setString(1, player.getUUID());
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                double tmp = Double.parseDouble(result.getString("money")) + money;
                PreparedStatement ts = connection.prepareStatement("UPDATE playerMoney" + save.name() + " SET money = ? WHERE uuid = ?");
                ts.setString(1, "" + tmp);
                ts.setString(2, player.getUUID());
                ts.executeUpdate();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        new LabyModDisplay().updateRubyDisplay(player.getBukkitPlayer(), ((int) new MoneyAPI(player, Money.RUBY).getMoney()));
        new LabyModDisplay().updateGemsDisplay(player.getBukkitPlayer(), ((int) new MoneyAPI(player, Money.GEMS).getMoney()));
    }

    public void removeMoney(double money) {
        try(Connection connection = Core.getDriver().getDataSource().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT money FROM playerMoney" + save.name() + " WHERE uuid = ?");
            statement.setString(1, player.getUUID());
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                double tmp = Double.parseDouble(result.getString("money")) - money;
                PreparedStatement ts = connection.prepareStatement("UPDATE playerMoney" + save.name() + " SET money = ? WHERE uuid = ?");
                ts.setString(1, "" + tmp);
                ts.setString(2, player.getUUID());
                ts.executeUpdate();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        new LabyModDisplay().updateRubyDisplay(player.getBukkitPlayer(), ((int) new MoneyAPI(player, Money.RUBY).getMoney()));
        new LabyModDisplay().updateGemsDisplay(player.getBukkitPlayer(), ((int) new MoneyAPI(player, Money.GEMS).getMoney()));
    }

    public double getMoney() {
        try(Connection connection = Core.getDriver().getDataSource().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT money FROM playerMoney" + save.name() + " WHERE uuid = ?");
            statement.setString(1, player.getUUID());
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                return Double.parseDouble(result.getString("money"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public void resetMoney() {
        try(Connection connection = Core.getDriver().getDataSource().getConnection()) {
            PreparedStatement ts = connection.prepareStatement("UPDATE playerMoney" + save.name() + " SET money = ? WHERE uuid = ?");
            ts.setString(1, "0.0");
            ts.setString(2, player.getBukkitPlayer().getUniqueId().toString());
            ts.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        new LabyModDisplay().updateRubyDisplay(player.getBukkitPlayer(), ((int) new MoneyAPI(player, Money.RUBY).getMoney()));
        new LabyModDisplay().updateGemsDisplay(player.getBukkitPlayer(), ((int) new MoneyAPI(player, Money.GEMS).getMoney()));
    }

    public void setMoney(double money) {
        try(Connection connection = Core.getDriver().getDataSource().getConnection()) {
            PreparedStatement ts = connection.prepareStatement("UPDATE playerMoney" + save.name() + " SET money = ? WHERE uuid = ?");
            ts.setString(1, "" + money);
            ts.setString(2, player.getBukkitPlayer().getUniqueId().toString());
            ts.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        new LabyModDisplay().updateRubyDisplay(player.getBukkitPlayer(), ((int) new MoneyAPI(player, Money.RUBY).getMoney()));
        new LabyModDisplay().updateGemsDisplay(player.getBukkitPlayer(), ((int) new MoneyAPI(player, Money.GEMS).getMoney()));
    }

    public boolean hasMoney(double money) {
        return getMoney() >= money;
    }

}
