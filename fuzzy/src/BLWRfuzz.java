import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTMotor;
import lejos.nxt.SensorPort;
import nxtfuzzylogic.NXTFuzzyLogic;
import nxtfuzzylogic.*;
import lejos.nxt.*;
public class BLWRfuzz {
	public static void main(String[] args) 
	{ 
		NXTFuzzyLogic nfl = new NXTFuzzyLogic();
		NXTMotor mA = new NXTMotor(MotorPort.A);
		NXTMotor mB = new NXTMotor(MotorPort.B);
		//definition of 2 input linguistic variable - ambient light which is going to be get from light sensor
	    nfl.defineInputLinguisticVariable("LightLeft", 0, 100, 0);
	    nfl.defineInputLinguisticVariable("LightRight", 0, 100, 0);

	    //definition of 2 output linguistic variable - speed of motor A and B
	    nfl.defineOutputLinguisticVariable("speedA", -80, 75, 50, NXTFuzzyLogic.COG);//setting motor speed range
	    nfl.defineOutputLinguisticVariable("speedB", -80, 75, 50, NXTFuzzyLogic.COG);//setting motor speed range
	 
	    
	    //left sensor
	   // nfl.defineTermZType("blackleft", "LightLeft", 25, 39);
	    nfl.defineTermZType("blackleft", "LightLeft",32,43);
	    nfl.defineTermTriangular("greyleft", "LightLeft",38 ,43, 53);
	    nfl.defineTermSType("whiteleft", "LightLeft",45 ,55);
	   // nfl.defineTermSType("whiteleft", "LightLeft", 47, 56);
	    
	    //right sensor    
	   // nfl.defineTermZType("blackright", "LightRight", 25, 39);
	    nfl.defineTermZType("blackright", "LightRight",32,43);
	    nfl.defineTermTriangular("greyright", "LightRight",38 ,45, 53);
	    nfl.defineTermSType("whiteright", "LightRight",47 ,55);
	   // nfl.defineTermSType("whiteright", "LightRight", 47, 56);
	  
	    //motor A
	    nfl.defineTermZType("MNA", "speedA", -70, -55);
	    nfl.defineTermTriangular("NA","speedA", -60,-45,-35);
	    nfl.defineTermTriangular("LNA","speedA", -40, -20, -10);
	    nfl.defineTermTriangular("LPA","speedA", 10, 20, 40);
	    nfl.defineTermTriangular("PA","speedA", 35, 45, 60);
	    nfl.defineTermSType("MPA","speedA", 55,70);
	    
	    //motor B
	    nfl.defineTermZType("MNB", "speedB", -70, -45);
	    nfl.defineTermTriangular("NB","speedB",  -60,-45,-35);
	    nfl.defineTermTriangular("LNB","speedB", -40, -20, -10);
	    nfl.defineTermTriangular("LPB","speedB", 10, 20, 40);
	    nfl.defineTermTriangular("PB","speedB", 35, 45, 60);
	    nfl.defineTermSType("MPB","speedB", 55,70);
	  

	    //definition of rules
	   
	    nfl.defineRule(new String[] {"whiteleft","whiteright"}, "MPA", NXTFuzzyLogic.COG);
	    nfl.defineRule(new String[] {"whiteleft","whiteright"}, "MNB", NXTFuzzyLogic.COG);
	    nfl.defineRule(new String[] {"whiteleft","greyright"}, "PA", NXTFuzzyLogic.COG);
	    nfl.defineRule(new String[] {"whiteleft","greyright"}, "NB", NXTFuzzyLogic.COG);
	    nfl.defineRule(new String[] {"whiteleft","blackright"}, "MPA", NXTFuzzyLogic.COG);
	    nfl.defineRule(new String[] {"whiteleft","blackright"}, "MPB", NXTFuzzyLogic.COG);
	    
	    nfl.defineRule(new String[] {"greyleft","whiteright"}, "NA", NXTFuzzyLogic.COG);
	    nfl.defineRule(new String[] {"greyleft","whiteright"}, "PB", NXTFuzzyLogic.COG);
	    nfl.defineRule(new String[] {"greyleft","greyright"}, "NA", NXTFuzzyLogic.COG);
	    nfl.defineRule(new String[] {"greyleft","greyright"}, "PB", NXTFuzzyLogic.COG);
	    nfl.defineRule(new String[] {"greyleft","blackright"}, "LNA", NXTFuzzyLogic.COG);
	    nfl.defineRule(new String[] {"greyleft","blackright"}, "LPB", NXTFuzzyLogic.COG);
	    
	    nfl.defineRule(new String[] {"blackleft","whiteright"}, "MNA", NXTFuzzyLogic.COG);
	    nfl.defineRule(new String[] {"blackleft","whiteright"}, "MPB", NXTFuzzyLogic.COG);
	    nfl.defineRule(new String[] {"blackleft","greyright"}, "NA", NXTFuzzyLogic.COG);
	    nfl.defineRule(new String[] {"blackleft","greyright"}, "PB", NXTFuzzyLogic.COG);
	    nfl.defineRule(new String[] {"blackleft","blackright"}, "MNA", NXTFuzzyLogic.COG);
	    nfl.defineRule(new String[] {"blackleft","blackright"}, "MPB", NXTFuzzyLogic.COG);
	    //function that initialize defined set of rules
	    nfl.init();

	    LightSensor LightLeft = new LightSensor(SensorPort.S1, true);
	    LightSensor LightRight = new LightSensor(SensorPort.S2, true);
	    Button.ENTER.waitForPress();
	    while (true)
	    {
	        nfl.setInputValue("LightLeft",LightLeft.readValue());
	        LCD.drawInt(LightLeft.readValue(), 1, 0);
	        nfl.setInputValue("LightRight",LightRight.readValue());
	        LCD.drawInt(LightRight.readValue(), 1, 1);
	        nfl.evaluate();

	        LCD.drawInt((int)nfl.getOutputValue("speedA"), 1, 3);
	        LCD.drawInt((int)nfl.getOutputValue("speedB"), 1, 4);

	        if (nfl.getOutputValue("speedA")<0)
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
	        if(Button.ESCAPE.isPressed())
	        {
	        	break;
	        }
	    }   
	}
}
