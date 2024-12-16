package com.anonymity.controller;

import com.anonymity.entity.Room;
import com.anonymity.service.RoomServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
public class RoomController extends TextWebSocketHandler {

    @Autowired
    private RoomServiceImpl roomService;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String payload = message.getPayload();

        if (payload.startsWith("CREATE")) {
            String code = roomService.createRoom();
            System.out.println("printing room code :-> "+code);
            session.sendMessage(new TextMessage("ROOM_CREATED:" + code));
        } else if (payload.startsWith("JOIN")) {
            String code = payload.split(":")[1];
            if (roomService.joinRoom(code) != null) {
                session.sendMessage(new TextMessage("ROOM_JOINED:" + code));
            } else {
                session.sendMessage(new TextMessage("ERROR: Room not found"));
            }
        }
    }
}