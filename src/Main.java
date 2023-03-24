import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    static boolean isRomNumericOne = false, isRomNumericTwo = false;
    static int numberOne = 0, numberTwo = 0, numberAnswer = 0;
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine();
        System.out.println(calc(input));
    }

    public static String calc (String input){

        String[] strings =input.split(" ");

        if(strings.length != 3){
            throw new RuntimeException("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }
        variableIsRomanOrArabic(strings[0], strings[2]);
        initNumberOneAndTwo(strings[0],strings[2]);
        numberAnswer = initAnswer(strings[1]);
            return giveAnswer(numberAnswer);
    }

     private static int initAnswer(String operation){
        switch (operation){
            case "+": numberAnswer = numberOne + numberTwo; break;
            case "-": numberAnswer = numberOne - numberTwo; break;
            case "*": numberAnswer = numberOne * numberTwo; break;
            case "/": numberAnswer = numberOne / numberTwo; break;
            default:
                throw new RuntimeException("нет такой операции");
        }
        return numberAnswer;
    }
    private static void variableIsRomanOrArabic(String one, String two) {
        for (RomNumeric value : RomNumeric.values()) {
            if (one.equals(String.valueOf(value))) {
                isRomNumericOne = true;
            }
            if (two.equals(String.valueOf(value))) {
                isRomNumericTwo = true;
            }
        }
    }
    private static void initNumberOneAndTwo(String one, String two){

        if(isRomNumericOne && isRomNumericTwo){
            RomNumeric romNumeric1 = RomNumeric.valueOf(one);
            RomNumeric romNumeric2 = RomNumeric.valueOf(two);
            numberOne = romNumeric1.getNumeric();
            numberTwo = romNumeric2.getNumeric();

        } else if (!isRomNumericOne && isRomNumericTwo || isRomNumericOne && !isRomNumericTwo) {
            throw new RuntimeException("используются одновременно разные системы счисления");
        }

        if(numberOne == 0 && numberTwo == 0) {
            numberOne = Integer.parseInt(one);
            numberTwo = Integer.parseInt(two);

        }
    }
private static String giveAnswer(int numberAnswer){
    if(isRomNumericOne && isRomNumericTwo){
        if( numberAnswer > 0) {
            return romanToArabic(numberAnswer);
        }
        else {
            throw new RuntimeException("в римской системе нет отрицательных чисел");
        }
    }
    return String.valueOf(numberAnswer);
}

    public static String romanToArabic(int  number) {
        StringBuilder builder = new StringBuilder();

        for (RomNumeric romanNumeric: RomNumeric.values()){
            while (number >= romanNumeric.getNumeric() ){
                builder.append(romanNumeric.name());
                number -= romanNumeric.getNumeric();
            }
        }
        return builder.toString();
    }

}