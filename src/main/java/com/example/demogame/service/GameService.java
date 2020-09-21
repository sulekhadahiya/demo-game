package com.example.demogame.service;

import com.example.demogame.model.*;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class GameService {

    private static final Logger logger = LoggerFactory.getLogger(GameService.class);

    /**
     * Converts json node for rooms to list of rooms.
     * @param roomsNode represents rooms from game json file.
     * @return list of room
     */
    public List<Room> jsonNodeToRooms(List<JsonNode> roomsNode, List<Item> items){

        logger.info("extracting rooms");

        List<Room> result = new LinkedList<>();
        JsonNode jsonNode = roomsNode.get(0);
        for (JsonNode jn : jsonNode){
            JsonNode idNode = jn.get("id");
            JsonNode descriptionNode = jn.get("description");
            JsonNode connectionsNode = jn.get("Connections");
            List<Connection> connectionList = jsonNodeToConnections(connectionsNode);
            List<JsonNode> itemsInRoom = jn.findValues("itemsinRoom");
            List<Item> itemList = jsonNodeToItemsInRoom(itemsInRoom, items);
            JsonNode winningItemIdNode = jn.get("winningItemId");
            JsonNode winningMessageNode = jn.get("winningMessage");
            if (winningItemIdNode != null && winningMessageNode != null){
                Room room = new Room(idNode.asInt(), descriptionNode.asText(), connectionList, itemList,
                        winningItemIdNode.asInt(), winningMessageNode.asText());
                result.add(room);
            }else{
                Room room = new Room(idNode.asInt(), descriptionNode.asText(), connectionList, itemList);
                result.add(room);
            }
        }
        return result;
    }

    /**
     * Converts json nodes for connection to list of connection
     * @param connectionNode represents json node for connection of rooms.
     * @return list of connections.
     */
    public List<Connection> jsonNodeToConnections(JsonNode connectionNode){

        logger.info("extracting connections for one room");

        List<Connection> result = new LinkedList<>();
        JsonNode jsonNode = connectionNode.get(0);
        //
        if (jsonNode.get(Direction.East.name()) != null){
            JsonNode eastJsonNode = jsonNode.get(Direction.East.name());
            Connection connection = new Connection(Direction.East, eastJsonNode.asInt());
            result.add(connection);
        }
        if (jsonNode.get(Direction.West.name()) != null){
            JsonNode westJsonNode = jsonNode.get(Direction.West.name());
            Connection connection = new Connection(Direction.West, westJsonNode.asInt());
            result.add(connection);
        }
        if (jsonNode.get(Direction.North.name()) != null){
            JsonNode northJsonNode = jsonNode.get(Direction.North.name());
            Connection connection = new Connection(Direction.North, northJsonNode.asInt());
            result.add(connection);
        }
        if (jsonNode.get(Direction.South.name()) != null){
            JsonNode southJsonNode = jsonNode.get(Direction.South.name());
            Connection connection = new Connection(Direction.South, southJsonNode.asInt());
            result.add(connection);
        }

        return result;
    }

    public List<Item> jsonNodeToItemsInRoom(List<JsonNode> itemNode, List<Item> items){

        logger.info("extracting items for one room");

        List<Item> result = new LinkedList<>();
        int n = itemNode.size();
        for (int k=0; k<n; k++){
            JsonNode jsonNode = itemNode.get(k);
            for (JsonNode jn : jsonNode){
                JsonNode idNode = jn.get("id");
                int id = idNode.asInt();
                for (Item i : items){
                    if (id == i.getId()){
                        result.add(i);
                    }
                }
            }
        }

        return result;
    }

    public List<Item> jsonNodeToItems(List<JsonNode> itemNodes){

        logger.info("extracting all the items");

        List<Item> result = new LinkedList<>();
        JsonNode jsonNode = itemNodes.get(0);
        for (JsonNode jn : jsonNode){
            JsonNode idNode = jn.get("id");
            JsonNode nameNode = jn.get("name");
            Item item = new Item(idNode.asInt(), nameNode.asText());
            result.add(item);
        }
        return result;
    }

    public void playGame(JsonNode jsonNode){

        List<JsonNode> roomNodes =  jsonNode.findValues("Rooms");
        List<JsonNode> itemNodes =  jsonNode.findValues("Items");

        List<Item> items = jsonNodeToItems(itemNodes);
        List<Room> rooms = jsonNodeToRooms(roomNodes, items);

        Player player = new Player();
        player.playTheGame(rooms);
    }

}
