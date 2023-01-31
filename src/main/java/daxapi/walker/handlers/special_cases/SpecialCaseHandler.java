package daxapi.walker.handlers.special_cases;

import daxapi.walker.handlers.passive_action.PassiveAction;
import daxapi.walker.models.MoveTask;
import daxapi.walker.models.enums.MoveActionResult;

import java.util.List;

public interface SpecialCaseHandler {

    boolean shouldHandle(MoveTask moveTask);

    MoveActionResult handle(MoveTask moveTask, List<PassiveAction> passiveActionList);

    default String getName() {
        return this.getClass().getSimpleName();
    }

}
