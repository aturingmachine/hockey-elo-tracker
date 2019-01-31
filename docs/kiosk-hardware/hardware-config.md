Hardware Configuration:
	•	raspberry pi 3 rev B+
	•	7” touch screen display
	•	Kiosk Housing
	•	Arduino
	•	Arduino Housing
	•	HID badge reader 
	•	Power Supply
	•	(optional) USB Keyboard

	+———————————————++——————————————+
	|		||		|
	| RPI 3		|| 7”		|	   (optional)
	| Rev B+	|| Display	|	+——————————————+
	|    		||		|———————| USB Keyboard |
	+———————————————++——————————————+	+——————————————+
		|		|
USB serial  	|		|
Data to Pi    	|     		|
/dev/ttyUSB0	|		|
   		|		|
	+————————-+		| 
	| Arduino |    		| 5v
	| Nano	  |———————+	|
	+————————-+	  | 	|
    	 	|	  |	|
Digital I/O    	|   	  | 5v	|
    	        |    	  |  	|
	+—————————+	+———————————————+
	|  HID    |	| 5 Volt 2 Amp	|
	|  Card	  |  5v | DC Power	|
	|  Reader |-————| supply	|
	+—————————+	+———————————————+
