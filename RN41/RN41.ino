/*
  Example Bluetooth Serial Passthrough Sketch
 by: Jim Lindblom
 SparkFun Electronics
 date: February 26, 2013
 license: Public domain

 This example sketch converts an RN-42 bluetooth module to
 communicate at 9600 bps (from 115200), and passes any serial
 data between Serial Monitor and bluetooth module.
 */
#include <SoftwareSerial.h>  

int bluetoothTx = 10;  // TX-Output pin of RN41, Arduino D10
int bluetoothRx = 11;  // RX-Input pin of RN41, Arduino D11
SoftwareSerial BT(bluetoothTx, bluetoothRx);
//10 RX ARDUINO -> TX RN-41
//11 TX ARDUINO -> RX RN-41d
char cadena[255]; //Creamos un array de caracteres de 256 cposiciones
int i=0; //Tamaño actual del array
int yellow=13;

void setup()
{
  Serial.begin(115200);  // Begin the serial monitor at 9600bps

  BT.begin(115200);  // The Bluetooth Mate defaults to 115200bps
  /*
  bluetooth.print("$");  // Print three times individually
  bluetooth.print("$");
  bluetooth.print("$");  // Enter command mode
  delay(100);  // Short delay, wait for the Mate to send back CMD
  bluetooth.println("U,9600,N");  // Temporarily Change the baudrate to 9600, no parity
  // 115200 can be too fast at times for NewSoftSerial to relay the data reliably
  bluetooth.begin(9600);  // Start bluetooth serial at 9600
  */
  // initialize digital pin 13 as an output.
  pinMode(yellow, OUTPUT);
  Serial.println("Setup Done");
}

void loop()
{
  //Cuando haya datos disponibles
  if(BT.available())
  {
    //Serial.println("BT.available()");
    
    char dato=BT.read(); //Guarda los datos carácter a carácter en la variable "dato"
 
    cadena[i++]=dato; //Vamos colocando cada carácter recibido en el array "cadena"
 
    //Cuando reciba una nueva línea (al pulsar enter en la app) entra en la función
    if(dato=='\n')
    {
      Serial.println("string obtained: ");
      Serial.println(cadena); //Visualizamos el comando recibido en el Monitor Serial
      /*
      //GREEN LED
      if(strstr(cadena,"green on")!=0)
      {
        digitalWrite(green,HIGH);
      }
      if(strstr(cadena,"green off")!=0)
      {
        digitalWrite(green,LOW);
      }
      */
      //YELLOW LED
      if(strstr(cadena,"yellow on")!=0)
      {
        digitalWrite(yellow,HIGH);
        BT.write("\ryellow on");
        Serial.println("ACK - yellow on");
      }
      if(strstr(cadena,"yellow off")!=0)
      {
        digitalWrite(yellow,LOW);
        BT.write("\ryellow off");
        Serial.println("ACK - yellow off");
      }
      /*
      //RED LED
      if(strstr(cadena,"red on")!=0)
      {
        digitalWrite(red,HIGH);
      }
      if(strstr(cadena,"red off")!=0)
      {
        digitalWrite(red,LOW);
      }
      //ALL ON
      if(strstr(cadena,"on all")!=0)
      {
        digitalWrite(green,HIGH);
        digitalWrite(yellow,HIGH);
        digitalWrite(red,HIGH);
      }
      //ALL OFF
      if(strstr(cadena,"off all")!=0)
      {
        digitalWrite(green,LOW);
        digitalWrite(yellow,LOW);
        digitalWrite(red,LOW);
      }
      */
      clean(); //Ejecutamos la función clean() para limpiar el array
    }
  }
}
 
//Limpia el array
void clean()
{
  for (int cl=0; cl<=i; cl++)
  {
    cadena[cl]=0;
  }
  i=0;
}

