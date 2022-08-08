package world.axe.axecore.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import world.axe.axecore.custom.ItemModifier;
import world.axe.axecore.player.AXEPlayer;

public class ProtocolHandler {

    // @todo add selection system over clickable armorstands

    private final Plugin plugin;

    public ProtocolHandler(Plugin plugin) {
        this.plugin = plugin;
    }

    public void init() {
        PacketAdapter adapter = new PacketAdapter(plugin, ListenerPriority.HIGHEST, PacketType.Play.Server.SET_SLOT) {
            @Override
            public void onPacketSending(PacketEvent packetEvent) {
                // Tried packetEvent.getPacket().deepClone();
                PacketContainer packet = packetEvent.getPacket();
                StructureModifier<ItemStack> itemStackStructureModifier = packet.getItemModifier();
                for(int i = 0; i < itemStackStructureModifier.size(); i++) {
                    // Tried itemStackStructureModifier.read(i).clone();
                    ItemStack itemStack = itemStackStructureModifier.read(i);
                    if (itemStack != null) {
                        if(itemStack.getType() == Material.AIR) return;
                        if(itemStack.getItemMeta().hasDisplayName()) {
                            ItemStack stack = new ItemModifier().loadModifications(itemStack, new AXEPlayer(packetEvent.getPlayer()));
                            itemStackStructureModifier.write(i, stack);
                        } else {
                            ItemStack stack = new ItemModifier().loadModifications(itemStack);
                            itemStackStructureModifier.write(i, stack);
                        }
                    }
                }
            }
        };
        ProtocolLibrary.getProtocolManager().addPacketListener(adapter);
    }

}
