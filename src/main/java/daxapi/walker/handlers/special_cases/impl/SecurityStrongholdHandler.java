package daxapi.walker.handlers.special_cases.impl;

import daxapi.walker.handlers.move_task.MoveTaskHandler;
import daxapi.walker.handlers.passive_action.PassiveAction;
import daxapi.walker.handlers.special_cases.SpecialCaseHandler;
import daxapi.walker.models.DaxLogger;
import daxapi.walker.models.MoveTask;
import daxapi.walker.models.enums.MoveActionResult;

import java.util.List;

public class SecurityStrongholdHandler implements MoveTaskHandler, DaxLogger, SpecialCaseHandler {

    @Override
    public boolean shouldHandle(MoveTask moveTask) {
        return false;
    }

    @Override
    public MoveActionResult handle(MoveTask moveTask, List<PassiveAction> passiveActionList) {
        return null;
    }

}
