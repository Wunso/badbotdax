package daxapi.walkerengine;

import daxapi.apilib.DaxConfigs;

public interface Loggable {

    enum Level {
        VERBOSE,
        INFO,
        SEVERE,
        WARNING;
    }

    String getName();

    default void log(CharSequence debug){
        System.out.println("[" + getName() + "] " + debug);
    }

    default void log(Level level, CharSequence debug) {
        if (!DaxConfigs.logging){
            return;
        }
        System.out.println(level + " [" + getName() + "] " + debug);
    }
}
