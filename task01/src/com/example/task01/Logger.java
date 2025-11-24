package com.example.task01;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Logger {
    private static final Map<String, Logger> LOGGERS = new HashMap<>();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final String name;
    private Level level;

    public enum Level {
        DEBUG,
        INFO,
        WARNING,
        ERROR
    }

    private Logger(String name) {
        this.name = name;
        this.level = Level.DEBUG;
    }

    public static Logger getLogger(String name) {
        return LOGGERS.computeIfAbsent(name, Logger::new);
    }

    public String getName() {
        return name;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    private boolean shouldLog(Level messageLevel) {
        return messageLevel.ordinal() >= level.ordinal();
    }

    private void log(Level level, String message) {
        if (!shouldLog(level)) return;

        String timestamp = getFormattedTimestamp();
        String output = String.format("[%s] %s %s - %s", level, timestamp, name, message);

        if (level == Level.ERROR || level == Level.WARNING) {
            System.err.println(output);
        } else {
            System.out.println(output);
        }
    }

    private void log(Level level, String template, Object... args) {
        if (!shouldLog(level)) return;
        String formattedMessage = MessageFormat.format(template, args);
        log(level, formattedMessage);
    }

    private String getFormattedTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DATE_FORMATTER) + " " + now.format(TIME_FORMATTER);
    }

    // Методы для уровней логирования
    public void debug(String message) {
        log(Level.DEBUG, message);
    }

    public void debug(String template, Object... args) {
        log(Level.DEBUG, template, args);
    }

    public void info(String message) {
        log(Level.INFO, message);
    }

    public void info(String template, Object... args) {
        log(Level.INFO, template, args);
    }

    public void warning(String message) {
        log(Level.WARNING, message);
    }

    public void warning(String template, Object... args) {
        log(Level.WARNING, template, args);
    }

    public void error(String message) {
        log(Level.ERROR, message);
    }

    public void error(String template, Object... args) {
        log(Level.ERROR, template, args);
    }
}