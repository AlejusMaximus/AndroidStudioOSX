/*
  www.diymakers.es
  by A.García
  Arduino + Bluetooth
  Tutorial en: http://diymakers.es/arduino-bluetooth/
*/
/*

The circuit: 
------------
 * RX is digital pin 10 (connect to JY-MCU's TX) 
 * TX is digital pin 11 (connect to JY-MCU's RX) (through voltage divider 5V((10k+10k)/(10k+10k+10k)))
 SoftwareSerial mySerial(10, 11); // RX, TX
 
 Note:
 Not all pins on the Mega and Mega 2560 support change interrupts, 
 so only the following can be used for RX: 
 10, 11, 12, 13, 50, 51, 52, 53, 62, 63, 64, 65, 66, 67, 68, 69
 
 JY-MCU
 ------
 
 MONITOR SERIAL:
 --------------
 Desplaçament automatic & sense salt de linia
 AT -> OK
 AT+VERSION -> OKlinvorV1.8
 AT+BAUD4 -> OK9600
 AT+NAMEALEJUSJYMCU -> OKsetname
 AT+PIN4646 -> OKsetPIN
 
*/

#include <SoftwareSerial.h> 

SoftwareSerial BT(10,11); 
//10 RX ARDUINO -> TX JY-MCU
//11 TX ARDUINO -> RX JY-MCU (through voltage divider 5V((10k+10k)/(10k+10k+10k)))
char cadena[255]; //Creamos un array de caracteres de 256 cposiciones
int i=0; //Tamaño actual del array
int yellow=13;
void setup()
{
  BT.begin(9600); //Velocidad del puerto del módulo Bluetooth
  Serial.begin(9600); //Abrimos la comunicación serie con el PC y establecemos velocidad
  // initialize digital pin 13 as an output.
  pinMode(yellow, OUTPUT);
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
