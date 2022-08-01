package world.axe.axecore.economy;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import world.axe.axecore.player.AXEPlayer;

import java.util.List;
import java.util.Objects;

public class VaultIntegration implements Economy {

    public EconomyResponse bankBalance(final String arg0) {
        return null;
    }

    public EconomyResponse bankDeposit(final String arg0, final double arg1) {
        return null;
    }

    public EconomyResponse bankHas(final String arg0, final double arg1) {
        return null;
    }

    public EconomyResponse bankWithdraw(final String arg0, final double arg1) {
        return null;
    }

    public EconomyResponse createBank(final String arg0, final String arg1) {
        return null;
    }

    public EconomyResponse createBank(final String arg0, final OfflinePlayer arg1) {
        return null;
    }

    public boolean createPlayerAccount(final String arg0) {
        return true;
    }

    public boolean createPlayerAccount(final OfflinePlayer arg0) {
        return true;
    }

    public boolean createPlayerAccount(final String arg0, final String arg1) {
        return true;
    }

    public boolean createPlayerAccount(final OfflinePlayer arg0, final String arg1) {
        return true;
    }

    public String currencyNamePlural() {
        return "";
    }

    public String currencyNameSingular() {
        return "";
    }

    public EconomyResponse deleteBank(final String arg0) {
        return null;
    }

    public EconomyResponse depositPlayer(final String arg0, final double arg1) {
        MoneyAPI api = new MoneyAPI(new AXEPlayer(Bukkit.getPlayer(arg0)), Money.RUBY);
        api.addMoney(arg1);
        return new EconomyResponse(arg1, this.getBalance(arg0), EconomyResponse.ResponseType.SUCCESS, "It worked!");
    }

    public EconomyResponse depositPlayer(final OfflinePlayer arg0, final double arg1) {
        MoneyAPI api = new MoneyAPI(new AXEPlayer(arg0), Money.RUBY);
        api.addMoney(arg1);
        return new EconomyResponse(arg1, this.getBalance(arg0), EconomyResponse.ResponseType.SUCCESS, "It worked!");
    }

    public EconomyResponse depositPlayer(final String arg0, final String arg1, final double arg2) {
        MoneyAPI api = new MoneyAPI(new AXEPlayer(Bukkit.getPlayer(arg0)), Money.RUBY);
        api.addMoney(arg2);
        return new EconomyResponse(arg2, this.getBalance(arg0), EconomyResponse.ResponseType.SUCCESS, "It worked!");
    }

    public EconomyResponse depositPlayer(final OfflinePlayer arg0, final String arg1, final double arg2) {
        MoneyAPI api = new MoneyAPI(new AXEPlayer(arg0), Money.RUBY);
        api.addMoney(arg2);
        return new EconomyResponse(arg2, this.getBalance(arg0), EconomyResponse.ResponseType.SUCCESS, "It worked!");
    }

    public String format(final double arg0) {
        return String.valueOf(arg0);
    }

    public int fractionalDigits() {
        return 0;
    }

    public double getBalance(final String arg0) {
        MoneyAPI api = new MoneyAPI(new AXEPlayer(Bukkit.getPlayer(arg0)), Money.RUBY);
        return api.getMoney();
    }

    public double getBalance(final OfflinePlayer arg0) {
        MoneyAPI api = new MoneyAPI(new AXEPlayer(arg0), Money.RUBY);
        return api.getMoney();
    }

    public double getBalance(final String arg0, final String arg1) {
        MoneyAPI api = new MoneyAPI(new AXEPlayer(Bukkit.getPlayer(arg0)), Money.RUBY);
        return api.getMoney();
    }

    public double getBalance(final OfflinePlayer arg0, final String arg1) {
        MoneyAPI api = new MoneyAPI(new AXEPlayer(arg0), Money.RUBY);
        return api.getMoney();
    }

    public List<String> getBanks() {
        return null;
    }

    public String getName() {
        return "AXECore";
    }

    public boolean has(final String arg0, final double arg1) {
        MoneyAPI api = new MoneyAPI(new AXEPlayer(Bukkit.getPlayer(arg0)), Money.RUBY);
        return api.hasMoney(arg1);
    }

    public boolean has(final OfflinePlayer arg0, final double arg1) {
        MoneyAPI api = new MoneyAPI(new AXEPlayer(arg0), Money.RUBY);
        return api.hasMoney(arg1);
    }

    public boolean has(final String arg0, final String arg1, final double arg2) {
        MoneyAPI api = new MoneyAPI(new AXEPlayer(Bukkit.getPlayer(arg0)), Money.RUBY);
        return api.hasMoney(arg2);
    }

    public boolean has(final OfflinePlayer arg0, final String arg1, final double arg2) {
        MoneyAPI api = new MoneyAPI(new AXEPlayer(arg0), Money.RUBY);
        return api.hasMoney(arg2);
    }

    public boolean hasAccount(final String arg0) {
        return Objects.requireNonNull(Bukkit.getPlayerExact(arg0)).hasPlayedBefore();
    }

    public boolean hasAccount(final OfflinePlayer arg0) {
        return arg0.hasPlayedBefore();
    }

    public boolean hasAccount(final String arg0, final String arg1) {
        return Objects.requireNonNull(Bukkit.getPlayerExact(arg0)).hasPlayedBefore();
    }

    public boolean hasAccount(final OfflinePlayer arg0, final String arg1) {
        return arg0.hasPlayedBefore();
    }

    public boolean hasBankSupport() {
        return false;
    }

    public EconomyResponse isBankMember(final String arg0, final String arg1) {
        return null;
    }

    public EconomyResponse isBankMember(final String arg0, final OfflinePlayer arg1) {
        return null;
    }

    public EconomyResponse isBankOwner(final String arg0, final String arg1) {
        return null;
    }

    public EconomyResponse isBankOwner(final String arg0, final OfflinePlayer arg1) {
        return null;
    }

    public boolean isEnabled() {
        return true;
    }

    public EconomyResponse withdrawPlayer(final String arg0, final double arg1) {
        MoneyAPI api = new MoneyAPI(new AXEPlayer(Bukkit.getPlayer(arg0)), Money.RUBY);
        api.removeMoney(arg1);
        return new EconomyResponse(arg1, this.getBalance(arg0), EconomyResponse.ResponseType.SUCCESS, "It worked!");
    }

    public EconomyResponse withdrawPlayer(final OfflinePlayer arg0, final double arg1) {
        MoneyAPI api = new MoneyAPI(new AXEPlayer(arg0), Money.RUBY);
        api.removeMoney(arg1);
        return new EconomyResponse(arg1, this.getBalance(arg0), EconomyResponse.ResponseType.SUCCESS, "It worked!");
    }

    public EconomyResponse withdrawPlayer(final String arg0, final String arg1, final double arg2) {
        MoneyAPI api = new MoneyAPI(new AXEPlayer(Bukkit.getPlayer(arg0)), Money.RUBY);
        api.removeMoney(arg2);
        return new EconomyResponse(arg2, this.getBalance(arg0), EconomyResponse.ResponseType.SUCCESS, "It worked!");
    }

    public EconomyResponse withdrawPlayer(final OfflinePlayer arg0, final String arg1, final double arg2) {
        MoneyAPI api = new MoneyAPI(new AXEPlayer(arg0), Money.RUBY);
        api.removeMoney(arg2);
        return new EconomyResponse(arg2, this.getBalance(arg0), EconomyResponse.ResponseType.SUCCESS, "It worked!");
    }
}