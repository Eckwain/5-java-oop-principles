package com.example.task04;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Продвинутый логгер с поддержкой различных обработчиков сообщений
 */
public class Logger {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final String name;
    private Level level;
    private final List<MessageHandler> handlers;

    public enum Level {
        DEBUG,
        INFO,
        WARNING,
        ERROR
    }

    public Logger(String name) {
        this.name = name;
        this.level = Level.DEBUG;
        this.handlers = new ArrayList<>();
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

    /**
     * Добавляет обработчик сообщений
     */
    public void addHandler(MessageHandler handler) {
        handlers.add(handler);
    }

    /**
     * Удаляет обработчик сообщений
     */
    public void removeHandler(MessageHandler handler) {
        handlers.remove(handler);
    }

    private boolean shouldLog(Level messageLevel) {
        return messageLevel.ordinal() >= level.ordinal();
    }

    private void log(Level level, String message) {
        if (!shouldLog(level)) return;

        String timestamp = getFormattedTimestamp();
        String formattedMessage = String.format("[%s] %s %s - %s",
                level, timestamp, name, message);

        // Передаем сообщение всем обработчикам
        for (MessageHandler handler : handlers) {
            handler.handleMessage(formattedMessage);
        }
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
        log(Level.DEBUG, String.format(template, args));
    }

    public void info(String message) {
        log(Level.INFO, message);
    }

    public void info(String template, Object... args) {
        log(Level.INFO, String.format(template, args));
    }

    public void warning(String message) {
        log(Level.WARNING, message);
    }

    public void warning(String template, Object... args) {
        log(Level.WARNING, String.format(template, args));
    }

    public void error(String message) {
        log(Level.ERROR, message);
    }

    public void error(String template, Object... args) {
        log(Level.ERROR, String.format(template, args));
    }
}