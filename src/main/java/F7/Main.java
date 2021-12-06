package F7;

import F7.entities.classes.*;
import F7.entities.construction.*;
import F7.ui.*;

public class Main {
    public static void main(String[] args) throws Exception {
        MapMenu.setCurrentMap(new Map(Maps.plains)); // TODO: Move to MainMenu once i get more maps and ways to move from one to another
        Enemies.setEnemyHashMap();
        Weapons.setWeaponHashMap();
        Shields.setShieldHashMap();
        Rarities.setRaritiesArrayList();

        MainMenu.menu();
        // ObjectMapper objectMapper = new ObjectMapper();
        // objectMapper.writeValue(new File("src\\main\\java\\F7\\test.json"), Players.dev);

        // Player test = objectMapper.readValue(new File("src\\main\\java\\F7\\test.json"), Player.class);
        // System.out.println(test.getName());
    }
}
