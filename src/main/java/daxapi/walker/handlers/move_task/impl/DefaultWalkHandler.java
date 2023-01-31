package daxapi.walker.handlers.move_task.impl;

import daxapi.walker.handlers.move_task.MoveTaskHandler;
import daxapi.walker.handlers.passive_action.PassiveAction;
import daxapi.walker.models.MoveTask;
import daxapi.walker.models.enums.MoveActionResult;
import daxapi.walker.utils.AccurateMouse;
import daxapi.walker.utils.path.DaxPathFinder;
import net.runelite.osrsbb.methods.Web;
import net.runelite.osrsbb.util.StdRandom;

import java.util.List;

public class DefaultWalkHandler implements MoveTaskHandler {

    @Override
    public MoveActionResult handle(MoveTask moveTask, List<PassiveAction> passiveActionList) {
        if (!AccurateMouse.clickMinimap(moveTask.getDestination())) {
            return MoveActionResult.FAILED;
        }
        int initialDistance = DaxPathFinder.distance(moveTask.getDestination());

        if (!waitFor(() -> {
            int currentDistance = DaxPathFinder.distance(moveTask.getDestination());
            return currentDistance <= 2 || initialDistance - currentDistance > getDistanceOffset();
        }, 3500, passiveActionList).isSuccess()) {
            return MoveActionResult.FAILED;
        }

        return MoveActionResult.SUCCESS;
    }

    private int getDistanceOffset() {
        return  Web.methods.walking.isRunEnabled() ? (int) StdRandom.gaussian(3, 10, 7, 2) : (int) StdRandom.gaussian(2, 10, 5, 2);
    }

}
