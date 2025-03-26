from gpiozero import LED, Button, Buzzer, MotionSensor, DistanceSensor
from signal import pausein
pir = MotionSensor(4) #pin number? i think
led = LED(16)

pir.when_motion = led.on
pir.when_no_motion = led.off

pause