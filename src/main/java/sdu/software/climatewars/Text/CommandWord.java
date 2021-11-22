package sdu.software.climatewars.Text;

public enum CommandWord
{
    GO("go"), QUIT("quit"), HELP("help"), UNKNOWN("?"), STATS("stats"), FIGHT("fight"), FLEE("flee"), GIVE("give"), MERGE("merge"), EXILE("exile"), NOTHING("nothing");
    
    private final String commandString;
    
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }

    public String getCommandString() {
        return commandString;
    }

    public String toString()
    {
        return commandString;
    }
}
