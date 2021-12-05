package F7.entities.construction;

import java.util.HashMap;
import F7.entities.classes.*;

public class Enemies { 
    public static Enemy bear = new Enemy("Bear", "Native to these lands, and very territorial.", 7, 4, 1, 68, Rarities.common);

    public static Enemy hobo = new Enemy("Hobo", "Putting up a fight with an F7 android? This can't go well for them.", 3, 5, 1, 73, Rarities.common);
    
    public static Enemy nomad = new Enemy("Nomad", "Weary travelers who want your parts for cash.", 9, 4, 1, 75, Rarities.uncommon);

    public static Enemy swarm = new Enemy("Swarm of Bees", "Not something you want to be caught up in.", 11, 3, 1, 68, Rarities.uncommon);

    public static Enemy f3 = new Enemy("F3", "A younger generation of the F-series android. They exist only to fight.", 6, 6, 1, 84, Rarities.rare);
    
    public static Enemy logger = new Enemy("Logger", "Big. Bulky. Has a chainsaw.", 9, 11, 1, 35, Rarities.rare);

    public static Enemy mutantHawk = new Enemy("Mutant Hawk", "The hawks around here have been hanging around the radiation for too long.", 12, 6, 1, 70, Rarities.exceptional);

    public static Enemy tank = new Enemy("M7-Abrams Tank", "This hunk of metal could destroy you in an instant.", 20, 10, 1, 30, Rarities.exceptional);

    public static Enemy angel = new Enemy("Angel", "They've had enough of your hubris.", 8, 10, 1, 95, Rarities.godly);

    // can remove now because no need to use MAX_VALUE
    //public static Enemy dev = new Enemy("The Developer", ".oremoR nhoJ ,em llik tsum uoy ,emag eht niw oT", Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 101, Rarities.godly);

    public static HashMap<Rarity, Enemy[]> enemyHashMap = new HashMap<>();
    
    public static void setEnemyHashMap() {
        enemyHashMap.put(Rarities.common, new Enemy[] {bear, hobo});
        enemyHashMap.put(Rarities.uncommon, new Enemy[] {nomad, swarm});
        enemyHashMap.put(Rarities.rare, new Enemy[] {f3, logger});
        enemyHashMap.put(Rarities.exceptional, new Enemy[] {mutantHawk, tank});
        enemyHashMap.put(Rarities.godly, new Enemy[] {angel});
    }
}
