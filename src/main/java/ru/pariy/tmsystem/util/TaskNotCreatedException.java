package ru.pariy.tmsystem.util;

public class TaskNotCreatedException extends RuntimeException {
    public TaskNotCreatedException(String message) {
        super(message);
    }
}
