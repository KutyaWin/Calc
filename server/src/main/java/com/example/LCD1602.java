package com.example;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.wiringpi.Lcd;

public class LCD1602 {
    private final int lcdHandle;

    public LCD1602() {
        // Initialize GPIO controller
        final GpioController gpio = GpioFactory.getInstance();

        // Initialize LCD
        lcdHandle = Lcd.lcdInit(2, 16, 4, // number of row, column and bit mode
                11, // RS pin
                10, // Strobe pin
                6,  // Data bit 1
                5,  // Data bit 2
                4,  // Data bit 3
                1,  // Data bit 4
                0, 0, 0, 0); // Unused pins

        if (lcdHandle == -1) {
            System.out.println("LCD initialization failed!");
            return;
        }

        // Clear LCD
        Lcd.lcdClear(lcdHandle);
    }

    public void displayText(String text) {
        Lcd.lcdClear(lcdHandle);
        Lcd.lcdHome(lcdHandle);
        Lcd.lcdPuts(lcdHandle, text);
    }
}