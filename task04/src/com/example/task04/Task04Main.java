package com.example.task04;

import java.time.temporal.ChronoUnit;

public class Task04Main {
    public static void main(String[] args) {
        try {
            // Создаем логгер
            Logger logger = LoggerFactory.getLogger("main");
            logger.setLevel(Logger.Level.INFO);

            // Добавляем несколько обработчиков
            logger.addHandler(new ConsoleHandler());
            logger.addHandler(new FileHandler("application.log"));

            // Создаем RotationFileHandler с обработкой возможных ошибок
            try {
                RotationFileHandler rotationHandler = new RotationFileHandler("logs/app", ChronoUnit.HOURS);
                logger.addHandler(rotationHandler);
            } catch (Exception e) {
                System.err.println("Не удалось создать RotationFileHandler: " + e.getMessage());
                // Создаем резервный обработчик в текущей директории
                RotationFileHandler fallbackHandler = new RotationFileHandler("app_logs", ChronoUnit.HOURS);
                logger.addHandler(fallbackHandler);
            }

            // MemoryHandler с буфером на 5 сообщений, сбрасывающий в консоль
            MemoryHandler memoryHandler = new MemoryHandler(new ConsoleHandler(), 5);
            logger.addHandler(memoryHandler);

            // Логируем сообщения
            logger.info("Приложение запущено");
            logger.debug("Отладочная информация"); // Не будет выведено, т.к. уровень INFO
            logger.warning("Нехватка памяти: %d МБ свободно", 512);
            logger.error("Ошибка подключения к базе данных");

            // Принудительно сбрасываем буфер MemoryHandler
            memoryHandler.flush();

        } catch (Exception e) {
            System.err.println("Критическая ошибка при настройке логирования: " + e.getMessage());
            e.printStackTrace();
        }
    }
}