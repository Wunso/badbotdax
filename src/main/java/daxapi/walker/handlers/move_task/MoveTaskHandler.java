package daxapi.walker.handlers.move_task;

import daxapi.walker.handlers.passive_action.PassiveAction;
import daxapi.walker.models.MoveTask;
import daxapi.walker.models.WaitCondition;
import daxapi.walker.models.enums.ActionResult;
import daxapi.walker.models.enums.MoveActionResult;
import daxapi.walkerengine.WaitFor;
import net.runelite.osrsbb.methods.Web;
import net.runelite.osrsbb.util.Timer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public interface MoveTaskHandler {

    MoveActionResult handle(MoveTask moveTask, List<PassiveAction> passiveActionList);

    /**
     * @param waitCondition
     * @param timeout
     * @param passiveActionList
     * @return If player stops moving, return fail condition.
     */
    default ActionResult waitForConditionOrNoMovement(WaitCondition waitCondition, long timeout,
                                                      List<PassiveAction> passiveActionList) {
        if (passiveActionList == null) passiveActionList = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        AtomicLong lastMoved = new AtomicLong(System.currentTimeMillis());
        passiveActionList.add(() -> {
            if (Web.methods.players.getMyPlayer().isLocalPlayerMoving() || Timer.timeFromMark(startTime) < 1750) {
                lastMoved.set(System.currentTimeMillis());
                return ActionResult.CONTINUE;
            }

            if (Timer.timeFromMark(lastMoved.get()) > 1250) return ActionResult.FAILURE;
            return ActionResult.CONTINUE;
        });
        return waitFor(waitCondition, timeout, passiveActionList);
    }

    default ActionResult waitFor(WaitCondition waitCondition, long timeout, List<PassiveAction> passiveActionList) {
        long end = System.currentTimeMillis() + timeout;
        while (System.currentTimeMillis() < end) {
            if (waitCondition.action()) return ActionResult.CONTINUE;

            for (PassiveAction passiveAction : passiveActionList) {
                if (!passiveAction.shouldActivate()) continue;

                ActionResult actionResult = passiveAction.activate();
                switch (actionResult) {
                    case EXIT_SUCCESS:
                    case EXIT_FAILURE:
                        return actionResult;
                }

            }
            WaitFor.milliseconds(80, 150);
        }
        return ActionResult.FAILURE;
    }

}
