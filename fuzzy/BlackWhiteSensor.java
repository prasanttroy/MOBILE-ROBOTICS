import lejos.nxt.*;

public class BlackWhiteSensor {

   LightSensor ls; 
  int blackLightValueleft , blackLightValueright;
  int whiteLightValueleft ,whiteLightValueright;
  int Threshold;

   public BlackWhiteSensor(SensorPort p)
   {
	   ls = new LightSensor(p); 
	   ls.setFloodlight(true);
   }

   private int read(String color){
	   
	   int lightValue=0;
	   
	   while (Button.ENTER.isDown());			//change
	   
	   LCD.clear();
	   LCD.drawString("Press ENTER", 0, 0);
	   LCD.drawString("to callibrate", 0, 1);
	   LCD.drawString(color, 0, 2);
	   while( !Button.ENTER.isPressed() ){			//chna
	      lightValue = ls.readValue();
	      LCD.drawInt(lightValue, 4, 10, 2);
	      LCD.refresh();
	   }
	   return lightValue;
   }
   
   public void calibrate()
   {
	   blackLightValueleft= read("black");
	   whiteLightValueright = read("white");
	   
	  // blackLightValueright = read("black");
	  // whiteLightValueright = read("white");
	   // The threshold is calculated as the median between 
	   // the two readings over the two types of surfaces
	   Threshold = (blackLightValueleft +whiteLightValueleft)/2;
	   LCD.clear();
   }
   
   public boolean black() {
           //return (ls.readValue()< blackWhiteThreshold-8);
	   return (ls.readValue()<Threshold);
   }
   
   public boolean white() {
	  // return (ls.readValue()> blackWhiteThreshold-8);
	   return (ls.readValue()> Threshold);
   }
   
   //public boolean border() {
	  // return (Math.abs(ls.readValue()-blackWhiteThreshold)<=4);
  // }
   
   public int light() {
 	   return ls.readValue();
   }
   
}
