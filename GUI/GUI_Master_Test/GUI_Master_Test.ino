/*
The MIT License (MIT)

Copyright (c) 2015 AlejusMaximus

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.


Setting up RN-41 before running this code into Arduino, done in Windows 8:

Tera Term -> http://en.sourceforge.jp/projects/ttssh2/releases/
COM4:9600baud
GO INSIDE COMAND MODE -> $$$
H + enter -> basic configuration
Change device name -> SN,ALEJUSRN41
D -> BASIC SETTINGS.
Change Baudrate -> SU,9600
D
Change PIN -> SP,4646
TO GO OUT-> ---
END
https://www.youtube.com/watch?v=QCXw_kHZ8Ok
*/

//#include <SoftwareSerial.h>  

//int bluetoothTx = 10;  // TX-Output pin of RN41, Arduino D10
//int bluetoothRx = 11;  // RX-Input pin of RN41, Arduino D11
//SoftwareSerial BT(bluetoothTx, bluetoothRx);
//10 RX ARDUINO -> TX RN-41
//11 TX ARDUINO -> RX RN-41d
char cadena[255]; //Creamos un array de caracteres de 256 cposiciones
int i=0; //Tamaño actual del array
int yellow=13;
void setup()
{
  //BT.begin(9600); //Velocidad del puerto del módulo Bluetooth
  Serial.begin(9600); //Abrimos la comunicación serie con el PC y establecemos velocidad
  // initialize digital pin 13 as an output.
  pinMode(yellow, OUTPUT);
}

void loop()
{
  //Cuando haya datos disponibles
  if(Serial.available())
  {
    //Serial.println("BT.available()");
    
    char dato=Serial.read(); //Guarda los datos carácter a carácter en la variable "dato"
 
    cadena[i++]=dato; //Vamos colocando cada carácter recibido en el array "cadena"
 
    //Cuando reciba una nueva línea (al pulsar enter en la app) entra en la función
    if(dato=='\n')
    {
      //Serial.println("string obtained: ");
      //Serial.println(cadena); //Visualizamos el comando recibido en el Monitor Serial
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
        //BT.write("yellow on");
        Serial.println("ACK - yellow on");
      }
      if(strstr(cadena,"yellow off")!=0)
      {
        digitalWrite(yellow,LOW);
        //BT.write("yellow off");
        Serial.println("ACK -yellow off");
      }
      //RED LED
      if(strstr(cadena,"red on")!=0)
      {
        //digitalWrite(red,HIGH);
        Serial.println("ACK - red on");
      }
      if(strstr(cadena,"red off")!=0)
      {
        //digitalWrite(red,LOW);
        Serial.println("ACK - red off");
      }
      /*
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
