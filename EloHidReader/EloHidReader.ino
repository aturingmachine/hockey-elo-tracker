/*
 * HID RFID Reader Wiegand Interface for Arduino Uno
 * Originally by  Daniel Smith, 2012.01.30 -- http://www.pagemac.com/projects/rfid/arduino_wiegand
 * 
 * Updated 2016-11-23 by Jon "ShakataGaNai" Davis.
 * See https://obviate.io/?p=7470 for more details & instructions
*/

#define MAX_BITS 100                 // max number of bits 
#define WEIGAND_WAIT_TIME  3000      // time to wait for another weigand pulse.  
 
unsigned char databits[MAX_BITS];    // stores all of the data bits
unsigned char bitCount;              // number of bits currently captured
unsigned char flagDone;              // goes low when data is currently being captured
unsigned int weigand_counter;        // countdown until we assume there are no more bits
 
unsigned long facilityCode=0;        // decoded facility code
unsigned long cardCode=0;            // decoded card code

int LED_GREEN = 11;
int LED_RED = 12;
int BEEP_BEEP = 10; 

// interrupt that happens when INTO goes low (0 bit)
void ISR_INT0() {
  // Serial.print("0"); // raw data
  bitCount++;
  flagDone = 0;
  weigand_counter = WEIGAND_WAIT_TIME;
}
 
// interrupt that happens when INT1 goes low (1 bit)
void ISR_INT1() {
  // Serial.print("1"); // raw data
  databits[bitCount] = 1;
  bitCount++;
  flagDone = 0;
  weigand_counter = WEIGAND_WAIT_TIME;  
}
 
void setup() {
  pinMode(2, INPUT); // DATA0 (INT0)
  pinMode(3, INPUT); // DATA1 (INT1)
 
  Serial.begin(9600);
  Serial.println("{\"acknowledge\": true, \"payload\": {}}");
 
  // binds the ISR functions to the falling edge of INTO and INT1
  attachInterrupt(0, ISR_INT0, FALLING);  
  attachInterrupt(1, ISR_INT1, FALLING);

  weigand_counter = WEIGAND_WAIT_TIME;
}
 
void loop() {
  // This waits to make sure that there have been no more data pulses before processing data
  if (!flagDone) {
    if (--weigand_counter == 0)
      flagDone = 1;  
  }
 
  if (bitCount > 0 && flagDone) {
    unsigned char i;
 
    if (bitCount == 35) {
      // 35 bit HID Corporate 1000 format - facility code = bits 2 to 14
      for (i=2; i<14; i++) {
         facilityCode <<=1;
         facilityCode |= databits[i];
      }
      // card code = bits 15 to 34
      for (i=14; i<34; i++) {
         cardCode <<=1;
         cardCode |= databits[i];
      }
      printBits();
    } else if (bitCount == 26) {
      // standard 26 bit format - facility code = bits 2 to 9
      for (i=1; i<9; i++) {
         facilityCode <<=1;
         facilityCode |= databits[i];
      }
      // card code = bits 10 to 23
      for (i=9; i<25; i++) {
         cardCode <<=1;
         cardCode |= databits[i];
      }
      printBits();  
    } else if ( bitCount == 37 ) { // Wiegand 37bit
        // https://www.ooaccess.com/kb/37-bit-fc/ - facility code = bits 1 to 16
        for(i = 1; i < 17; ++i ) {
            facilityCode <<=1;
            facilityCode |= databits[i];
        }
        // card code = bits 17 to 35
        for (i = 17; i < 36; ++i) {
            cardCode <<=1;
            cardCode |= databits[i];
        }
        printBits();  
    }
 
     // clean up
     bitCount = 0;
     facilityCode = 0;
     cardCode = 0;
     for (i=0; i<MAX_BITS; i++) 
     {
       databits[i] = 0;
     }
  }
}
 
void printBits() {
      Serial.print("{\"acknowledge\": true, \"payload\": {");
      Serial.print("\"facilityCode\":\"");
      Serial.print(facilityCode);
      Serial.print("\", \"cardCode\":\"");
      Serial.print(cardCode);
      Serial.println("\"}}");
}
