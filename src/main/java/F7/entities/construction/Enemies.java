package F7.entities.construction;

import java.util.HashMap;
import F7.entities.classes.*;

public class Enemies { 
    private static Enemy bear = new Enemy("Bear", "Native to these lands,\nand very territorial.", 7, 4, 1, 68, Rarities.common);

    private static Enemy hobo = new Enemy("Hobo", "Putting up a fight\nwith an F7 android? \nThis can't go well for them.", 3, 5, 1, 73, Rarities.common);
    
    private static Enemy nomad = new Enemy("Nomad", "Weary travelers who want\nyour parts for cash.", 9, 4, 1, 75, Rarities.uncommon);

    private static Enemy swarm = new Enemy("Swarm of Bees", "Not something you want\nto be caught up in.", 11, 3, 1, 68, Rarities.uncommon);

    private static Enemy f3 = new Enemy("F3", "A younger generation of\nthe F-series android. They\nexist only to fight.", 6, 6, 1, 84, Rarities.rare);
    
    private static Enemy logger = new Enemy("Logger", "Big.\nBulky.\nHas a chainsaw.\nDon't get hit.", 9, 11, 1, 35, Rarities.rare);

    private static Enemy mutantHawk = new Enemy("Mutant Hawk", "The hawks around here\nhave been hanging around\nthe radiation for too long.", 12, 6, 1, 70, Rarities.exceptional);

    private static Enemy tank = new Enemy("M7-Abrams Tank", "This hunk of metal\ncould destroy you in\nan instant.", 20, 10, 1, 30, Rarities.exceptional);

    private static Enemy angel = new Enemy("Angel", "They've had enough\nof your hubris.\nYou go against the\nnatural law. ", 8, 10, 1, 95, Rarities.godly);

    // TODO: make private
    public static HashMap<Rarity, Enemy[]> enemyHashMap = new HashMap<>();

    public static HashMap<Rarity, Enemy[]> getEnemyHashMap() {return enemyHashMap;}

    public static void setEnemyHashMap() {
        enemyHashMap.put(Rarities.common, new Enemy[] {bear, hobo});
        enemyHashMap.put(Rarities.uncommon, new Enemy[] {nomad, swarm});
        enemyHashMap.put(Rarities.rare, new Enemy[] {f3, logger});
        enemyHashMap.put(Rarities.exceptional, new Enemy[] {mutantHawk, tank});
        enemyHashMap.put(Rarities.godly, new Enemy[] {angel});
    }
}
