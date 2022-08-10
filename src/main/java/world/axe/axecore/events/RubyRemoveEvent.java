package world.axe.axecore.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class RubyRemoveEvent extends Event implements Cancellable {

    private boolean cancelled = false;
    private final String uuid;
    private final double amount;
    private final double before;
    private final double after;


    public RubyRemoveEvent(String uuid, double amount, double before, double after) {
        this.uuid = uuid;
        this.amount = amount;
        this.before = before;
        this.after = after;
    }

    public double getAmount() {
        return amount;
    }

    public double getBefore() {
        return before;
    }

    public double getAfter() {
        return after;
    }

    public String getPlayerUUID() {
        return uuid;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = true;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return new HandlerList();
    }

}
