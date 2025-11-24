package com.example.task04;

import java.util.HashMap;
import java.util.Map;

/**
 * Утилитный класс для управления логгерами
 */
public class LoggerFactory {
    private static final Map<String, Logger> LOGGERS = new HashMap<>();

    /**
     * Возвращает логгер с указанным именем
     */
    public static Logger getLogger(String name) {
        return LOGGERS.computeIfAbsent(name, Logger::new);
    }
}