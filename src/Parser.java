/**
 * Parser.java cleans a line of code and
 */

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
                lineNumber++;
                rawLine = inputFile.nextLine();
                advance();
                parse();
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

    /**
     * helper method parses line depending on instruction type
     */
    private void parse()
    {
        if(commandType == C_COMMAND)
        {
            parseDest();
            parseComp();
            parseJump();
        }
        else{
            parseSymbol();
        }
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
        if(cleanLine.contains("="))
        {
                int equalsLocation = cleanLine.indexOf("=");
                destMnemonic = cleanLine.substring(0, equalsLocation);
        }
    }


    /**
     * helper method parses line to get comp part
     */
    private void parseComp()
    {
        if(cleanLine.contains(";"))
        {
            int colonLocation = cleanLine.indexOf(";");
            compMnemonic = cleanLine.substring(0, colonLocation);
        }
        else if (cleanLine.contains("=")){
            int equalLocation = cleanLine.indexOf("=");
            compMnemonic = cleanLine.substring(equalLocation + 1, cleanLine.length());
        }
    }

    /**
     * helper method parses line to get jump part
     */
    private void parseJump()
    {
        if(cleanLine.contains(";")) {
            int colonLocation = cleanLine.indexOf(";");
            jumpMnemonic = cleanLine.substring(colonLocation + 1, cleanLine.length());
        }
    }

    /**
     * getter for commandType
     * @return
     */
    public char getCommandType()
    {
        return commandType;
    }

    /**
     * getter for symbol
     * @return
     */
    public String getSymbol()
    {
        return symbol;
    }

    /**
     * getter for destMnemonic
     * @return
     */
    public String getDest()
    {
        return destMnemonic;
    }

    /**
     * getter for compMnemonic
     * @return
     */
    public String getComp()
    {
        return compMnemonic;
    }

    /**
     * getter for jumpMnemonic
     * @return
     */
    public String getJump()
    {
        return jumpMnemonic;
    }

    /**
     * getter for string commandType
     * @return
     */
    public String getCommandTypeString()
    {
        return "" + commandType;
    }

    /**
     * getter for rawline
     * @return
     */
    public String getRawLine()
    {
        return rawLine;
    }

    /**
     * getter for cleanline
     * @return
     */
    public String getCleanLine()
    {
        return cleanLine;
    }

    /**
     * getter for linenumber
     * @return
     */
    public int getLineNumber()
    {
        return lineNumber;
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