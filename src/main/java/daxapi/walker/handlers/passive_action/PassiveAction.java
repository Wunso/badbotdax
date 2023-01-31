package daxapi.walker.handlers.passive_action;

import daxapi.walker.models.enums.ActionResult;

public interface PassiveAction {

    default boolean shouldActivate() {
        return true;
    }

    ActionResult activate();

}
