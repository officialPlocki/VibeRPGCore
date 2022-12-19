package eu.viberpg.core.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import eu.viberpg.core.Core;

public class MeasureCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender.hasPermission("axe.measure")) {
            Core.getAudio().importSounds(args[0], sender);
        }
        return false;
    }

}
