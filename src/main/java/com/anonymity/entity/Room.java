package com.anonymity.entity;

import java.util.HashSet;
import java.util.Set;

public class Room {
    private String code;
    private Set<String> users = new HashSet<>();

    public Room(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public Set<String> getUsers() {
        return users;
    }

    public void addUser(String username) {
        users.add(username);
    }
}