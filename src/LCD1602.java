import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.wiringpi.Lcd;

public class LCD1602 {
    private final int lcdHandle;

    public LCD1602() {
        // Initialize GPIO controller
        final GpioController gpio = GpioFactory.getInstance();

        // Initialize LCD
        lcdHandle = Lcd.lcdInit(2, 16, 4, // number of row, column and bit mode
                RaspiPin.GPIO_11, // RS pin
                RaspiPin.GPIO_10, // Strobe pin
                RaspiPin.GPIO_06, // Data bit 1
                RaspiPin.GPIO_05, // Data bit 2
                RaspiPin.GPIO_04, // Data bit 3
                RaspiPin.GPIO_01, // Data bit 4
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