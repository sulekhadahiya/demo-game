package com.example.demogame.model;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public class GameModel {

    List<Room> rooms;
    List<Item> items;

    public GameModel(List<Room> rooms, List<Item> items) {
        this.rooms = rooms;
        this.items = items;
    }

}
