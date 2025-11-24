package com.example.task04;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Обработчик, выводящий сообщения в файл
 */
public class FileHandler implements MessageHandler {
    private final String filePath;
    private PrintWriter writer;

    public FileHandler(String filePath) {
        this.filePath = filePath;

        // Создаем родительские директории, если они не существуют
        createParentDirs(filePath);

        try {
            // Открываем файл в режиме дозаписи
            this.writer = new PrintWriter(new FileWriter(filePath, true));
        } catch (IOException e) {
            throw new RuntimeException("Не удалось открыть файл для записи: " + filePath, e);
        }
    }

    @Override
    public void handleMessage(String message) {
        writer.println(message);
        writer.flush(); // Сбрасываем буфер после каждой записи
    }

    /**
     * Создает все родительские директории для указанного пути
     */
    private void createParentDirs(String filePath) {
        File file = new File(filePath);
        File parentDir = file.getParentFile();

        if (parentDir != null && !parentDir.exists()) {
            boolean created = parentDir.mkdirs();
            if (!created) {
                System.err.println("Не удалось создать директорию: " + parentDir.getAbsolutePath());
            }
        }
    }

    /**
     * Закрывает файловый поток
     */
    public void close() {
        if (writer != null) {
            writer.close();
        }
    }
}