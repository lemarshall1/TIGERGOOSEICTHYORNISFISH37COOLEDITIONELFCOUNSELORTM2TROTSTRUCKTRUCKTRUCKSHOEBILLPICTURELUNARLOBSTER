# Define GPIO pins
from gpiozero import LED, DigitalInputDevice, Button
from time import sleep


#initialization of variables
button = Button(2)  # GPIO pin for the button
ir_led = LED(3)     # GPIO pin for the infrared LED
ir_receiver = DigitalInputDevice(4)  # GPIO pin for the infrared receiver
flash_led = LED(17)  # GPIO pin for the flashing LED

gameOn = True # should the game keep going
lives = 3 # number of starting lives


def shoot():
	# Shoot the infrared LED
	ir_led.on()
	sleep(0.2)
	ir_led.off()


# Flash the LED when the infrared receiver detects a signal
def flash_light(a,b):
	flash_led.blink(on_time=b, off_time=b, n=a)
	lives = lives - 1

while lives > 0 and gameOn: 
	button.when_pressed = shoot
	
	if lives == 0:
		#end game
		gameOn = False
		flash_light(10,0.1)
		pass
	else:
		if ir_receiver.is_active:
			flash_light(3,0.2)
			lives -= 1
		pass

sleep(0.1)  # Add a small delay to avoid excessive CPU usage		



