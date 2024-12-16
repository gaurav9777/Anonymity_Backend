package com.anonymity.service;

import com.anonymity.entity.Room;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RoomServiceImpl {
    private final Map<String, Room> rooms = new ConcurrentHashMap<>();

    public String createRoom() {
        String code;
        do {
            code = generateRandomCode();
        } while (rooms.containsKey(code));
        rooms.put(code, new Room(code));
        return code;
    }

    public Room joinRoom(String code) {
        return rooms.get(code);
    }

    private String generateRandomCode() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }
}
