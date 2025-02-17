package com.eeverest.gui;

import net.minecraft.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public enum Trait {
    NONE("", false),
    UUID("", true),
    // EVEREST
    VOID("", false),
    TO_WISH_APON_A_SHOOTING_STAR("", false),
    INFINITE_POOLS("", false),
    // NICKEL
    HALF_GILLS("When Touching Any Water (including rain) Gain Conduit Power But When Not Touching Water Gain Weakness", false),
    TOP_OF_THE_FOOD_CHAIN("All Hostile Underwater Mobs Are Passive But Dolphins Are Always Aggressive", false),
    BLOOD_SCENT("When A Player Is In Water Or Touching Water (including rain) They Gain The Glowing Effect (Client Side Ofc)", false),
    // VANNIE
    WE_RIDE_AT_SUNSET("Horses that you ride are faster than normal", false),
    FASTEST_HANDS_IN_THE_SYSTEM("Charge/Load all ranged weapons faster", false),
    THE_ROCKETS_RED_GLARE("Indirect damage like explosion that you cause do more damage", false),
    // NOPEABLE
    DIVINE_INTERVENTION("You have a 15% buff in many minor stats, this includes but is not limited to, attack damage, speed, attack speed, hunger and regeneration", false),
    ADRENALINE("All of your previous buffs are instead increased to a 25% increase when your on 3 hearts or less", false),
    DISARMED("You do not have your left arm. You cannot use your offhand or use items like reloading a Crossbow or holding an Amarite Longsword", false),
    // HARPER
    SHAPES_AND_COLOURS("After 5 minutes, there is a 50% chance for me to get a screen effect that simulates the effects of drugs for 1 minute", false),
    NUTRITIONAL("Her nutritional gas and inability  to remove the mask causes her to no longer be able to eat or drink potions, for slower hunger regeneration", false),
    GASSED_UP("The permanent high effecting her body has caused her to gain more energy and her neurons to fire faster", false),
    // HAILEY
    WEEEE("Throws people 3-4 blocks in the air [only when hitting someone with their hands]", false),
    HEAVY_HITTER("Decreased attack speed, increased attack damage", false),
    WEIGHED_DOWN("Increased knockback resistance, but has constant slowness 1", false),
    // PLURIXITY
    CRESCENT_THIEF("Copies/steals the nearest Players abilities", false),
    // KAJ0JAJ0
    SOULLESS_CREATURE("You're not really alive so most monsters dont attack you but since youre not alive you dont have natural regeneration", false),
    STRONG_HANDS_EVEN_STRONGER_MORALS("You deal more base damage (only in sunlight) but you can't get any player lower than half a heart", false),
    PHOTOSYNTHESIS("Being a plant makes it so you can't eat food, you have to rely on a source of light", false),
    // PIPOBUTTER
    MAGMA_COVERED("Because of your magma skin gain fire immunity and  you can swim in lava just like water, But you cannot swim in water as you  will sink because your skin rapid cooling.", false),
    FAMILIARITY("You're a nether born creature and others see you as neutral, if you attack them they will attack back.", false),
    COMFORTING_WARMTH("While being on fire you gain regen one and if inside of lava you have regen 2", false),
    // WAZZO
    SLIPPERY("all blocks behave as ice, making me frictionless", false),
    TALL("step height increase to a full block", false),
    ACCELERATION_MATRIX("because I am frictionless the longer I run the faster I go, and at certain thresholds I gain positive effects. The first is increased attack speed (3X), then even faster I’ll just do damage to people by running into people, and at “maximum” speed I can run on water (and lava if wearing netherite boots), all with the caveat that the faster I go, if I were to crash, I take exponentially increased", false),
    // RAFSA
    STRONGER_THAN_STONE("After being in the caves so long, you have had stone, moss and anything that doesn't or has yet to exist make its home on your body, making you tankier (+2 hearts) but being extremely slow (slowness 2)", false),
    WILL_TO_LIVE_ON("While the infection still tries to weaken you, (less mining speed on everything) your pure determination to live on allows you to have higher stats based on health (+4% to general stats, speed both movement and attack, damage, etc. (just copy divine intervention from nope i aint being original fuck allat) per half-heart lost)", false),
    INFECTION("While you are tall and strong, you aren't immune to disease and corruption, being infected by an indescribable, unkillable infection, only weakened by heat, and while it hides under your hard exterior, it takes your energy and gives you its weaknesses, making you weaker to fire (fire/lava) but stronger against frost (freeze/frostbite)", false),
    // WINTER
    PRIVILEGED_PLATES("Gold armor has properties now", false),
    SMOKED_LUNGS("Winter's badly damaged body, makes it so he drowns quicker and at high altitudes.", false),
    AURIC_ARTERIES("Gold is all over and inside of Winter, making his body overall harder, defence is increased.", false),
    // POM
    LINE_OF_SIGHT("When any player is directly looked at (use the enderman range) they will be highlighted, but only for Mel.", true),
    NO_ENZYMES("Mel can not eat food, or get hungry. She will have buffed natural regeneration instead.", true),
    ACROBATICS("Mel will have natural speed 1 and jump boost 1. Can stack with speed and jump boost potion effects.", true);

    public final String name;
    public final String description;
    public final boolean hidden;

    private Trait(String description, boolean hidden) {
        this.name = this.toString().toLowerCase(Locale.ROOT);
        this.hidden = hidden;
        this.description = description;
    }

    public static @Nullable Trait fromString(String name) {
        for(Trait trait : values()) {
            if (trait.name.equalsIgnoreCase(name)) {
                return trait;
            }
        }

        return null;
    }
}
