public class Test {

    public static void main(String[] args) {

        SymbolTable symbolTable = new SymbolTable();

        symbolTable.isValidName("test");
        symbolTable.isValidName("5test");
        symbolTable.isValidName("$test");
        symbolTable.isValidName("/test");
        symbolTable.isValidName("432");





    }

}
