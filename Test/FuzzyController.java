 import nxtfuzzylogic.*;
 import lejos.nxt.*;
 public class FuzzyController {
public static void main(String[] args) 
{ NXTFuzzyLogic nfl = new NXTFuzzyLogic();
//definition of input linguistic variable - ambient light which is going to be get from light sensor
    nfl.defineInputLinguisticVariable("AmbientLight", 0, 100, 0);

    //definition of 2 output linguistic variable - speed of motor A and B
    nfl.defineOutputLinguisticVariable("speedA", -300, 300, 0, NXTFuzzyLogic.COG);
    nfl.defineOutputLinguisticVariable("speedB", -300, 300, 0, NXTFuzzyLogic.COG);

    //definition of terms that describes AmbientLight variable
    nfl.defineTermTrapezoidal("grey", "AmbientLight", 40, 42, 48, 50);
    nfl.defineTermTriangular("black", "AmbientLight",32 ,38, 42);
    nfl.defineTermTriangular("white", "AmbientLight",48 ,51, 54);
    nfl.defineTermSType("very white", "AmbientLight", 53, 55);
    nfl.defineTermTrapezoidal("very black", "AmbientLight", 0, 0, 30, 34);

    //definition of terms that describes speed of Motor A
    nfl.defineTermTriangular("fastA", "speedA", 200, 220, 250);
    nfl.defineTermTriangular("slowA", "speedA", 60, 140, 220);
    nfl.defineTermTrapezoidal("veryslowA", "speedA", 0, 5, 5, 10);
    nfl.defineTermSType("veryfastA", "speedA", 220, 250);

    //definition of terms that describes speed of Motor B
    nfl.defineTermTriangular("fastB", "speedB", 200, 220, 250);
    nfl.defineTermTriangular("slowB", "speedB", 60, 140, 220);
    nfl.defineTermTrapezoidal("veryslowB", "speedB", 0, 5, 5, 10);
    nfl.defineTermSType("veryfastB", "speedB", 220, 250);

    //definition of rules
    nfl.defineRule(new String[] {"grey"},"veryfastA", NXTFuzzyLogic.MIN);
    nfl.defineRule(new String[] {"grey"},"veryfastB", NXTFuzzyLogic.MIN);
    nfl.defineRule(new String[] {"black"},"fastA", NXTFuzzyLogic.MIN);
    nfl.defineRule(new String[] {"black"},"slowB", NXTFuzzyLogic.MIN);
    nfl.defineRule(new String[] {"white"},"slowA", NXTFuzzyLogic.MIN);
    nfl.defineRule(new String[] {"white"},"fastB", NXTFuzzyLogic.MIN);
    nfl.defineRule(new String[] {"very white"},"veryslowA", NXTFuzzyLogic.MIN);
    nfl.defineRule(new String[] {"very white"},"veryfastB", NXTFuzzyLogic.MIN);
    nfl.defineRule(new String[] {"very black"},"veryslowB", NXTFuzzyLogic.MIN);
    nfl.defineRule(new String[] {"very black"},"veryfastA", NXTFuzzyLogic.MIN);
    //function that initialize defined set of rules
    nfl.init();

    LightSensor ambientLight = new LightSensor(SensorPort.S1, true);
    while (true)
    {
        nfl.setInputValue("AmbientLight", ambientLight.readValue());
        LCD.drawInt(ambientLight.readValue(), 1, 0);
        nfl.evaluate();

        LCD.drawInt((int)nfl.getOutputValue("speedA"), 1, 1);
        LCD.drawInt((int)nfl.getOutputValue("speedB"), 1, 2);

        if (nfl.getOutputValue("speedA")<0)
        {
        Motor.A.setSpeed(Math.abs((int)nfl.getOutputValue("speedA")));
        Motor.A.backward();
        }
        else
        {
            Motor.A.setSpeed(Math.abs((int)nfl.getOutputValue("speedA")));
            Motor.A.forward();
        }

        if (nfl.getOutputValue("speedB")<0)
        {
        Motor.B.setSpeed(Math.abs((int)nfl.getOutputValue("speedB")));
        Motor.B.backward();
        }
        else
        {
            Motor.B.setSpeed(Math.abs((int)nfl.getOutputValue("speedB")));
            Motor.B.forward();
        }

    }   
}
}