 import nxtfuzzylogic.*;
 import lejos.nxt.*;
 public class FuzzyController {
public static void main(String[] args) 
{ 
	NXTFuzzyLogic nfl = new NXTFuzzyLogic();
	NXTMotor mA = new NXTMotor(MotorPort.C);
	NXTMotor mB = new NXTMotor(MotorPort.B);
	//definition of 2 input linguistic variable - ambient light which is going to be get from light sensor
    nfl.defineInputLinguisticVariable("LightLeft", 0, 100, 0);
    nfl.defineInputLinguisticVariable("LightRight", 0, 100, 0);

    //definition of 2 output linguistic variable - speed of motor A and B
    nfl.defineOutputLinguisticVariable("speedA", -80, 60, 50, NXTFuzzyLogic.COG);//setting motor speed range
    nfl.defineOutputLinguisticVariable("speedB", -80, 60, 50, NXTFuzzyLogic.COG);//setting motor speed range
 
    
    //left sensor
   // nfl.defineTermZType("blackleft", "LightLeft", 25, 39);
    nfl.defineTermTriangular("blackleft", "LightLeft",25,32,39);
    nfl.defineTermTriangular("greyleft", "LightLeft",34 ,43, 51);
    nfl.defineTermTriangular("whiteleft", "LightLeft",47 ,52, 60);
   // nfl.defineTermSType("whiteleft", "LightLeft", 47, 56);
    
    //right sensor    
   // nfl.defineTermZType("blackright", "LightRight", 25, 39);
    nfl.defineTermTriangular("blackright", "LightRight",25,32,39);
    nfl.defineTermTriangular("greyright", "LightRight",34 ,43, 51);
    nfl.defineTermTriangular("whiteright", "LightRight",47 ,52, 60);
   // nfl.defineTermSType("whiteright", "LightRight", 47, 56);
  
    //motor A
    nfl.defineTermZType("MNA", "speedA", -80, -45);
    nfl.defineTermTriangular("NA","speedA", -52,-34,-25);
    nfl.defineTermTriangular("ZA","speedA", -25, 20, 25);
    nfl.defineTermTriangular("PA","speedA", 25, 34, 52);
    nfl.defineTermSType("MPA","speedA", 45,55);
    
    //motor B
    nfl.defineTermZType("MNB", "speedB", -80, -45);
    nfl.defineTermTriangular("NB","speedB",  -52,-34,-25);
    nfl.defineTermTriangular("ZB","speedB", -25, 20, 25);
    nfl.defineTermTriangular("PB","speedB", 25, 34, 52);
    nfl.defineTermSType("MPB","speedB", 45,55);
  

    //definition of rules
   
    nfl.defineRule(new String[] {"whiteleft","whiteright"}, "MPA", NXTFuzzyLogic.COG);
    nfl.defineRule(new String[] {"whiteleft","whiteright"}, "MNB", NXTFuzzyLogic.COG);
    nfl.defineRule(new String[] {"whiteleft","greyright"}, "PA", NXTFuzzyLogic.COG);
    nfl.defineRule(new String[] {"whiteleft","greyright"}, "MNB", NXTFuzzyLogic.COG);
    nfl.defineRule(new String[] {"whiteleft","blackright"}, "MPA", NXTFuzzyLogic.COG);
    nfl.defineRule(new String[] {"whiteleft","blackright"}, "MPB", NXTFuzzyLogic.COG);
    
    nfl.defineRule(new String[] {"greyleft","whiteright"}, "PA", NXTFuzzyLogic.COG);
    nfl.defineRule(new String[] {"greyleft","whiteright"}, "NB", NXTFuzzyLogic.COG);
    nfl.defineRule(new String[] {"greyleft","greyright"}, "PA", NXTFuzzyLogic.COG);
    nfl.defineRule(new String[] {"greyleft","greyright"}, "PB", NXTFuzzyLogic.COG);
    nfl.defineRule(new String[] {"greyleft","blackright"}, "NA", NXTFuzzyLogic.COG);
    nfl.defineRule(new String[] {"greyleft","blackright"}, "PB", NXTFuzzyLogic.COG);
    
    nfl.defineRule(new String[] {"blackleft","whiteright"}, "PA", NXTFuzzyLogic.COG);
    nfl.defineRule(new String[] {"blackleft","whiteright"}, "MNB", NXTFuzzyLogic.COG);
    nfl.defineRule(new String[] {"blackleft","greyright"}, "PA", NXTFuzzyLogic.COG);
    nfl.defineRule(new String[] {"blackleft","greyright"}, "NB", NXTFuzzyLogic.COG);
    nfl.defineRule(new String[] {"blackleft","blackright"}, "MNA", NXTFuzzyLogic.COG);
    nfl.defineRule(new String[] {"blackleft","blackright"}, "MPB", NXTFuzzyLogic.COG);
    //function that initialize defined set of rules
    nfl.init();

    LightSensor LightLeft = new LightSensor(SensorPort.S1, true);
    LightSensor LightRight = new LightSensor(SensorPort.S2, true);
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