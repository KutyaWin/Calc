package com.example.service;

import com.example.LCD1602;
import com.example.RomNumeric;

public class CalculatorService {
    private boolean isRomNumericOne = false;
    private boolean isRomNumericTwo = false;
    private int numberOne = 0;
    private int numberTwo = 0;
    private LCD1602 lcd;

    public CalculatorService() {
        this.lcd = new LCD1602();
    }

    public String calculate(String input) {
        try {
            String[] strings = input.trim().split("\\s+");

            if (strings.length != 3) {
                throw new RuntimeException("Неверный формат. Используйте: ЧИСЛО1 ОПЕРАЦИЯ ЧИСЛО2");
            }

            // Проверяем, являются ли числа римскими
            checkRomanNumerals(strings[0], strings[2]);

            // Инициализируем числа
            initNumbers(strings[0], strings[2]);

            // Выполняем операцию
            int result = calculateResult(strings[1]);

            // Формируем ответ
            String answer = formatResult(result);

            // Выводим на дисплей
            lcd.displayText(formatForLCD(answer));

            return answer;

        } catch (Exception e) {
            lcd.displayText(formatForLCD("Ошибка: " + e.getMessage()));
            throw e;
        }
    }

    private void checkRomanNumerals(String one, String two) {
        isRomNumericOne = false;
        isRomNumericTwo = false;

        for (RomNumeric value : RomNumeric.values()) {
            if (one.equals(String.valueOf(value))) {
                isRomNumericOne = true;
            }
            if (two.equals(String.valueOf(value))) {
                isRomNumericTwo = true;
            }
        }
    }

    private void initNumbers(String one, String two) {
        if (isRomNumericOne && isRomNumericTwo) {
            RomNumeric romNumeric1 = RomNumeric.valueOf(one);
            RomNumeric romNumeric2 = RomNumeric.valueOf(two);
            numberOne = romNumeric1.getNumeric();
            numberTwo = romNumeric2.getNumeric();
        } else if (isRomNumericOne != isRomNumericTwo) {
            throw new RuntimeException("Используются разные системы счисления");
        } else {
            try {
                numberOne = Integer.parseInt(one);
                numberTwo = Integer.parseInt(two);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Некорректные числа");
            }
        }
    }

    private int calculateResult(String operation) {
        switch (operation) {
            case "+": return numberOne + numberTwo;
            case "-": return numberOne - numberTwo;
            case "*": return numberOne * numberTwo;
            case "/":
                if (numberTwo == 0) throw new ArithmeticException("Деление на ноль");
                return numberOne / numberTwo;
            default:
                throw new RuntimeException("Неподдерживаемая операция: " + operation);
        }
    }

    private String formatResult(int result) {
        if (isRomNumericOne && isRomNumericTwo) {
            if (result <= 0) {
                throw new RuntimeException("В римской системе нет отрицательных чисел");
            }
            return toRoman(result);
        }
        return String.valueOf(result);
    }

    private String toRoman(int number) {
        StringBuilder result = new StringBuilder();
        for (RomNumeric rn : RomNumeric.values()) {
            while (number >= rn.getNumeric()) {
                result.append(rn.name());
                number -= rn.getNumeric();
            }
        }
        return result.toString();
    }

    private String formatForLCD(String text) {
        return text.length() > 16 ? text.substring(0, 16) : text;
    }
}