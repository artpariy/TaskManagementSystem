package ru.pariy.tmsystem.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public enum TaskStatus {
    TODO(1),
    INPROGRESS(2),
    DONE(3);
    private final int id;
    TaskStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static TaskStatus getInstance(long id) {
        return Arrays.stream(values()).filter(status -> status.getId() == id)
                        .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Status by id=" + id + " not found"));
    }
}
