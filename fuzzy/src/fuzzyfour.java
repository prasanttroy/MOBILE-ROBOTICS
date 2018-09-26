
import nxtfuzzylogic.*;
import lejos.nxt.*;
public class fuzzyfour {

public static void main(String[] args) 
{ 
	NXTFuzzyLogic nfl = new NXTFuzzyLogic();
	NXTMotor mA = new NXTMotor(MotorPort.A);
	NXTMotor mB = new NXTMotor(MotorPort.B);
	BlackWhiteSensor bws =new BlackWhiteSensor(SensorPort.S1);
	int b,w,diff, bdel,gdel;
	bws.calibrate();
	int th=bws.Threshold;
	b=bws.blackLightValue;
	w=bws.whiteLightValue;
	diff=w-b;
	gdel=(int) (diff*.4);
	bdel=(int) (diff*.35);
	
	
	
	
	//definition of 2 input linguistic variable - ambient light which is going to be get from light sensor
   nfl.defineInputLinguisticVariable("LightLeft", 0, 100, 0);
   nfl.defineInputLinguisticVariable("LightRight", 0, 100, 0);

 //definition of 2 output linguistic variable - speed of motor A and B
   nfl.defineOutputLinguisticVariable("speedA", -76, 76, 20, NXTFuzzyLogic.MIN);//setting motor speed range 	//MIN
   nfl.defineOutputLinguisticVariable("speedB", -76, 76, 20, NXTFuzzyLogic.MIN);//setting motor speed range		//MIN

   
   //left sensor
  // nfl.defineTermZType("blackleft", "LightLeft", 25, 39);
   nfl.defineTermZType("blackleft", "LightLeft",b,b+bdel);//b,th-4);
   nfl.defineTermTriangular("greyleft", "LightLeft",th-gdel,th,th+gdel);//th-6 ,th, th+6);
   nfl.defineTermSType("whiteleft", "LightLeft",w-bdel,w);//th+4, w);
  // nfl.defineTermSType("whiteleft", "LightLeft", 47, 56);
   
   //right sensor    
  // nfl.defineTermZType("blackright", "LightRight", 25, 39);
   nfl.defineTermZType("blackright", "LightRight",b,b+bdel);
   nfl.defineTermTriangular("greyright", "LightRight",th-gdel,th,th+gdel);
   nfl.defineTermSType("whiteright", "LightRight",w-bdel,w);
  // nfl.defineTermSType("whiteright", "LightRight", 47, 56);
 
   //motor A
   nfl.defineTermZType("VNA", "speedA", -75, -65);
   nfl.defineTermTriangular("MNA","speedA", -70,-60,-50);
   nfl.defineTermTriangular("NA","speedA", -55,-45,-35);
   nfl.defineTermTriangular("LNA","speedA", -40, -20, 20);
   nfl.defineTermTriangular("LPA", "speedA", -20, 20, 40);
   nfl.defineTermTriangular("PA","speedA", 35, 45, 55);
   nfl.defineTermTriangular("MPA","speedA", 50, 60, 70);
   nfl.defineTermSType("VPA","speedA", 65,75);
   
   //motor B
   nfl.defineTermZType("VNB", "speedB", -75, -65);
   nfl.defineTermTriangular("MNB","speedB", -70,-60,-50);
   nfl.defineTermTriangular("NB","speedB", -55,-45,-35);
   nfl.defineTermTriangular("LNB","speedB",-40, -20, 20);
   nfl.defineTermTriangular("LPB", "speedB", -20, 20, 40);
   nfl.defineTermTriangular("PB","speedB",  35, 45, 55);
   nfl.defineTermTriangular("MPB","speedB", 50, 60, 70);
   nfl.defineTermSType("VPB","speedB",  65,75);
 
   //definition of rules
   nfl.defineRule(new String[] {"whiteleft","whiteright"}, "MPA", NXTFuzzyLogic.MIN);				//MPA --straight
   nfl.defineRule(new String[] {"whiteleft","whiteright"}, "MNB", NXTFuzzyLogic.MIN);				//MPB
   nfl.defineRule(new String[] {"whiteleft","greyright"}, "PA", NXTFuzzyLogic.MIN);				//PA   right
   nfl.defineRule(new String[] {"whiteleft","greyright"}, "NB", NXTFuzzyLogic.MIN);				//MNB
   nfl.defineRule(new String[] {"whiteleft","blackright"}, "MPA", NXTFuzzyLogic.MIN);				//MPA	right
   nfl.defineRule(new String[] {"whiteleft","blackright"}, "MPB", NXTFuzzyLogic.MIN);				//MNB
   
   nfl.defineRule(new String[] {"greyleft","whiteright"}, "NA", NXTFuzzyLogic.MIN);				//MNA	left
   nfl.defineRule(new String[] {"greyleft","whiteright"}, "PB", NXTFuzzyLogic.MIN);				//PB
   nfl.defineRule(new String[] {"greyleft","greyright"}, "NA", NXTFuzzyLogic.MIN);   				//PA		
   nfl.defineRule(new String[] {"greyleft","greyright"}, "PB", NXTFuzzyLogic.MIN);					//PB
   nfl.defineRule(new String[] {"greyleft","blackright"}, "NA", NXTFuzzyLogic.MIN);				//MNA		left
   nfl.defineRule(new String[] {"greyleft","blackright"}, "LPB", NXTFuzzyLogic.MIN);				//PB
   
   nfl.defineRule(new String[] {"blackleft","whiteright"}, "MNA", NXTFuzzyLogic.MIN);				//MNA		right
   nfl.defineRule(new String[] {"blackleft","whiteright"}, "MPB", NXTFuzzyLogic.MIN);				//MPB
   nfl.defineRule(new String[] {"blackleft","greyright"}, "NA", NXTFuzzyLogic.MIN);				//MPA		
   nfl.defineRule(new String[] {"blackleft","greyright"}, "PB", NXTFuzzyLogic.MIN);				//MNB
   nfl.defineRule(new String[] {"blackleft","blackright"}, "MNA", NXTFuzzyLogic.MIN);				//PA
   nfl.defineRule(new String[] {"blackleft","blackright"}, "PB", NXTFuzzyLogic.MIN);				//PB
   //function that initialize defined set of rules
   nfl.init();
   LightSensor LightLeft = new LightSensor(SensorPort.S1, true);
   LightSensor LightRight = new LightSensor(SensorPort.S2, true);
   Button.ENTER.waitForPress();
   while (!Button.ESCAPE.isPressed())
   {
       nfl.setInputValue("LightLeft",LightLeft.readValue());
       LCD.drawInt(LightLeft.readValue(), 1, 0);
       nfl.setInputValue("LightRight",LightRight.readValue());
       LCD.drawInt(LightRight.readValue(), 1, 1);
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