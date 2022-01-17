package F7.ui;

import com.diogonunes.jcolor.*;
import F7.Utils;
import F7.entities.construction.*;

public class DeathMenu {
    public static void menu() throws Exception {
        Utils.clear();

        Thread.sleep(Utils.getSTANDARD());
        Utils.scrollText(Ansi.colorize("RRS Prototype 67TLB Revival System", Attribute.TEXT_COLOR(50)) + " is installed in your system.\n", Utils.SCROLL);
        Thread.sleep(Utils.QUICK_STANDARD);
        Utils.scrollText("Reviving you.\n", Utils.SCROLL);

        Players.getPlayer().setTempHealth(Players.getPlayer().getHealth());

        if (Utils.chance(50) && !Players.getPlayer().weaponEquipped().equals(Weapons.getFists())) { //* 50% chance of destroying equipped weapon on death
            Utils.scrollText(String.format("%s has been destroyed in the process.\n", Players.getPlayer().weaponEquipped().toString(true)), Utils.SCROLL);
            
            Players.getPlayer().getWeapons()[Players.getPlayer().getEquippedIndex()] = null;
        }

        Thread.sleep(Utils.QUICK_STANDARD);
        Utils.scrollText("Thank you for using RRS' Revival System.", Utils.SCROLL);
        Thread.sleep(Utils.QUICK_STANDARD);
        MapMenu.menu();
    }
}
