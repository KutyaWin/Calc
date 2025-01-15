package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    static boolean isRomNumericOne = false, isRomNumericTwo = false;
    static int numberOne = 0, numberTwo = 0, numberAnswer = 0;
    static LCD1602 lcd;

    public static void main(String[] args) throws IOException {
        lcd = new LCD1602();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine();
        String result = calc(input);
        System.out.println(result);
        // Вывод результата на экран дисплея может быть только до 16 символов на одной строке
        lcd.displayText(formatForLCD(result));
    }

    public static String calc (String input) {

        String[] strings = input.split(" ");

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
            throw new RuntimeException(
                    "используются одновременно разные системы счисления " +
                            "или вы ввели числа не от 1 до 10");
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

    private static String formatForLCD(String text) {
        if (text.length() > 16) {
            return text.substring(0, 16); // Обрезаем строку до 16 символов
        }
        return text;
    }

}

// Добавить вывод римских чисел на Экран оборудования (само оборудование надо назвать)
// и где это происходит, в какой части кода (можно без оборудования)
//https://www.ozon.ru/product/simvolnyy-displey-lcd1602-bez-i2c-zelenyy-1-sht-1331084666/?asb=sJS5tf5WN7hc5wZUZ%252BWPX7CV5oULCmB8YKl4Ae3zp8Y%253D&asb2=jeSCZAIvVlxPDAygQpQLTvVpqoqGRs51Be-kl2lhYvlBghJvwutUb6ODunjd1UB0&avtc=1&avte=4&avts=1736934160&keywords=символьный+дисплей+1602&reviewsVariantMode=2
// оборудование  - Символьный дисплей LCD1602 без i2c зеленый 1 шт
