package com.example.EpamFinalProject.entity;

import java.sql.Timestamp;
import java.util.Set;


public class Activity {
    private int id;
    private String name;
    private Timestamp startTime;
    private Timestamp endTime;
    private String description;
    private int typeOfActivityId;
    private Set<User> users;

    public int getId() {
        return id;
    }

    public Activity setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Activity setName(String name) {
        this.name = name;
        return this;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public Activity setStartTime(Timestamp startTime) {
        this.startTime = startTime;
        return this;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public Activity setEndTime(Timestamp endTime) {
        this.endTime = endTime;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Activity setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getTypeOfActivityId() {
        return typeOfActivityId;
    }

    public Activity setTypeOfActivityId(int typeOfActivityId) {
        this.typeOfActivityId = typeOfActivityId;
        return this;
    }
}
