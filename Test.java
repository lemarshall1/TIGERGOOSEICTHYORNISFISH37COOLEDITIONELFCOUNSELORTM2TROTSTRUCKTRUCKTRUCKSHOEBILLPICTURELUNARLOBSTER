import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;

public class Test {
	private static int lives = 3;

	public static void main(String[] args) throws InterruptedException {
		// Create GPIO controller
		final GpioController gpio = GpioFactory.getInstance();

		// Define GPIO pins
		final GpioPinDigitalInput button = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN);
		final GpioPinDigitalOutput irLed = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03);
		final GpioPinDigitalInput irReceiver = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04, PinPullResistance.PULL_DOWN);
		final GpioPinDigitalOutput flashLed = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_17);

		// Turn on the infrared LED when the button is pressed
		button.addListener(event -> {
			if (event.getState().isHigh()) {
				irLed.high();
			} else {
				irLed.low();
			}
		});

		// Flash the LED when the infrared receiver detects a signal
		irReceiver.addListener(event -> {
			if (event.getState().isHigh()) {
				flashLight(flashLed);
			}
		});

		// Keep the program running while lives > 0
		while (lives > 0) {
			Thread.sleep(100);
		}

		// Shutdown GPIO controller
		gpio.shutdown();
	}

	private static void flashLight(GpioPinDigitalOutput flashLed) {
		for (int i = 0; i < 5; i++) {
			flashLed.high();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			flashLed.low();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		lives--;
	}
}