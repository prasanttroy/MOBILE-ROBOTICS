
import nxtfuzzylogic.*;
import lejos.nxt.*;
import lejos.nxt.addon.ColorHTSensor;
public class Fuzzyfive {

public static void main(String[] args) 
{ 
	NXTFuzzyLogic nfl = new NXTFuzzyLogic();
	NXTMotor mA = new NXTMotor(MotorPort.A);
	NXTMotor mB = new NXTMotor(MotorPort.C);
	BlackWhiteSensor bws =new BlackWhiteSensor(SensorPort.S1);
	//ColorHTSensor clr = new ColorHTSensor(SensorPort.S2);
	int b,w,diff, bdel,gdel;
	bws.calibrate();
	//Button.ENTER.waitForPress();
	int th=bws.Threshold;
	b=bws.blackLightValue;
	w=bws.whiteLightValue;
	diff=w-b;
	gdel=(int) (diff*.35);
	bdel=(int) (diff*.35);
	
		
	//definition of 2 input linguistic variable - ambient light which is going to be get from light sensor
   nfl.defineInputLinguisticVariable("LightLeft", 0, 12, 7);
   nfl.defineInputLinguisticVariable("LightRight", 0, 100, 0);

 //definition of 2 output linguistic variable - speed of motor A and B
   nfl.defineOutputLinguisticVariable("speedA", -90, 90, 40, NXTFuzzyLogic.MIN);//setting motor speed range 	//MIN
   nfl.defineOutputLinguisticVariable("speedB", -90, 90, 40, NXTFuzzyLogic.MIN);//setting motor speed range		//MIN

   
   //left sensor
  // nfl.defineTermZType("blackleft", "LightLeft", 25, 39);
   nfl.defineTermZType("blackright", "LightRight",b,b+bdel);//b,th-4);
   nfl.defineTermTriangular("greyright", "LightRight",th-gdel,th,th+gdel);//th-6 ,th, th+6);
   nfl.defineTermSType("whiteright", "LightRight",w-bdel,w);//th+4, w);
  // nfl.defineTermSType("whiteleft", "LightLeft", 47, 56);
   
   //right sensor
   nfl.defineTermSingleton("black","LightLeft",6);
   nfl.defineTermSingleton("white","LightLeft",7);
   /*
   nfl.defineTermSingleton("blackright", "LightRight", 7);
   nfl.defineTermSingleton("whiteright", "LightRight", 6);*/
  /* nfl.defineTermZType("black", "LightLeft",(float) 6.5,7);
   //nfl.defineTermTriangular("greyright", "LightRight",);
   nfl.defineTermSType("white", "LightLeft",6,(float) 6.5);*/
/*  // nfl.defineTermZType("blackright", "LightRight", 25, 39);
   nfl.defineTermZType("blackright", "LightRight",b,b+bdel);
   nfl.defineTermTriangular("greyright", "LightRight",th-gdel,th,th+gdel);
   nfl.defineTermSType("whiteright", "LightRight",w-bdel,w);
  // nfl.defineTermSType("whiteright", "LightRight", 47, 56);
 */
   //motor A
   nfl.defineTermZType("VNA", "speedA", -80, -65);
   nfl.defineTermTriangular("MNA","speedA", -65,-55,-50);
   nfl.defineTermTriangular("NA","speedA", -54,-44,-37);
   nfl.defineTermTriangular("LNA","speedA", -40, -20, 0);
   nfl.defineTermTriangular("LPA", "speedA", 0, 20, 40);
   nfl.defineTermTriangular("PA","speedA", 37, 44, 54);
   nfl.defineTermTriangular("MPA","speedA", 50, 55, 65);
   nfl.defineTermSType("VPA","speedA", 65,80);
   
   //motor B
   nfl.defineTermZType("VNB", "speedB", -80, -65);
   nfl.defineTermTriangular("MNB","speedB", -65,-55,-50);
   nfl.defineTermTriangular("NB","speedB", -54,-44,-37);
   nfl.defineTermTriangular("LNB","speedB",-40, -20, 0);
   nfl.defineTermTriangular("LPB", "speedB", 0, 20, 40);
   nfl.defineTermTriangular("PB","speedB",  37, 44, 54);
   nfl.defineTermTriangular("MPB","speedB", 50, 55, 65);
   nfl.defineTermSType("VPB","speedB",  65,80);
 
   //definition of rules
   
   nfl.defineRule(new String[] {"white","whiteright"}, "MPA", NXTFuzzyLogic.COG);
   nfl.defineRule(new String[] {"white","whiteright"}, "MNB", NXTFuzzyLogic.COG);
   nfl.defineRule(new String[] {"white","greyright"}, "PA", NXTFuzzyLogic.COG);
   nfl.defineRule(new String[] {"white","greyright"}, "NB", NXTFuzzyLogic.COG);
   nfl.defineRule(new String[] {"white","blackright"}, "MPA", NXTFuzzyLogic.COG);
   nfl.defineRule(new String[] {"white","blackright"}, "MPB", NXTFuzzyLogic.COG);
   nfl.defineRule(new String[] {"black","whiteright"}, "MNA", NXTFuzzyLogic.COG);
   nfl.defineRule(new String[] {"black","whiteright"}, "MPB", NXTFuzzyLogic.COG);
   nfl.defineRule(new String[] {"black","greyright"}, "PA", NXTFuzzyLogic.COG);
   nfl.defineRule(new String[] {"black","greyright"}, "NB", NXTFuzzyLogic.COG);
   nfl.defineRule(new String[] {"black","blackright"}, "MNA", NXTFuzzyLogic.COG);
   nfl.defineRule(new String[] {"black","blackright"}, "VPB", NXTFuzzyLogic.COG);
   
   /*nfl.defineRule(new String[] {"whiteleft","whiteright"}, "PA", NXTFuzzyLogic.COG);
   nfl.defineRule(new String[] {"whiteleft","whiteright"}, "MNB", NXTFuzzyLogic.COG);
   nfl.defineRule(new String[] {"whiteleft","blackright"}, "PA", NXTFuzzyLogic.COG);
   nfl.defineRule(new String[] {"whiteleft","blackright"}, "PB", NXTFuzzyLogic.COG);
   nfl.defineRule(new String[] {"greyleft","whiteright"}, "LPA", NXTFuzzyLogic.COG);
   nfl.defineRule(new String[] {"greyleft","whiteright"}, "PB", NXTFuzzyLogic.COG);
   nfl.defineRule(new String[] {"greyleft","blackright"}, "LPA", NXTFuzzyLogic.COG);
   nfl.defineRule(new String[] {"greyleft","blackright"}, "PB", NXTFuzzyLogic.COG);
   nfl.defineRule(new String[] {"blackleft","whiteright"}, "MNA", NXTFuzzyLogic.COG);
   nfl.defineRule(new String[] {"blackleft","whiteright"}, "PB", NXTFuzzyLogic.COG);
   nfl.defineRule(new String[] {"blackleft","blackright"}, "MNA", NXTFuzzyLogic.COG);
   nfl.defineRule(new String[] {"blackleft","blackright"}, "PB", NXTFuzzyLogic.COG);*/
   
  /* nfl.defineRule(new String[] {"whiteleft","whiteright"}, "MPA", NXTFuzzyLogic.MIN);				//MPA --straight
   nfl.defineRule(new String[] {"whiteleft","whiteright"}, "MNB", NXTFuzzyLogic.MIN);				//MPB
   nfl.defineRule(new String[] {"whiteleft","greyright"}, "PA", NXTFuzzyLogic.MIN);				//PA   right
   nfl.defineRule(new String[] {"whiteleft","greyright"}, "MNB", NXTFuzzyLogic.MIN);				//MNB
   nfl.defineRule(new String[] {"whiteleft","blackright"}, "VPA", NXTFuzzyLogic.MIN);				//MPA	right
   nfl.defineRule(new String[] {"whiteleft","blackright"}, "VPB", NXTFuzzyLogic.MIN);				//MNB
   
   nfl.defineRule(new String[] {"greyleft","whiteright"}, "NA", NXTFuzzyLogic.MIN);				//MNA	left
   nfl.defineRule(new String[] {"greyleft","whiteright"}, "PB", NXTFuzzyLogic.MIN);				//PB
   nfl.defineRule(new String[] {"greyleft","greyright"}, "MNA", NXTFuzzyLogic.MIN);   				//PA		
   nfl.defineRule(new String[] {"greyleft","greyright"}, "PB", NXTFuzzyLogic.MIN);					//PB
   nfl.defineRule(new String[] {"greyleft","blackright"}, "MNA", NXTFuzzyLogic.MIN);				//MNA		left
   nfl.defineRule(new String[] {"greyleft","blackright"}, "PB", NXTFuzzyLogic.MIN);				//PB
   
   nfl.defineRule(new String[] {"blackleft","whiteright"}, "MNA", NXTFuzzyLogic.MIN);				//MNA		right
   nfl.defineRule(new String[] {"blackleft","whiteright"}, "MPB", NXTFuzzyLogic.MIN);				//MPB
   nfl.defineRule(new String[] {"blackleft","greyright"}, "NA", NXTFuzzyLogic.MIN);				//MPA		
   nfl.defineRule(new String[] {"blackleft","greyright"}, "PB", NXTFuzzyLogic.MIN);				//MNB
   nfl.defineRule(new String[] {"blackleft","blackright"}, "VNA", NXTFuzzyLogic.MIN);				//PA
   nfl.defineRule(new String[] {"blackleft","blackright"}, "PB", NXTFuzzyLogic.MIN);				//PB
   //function that initialize defined set of rules*/
   nfl.init();
   LightSensor LightRight = new LightSensor(SensorPort.S1);
   ColorHTSensor LightLeft = new ColorHTSensor(SensorPort.S4);
   //Button.ENTER.waitForPress();
   while (!Button.ESCAPE.isPressed())
   {
       //nfl.setInputValue("LightLeft",LightLeft.getColorID());
       nfl.setInputValue("LightRight",LightRight.readValue());
       LCD.drawInt(LightRight.readValue(), 1, 0);
       if(LightLeft.getColorID()==7)
    	   nfl.setInputValue("LightRight",7);
       else
    	   nfl.setInputValue("LightRight",6);
       LCD.drawInt(LightLeft.getColorID(), 1, 1);
       nfl.evaluate();

       LCD.drawInt((int)nfl.getOutputValue("speedA"), 1, 3);
       LCD.drawInt((int)nfl.getOutputValue("speedB"), 1, 4);
       LCD.refresh();

       if (nfl.getOutputValue("speedA")<0 )
       {
	        mA.setPower(Math.abs((int)nfl.getOutputValue("speedA")));
	        mA.backward();
       }
       else
       {
           mA.setPower(Math.abs((int)nfl.getOutputValue("speedA")));
           mA.forward();
       }

       if (nfl.getOutputValue("speedB")<0)
       {
	        mB.setPower(Math.abs((int)nfl.getOutputValue("speedB")));
	        mB.backward();
       }
       else
       {
           mB.setPower(Math.abs((int)nfl.getOutputValue("speedB")));
           mB.forward();
       }
       
       
   }   
}
}