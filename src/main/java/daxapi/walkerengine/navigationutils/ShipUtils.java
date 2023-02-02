package daxapi.walkerengine.navigationutils;

import daxapi.Filters;
import daxapi.walkerengine.WaitFor;
import daxapi.walkerengine.interactionhandling.InteractionHelper;
import net.runelite.osrsbb.api.Web;
import net.runelite.osrsbb.util.StdRandom;
import net.runelite.osrsbb.wrappers.RSArea;
import net.runelite.osrsbb.wrappers.RSObject;
import net.runelite.osrsbb.wrappers.subwrap.WalkerTile;



public class ShipUtils {

    private static final WalkerTile[] SPECIAL_CASES = new WalkerTile[]{new WalkerTile(2663, 2676, 1)};

    public static boolean isOnShip() {
        WalkerTile playerPos = new WalkerTile(new WalkerTile(Web.methods.players.getMyPlayer().getLocation()));
        for (WalkerTile specialCase : SPECIAL_CASES){
            if (new RSArea(specialCase, 5).contains(playerPos)){
                return true;
            }
        }
        return getGangplank() != null
                && new WalkerTile(Web.methods.players.getMyPlayer().getLocation()).getWorldLocation().getPlane() == 1
                && Web.methods.objects.getAll(Filters.Objects.nameEquals("Ship's wheel", "Ship's ladder", "Anchor")).length > 0;
    }

    public static boolean crossGangplank() {
        RSObject gangplank = getGangplank();
        if (gangplank == null){
            return false;
        }
        if (!gangplank.doAction("Cross")){
            return false;
        }
        if (WaitFor.condition(1000, () -> Web.methods.game.getCrosshairState() == 2 ? WaitFor.Return.SUCCESS : WaitFor.Return.IGNORE) != WaitFor.Return.SUCCESS){
            return false;
        }
        return WaitFor.condition(StdRandom.uniform(2500, 3000), () -> !ShipUtils.isOnShip() ? WaitFor.Return.SUCCESS : WaitFor.Return.IGNORE) == WaitFor.Return.SUCCESS;
    }

    private static RSObject getGangplank(){
        return InteractionHelper.getRSObject(Filters.Objects.nameEquals("Gangplank").combine(Filters.Objects.actionsContains("Cross"), true));
    }

}
