
/**
 * Write a description of class Item here.
 *Item class that holds info for the item description/weight of the item. It uses different getter methods in order to achieve
 *its data retrieving commands
 * @author Sebastian Portillo
 * @version 11/05/24
 */
public class Item
{
    // instance variables 
    private String itemDescription; //item's description
    private int itemWeight;         //item's weight
    
    

    /**
     * Constructor for objects of class Item
     */
    public Item()
    {
    // initialise instance variables
    itemDescription = "";
    itemWeight = 0;
    
    }
    /**
     * Separate constructor for objects of class Item that 
     * sets instance variables with parameter values
     */
    public Item (String description, int weight){
    //initializes instace variables
    itemDescription = description;
    itemWeight = weight;
    
    }
    /**
     * This method returns a description for the item contained 
     * within a room.
     * @return description and weight of the item
     */
    public String getItemDescription(){
    String itemString = "Item name: ";
    itemString += this.itemDescription + "\n";
    itemString += "Item Weight:"+ this.itemWeight;
    return itemString;
    }
    /**
     * This method gets the name of the item
     */
    
    public String getItemName(){
    return itemDescription;
    }
    /**
     * This method gets the weight of an item
     */
    public int getWeight(){
        return itemWeight;
    }
    }

