package com.example.EpamFinalProject.entity;

import java.util.Set;

public class User {
    private int id;
    private String login;
    private String password;
    private String name;
    private String surname;
    private int roleId;
    private Set<Activity> activities;

    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public User setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public User setSurname(String surname) {
        this.surname = surname;
        return this;
    }


    public int getRoleId() {
        return roleId;
    }

    public User setRoleId(int roleId) {
        this.roleId = roleId;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", roleId=" + roleId +
                ", activities=" + activities +
                '}';
    }
}
