/**
 * Assembler.java main method for the assembler
 * Methods for firstpass and secondpass runs
 *
 * @author Arhip Bobkov
 * @version 1.0
 */
//TODO: don't forget to document each method in all classes!
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;

public class Assembler {
	
	// ALGORITHM:
	// get input file name
	// create output file name and stream
	
	// create symbol table
	// do first pass to build symbol table (no output yet!)
	// do second pass to output translated ASM to HACK code
	
	// print out "done" message to user
	// close output file stream
	public static void main(String[] args) {
		
		String inputFileName, outputFileName;
		PrintWriter outputFile = null; //keep compiler happy
		SymbolTable symbolTable = new SymbolTable();
		int romAddress, ramAddress;
	
		//get input file name from command line or console input
		if(args.length == 1) {
			System.out.println("command line arg = " + args[0]);
			inputFileName = args[0];
		}
		else
		{
			Scanner keyboard = new Scanner(System.in);

			System.out.println("Please enter assembly file name you would like to assemble.");
			System.out.println("Don't forget the .asm extension: ");
			inputFileName = keyboard.nextLine();

			keyboard.close();

		}

		outputFileName = inputFileName.substring(0,inputFileName.lastIndexOf('.')) + ".hack";
							
		try {
			outputFile = new PrintWriter(new FileOutputStream(outputFileName));

		} catch (FileNotFoundException ex) {
			System.err.println("Could not open output file " + outputFileName);
			System.err.println("Run program again, make sure you have write permissions, etc.");
			System.exit(0);
		}
		
		// TODO: finish driver as algorithm describes
		firstPass(inputFileName, symbolTable);
		secondPass(inputFileName, symbolTable, outputFile);
		System.out.println("done");
		outputFile.close();
	}
	
	// TODO: march through the source code without generating any code
		//for each label declaration (LABEL) that appears in the source code,
		// add the pair <LABEL, n> to the symbol table
		// n = romAddress which you should keep track of as you go through each line
	//HINT: when should rom address increase? What kind of commands?
	//When L or A command has a symbol

	/**
	 * firstPass does the firstPass
	 * @param inputFileName
	 * @param symbolTable
	 */
	private static void firstPass(String inputFileName, SymbolTable symbolTable) {
		int n = 0;
		Parser parser = new Parser(inputFileName);
		while(parser.hasMoreCommands())
		{
			parser.advance();
			if(parser.getCommandType() == Parser.L_COMMAND)
			{
				symbolTable.addEntry(parser.getSymbol(), n);
			}
			else if(parser.getCommandType() == Parser.A_COMMAND || parser.getCommandType() == Parser.C_COMMAND)
			{
				n++;
			}
		}

	}
	
	// TODO: march again through the source code and process each line:
		// if the line is a c-instruction, simple (translate)
		// if the line is @xxx where xxx is a number, simple (translate)
		// if the line is @xxx and xxx is a symbol, look it up in the symbol
		//	table and proceed as follows:
			// If the symbol is found, replace it with its numeric value and
			//	and complete the commands translation
			// If the symbol is not found, then it must represent a new variable:
				// add the pair <xxx, n> to the symbol table, where n is the next
				//	available RAM address, and complete the commands translation
	// HINT: when should rom address increase?  What should ram address start
	// at? When should it increase?  What do you do with L commands and No commands?
	// Rom address does not increase during the second pass, Ram starts at 16 an increments whenever
	// a new variable is added to the symbol table.
	// Nothing we do not have if statements that keep track of them, the program will just iterate over them
	private static void secondPass(String inputFileName, SymbolTable symbolTable, PrintWriter outputFile) {

		int n = 16;
		Parser parser = new Parser(inputFileName);
		Code code = new Code();
		while(parser.hasMoreCommands())
		{
			parser.advance();
			if(parser.getCommandType() == Parser.C_COMMAND)
			{
				//111A CCCC CCDD DJJJ
				String cInstruction;
				cInstruction = "111" + code.getComp(parser.getComp()) +
						               code.getDest(parser.getDest()) +
						               code.getJump(parser.getJump());

				outputFile.println(cInstruction);
			}
			if(parser.getCommandType() == Parser.A_COMMAND)
			{
				//0vvv vvvv vvvv vvvv
				String aInstruction;
				if(parser.getSymbol().matches("\\d+"))
				{
					aInstruction = code.decimalToBinary(Integer.parseInt(parser.getSymbol()));
					outputFile.println(aInstruction);
				}

				else
				{
					if(symbolTable.contains(parser.getSymbol()))
					{
						aInstruction = code.decimalToBinary(symbolTable.getAddress(parser.getSymbol()));
						outputFile.println(aInstruction);
					}
					else{
						symbolTable.addEntry(parser.getSymbol(), n);
						aInstruction = code.decimalToBinary(symbolTable.getAddress(parser.getSymbol()));
						outputFile.println(aInstruction);
						n++;
					}
				}
			}
		}
	}
	


}