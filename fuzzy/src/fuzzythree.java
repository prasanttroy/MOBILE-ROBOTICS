 import nxtfuzzylogic.*;
 import lejos.nxt.*;
 public class fuzzythree 
 {
public static void main(String[] args) 
{ 
	NXTFuzzyLogic nfl = new NXTFuzzyLogic();
	NXTMotor mA = new NXTMotor(MotorPort.A);
	NXTMotor mB = new NXTMotor(MotorPort.B);
	BlackWhiteSensor bws =new BlackWhiteSensor(SensorPort.S1);
	Button.ENTER.waitForPress();
	int b,w;
	bws.calibrate();
	int th=bws.Threshold;
	b=bws.blackLightValue;
	w=bws.whiteLightValue;
	
	//definition of 2 input linguistic variable - ambient light which is going to be get from light sensor
    nfl.defineInputLinguisticVariable("LightLeft", 0, 100, 0);
    nfl.defineInputLinguisticVariable("LightRight", 0, 100, 0);

  //definition of 2 output linguistic variable - speed of motor A and B
    nfl.defineOutputLinguisticVariable("speedA", -85, 85, 20, NXTFuzzyLogic.MIN);//setting motor speed range 	//MIN
    nfl.defineOutputLinguisticVariable("speedB", -85, 85, 20, NXTFuzzyLogic.MIN);//setting motor speed range		//MIN
 
    
    //left sensor
   // nfl.defineTermZType("blackleft", "LightLeft", 25, 39);
    nfl.defineTermZType("blackleft", "LightLeft",b,th-4);
    nfl.defineTermTriangular("greyleft", "LightLeft",th-6 ,th, th+6);
    nfl.defineTermSType("whiteleft", "LightLeft",th+4, w);
   // nfl.defineTermSType("whiteleft", "LightLeft", 47, 56);
    
    //right sensor    
   // nfl.defineTermZType("blackright", "LightRight", 25, 39);
    nfl.defineTermZType("blackright", "LightRight",b,th-4);
    nfl.defineTermTriangular("greyright", "LightRight",th-6 ,th, th+6);
    nfl.defineTermSType("whiteright", "LightRight",th+4, w);
   // nfl.defineTermSType("whiteright", "LightRight", 47, 56);
  
    //motor A
    nfl.defineTermZType("MNA", "speedA", -71, -65);
    nfl.defineTermTriangular("NA","speedA", -68,-54,-40);
    nfl.defineTermTriangular("LNA","speedA", -46, -23, 0);
    nfl.defineTermTriangular("LPA", "speedA", 0, 23, 46);
    nfl.defineTermTriangular("PA","speedA", 40, 54, 68);
    nfl.defineTermSType("MPA","speedA", 65,71);
    
    //motor B
    nfl.defineTermZType("MNB", "speedB", -71, -65);
    nfl.defineTermTriangular("NB","speedB", -68,-54,-40);
    nfl.defineTermTriangular("LNB","speedB", -46, -23, 0);
    nfl.defineTermTriangular("LPB", "speedB", 0, 23, 46);
    nfl.defineTermTriangular("PB","speedB", 40, 54, 68);
    nfl.defineTermSType("MPB","speedB", 65,71);
  
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
    nfl.defineRule(new String[] {"greyleft","blackright"}, "LNA", NXTFuzzyLogic.MIN);				//MNA		left
    nfl.defineRule(new String[] {"greyleft","blackright"}, "LPB", NXTFuzzyLogic.MIN);				//PB
    
    nfl.defineRule(new String[] {"blackleft","whiteright"}, "MNA", NXTFuzzyLogic.MIN);				//MNA		right
    nfl.defineRule(new String[] {"blackleft","whiteright"}, "MPB", NXTFuzzyLogic.MIN);				//MPB
    nfl.defineRule(new String[] {"blackleft","greyright"}, "NA", NXTFuzzyLogic.MIN);				//MPA		
    nfl.defineRule(new String[] {"blackleft","greyright"}, "PB", NXTFuzzyLogic.MIN);				//MNB
    nfl.defineRule(new String[] {"blackleft","blackright"}, "MNA", NXTFuzzyLogic.MIN);				//PA
    nfl.defineRule(new String[] {"blackleft","blackright"}, "MPB", NXTFuzzyLogic.MIN);				//PB
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