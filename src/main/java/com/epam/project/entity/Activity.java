package com.epam.project.entity;

import com.epam.project.constants.Status;
import com.epam.project.constants.TypeOfActivity;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;


public class Activity {
    private int id;
    private String name;
    private Timestamp startTime;
    private Timestamp endTime;
    private String description;
    private TypeOfActivity typeOfActivity;
    private Status status;
    private int userID;
    private Set<User> users;


    public int getUserID() {
        return userID;
    }

    public Activity setUserID(int userID) {
        this.userID = userID;
        return this;
    }

    public TypeOfActivity getTypeOfActivity() {
        return typeOfActivity;
    }

    public Activity setTypeOfActivity(TypeOfActivity typeOfActivity) {
        this.typeOfActivity = typeOfActivity;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public Activity setStatus(Status status) {
        this.status = status;
        return this;
    }

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

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", description='" + description + '\'' +
                ", typeOfActivity=" + typeOfActivity +
                ", status=" + status +
                ", userID=" + userID +
                ", users=" + users +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return id == activity.id && Objects.equals(name, activity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
