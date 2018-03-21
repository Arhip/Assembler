public class Test {

    public static void main(String[] args) {


        SymbolTable symbolTable = new SymbolTable();

        System.out.println(symbolTable.isValidName("s@ld1")); //false
        System.out.println(symbolTable.isValidName("1sold1")); //false
        System.out.println(symbolTable.isValidName("so.d1"));
        System.out.println(symbolTable.isValidName("so.d1."));
        System.out.println(symbolTable.isValidName("$sold."));
        System.out.println(symbolTable.isValidName("sold$"));
        System.out.println(symbolTable.isValidName("8sold$")); //false
        System.out.println(symbolTable.isValidName(":sold$"));
        System.out.println(symbolTable.isValidName(":so:d$"));
        System.out.println(symbolTable.isValidName("_sold$"));
        System.out.println(symbolTable.isValidName("_sol$"));
        System.out.println(symbolTable.isValidName("_so$y"));













    }


}
