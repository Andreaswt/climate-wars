package sdu.software.climatewars.Text;

import sdu.software.climatewars.Text.Command;
import sdu.software.climatewars.Text.CommandWords;

import java.util.Scanner;

public class Parser 
{
    private final CommandWords commands;
    private final Scanner reader;

    public Parser() 
    {
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }

    public Command getCommand()
    {
        String inputLine;
        String word1 = null;
        String word2 = null;

        System.out.print("> "); 

        inputLine = reader.nextLine();

        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next().toLowerCase();
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next().toLowerCase();
            }
        }

        return new Command(commands.getCommandWord(word1), word2);
    }

    public void showCommands()
    {
        commands.showAll();
    }
}
