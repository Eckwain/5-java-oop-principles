package com.example.task04;

/**
 * Интерфейс для обработчиков сообщений логгера
 */
public interface MessageHandler {
    /**
     * Обрабатывает сообщение логгера
     * @param message сообщение для обработки
     */
    void handleMessage(String message);
}