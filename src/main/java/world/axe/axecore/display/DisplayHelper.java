package world.axe.axecore.display;

public class DisplayHelper {

    public static class inventories {

        public enum language {

            EN("⊂"),
            DE("‡"),
            FR("➽");

            public final String val;

            language(String val) {
                this.val = val;
            }

            public String val() {
                return this.val;
            }
        }

    }

    public enum special {

        ANCIENT("╗"),
        ARTIFACT("╝"),
        COSMETIC("¢"),
        DIVINE("┐"),
        EXOTIC("└"),
        JUNK("┴"),
        MAGIC("┬"),
        MYTHIC("├"),
        PREMIUM("─"),
        TRASH("┼"),
        UNIQUE("╚");

        private final String val;

        special(String val) {
            this.val = val;
        }

        public String val() {
            return this.val;
        }
    }

    public enum quality {

        A("▒"),
        B("▓"),
        C("█"),
        D("│"),
        E("┤"),
        F("║"),
        S("╣");

        private final String val;

        quality(String val) {
            this.val = val;
        }

        public String val() {
            return this.val;
        }
    }

    public enum items {

        CLAIM("▼"),
        CODEX("►"),
        GUIDE_TAG("◄"),
        INFO("■"),
        MAIN_MENU("▄"),
        MENU("▬"),
        TALK("▀"),
        VAULT("░");

        private final String val;

        items(String val) {
            this.val = val;
        }

        public String val() {
            return this.val;
        }
    }

    public enum explicit {

        ADVANCED_TAG("↔"),
        CRIT_CHANCE("⇧"),
        CRIT_DAMAGE("↕"),
        DEFENSE("➤"),
        DOOM_TAG("☝"),
        DRACONIC_TAG("☟"),
        EASY_TAG("☜"),
        EXALTED_TAG("☞"),
        GROWTH_TAG("½"),
        HARD_TAG("¼"),
        HELL_TAG("¾"),
        LIMITED_TAG("‰"),
        MEDIUM_TAG("ண"),
        RELIC_TAG("ஒ"),
        SPEED("·"),
        SPIRITUAL_TAG("•"),
        STRENGTH("●"),
        TAGS("○"),
        TIKTOK_TAG("◘"),
        UNOBTAINABLE_TAG("¤"),
        UNTRADEABLE_TAG("Θ"),
        WISDOM("▲");

        private final String val;

        explicit(String val) {
            this.val = val;
        }

        public String val() {
            return this.val;
        }
    }

    public enum common {

        ONE_STAR("✿"),
        TWO_STAR("❖"),
        THREE_STAR("☂"),
        FOUR_STAR("☃"),
        FIVE_STAR("♀");

        private final String val;

        common(String val) {
            this.val = val;
        }

        public String val() {
            return this.val;
        }

    }

    public enum epic {

        ONE_STAR("♂"),
        TWO_STAR("‼"),
        THREE_STAR("¶"),
        FOUR_STAR("ð"),
        FIVE_STAR("☯");

        private final String val;

        epic(String val) {
            this.val = val;
        }

        public String val() {
            return this.val;
        }

    }

    public enum heroic {

        ONE_STAR("✌"),
        TWO_STAR("♆"),
        THREE_STAR("〄"),
        FOUR_STAR("☣"),
        FIVE_STAR("☛");

        private final String val;

        heroic(String val) {
            this.val = val;
        }

        public String val() {
            return this.val;
        }

    }

    public enum legendary {

        ONE_STAR("Ψ"),
        TWO_STAR("✐"),
        THREE_STAR("¿"),
        FOUR_STAR("æ"),
        FIVE_STAR("ℛ");

        private final String val;

        legendary(String val) {
            this.val = val;
        }

        public String val() {
            return this.val;
        }

    }

    public enum mythical {

        ONE_STAR("�"),
        TWO_STAR("✖"),
        THREE_STAR("▦"),
        FOUR_STAR("×"),
        FIVE_STAR("☒");

        private final String val;

        mythical(String val) {
            this.val = val;
        }

        public String val() {
            return this.val;
        }

    }

    public enum rare {

        ONE_STAR("✝"),
        TWO_STAR("♠"),
        THREE_STAR("♥"),
        FOUR_STAR("♦"),
        FIVE_STAR("♣");

        private final String val;

        rare(String val) {
            this.val = val;
        }

        public String val() {
            return this.val;
        }

    }

    public enum ultrarare {

        ONE_STAR("❤"),
        TWO_STAR("♡"),
        THREE_STAR("♥"),
        FOUR_STAR("❥"),
        FIVE_STAR("↑");

        private final String val;

        ultrarare(String val) {
            this.val = val;
        }

        public String val() {
            return this.val;
        }

    }

    public enum uncommon {

        ONE_STAR("←"),
        TWO_STAR("→"),
        THREE_STAR("↓"),
        FOUR_STAR("➥"),
        FIVE_STAR("↨");

        private final String val;

        uncommon(String val) {
            this.val = val;
        }

        public String val() {
            return this.val;
        }

    }

    public enum admin {

        CONSOLE_TAG("♔"),
        SERVER("彡");

        private final String val;

        admin(String val) {
            this.val = val;
        }

        public String val() {
            return this.val;
        }

    }

    public enum ranks {

        ADMIN_TAG("ⓑ"),
        ANCIENT_TAG("ⓒ"),
        BAMBOO_TAG("ⓓ"),
        BARON_TAG("ⓔ"),
        CHAMPION_TAG("ÿ"),
        BOOSTER_TAG("ⓕ"),
        CAESAR_TAG("ⓖ"),
        DEVELOPER_TAG("ⓗ"),
        DONOR_TAG("ⓘ"),
        DUKE_TAG("ⓙ"),
        ELITE_TAG("ⓚ"),
        EMPEROR_TAG("ⓛ"),
        ENGINEER("ⓜ"),
        EVENT_GUIDE("ⓝ"),
        EXTREME_TAG("ⓞ"),
        GOD_TAG("ⓟ"),
        BUILDER_TAG("☼"),
        HEADADMIN_TAG("ⓠ"),
        HELPER_TAG("ⓡ"),
        HERO_TAG("ⓢ"),
        IMMORTAL_TAG("ⓣ"),
        INSANE_TAG("ⓤ"),
        KNIGHT_TAG("ⓥ"),
        KRATOS_TAG("ⓦ"),
        LEGEND_TAG("ⓧ"),
        LEGION_TAG("ⓨ"),
        MAGE_TAG("ⓩ"),
        MANAGER_TAG("☆"),
        MOD_TAG("★"),
        MVP_TAG("✰"),
        MVPPLUS_TAG("☼"),
        MVPPLUSPLUS_TAG("❅"),
        NOBLE_TAG("☀"),
        OMEGA_TAG("❊"),
        OWNER_TAG("✪"),
        PRINCE_TAG("✼"),
        RSTAFF_TAG("☻"),
        SAGE_TAG("☺"),
        SRMOD_TAG("シ"),
        STAFF_TAG("☠"),
        TESTER_TAG("☎"),
        TWITCH_TAG("♫"),
        ULTRA_TAG("♪"),
        VIP_TAG("™"),
        VIPPLUS_TAG("®"),
        VIPPLUSPLUS_TAG("©"),
        YOUTUBER_TAG("☢");

        private final String val;

        ranks(String val) {
            this.val = val;
        }

        public String val() {
            return this.val;
        }
    }

    public enum npc {

        ADVENTURER_TAG("⑦"),
        ALCHEMIST("⑧"),
        AUCTION("⑨"),
        BANK("⑩"),
        BATTLEPASS("❶"),
        COSMETICS("➋"),
        CRATES("➌"),
        DOCTOR("➍"),
        ECONOMY("➎"),
        ENCHANT("➏"),
        ENCHANTER("➐"),
        EXCHANGE("➑"),
        FACTION("➒"),
        FAMILY_TAG("❿"),
        GUARDIAN_TAG("Ⓐ"),
        HERALD_TAG("Ⓑ"),
        JOBS("Ⓒ"),
        KEYSMITH("Ⓓ"),
        KITS("Ⓔ"),
        MARKET("Ⓕ"),
        MERCHANT("Ⓖ"),
        MYSTERY("Ⓗ"),
        PETSHOP("Ⓘ"),
        PLAYER_SHOP("Ⓙ"),
        QUEST("◁"),
        RANKUP("Ⓛ"),
        REFORGE("Ⓜ"),
        REWARDS("Ⓝ"),
        SECURITY("Ⓞ"),
        SETTINGS("Ⓟ"),
        SHOP("Ⓠ"),
        SKILLS("Ⓡ"),
        STATISTIC("Ⓢ"),
        SUPPORT_TAG("Ⓣ"),
        TINKERER("Ⓤ"),
        TRADER("Ⓥ"),
        TUTORIAL_GUIDE("Ⓧ"),
        VILLAGER_TAG("Ⓦ"),
        VOTE("Ⓨ"),
        WARP("Ⓩ"),
        WARRIOR_TAG("ⓐ");
        private final String val;

        npc(String val) {
            this.val = val;
        }

        public String val() {
            return this.val;
        }

    }

    public enum icons {

        PIXEL("①"),
        DONATE("②"),
        LOGIN("③"),
        MONEY("④"),
        RUBY("⑤"),
        RUBY_ICON("⑥");

        private final String val;
        icons(String val) {
            this.val = val;
        }

        public String val() {
            return this.val;
        }
    }

}
