package daxapi.shared.helpers;

import net.runelite.cache.definitions.ObjectDefinition;
import net.runelite.osrsbb.internal.wrappers.Filter;
import net.runelite.osrsbb.methods.Web;
import net.runelite.osrsbb.wrappers.RSObject;

import java.util.Arrays;
import java.util.List;


public class RSObjectHelper {

    public static RSObject get(Filter<RSObject> filter){
        RSObject[] objects = Web.methods.objects.getAll(filter);
        return objects.length > 0 ? objects[0] : null;
    }

    public static boolean exists(Filter<RSObject> filter){
        return Web.methods.objects.getAll(filter).length > 0;
    }

    public static List<String> getActionsList(RSObject object){
        return Arrays.asList(getActions(object));
    }

    public static String[] getActions(RSObject object){
        String[] emptyActions = new String[0];
        ObjectDefinition definition = object.getDef();
        if (definition == null){
            return emptyActions;
        }
        String[] actions = definition.getActions();
        for (int i = 0; i < actions.length; i++) {
            if (actions[i] == null) {
                actions[i] = "";
            }
        }
        return actions != null ? actions : emptyActions;
    }

    public static String getName(RSObject object){
        ObjectDefinition definition = object.getDef();
        if (definition == null){
            return "null";
        }
        String name = definition.getName();
        return name != null ? name : "null";
    }

}
