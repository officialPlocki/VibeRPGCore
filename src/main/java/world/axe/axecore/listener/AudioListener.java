package world.axe.axecore.listener;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import world.axe.axecore.AXECore;
import world.axe.axecore.player.AXEPlayer;
import world.axe.axecore.player.Profile;
import world.axe.axecore.player.VoicePacks;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class AudioListener implements Listener {

    private static final HashMap<Player, Integer> map = new HashMap<>();

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if(!AXECore.getAudio().isPlaying(event.getPlayer())) {
            Profile profile = new AXEPlayer(event.getPlayer()).getActiveProfile();
            if(profile.getSoundSettings().isAmbientMusic()) {
                List<String> sounds = AXECore.getAudio().getBiomeMusic(event.getPlayer().getLocation().getBlock().getBiome());
                String sound = sounds.get(new Random().nextInt(sounds.size()) - 1);
                if(profile.getSoundSettings().isAnnounce()) {
                    event.getPlayer().sendMessage("Playing now: " + sound.replaceAll("wav_", "") + " - Electronic AXE");
                }
                AXECore.getAudio().playSound(event.getPlayer(), sound, AXECore.getAudio().getSoundVolume(sound), true);
            }
        }
        if(!(event.getFrom().distance(event.getTo()) <= 0.21)) {
            if(!event.getPlayer().isFlying()) {
                if(!event.getPlayer().isJumping()) {
                    Profile profile = new AXEPlayer(event.getPlayer()).getActiveProfile();
                    map.put(event.getPlayer(), map.getOrDefault(event.getPlayer(), 0) + 1);
                    if(map.get(event.getPlayer()) >= 10) {
                        map.put(event.getPlayer(), 0);
                        if ((int) event.getFrom().getX() != (int) event.getTo().getX() || (int) event.getFrom().getY() != (int) event.getTo().getY() || (int) event.getFrom().getZ() != (int) event.getTo().getZ()) {
                            if(event.getPlayer().isSprinting()) {
                                if(profile.getSoundSettings().isBreath()) {
                                    VoicePacks pack = profile.getVoicePack();
                                    if(pack.name().contains("male")) {
                                        List<String> sounds = AXECore.getAudio().getSoundPack("male_c_breath_fast_single");
                                        String sound = sounds.get(new Random().nextInt(sounds.size() - 1));
                                        AXECore.getAudio().playSound(event.getPlayer(), sound, 5, false);
                                    } else {
                                        List<String> sounds = AXECore.getAudio().getSoundPack("female_a_breath_run");
                                        String sound = sounds.get(new Random().nextInt(sounds.size()) - 1);
                                        AXECore.getAudio().playSound(event.getPlayer(), sound, 5, false);
                                    }
                                }
                            } else {
                                Random random = new Random();
                                if(random.nextInt(10) == 4) {
                                    if(profile.getSoundSettings().isBreath()) {
                                        VoicePacks pack = profile.getVoicePack();
                                        if(pack.name().contains("male")) {
                                            List<String> sounds = AXECore.getAudio().getSoundPack("male_b_breathing_sequence");
                                            for(String s : sounds) {
                                                if(s.contains("long")) {
                                                    sounds.remove(s);
                                                }
                                            }
                                            String sound = sounds.get(new Random().nextInt(sounds.size() - 1));
                                            AXECore.getAudio().playSound(event.getPlayer(), sound, 5, false);
                                        } else {
                                            List<String> sounds = AXECore.getAudio().getSoundPack("female_a_breath_jog");
                                            String sound = sounds.get(new Random().nextInt(sounds.size() - 1));
                                            AXECore.getAudio().playSound(event.getPlayer(), sound, 5, false);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onJump(PlayerJumpEvent event) {
        if(event.getPlayer().isSprinting()) return;
        Profile profile = new AXEPlayer(event.getPlayer()).getActiveProfile();
        if(profile.getSoundSettings().isJump()) {
            VoicePacks pack = profile.getVoicePack();
            if(pack.equals(VoicePacks.male_a)) {
                List<String> sounds = AXECore.getAudio().getSoundPack(pack.name() + "_effort_grunt");
                String sound = sounds.get(new Random().nextInt(sounds.size() - 1));
                AXECore.getAudio().playSound(event.getPlayer(), sound, 35, false);
            } else if(pack.equals(VoicePacks.male_b)) {
                List<String> sounds = AXECore.getAudio().getSoundPack(pack.name() + "_effort_jump");
                String sound = sounds.get(new Random().nextInt(sounds.size() - 1));
                AXECore.getAudio().playSound(event.getPlayer(), sound, 35, false);
            } else if(pack.equals(VoicePacks.male_c)) {
                List<String> sounds = AXECore.getAudio().getSoundPack(pack.name() + "_effort_short_jump");
                String sound = sounds.get(new Random().nextInt(sounds.size() - 1));
                AXECore.getAudio().playSound(event.getPlayer(), sound, 35, false);
            } else if(pack.equals(VoicePacks.female_a)) {
                List<String> sounds = AXECore.getAudio().getSoundPack(pack.name() + "_effort_jump");
                String sound = sounds.get(new Random().nextInt(sounds.size() - 1));
                AXECore.getAudio().playSound(event.getPlayer(), sound, 35, false);
            } else if(pack.equals(VoicePacks.female_b)) {
                List<String> sounds = AXECore.getAudio().getSoundPack(pack.name() + "_effort_jump");
                String sound = sounds.get(new Random().nextInt(sounds.size() - 1));
                AXECore.getAudio().playSound(event.getPlayer(), sound, 35, false);
            } else if(pack.equals(VoicePacks.female_c)) {
                List<String> sounds = AXECore.getAudio().getSoundPack(pack.name() + "_effort_jump");
                String sound = sounds.get(new Random().nextInt(sounds.size() - 1));
                AXECore.getAudio().playSound(event.getPlayer(), sound, 35, false);
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Profile profile = new AXEPlayer(event.getPlayer()).getActiveProfile();
        if(profile.getSoundSettings().isDeath()) {
            VoicePacks pack = profile.getVoicePack();
            if(pack.equals(VoicePacks.male_a)) {
                List<String> sounds = AXECore.getAudio().getSoundPack(pack.name() + "_grunt_pain_death");
                String sound = sounds.get(new Random().nextInt(sounds.size() - 1));
                AXECore.getAudio().playSound(event.getPlayer(), sound, 35, false);
            } else if(pack.equals(VoicePacks.male_b)) {
                List<String> sounds = AXECore.getAudio().getSoundPack(pack.name() + "_death_high");
                sounds.addAll(AXECore.getAudio().getSoundPack(pack.name() + "_death_low"));
                String sound = sounds.get(new Random().nextInt(sounds.size() - 1));
                AXECore.getAudio().playSound(event.getPlayer(), sound, 35, false);
            } else if(pack.equals(VoicePacks.male_c)) {
                List<String> sounds = AXECore.getAudio().getSoundPack(pack.name() + "_death");
                String sound = sounds.get(new Random().nextInt(sounds.size() - 1));
                AXECore.getAudio().playSound(event.getPlayer(), sound, 35, false);
            } else if(pack.equals(VoicePacks.male_d)) {
                List<String> sounds = AXECore.getAudio().getSoundPack(pack.name() + "_death");
                String sound = sounds.get(new Random().nextInt(sounds.size() - 1));
                AXECore.getAudio().playSound(event.getPlayer(), sound, 35, false);
            } else if(pack.equals(VoicePacks.female_a)) {
                List<String> sounds = AXECore.getAudio().getSoundPack(pack.name() + "_death");
                String sound = sounds.get(new Random().nextInt(sounds.size() - 1));
                AXECore.getAudio().playSound(event.getPlayer(), sound, 35, false);
            } else if(pack.equals(VoicePacks.female_b)) {
                List<String> sounds = AXECore.getAudio().getSoundPack(pack.name() + "_death");
                String sound = sounds.get(new Random().nextInt(sounds.size() - 1));
                AXECore.getAudio().playSound(event.getPlayer(), sound, 35, false);
            } else if(pack.equals(VoicePacks.female_c)) {
                List<String> sounds = AXECore.getAudio().getSoundPack(pack.name() + "_death");
                String sound = sounds.get(new Random().nextInt(sounds.size() - 1));
                AXECore.getAudio().playSound(event.getPlayer(), sound, 35, false);
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            if(event.getDamage() >= 1) {
                Profile profile = new AXEPlayer(player).getActiveProfile();
                if(profile.getSoundSettings().isAttack()) {
                    VoicePacks pack = profile.getVoicePack();
                    if(pack.equals(VoicePacks.male_a)) {
                        List<String> sounds = AXECore.getAudio().getSoundPack("male_b_attack_set");
                        String sound = sounds.get(new Random().nextInt(sounds.size() - 1));
                        AXECore.getAudio().playSound(player, sound, 35, false);
                    } else if(pack.equals(VoicePacks.male_b)) {
                        List<String> sounds = AXECore.getAudio().getSoundPack(pack.name() + "_attack_set");
                        String sound = sounds.get(new Random().nextInt(sounds.size() - 1));
                        AXECore.getAudio().playSound(player, sound, 35, false);
                    } else if(pack.equals(VoicePacks.male_c)) {
                        List<String> sounds = AXECore.getAudio().getSoundPack(pack.name() + "_attack");
                        String sound = sounds.get(new Random().nextInt(sounds.size() - 1));
                        AXECore.getAudio().playSound(player, sound, 35, false);
                    } else if(pack.equals(VoicePacks.male_d)) {
                        List<String> sounds = AXECore.getAudio().getSoundPack(pack.name() + "_attack_groan");
                        String sound = sounds.get(new Random().nextInt(sounds.size() - 1));
                        AXECore.getAudio().playSound(player, sound, 35, false);
                    } else if(pack.equals(VoicePacks.female_a)) {
                        List<String> sounds = AXECore.getAudio().getSoundPack(pack.name() + "_attack");
                        String sound = sounds.get(new Random().nextInt(sounds.size() - 1));
                        AXECore.getAudio().playSound(player, sound, 35, false);
                    } else if(pack.equals(VoicePacks.female_b)) {
                        List<String> sounds = AXECore.getAudio().getSoundPack(pack.name() + "_attack_set");
                        String sound = sounds.get(new Random().nextInt(sounds.size() - 1));
                        AXECore.getAudio().playSound(player, sound, 35, false);
                    } else if(pack.equals(VoicePacks.female_c)) {
                        List<String> sounds = AXECore.getAudio().getSoundPack(pack.name() + "_attack");
                        String sound = sounds.get(new Random().nextInt(sounds.size() - 1));
                        AXECore.getAudio().playSound(player, sound, 35, false);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if(event.getDamage() >= 1) {
                Profile profile = new AXEPlayer(player).getActiveProfile();
                if(profile.getSoundSettings().isDamage()) {
                    VoicePacks pack = profile.getVoicePack();
                    if(pack.equals(VoicePacks.male_a)) {
                        List<String> sounds = AXECore.getAudio().getSoundPack(pack.name() + "_grunt_pain");
                        String sound = sounds.get(new Random().nextInt(sounds.size() - 1));
                        AXECore.getAudio().playSound(player, sound, 35, false);
                    } else if(pack.equals(VoicePacks.male_b)) {
                        List<String> sounds = AXECore.getAudio().getSoundPack(pack.name() + "_hurt_pain_set");
                        String sound = sounds.get(new Random().nextInt(sounds.size() - 1));
                        AXECore.getAudio().playSound(player, sound, 35, false);
                    } else if(pack.equals(VoicePacks.male_c)) {
                        List<String> sounds = AXECore.getAudio().getSoundPack(pack.name() + "_hurt_pain");
                        String sound = sounds.get(new Random().nextInt(sounds.size() - 1));
                        AXECore.getAudio().playSound(player, sound, 35, false);
                    } else if(pack.equals(VoicePacks.male_d)) {
                        List<String> sounds = AXECore.getAudio().getSoundPack(pack.name() + "_hurt_pain");
                        String sound = sounds.get(new Random().nextInt(sounds.size() - 1));
                        AXECore.getAudio().playSound(player, sound, 35, false);
                    } else if(pack.equals(VoicePacks.female_a)) {
                        List<String> sounds = AXECore.getAudio().getSoundPack(pack.name() + "_attack");
                        String sound = sounds.get(new Random().nextInt(sounds.size() - 1));
                        AXECore.getAudio().playSound(player, sound, 35, false);
                    } else if(pack.equals(VoicePacks.female_b)) {
                        List<String> sounds = AXECore.getAudio().getSoundPack(pack.name() + "_attack_set");
                        String sound = sounds.get(new Random().nextInt(sounds.size() - 1));
                        AXECore.getAudio().playSound(player, sound, 35, false);
                    } else if(pack.equals(VoicePacks.female_c)) {
                        List<String> sounds = AXECore.getAudio().getSoundPack(pack.name() + "_attack");
                        String sound = sounds.get(new Random().nextInt(sounds.size() - 1));
                        AXECore.getAudio().playSound(player, sound, 35, false);
                    }
                }
            }
        }
    }

}
