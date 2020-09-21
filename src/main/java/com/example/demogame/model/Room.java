package com.example.demogame.model;

import java.util.List;

public class Room {

    int id;
    String description;
    List<Connection> connections;
    List<Item> itemsInRoom;
    int winningItemId;
    String winningMessage;

    public Room(int id, String description, List<Connection> connections, List<Item> itemsInRoom,
                int winningItemId, String winningMessage) {
        this.id = id;
        this.description = description;
        this.connections = connections;
        this.itemsInRoom = itemsInRoom;
        this.winningItemId = winningItemId;
        this.winningMessage = winningMessage;
    }
    public Room(int id, String description, List<Connection> connections, List<Item> itemsInRoom) {
        this.id = id;
        this.description = description;
        this.connections = connections;
        this.itemsInRoom = itemsInRoom;
    }
}
