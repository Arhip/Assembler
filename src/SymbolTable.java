/**
 * SymbolTable.java keeps symbolic association between numeric addresses and mnemonic names
 */

import java.util.HashMap;

public class SymbolTable {

    //Everything but integers, numbers not allowed before
    private static final String INITIAL_VALID_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_.$:";
    //All characters including numbers
    private static final String ALL_VALID_CHARS = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_.$:";
    private HashMap<String, Integer> symbolTable = new HashMap<>();

    /**
     * Initializes hashmap with predefined symbols
     */
    public SymbolTable()
    {
        symbolTable.put("SP", 0);
        symbolTable.put("LCL", 1);
        symbolTable.put("ARG", 2);
        symbolTable.put("THIS", 3);
        symbolTable.put("THAT", 4);
        symbolTable.put("R0", 0);
        symbolTable.put("R1", 1);
        symbolTable.put("R2", 2);
        symbolTable.put("R3", 3);
        symbolTable.put("R4", 4);
        symbolTable.put("R5", 5);
        symbolTable.put("R6", 6);
        symbolTable.put("R7", 7);
        symbolTable.put("R8", 8);
        symbolTable.put("R9", 9);
        symbolTable.put("R10", 10);
        symbolTable.put("R11", 11);
        symbolTable.put("R12", 12);
        symbolTable.put("R13", 13);
        symbolTable.put("R14", 14);
        symbolTable.put("R15", 15);
        symbolTable.put("SCREEN", 16384);
        symbolTable.put("KBD", 24576);
    }

    /**
     * Adds new pair of symbol/address to hashmap
     * @param symbol
     * @param address
     * @return
     */
    public boolean addEntry(String symbol, int address)
    {
        if(!contains(symbol) && isValidName(symbol))
        {
            symbolTable.put(symbol, address);
            return true;
        }
        else
        {
            System.out.println("Illegal name, entry cannot be added");
            return false;
        }

    }

    /**
     * Returns boolean of whether hashmap has symbol or not
     * @param symbol
     * @return
     */
    public boolean contains(String symbol)
    {
        if(symbolTable.containsKey(symbol))
        {
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Returns address in hashmap of given symbol
     * @param symbol
     * @return
     */
    public int getAddress(String symbol)
    {
        if(this.contains(symbol))
        {
            return (int)symbolTable.get(symbol);
        }
        return -1;
    }

    /**
     * checks if the symbol is valid
     * @param symbol
     * @return
     */
    private boolean isValidName(String symbol)
    {
        int count = 0;
        boolean secondPass = true;
        boolean firstPass = false;
        for(int i = 0; i < INITIAL_VALID_CHARS.length(); i++) {
            if (symbol.startsWith(INITIAL_VALID_CHARS.charAt(i) + ""))
            {
                firstPass = true;
            }
        }
        for(int i = 0; i < symbol.length(); i++){
            for(int j = 0; j < ALL_VALID_CHARS.length(); j++) {
                if (symbol.charAt(i) == ALL_VALID_CHARS.charAt(j)) {
                    count++;
                }
            }
        }
        if(count == symbol.length() && firstPass)
        {
            return true;
        }
        else{
            return false;
        }
    }
}
