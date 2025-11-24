package com.example.task01;

public class Task01Main {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger("main");
        logger.setLevel(Logger.Level.WARNING);
        logger.warning("Disk space low: {0}GB remaining", 2.5);
    }
}
