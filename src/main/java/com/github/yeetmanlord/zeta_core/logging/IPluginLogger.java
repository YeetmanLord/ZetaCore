package com.github.yeetmanlord.zeta_core.logging;

import net.md_5.bungee.api.ChatColor;

import javax.annotation.Nullable;

public interface IPluginLogger {

    void setDebugging(boolean debug);

    boolean isDebugging();

    void info(Object... args);

    void debug(Object... args);

    void warn(Object... args);

    void warn(Throwable throwable, Object... args);

    void error(Object... args);

    void error(Throwable throwable, Object... args);

    void fatal(Object... args);

    void fatal(Throwable throwable, Object... args);

    default void log(IPluginLogger.LogType type, @Nullable Throwable error, Object... args) {

        switch (type) {
            case INFO:
                info(args);
                break;

            case DEBUG:
                debug(args);
                break;

            case WARN:
                if (error != null) {
                    warn(error, args);
                } else {
                    warn(args);
                }
                break;

            case ERROR:
                if (error != null) {
                    error(error, args);
                } else {
                    error(args);
                }
                break;

            case FATAL:
                if (error != null) {
                    fatal(error, args);
                } else {
                    fatal(args);
                }
                break;
        }

    }

    default String convertToString(Object... objects) {

        if (objects == null || objects.length == 0 || objects[0] == null) {
            return "null";
        }

        StringBuilder str = new StringBuilder();

        for (int x = 0; x < objects.length; x++) {
            Object obj = objects[x];


            if (x == objects.length - 1) {
                if (obj == null) {
                    str.append("null").append(getColor().toString());
                } else {
                    str.append(obj).append(" ").append(getColor().toString());
                }
            } else {
                if (obj == null) {
                    str.append("null").append(getColor().toString());
                } else {
                    str.append(obj).append(getColor().toString());
                }
            }

        }

        return str.toString();

    }

    default String[] getStackTrace(Throwable exc) {

        StringBuilder exception = new StringBuilder(exc.toString());

        for (StackTraceElement elem : exc.getStackTrace()) {
            exception.append("\n\tat").append(elem.toString());
        }

        return exception.toString().split("\n");

    }

    enum LogType {
        INFO, DEBUG, ERROR, FATAL, WARN;
    }

    void setColor(ChatColor defaultColor);

    ChatColor getColor();
}
