package fuzzy;

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
    nfl.defineOutputLinguisticVariable("speedA", -80, 60, 0, NXTFuzzyLogic.MIN);//setting motor speed range
    nfl.defineOutputLinguisticVariable("speedB", -80, 60, 0, NXTFuzzyLogic.MIN);//setting motor speed range
 
    
    //left sensor
   // nfl.defineTermZType("blackleft", "LightLeft", 25, 39);
    nfl.defineTermZType("blackleft", "LightLeft",25,39);
    nfl.defineTermTriangular("greyleft", "LightLeft",34 ,43, 51);
    nfl.defineTermSType("whiteleft", "LightLeft",47, 60);
   // nfl.defineTermSType("whiteleft", "LightLeft", 47, 56);
    
    //right sensor    
   // nfl.defineTermZType("blackright", "LightRight", 25, 39);
    nfl.defineTermZType("blackright", "LightRight",25,39);
    nfl.defineTermTriangular("greyright", "LightRight",34 ,43, 51);
    nfl.defineTermSType("whiteright", "LightRight",47 , 60);
   // nfl.defineTermSType("whiteright", "LightRight", 47, 56);
  
    //motor A
    nfl.defineTermZType("MNA", "speedA", -85, -45);
    nfl.defineTermTriangular("NA","speedA", -52,-34,-25);
    nfl.defineTermTriangular("ZA","speedA", -25, 00, 25);
    nfl.defineTermTriangular("PA","speedA", 25, 40, 52);
    nfl.defineTermSType("MPA","speedA", 47,60);
    
    //motor B
    nfl.defineTermZType("MNB", "speedB", -85, -45);
    nfl.defineTermTriangular("NB","speedB",  -52,-34,-25);
    nfl.defineTermTriangular("ZB","speedB", -25, 00, 25);
    nfl.defineTermTriangular("PB","speedB", 25, 40, 52);
    nfl.defineTermSType("MPB","speedB", 47,60);
  
    //definition of rules
    nfl.defineRule(new String[] {"whiteleft","whiteright"}, "MPA", NXTFuzzyLogic.MIN);
    nfl.defineRule(new String[] {"whiteleft","whiteright"}, "MPB", NXTFuzzyLogic.MIN);
    nfl.defineRule(new String[] {"whiteleft","greyright"}, "PA", NXTFuzzyLogic.MIN);
    nfl.defineRule(new String[] {"whiteleft","greyright"}, "MNB", NXTFuzzyLogic.MIN);
    nfl.defineRule(new String[] {"whiteleft","blackright"}, "MPA", NXTFuzzyLogic.MIN);
    nfl.defineRule(new String[] {"whiteleft","blackright"}, "MNB", NXTFuzzyLogic.MIN);
    
    nfl.defineRule(new String[] {"greyleft","whiteright"}, "MNA", NXTFuzzyLogic.MIN);
    nfl.defineRule(new String[] {"greyleft","whiteright"}, "PB", NXTFuzzyLogic.MIN);
    nfl.defineRule(new String[] {"greyleft","greyright"}, "PA", NXTFuzzyLogic.MIN);
    nfl.defineRule(new String[] {"greyleft","greyright"}, "PB", NXTFuzzyLogic.MIN);
    nfl.defineRule(new String[] {"greyleft","blackright"}, "MNA", NXTFuzzyLogic.MIN);
    nfl.defineRule(new String[] {"greyleft","blackright"}, "PB", NXTFuzzyLogic.MIN);
    
    nfl.defineRule(new String[] {"blackleft","whiteright"}, "MNA", NXTFuzzyLogic.MIN);
    nfl.defineRule(new String[] {"blackleft","whiteright"}, "MPB", NXTFuzzyLogic.MIN);
    nfl.defineRule(new String[] {"blackleft","greyright"}, "MPA", NXTFuzzyLogic.MIN);
    nfl.defineRule(new String[] {"blackleft","greyright"}, "MNB", NXTFuzzyLogic.MIN);
    nfl.defineRule(new String[] {"blackleft","blackright"}, "PA", NXTFuzzyLogic.MIN);
    nfl.defineRule(new String[] {"blackleft","blackright"}, "PB", NXTFuzzyLogic.MIN);
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