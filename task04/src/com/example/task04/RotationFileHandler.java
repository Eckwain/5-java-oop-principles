package com.example.task04;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Обработчик с ротацией файлов по времени
 */
public class RotationFileHandler implements MessageHandler {
    private final String basePath;
    private final ChronoUnit rotationUnit;
    private final DateTimeFormatter fileFormatter;

    private PrintWriter currentWriter;
    private String currentPeriod;
    private LocalDateTime lastRotationTime;

    public RotationFileHandler(String basePath, ChronoUnit rotationUnit) {
        this.basePath = basePath;
        this.rotationUnit = rotationUnit;
        this.fileFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        this.lastRotationTime = LocalDateTime.now();

        // Создаем родительские директории, если они не существуют
        createParentDirs(basePath);
        rotateIfNeeded();
    }

    @Override
    public void handleMessage(String message) {
        rotateIfNeeded();
        currentWriter.println(message);
        currentWriter.flush();
    }

    private void rotateIfNeeded() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime truncatedNow = now.truncatedTo(rotationUnit);
        LocalDateTime truncatedLast = lastRotationTime.truncatedTo(rotationUnit);

        // Если текущий период отличается от предыдущего, выполняем ротацию
        if (!truncatedNow.equals(truncatedLast)) {
            if (currentWriter != null) {
                currentWriter.close();
            }

            String timestamp = truncatedNow.format(fileFormatter);
            String fileName = basePath + "_" + timestamp + ".log";

            try {
                currentWriter = new PrintWriter(new FileWriter(fileName, true));
                lastRotationTime = now;
            } catch (IOException e) {
                throw new RuntimeException("Не удалось создать файл для ротации: " + fileName, e);
            }
        }

        // Инициализация при первом вызове
        if (currentWriter == null) {
            String timestamp = truncatedNow.format(fileFormatter);
            String fileName = basePath + "_" + timestamp + ".log";
            try {
                currentWriter = new PrintWriter(new FileWriter(fileName, true));
            } catch (IOException e) {
                throw new RuntimeException("Не удалось создать файл: " + fileName, e);
            }
        }
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
     * Закрывает текущий файловый поток
     */
    public void close() {
        if (currentWriter != null) {
            currentWriter.close();
        }
    }
}