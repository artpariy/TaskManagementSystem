package ru.pariy.tmsystem.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum TaskPriority {
    HIGH(1),
    MEDIUM(2),
    LOW(3);
    private final int id;

    TaskPriority(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static TaskPriority getInstance(long id) {
        return Arrays.stream(values()).filter(priority -> priority.getId() == id)
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Priority by id=" + id + "not found"));

    }
}
