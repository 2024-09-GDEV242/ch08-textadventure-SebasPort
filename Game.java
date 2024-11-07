import java.util.*;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Sebastian Portillo
 * @version 2024.11.05
 */

public class Game 
{
    private Scanner reader;//interprets users input
    private Player player; //instance of player/when player is created
    private Parser parser; //parser
    private Room currentRoom; //current room that player is in
    private Room roomStack[]; //stacks rooms that were last visited 
    private int top;
    /**
     * Main method to play game outside of bluej
     */
    public static void main (String[] args){
    Game game = new Game();
    game.play();
    }
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        parser = new Parser();
        player = new Player();
        reader = new Scanner (System.in);
    }
    /**
     * This method is in charge of creating a player, using the
     * information gathered from user input
     */
    private void createPlayer(){
        System.out.println("Enter player name: ");
        String name = reader.nextLine();
        player.setPlayerName(name);
        createRooms();
        System.out.println("Enter max weight "+" that player can carry:");
        int weight = reader.nextInt();
        player.setMaximumWeight(weight);
    }

    /**
     * Create all the rooms and link their exits together.
     * We also create an array for each room that will contain an item
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office, castle, garden, lounge;
        //Creating the array of items for each room
        Item theaterItem [] = {
            new Item ("3D Glasses" , 75),
            new Item ("Magic Wand" , 25) };
        Item pubItem [] = {
            new Item ("8 pool bar" , 150),
            new Item ("Neon light" , 100) };
        Item outsideItem [] = {
            new Item ("Leaf Blower" , 250),
            new Item ("rusty old map" ,15) };
        Item labItem [] = {
            new Item ("Time Machine" , 500),
            new Item ("X-ray Goggles" , 35) };
        Item officeItem [] = {
            new Item ("Printer" , 700),
            new Item ("banana" , 25) };
        Item castleItem [] = {
            new Item ("Candles", 25),
            new Item ("Sword",  175)};
        Item gardenItem [] = {
            new Item ("Flowers", 10),
            new Item ("Flytrap", 50)};
        Item loungeItem [] = {
            new Item ("Basketball",30),
            new Item ("DVD-player",10)};
        
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        castle = new Room("in a castle tower");
        garden = new Room("in a botanical garden");
        lounge = new Room("in a lounge");
        
        //Adding items to respective room
        outside = addItemsToRoom (outside,outsideItem);
        theater = addItemsToRoom (theater, theaterItem);
        pub = addItemsToRoom (pub, pubItem);
        lab = addItemsToRoom (lab, labItem);
        office = addItemsToRoom (office, officeItem);
        garden = addItemsToRoom (garden, gardenItem);
        castle = addItemsToRoom ( castle, castleItem);
        lounge = addItemsToRoom (lounge, loungeItem);
        
        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theater.setExit("west", outside);

        pub.setExit("east", outside);
        pub.setExit("north",castle);
        pub.setExit("south", garden); 
        
        castle.setExit("south", pub);

        lab.setExit("north", outside);
        lab.setExit("east", office);
        lab.setExit("west", garden);
        
        garden.setExit("north", pub);
        garden.setExit("east", lab);
        garden.setExit("south", lounge);
        
        lounge.setExit("north", garden);

        office.setExit("west", lab);
        player.setCurrentRoom(outside);//Sets the player to start the game outside
    }
    /**
     * This method adds the array of items to the room and return the room object
     */
    private Room addItemsToRoom (Room room, Item items[]){
    for (int i = 0; i < items.length; i ++){
        room.addItem(items[i]); //if applicable, add the items to the room
    }
    return room;
    }
    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {          
        
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type " + CommandWord.HELP + "' if you need help.");
        System.out.println();
        createPlayer();
        System.out.println ("Hi player " + player.getPlayerName() + "!\n");
        System.out.println(player.getPlayerDescription());
        
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
            case BACK:
                backRoom();
                break;
            case TAKE:
                pickUpItemFromRoom(command);
                break;
            case DROP:
                dropItemInHand(command);
                break;
            case LOOK:
                look();
                break;
        }
        return wantToQuit;
    }

    // implementations of user commands:
    /**
     * Method responsible for the looking command, which prints the room description
     */
    private void look(){
    System.out.println(player.getPlayerDescription());
    }
    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }
    /**
     * This method accepts the command pickUpItem and makes the
     * player pick it up
     * @param command, the command to be proccesed
     * @return pickup methos
     */
    private void pickUpItemFromRoom (Command command){
        //checks whetger there exists a second word in the command
        if (!command.hasSecondWord()){
        //if there is no second word, we don't know what item
        //needs to be picked up
        System.out.println("Please type items name");
        return;
        }
        //get the second word
        String itemName = command.getSecondWord();
        //calls the pickUpItem method by passing the itemName by player object
        player.pickUpItem(itemName);
    }
    /**
     * Method that accepts the dropItem command and makes the
     * player drop the item being held
     * @param command, command to be processed
     */
    private void dropItemInHand (Command command){
        //checks whether there exists a second word
        if(!command.hasSecondWord()){
        //if there is no second word, we don't know what item
        //needs to be picked up
        System.out.println("Please type items name");
        return;
        }
        //gets secondword
        String itemName = command.getSecondWord();
        //calls the dropItem method by passing the itenName by player object
        player.dropItem(itemName);
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, if you moved from one room to another, then you can go "back" to it, otherwise print an error message.
     * @param command, command to be processed
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = player.getPlayersExit(direction);
        //Player commentRoom nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            player.setPlayersEnteringRoom(nextRoom);
            System.out.println(player.getPlayerDescription());
            //push(currentRoom); 
            //currentRoom = nextRoom;
            //PlayerSystem.out.println(currentRoom.getLongDescription());
        }
    }
    /**
     * This method adds the current room to a stack.
     * @param room, the name and info of room
     */
    private void push(Room room){
     if (top == roomStack.length-1){
     System.out.println("Room stack is full");
    }
     else{
      roomStack[++top] = room;
     }
    }
    /**
     * This method deletes the room at the top of roomStack
     * @return Room if it exist, otherwise its null.
     */
    private Room pop(){
     if (top < 0){
     System.out.println("Sorry, you are out of bounds, no previous rooms to go to");
     return null;
     }
     else{
         return roomStack [top--];
     }
    }
    /**
     * This method is in charge of going to the previous room
     * and showing it's information
     */
    private void backRoom(){
    player.movePlayerToPreviousRoom();
    //currentRoom = pop();
    //if (currentRoom !=null){
    //System.out.println(currentRoom.getLongDescription());
    //}
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @param command, command to process
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
