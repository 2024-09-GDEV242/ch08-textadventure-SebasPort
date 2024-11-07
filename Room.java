import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Sebastian Portillo
 * @version 11/05/24
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private ArrayList<Item> roomItems;          //stores items of each room

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<String, Room>(); //exits of a room
        
        roomItems = new ArrayList<Item>();  //items of a room
    }
    /**
     * This method gets the array list of the items added 
     * to the current room
     */
    public ArrayList<Item> getRoomItems(){
        return roomItems;
    }
    /**
     * This method gets the item object if the Item name
     * is found in the list of items present in the room
     */
    public Item getItem (String itemName){
        //loop the list of the items in the room
        for ( int i =0; i < roomItems.size(); i ++){
        //Checks if the itemName matches with any of the list of items present in the room
        if(roomItems.get(i).getItemName().equalsIgnoreCase(itemName)){
         //if there is a match, return the item present in the index   
         return roomItems.get(i);   
        }
        }
        return null;
    }
    /**
     * This method removes an item from the list of 
     * items the user has picked up
     */
    public void removeItem (Item item){
        //DOESN'T WORK
        //loops list of items in room currently
        //List<Item> newItems = new ArrayList<>();
        //for (Item currentItem : roomItems) {
        //if (!currentItem.getItemName().equals(item.getItemName())){
        //newItems.add(currentItem);
        // }
        //}
        // roomItems = newItems;
        //ITERATOR APPROACH
        //Iterator<Item> iterator = roomItems.iterator();
        //while (iterator.hasNext()){
        //Item currentItem = iterator.next();
        //if(currentItem.getItemName().equals(item.getItemName())){
        //iterator.remove();
        //break;
        //}
        //}
        //ORIGINAL APPROACH
    for (int i =0; i <roomItems.size(); i++){
          //if item matches with any item in the room,
         //then remove the item from list and break the loop
     if (roomItems.get(i) == item){
      roomItems.remove(item);
      break;
        
         
    }
    }
    }
    
    /**
     * This method retuns the string(list) of items present in the room
     */
    public String getItemsInRoom(){
    String returnItems = "Items in the rooms are: \n";
     for (Item item : roomItems){
     returnItems += item.getItemDescription() + "\n";
    }
    return returnItems;
    }
    /**
     * This method adds an Item into the room
     */
    public void addItem(Item item){
        roomItems.add(item);
    }
    

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room alongside the item's description
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getItemsInRoom() + ".\n"  + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits: ";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
}

