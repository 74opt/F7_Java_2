package F7.entities.construction;

import java.util.HashMap;
import F7.entities.classes.*;

public class Enemies { 
    private static Enemy bear = new Enemy("Bear", "Native to these lands,\nand very territorial.", 14, 4, 1, 68, Rarities.getCommon());

    private static Enemy hobo = new Enemy("Hobo", "Putting up a fight\nwith an F7 android? \nThis can't go well for them.", 6, 5, 1, 73, Rarities.getCommon());
    
    private static Enemy nomad = new Enemy("Nomad", "Weary travelers who want\nyour parts for cash.", 18, 4, 1, 75, Rarities.getUncommon());

    private static Enemy swarm = new Enemy("Swarm of Bees", "Not something you want\nto be caught up in.", 22, 3, 1, 68, Rarities.getUncommon());

    private static Enemy f3 = new Enemy("F3", "A younger generation of\nthe F-series android. They\nexist only to fight.", 12, 6, 1, 84, Rarities.getRare());
    
    private static Enemy logger = new Enemy("Logger", "Big.\nBulky.\nHas a chainsaw.\nDon't get hit.", 18, 11, 1, 35, Rarities.getRare());

    private static Enemy mutantHawk = new Enemy("Mutant Hawk", "The hawks around here\nhave been hanging around\nthe radiation for too long.", 24, 6, 1, 70, Rarities.getExceptional());

    private static Enemy tank = new Enemy("M7-Abrams Tank", "This hunk of metal\ncould destroy you in\nan instant.", 40, 10, 1, 30, Rarities.getExceptional());

    private static Enemy angel = new Enemy("Angel", "They've had enough\nof your hubris.\nYou go against the\nnatural law. ", 16, 10, 1, 95, Rarities.getGodly());

    private static HashMap<Rarity, Enemy[]> enemyHashMap = new HashMap<>();

    public static HashMap<Rarity, Enemy[]> getEnemyHashMap() {return enemyHashMap;}

    public static void setEnemyHashMap() {
        enemyHashMap.put(Rarities.getCommon(), new Enemy[] {bear, hobo});
        enemyHashMap.put(Rarities.getUncommon(), new Enemy[] {nomad, swarm});
        enemyHashMap.put(Rarities.getRare(), new Enemy[] {f3, logger});
        enemyHashMap.put(Rarities.getExceptional(), new Enemy[] {mutantHawk, tank});
        enemyHashMap.put(Rarities.getGodly(), new Enemy[] {angel});
    }
}
