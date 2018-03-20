public class Test {

    public static void main(String[] args) {

        String temp = "D;JMP";

        System.out.println(temp.substring(temp.indexOf(";")+ 1, temp.length()));

    }


}
