# Define GPIO pins
from gpiozero import LED, DigitalInputDevice, Button


button = Button(2)  # GPIO pin for the button
ir_led = LED(3)     # GPIO pin for the infrared LED
ir_receiver = DigitalInputDevice(4)  # GPIO pin for the infrared receiver
flash_led = LED(17)  # GPIO pin for the flashing LED

# Turn on the infrared LED when the button is pressed
button.when_pressed = ir_led.on
button.when_released = ir_led.off

lives = 3
while lives > 0:
	if button.is_pressed:
		if lives == 0:
			#why are you pressing the button when the game is over?
			break
	else:
		#why are you not pressing the button?
		break


	if ir_receiver.is_active:
		# Infrared signal detected
		pass
	

# Flash the LED when the infrared receiver detects a signal
def flash_light():
	flash_led.blink(on_time=0.2, off_time=0.2, n=5)
	lives = lives - 1


	

ir_receiver.when_activated = flash_light

pause()


