package com.example.task04;

import java.util.ArrayList;
import java.util.List;

/**
 * Обработчик, буферизующий сообщения в памяти
 */
public class MemoryHandler implements MessageHandler {
    private final MessageHandler targetHandler;
    private final int bufferSize;
    private final List<String> buffer;

    public MemoryHandler(MessageHandler targetHandler, int bufferSize) {
        this.targetHandler = targetHandler;
        this.bufferSize = bufferSize;
        this.buffer = new ArrayList<>(bufferSize);
    }

    @Override
    public void handleMessage(String message) {
        buffer.add(message);

        // Если буфер заполнен, сбрасываем сообщения в целевой обработчик
        if (buffer.size() >= bufferSize) {
            flush();
        }
    }

    /**
     * Принудительно сбрасывает буферизованные сообщения в целевой обработчик
     */
    public void flush() {
        for (String message : buffer) {
            targetHandler.handleMessage(message);
        }
        buffer.clear();
    }

    /**
     * Возвращает количество сообщений в буфере
     */
    public int getBufferSize() {
        return buffer.size();
    }
}