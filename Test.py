# Define GPIO pins
button = Button(2)  # GPIO pin for the button
ir_led = LED(3)     # GPIO pin for the infrared LED
ir_receiver = DigitalInputDevice(4)  # GPIO pin for the infrared receiver
flash_led = LED(17)  # GPIO pin for the flashing LED

# Turn on the infrared LED when the button is pressed
button.when_pressed = ir_led.on
button.when_released = ir_led.off

# Flash the LED when the infrared receiver detects a signal
def flash_light():
	flash_led.blink(on_time=0.2, off_time=0.2, n=5)

ir_receiver.when_activated = flash_light

pause()


