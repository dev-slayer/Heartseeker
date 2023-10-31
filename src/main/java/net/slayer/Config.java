package net.slayer;

import eu.midnightdust.lib.config.MidnightConfig;

public class Config extends MidnightConfig {
    @Entry public static int minHearts = 3;
    @Entry public static int maxHearts = 20;
    @Entry public static boolean loseHeartsOnDeath = true;
    @Entry public static boolean resetHeartsOnDeath = false;
    @Entry public static int heartConsumptionTime = 20;
}
