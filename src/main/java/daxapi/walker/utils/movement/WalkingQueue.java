package daxapi.walker.utils.movement;

import net.runelite.osrsbb.methods.Web;
import net.runelite.osrsbb.wrappers.RSCharacter;
import net.runelite.osrsbb.wrappers.subwrap.WalkerTile;

import java.util.ArrayList;


public class WalkingQueue {

    public static boolean isWalkingTowards(WalkerTile tile){
        WalkerTile tile1 = getWalkingTowards();
        return tile1 != null && tile1.equals(tile);
    }

    public static WalkerTile getWalkingTowards(){
        ArrayList<WalkerTile> tiles = getWalkingQueue();
        return tiles.size() > 0 && !tiles.get(0).equals(new WalkerTile(Web.methods.players.getMyPlayer().getLocation())) ? tiles.get(0) : null;
    }

    public static ArrayList<WalkerTile> getWalkingQueue(){
        return getWalkingQueue(Web.methods.players.getMyPlayer());
    }

    public static WalkerTile getWalkingTowards(RSCharacter rsCharacter){
        ArrayList<WalkerTile> tiles = getWalkingQueue(rsCharacter);
        return tiles.size() > 0 && !tiles.get(0).equals(rsCharacter.getLocation()) ? tiles.get(0) : null;
    }

    public static ArrayList<WalkerTile> getWalkingQueue(RSCharacter rsCharacter){
        ArrayList<WalkerTile> walkingQueue = new ArrayList<>();
        if (rsCharacter == null){
            return walkingQueue;
        }
        int[] xIndex = rsCharacter.getPathX(), yIndex = rsCharacter.getPathY();
        int plane = rsCharacter.getLocation().getWorldLocation().getPlane();

        for (int i = 0; i < xIndex.length && i < yIndex.length; i++) {
            walkingQueue.add(new WalkerTile(xIndex[i], yIndex[i], plane, WalkerTile.TYPES.SCENE).toWorldTile());
        }
        return walkingQueue;
    }

}
