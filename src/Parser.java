import com.sun.org.apache.xerces.internal.impl.xs.SchemaNamespaceSupport;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {

    public char NO_COMMAND = 'N';
    public char A_COMMAND = 'A';
    public char C_COMMAND = 'C';
    public char L_COMMAND = 'L';

    private Scanner inputFile;
    private int lineNumber = 0;
    private String rawLine = "";

    private String cleanLine = "";
    private char commandType;
    private String symbol = "";
    private String destMnemonic = "";
    private String compMnemonic = "";
    private String jumpMnemonic = "";


    public Parser(String inFileName){

        try {
            File file = new File(inFileName);
            inputFile = new Scanner(file);

            while(hasMoreCommands()){
                rawLine = inputFile.nextLine();
                advance();

            }
        }
        catch(FileNotFoundException ex){
            ex.printStackTrace();
            System.out.println("File not found program is now ending");
            System.exit(0);
        }
    }

    public boolean hasMoreCommands() {
        if(inputFile.hasNext())
        {
            return true;
        }
        else{
            inputFile.close();
            return false;
        }
    }

    public void advance() {
        rawLine = inputFile.nextLine();
        cleanLine();
        parseCommandType();
    }

    private void parse()
    {

    }

    /**
     * parses symbol for A and L commands
     */
    private void parseSymbol()
    {
        if(commandType == A_COMMAND)
        {
           symbol = cleanLine.substring(1, cleanLine.length());
        }
        else if(commandType == L_COMMAND)
        {
            symbol = cleanLine.substring(1, cleanLine.length()-1);
        }

    }

    /**
     * helper method parses line to get dest part
     */
    private void parseDest()
    {
        //dest = comp;jump
        if(commandType == C_COMMAND)
        {
            if(!cleanLine.contains(";"))
            {
                int equalsLocation = cleanLine.indexOf("=");
                destMnemonic = cleanLine.substring(0, equalsLocation);
            }
        }
    }

    private void parseComp()
    {
        if(commandType == C_COMMAND)
        {
            if(cleanLine.contains(";"))
            {
                int colonLocation = cleanLine.indexOf(";");
                compMnemonic = cleanLine.substring(0, colonLocation);
            }
            else{
                int equalLocation = cleanLine.indexOf("=");
                compMnemonic = cleanLine.substring(equalLocation + 1, cleanLine.length());
            }
        }
    }

    private void parseJump()
    {

    }

    /**
     * Cleans raw instruction by removing non-essential parts
     * String parameter isn't null
     */
    private void cleanLine() {
        String cleanLine = rawLine;
        int commentLocation = cleanLine.indexOf("//");

        if (commentLocation != -1) {
            cleanLine = cleanLine.substring(0, commentLocation);
        }
        cleanLine = cleanLine.replaceAll("\t", " ");
        cleanLine = cleanLine.replaceAll(" ", "");
        this.cleanLine = cleanLine;

    }

    /**
     * determines the command type
     */
    private void parseCommandType() {
        if (cleanLine.isEmpty()) {
            this.commandType = NO_COMMAND;
        }

        else if(cleanLine.contains("@"))
        {
            this.commandType = A_COMMAND;
        }

        else if(cleanLine.contains("("))
        {
            this.commandType = L_COMMAND;
        }
        else{
            this.commandType = C_COMMAND;
        }
    }
}