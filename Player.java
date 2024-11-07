import java.util.*;
/**
 * Player class is in charge of handling the player's name, description, maximum hold capacity and actions such as picking up an 
 * item and droping it. We achieve this by creating multiple methods such as pickUpItem, dropItem, setPlayerName, description. etc
 * This class pretty much takes care of every aspect of the player, such as actions, movement and information
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Player
{
    // instance variables - replace the example below with your own
    private String playerName;//hold players name
    private int maximumWeight;//holds player maximum weight capacity
    private Room currentRoom; //holds players current room
    private Stack<Room> roomStack; //stores the players visited rooms
    public String [] itemCarry = new String[20];//string that holds item when being carried
    public int i=0;
    private Item itemInHand; 
    

    /**
     * Constructor for objects of class Player
     */
    public Player()
    {
        // initialise instance variables
        playerName = "";
        currentRoom = null;
        maximumWeight = 10000;
        roomStack = new Stack<Room>();
        itemInHand=null;
    }
    /**
     * Parameter constructor
     * @param String name, users name
     * @param Room, current room
     * @maximumWeight, maximum weight
     */
    public Player (String name, Room currRoom, int maximumWeight){
        this.playerName = name;//players name
        this.currentRoom = currRoom;//current room in context
        this.maximumWeight = maximumWeight; //maximum weight that user can carry
        roomStack = new Stack<Room>(); //stack of rooms visited by user 
        itemInHand = null;    //item currently held by user.
    }
    /**
     * This method is in charge of setting the players name
     * @param pName, players name
     */
    public void setPlayerName (String pName){
        this.playerName = pName;
    }
    /**
     * This method returns the players name
     */
    public String getPlayerName (){
        return this.playerName;
    }
    /**
     * This method sets the current room 
     */
    public void setCurrentRoom (Room currRoom){
        this.currentRoom = currRoom;
    }
    /**
     * This method returns the current room
     */
    public Room getCurrentRoom(){
        return this.currentRoom;
    }
    /**
     * This method sets the maximum weight that the player can carry
     * @maxWeight, maximum weight capacity
     */
    public void setMaximumWeight (int maxWeight){
        this.maximumWeight = maxWeight;
    }
    /**
     * This method returns the maximum weight that the player can carry
     */
    public int getMaximumWeight (){
        return maximumWeight;
    }
    /**
     * This method sets the item that the player is 
     * holding in it's hand
     * @itempicked item picked by user 
     * 
     */
    public void setItemInHand (Item itempicked){
      itemInHand = itempicked;
    }
    /**
     * this method gets the intem that the player is holding
     * in it's hand
     * @return itemInHand
     */
    public Item getItemInHand(){
        return itemInHand;
    }
    
    /**
     * This method is gets the Player's description
     */
    public String getPlayerDescription(){
        String result = "Player" +playerName+": \n";
        if (itemInHand != null){
        result +="You are carrying a/an" + itemInHand.getItemName() +" item in hand. \n\n";
        }
        result += currentRoom.getLongDescription();
        return result;
    }
    /**
     * This method gets the exit room
     */
    public Room getPlayersExit(String direction){
        return currentRoom.getExit(direction);
    }
    /**
     * This method sets the room that the player is entering
     * @param nextRoom, the room being stacked
     */
    public void setPlayersEnteringRoom (Room nextRoom){
        //updates the stack roomStack with currentRoom value
        //before modifying the current value to 
        roomStack.push(currentRoom);
        currentRoom = nextRoom;
    }
    /**
     * This method allows the player to pick up an item
     * from a room and prints out what item is currently
     * being carried.
     * @param itemName, finds the item name in the rooms item list to see if it can pick it up
     */
    public void pickUpItem(String itemName){
    //Checks if the item is in the current room
    //if(currentRoom.getItemsInRoom().contains(itemName)){
    //Removes the item from the room's string and "picks it up"
    //String newItems = currentRoom.getItemsInRoom().replace(itemName, "");
    //Adds the item to the playes "inventory"
    //itemCarry[i] = itemName;
    //i++;
    //System.out.println("Picked up" + itemName);
    //}
    //else it prints out that the item is not in the room
    //else{
    //    System.out.println("Item is not in the room.");
    //}
    //CODE FOR ARRAY LIST MIGHT DELETE LATER
    Item tobePickedUp = new Item();
    ArrayList<Item> itemsInRoom = currentRoom.getRoomItems();
    for (Item item : itemsInRoom) {
    if(item.getItemName().equals(itemName)){
     tobePickedUp = item;   
     break;
    }
    }
    if (tobePickedUp == null){
        System.out.println(itemName + " Isn't here");
    }
    else {
    
    currentRoom.removeItem(tobePickedUp);
    setItemInHand(tobePickedUp);
    System.out.println("Picked up" + itemName);
    System.out.println("Item removed from room: " + itemName);
    System.out.println("Item added to invetory: " + itemName);
    }
    
    //currentRoom.removeItem(item);
    //setItemInHand(item);
    //System.out.println("Picked up" + itemName);
    //System.out.println("Item removed from room: " + item.getItemName());
    //System.out.println("Item added to invetory: " + item.getItemName());
    
    
    }
    //CODE FOR STRING
    //this.itemCarry[i] = itemName;
    //System.out.println("Item Carried: "+ itemCarry[i]);
    //i++;
    
    /**
     * This method allows the player to drop an item that
     * the player is currently carrying and prints it out in 
     * a message
     * @param itemName  uses the item name to drop the item if applicable
     */
    public void dropItem(String itemName){
        //checks if the player has any item in it's "inventory"
        if (i <=0){
            System.out.println("No more items in inventory");
        }
        //else remove the last item from the invetory
        else{
            --i;
            System.out.println("Item Dropped: " + itemCarry[i]);
            this.itemCarry[i] = null;
        }
    }
    /**
     * This method displays the players entering previous room info
     */
    public void movePlayerToPreviousRoom(){
        if (roomStack.empty()){
            System.out.println("Sorry, you're outside the"+ 
            "university" +"and there are no previous rooms to go to");
        }
        else {
            currentRoom = roomStack.pop();
            System.out.println("Player" +playerName+ ": ");
            if(itemInHand != null){
            System.out.println("You are carrying" + "a/an "+ itemInHand.getItemName() + "item in hand. \n\n");
            }
            System.out.println(currentRoom.getLongDescription());
        }
        
    }
    /**
     * This method checks if the given item can be picked up
     * @returns a boolean value
     * @param itemName uses it to determine if it is pickable
     */
    public boolean canBePickedUp (String itemName){
    //gets the item from the condition to check if it is null
    Item item = currentRoom.getItem(itemName);
    if(item == null){
    return false;
    }
    //check whether the weight of the item is less than the
    //capacity that the player can carry, also check if any item is not being carried by the player
    if (item.getWeight() < maximumWeight && !alreadyItemExistsInHand()){
    //return true if condition is satisfied
    return true;
    }
    else{
    return false;
    }
    }
    /**
     * This method checks wheter the item can be dropped by 
     * the player
     * @returns a boolean value
     * @param itemName to determine if it can be dropped
     */
    public boolean canBeDropped (String itemName){
    //checks whether the name of the item matches with the one
    //being carried currently, and also if any item is not being carried by the player
    if (itemInHand.getItemName().equalsIgnoreCase(itemName) && alreadyItemExistsInHand()){
    //return true if condition is satisfied
    return true;
    }
    else{
    return false;
    }
    }
    /**
     * This method checks wheter there exists an item in the players hand
     * or not
     * @returns a Boolean value
     */
    public boolean alreadyItemExistsInHand(){
        //checks whether the value of itemInHand is not null
        if (itemInHand != null){
            return true;
        }
        else {
            return false;
        }
    }   
}
    

