package com.example.demogame.model;

import org.apache.tomcat.util.buf.Ascii;

import java.util.List;

public class Player {

    public static void playTheGame(List<Room> rooms){

        for (Room r : rooms){

            //Describe the room's condition
            System.out.println(r.description);

            //Check if there is an item in the room and move
            if (r.itemsInRoom.isEmpty()){
                move(r.connections);
            }else {
                for (Item i : r.itemsInRoom){
                    pickItem(i, i.name);
                }
                move(r.connections);
            }

            // Drop item
            dropItem(r);
        }

    }

    public static void move(List<Connection> connection){
        for (Connection c : connection){
            if (c.direction == Direction.East ){
                moveEast();
            }
            if (c.direction == Direction.West){
                moveWest();
            }
            if (c.direction == Direction.North){
                moveNorth();
            }
            if (c.direction == Direction.South){
                moveSouth();
            }
            System.out.println(c.roomId);
        }
    }

    public static void pickItem(Item i, String name){
        System.out.println(name);

    }

    public static void dropItem(Room room){
        for (Item itm :room.itemsInRoom){
            if (itm.id == room.winningItemId){
                System.out.println(room.winningMessage);
            }
        }
    }

    public static void moveEast(){
        System.out.println("----->");
        System.out.println("\n[___]");
    }

    public static void moveWest(){
        System.out.println("<-----");
    }

    public static void moveNorth(){
        System.out.println("^" + "\n|" + "\n|" + "\n|");
    }

    public static void moveSouth(){
        System.out.println("|\n|\n|\nv");
    }
}
