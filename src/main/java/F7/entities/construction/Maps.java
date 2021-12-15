package F7.entities.construction;

import F7.entities.classes.Map;
import F7.entities.classes.Tile;
import com.diogonunes.jcolor.*;

public class Maps {
    // Blank
    private static Tile k = new Tile(" ", true);

    // Grass
    private static Tile g = new Tile("^1░", false);
    private static Tile v = new Tile("^2░", false);
    private static Tile b = new Tile("^g░", false);

    // Stone
    private static Tile s = new Tile("^3▓", false);
    private static Tile z = new Tile("^G▓", false);
    private static Tile x = new Tile("^4▓", false);

    // Dirt
    private static Tile d = new Tile("^6░", false);
    private static Tile n = new Tile("^5▓", false);

    // Water
    private static Tile a = new Tile("^7▒", true);
    private static Tile u = new Tile("^B▒", true);

    // Wall
    private static Tile w = new Tile("^8█", true);

    // Foliage
    private static Tile r = new Tile("^0#", true);
    private static Tile h = new Tile("^9#", true);
    private static Tile y = new Tile("^2#", true);

    // Player
    private static Tile f = new Tile(Ansi.colorize("^R7", Attribute.TEXT_COLOR(196)), false);
    public static Tile getPlayer() {return f;}

    public static Map plains = new Map("Kwéleches Plains", new Tile[][]
        {{k,k,k,k,k,r,r,h,y,r,r,r,r,y,h,h,h,h,y,r,h,y,r,h,y,h,y,y,h,r,h,r,r,r,h,y,r,h,h,h,r,y,y,y,a,a,u,u,u,u,u,u,u,u,u,u,u,u,u,u},
        {k,k,k,k,y,h,r,r,h,y,y,h,g,g,g,g,g,g,g,g,g,g,g,g,r,y,h,g,g,d,g,g,g,h,r,h,g,g,g,g,g,r,y,a,a,a,u,u,u,u,u,u,u,u,u,u,u,u,u,u},
        {k,k,k,h,y,y,r,h,r,r,g,g,g,y,g,g,g,g,g,y,g,g,g,g,g,g,g,g,g,d,g,g,g,g,g,g,g,g,g,g,g,g,v,a,a,u,u,u,u,u,u,u,u,u,u,u,u,a,a,u},
        {k,k,k,r,r,y,h,y,g,g,g,g,g,g,b,b,b,b,g,g,g,g,g,g,g,g,g,g,g,d,g,g,g,g,h,g,g,r,g,g,g,v,v,a,a,u,u,u,u,u,u,u,u,u,u,a,a,a,a,u},
        {k,k,k,r,h,h,y,g,g,g,g,g,b,b,b,b,w,w,w,w,g,g,g,g,g,h,g,g,g,d,d,g,g,g,g,g,g,g,g,g,g,v,v,a,a,u,u,u,u,u,u,u,u,u,a,a,v,v,a,a},
        {k,k,k,r,y,r,g,g,g,g,r,g,b,b,b,b,w,s,s,w,g,g,g,g,g,g,g,g,g,g,d,g,g,g,g,g,g,g,g,g,v,v,a,a,u,u,u,u,u,u,u,u,u,a,a,v,v,v,a,a},
        {k,k,r,h,h,r,g,g,h,g,g,b,b,b,b,b,w,s,s,w,w,w,w,w,g,g,g,g,g,g,d,g,g,g,g,g,g,g,g,v,v,s,s,s,s,s,s,s,u,u,u,u,u,s,s,s,s,g,v,a},
        {k,k,h,y,r,y,g,y,y,g,g,g,g,b,b,b,w,s,s,s,s,x,x,w,g,g,g,g,g,g,d,g,g,g,g,g,g,g,g,v,s,z,z,z,z,z,z,z,z,z,z,u,u,a,a,v,g,g,v,a},
        {k,k,r,r,r,h,g,g,r,g,g,g,b,b,b,b,w,s,s,s,x,x,x,z,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,z,z,z,z,z,z,z,z,u,u,u,u,a,v,v,r,g,v,a},
        {k,k,h,r,y,y,r,g,g,g,g,g,b,b,b,b,w,w,w,w,w,w,w,w,g,g,g,g,g,g,g,g,g,g,g,g,g,g,g,v,v,z,z,z,z,z,z,z,z,z,z,z,z,z,z,z,z,v,v,a},
        {k,y,y,h,h,y,r,g,g,g,g,b,b,b,b,b,b,b,b,b,g,g,g,g,g,g,g,g,g,g,g,g,g,g,g,g,g,g,v,s,s,s,s,s,s,s,u,u,u,u,s,s,s,s,g,g,g,v,a,a},
        {k,y,r,h,r,y,h,g,h,g,g,g,g,g,g,g,g,g,g,g,g,g,g,g,g,g,g,g,g,g,g,g,y,g,g,g,v,v,v,v,a,a,u,u,u,u,u,u,u,a,a,v,g,g,g,g,g,v,a,u},
        {k,r,y,h,h,h,y,g,g,g,g,g,g,g,g,g,g,g,h,g,g,g,y,g,g,v,v,r,v,v,v,v,v,v,v,v,v,v,a,a,a,a,u,u,u,u,u,u,a,a,v,v,g,g,g,g,v,v,a,u},
        {k,h,y,r,y,y,y,g,g,h,g,g,g,g,g,g,g,g,g,g,g,v,v,v,v,v,a,a,a,a,a,a,a,a,a,a,a,a,a,a,u,u,u,u,u,u,a,a,a,a,v,g,g,g,g,g,v,a,a,u},
        {k,k,k,y,h,h,r,g,g,g,g,r,g,g,g,g,r,g,v,v,v,v,a,a,a,a,a,a,a,a,u,u,u,u,u,u,u,u,u,u,u,u,u,u,u,a,a,a,a,v,v,h,r,g,g,g,v,a,u,u},
        {k,k,k,k,k,r,h,y,h,y,g,g,g,g,g,g,v,v,v,a,a,a,a,u,u,u,u,u,u,u,u,u,u,u,u,u,u,u,u,u,u,a,a,a,a,a,a,v,v,v,h,g,g,g,g,g,v,a,u,u},
        {k,k,k,k,k,r,r,y,y,y,r,g,g,g,y,v,v,a,a,a,u,u,u,u,u,u,u,u,u,u,u,a,a,a,a,a,a,a,a,a,a,a,v,v,v,v,v,v,g,g,g,g,g,g,g,v,a,a,u,u},
        {k,k,k,k,k,k,k,k,h,h,y,h,y,g,v,v,a,a,u,u,u,u,u,u,a,a,a,a,a,a,a,a,v,v,v,v,v,v,v,v,v,v,g,g,g,y,g,g,g,v,v,v,v,v,v,v,a,u,u,u},
        {k,k,k,k,k,k,k,k,y,h,r,r,r,v,v,a,a,u,u,u,u,u,u,a,a,v,v,v,v,v,v,v,v,v,v,v,v,v,v,v,r,v,v,v,v,v,v,v,v,v,a,a,a,a,a,a,a,u,u,u},
        {k,k,k,k,k,k,k,k,r,r,a,a,a,a,a,a,u,u,u,u,u,u,u,u,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,u,u,u,u,u,u,u,u,u}}
    );
}
