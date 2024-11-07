/**
 * Representations for all the valid command words for the game
 * along with a string in a particular language.
 * 
 * @author  Sebastian Portillo
 * @version 11/06/24
 */
public enum CommandWord
{
    // A value for each command word along with its
    // corresponding user interface string.
    GO("go"), QUIT("quit"), HELP("help"), UNKNOWN("?"), BACK("back"), TAKE("take"), DROP("drop"), LOOK("look");
    //Added the BACK, TAKE, LOOK and DROP command, to be recognized as the command
    //responsible for going to the "previous room" if applicable.
    // The command string.
    private String commandString;
    
    /**
     * Initialise with the corresponding command string.
     * @param commandString The command string.
     */
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }
    
    /**
     * @return The command word as a string.
     */
    public String toString()
    {
        return commandString;
    }
}
