import nxtfuzzylogic.*;
import lejos.nxt.*;

public class fuzzy_single {

	public static void main(String[] args) 
	{ 
		NXTFuzzyLogic nfl = new NXTFuzzyLogic();
		NXTMotor mA = new NXTMotor(MotorPort.B);
		NXTMotor mB = new NXTMotor(MotorPort.C);
		//NXTMotor mC = new NXTMotor(MotorPort.C);
		BlackWhiteSensor bws =new BlackWhiteSensor(SensorPort.S1);
		bws.calibrate();
		Button.ENTER.waitForPress();
		int b,w,diff1,diff2, bdel,gdel,bg,wg;
		int th=bws.Threshold;
		b=bws.blackLightValue;
		w=bws.whiteLightValue;
		diff1=w-b;
		diff2=(w-b)/4;
		gdel=(int) (diff2*.35);
		bdel=(int) (diff2*.35);
		bg=(b+th)/2;
		wg=(w+th)/2;
		
		//definition of 2 input linguistic variable - ambient light which is going to be get from light sensor
		   nfl.defineInputLinguisticVariable("LightLeft", 0, 100, 0);
		 //  nfl.defineInputLinguisticVariable("LightRight", 0, 100, 0);

		 //definition of 2 output linguistic variable - speed of motor A and B
		   nfl.defineOutputLinguisticVariable("speedA", -80, 80, 45, NXTFuzzyLogic.MIN);//setting motor speed range 	//MIN
		   nfl.defineOutputLinguisticVariable("speedB", -80, 80, 25, NXTFuzzyLogic.MIN);//setting motor speed range		//MIN

		   
		   nfl.defineTermZType("blackleft", "LightLeft",b,b+bdel);
		   nfl.defineTermTriangular("blackgreyleft", "LightLeft",bg-gdel,bg,bg+gdel);//b,th-4);
		   nfl.defineTermTriangular("greyleft", "LightLeft",th-gdel,th,th+gdel);//th-6 ,th, th+6);
		   nfl.defineTermTriangular("whitegreyleft", "LightLeft",wg-gdel,wg,wg+gdel);//th+4, w);
		   nfl.defineTermSType("whiteleft", "LightLeft", w-bdel,w);
		 //motor A
		   nfl.defineTermZType("VNA", "speedA", -80, -71);
		   nfl.defineTermTriangular("MNA","speedA", -70,-60,-50);
		   nfl.defineTermTriangular("NA","speedA", -59,-44,-37);
		   nfl.defineTermTriangular("LNA","speedA", -40, -20, 0);
		   nfl.defineTermTriangular("LPA", "speedA", 0, 20, 40);
		   nfl.defineTermTriangular("PA","speedA", 37, 44, 59);
		   nfl.defineTermTriangular("MPA","speedA", 50, 60, 70);
		   nfl.defineTermSType("VPA","speedA", 71,80);
		   
		   //motor B
		   nfl.defineTermZType("VNB", "speedB", -80, -71);
		   nfl.defineTermTriangular("MNB","speedB", -70,-60,-50);
		   nfl.defineTermTriangular("NB","speedB", -59,-44,-37);
		   nfl.defineTermTriangular("LNB","speedB",-40, -20, 0);
		   nfl.defineTermTriangular("LPB", "speedB", 0, 20, 40);
		   nfl.defineTermTriangular("PB","speedB",  37, 44, 59);
		   nfl.defineTermTriangular("MPB","speedB", 50, 60, 70);
		   nfl.defineTermSType("VPB","speedB",  71,80);
		 
		   
		   nfl.defineRule(new String[] {"whiteleft"}, "MPA", NXTFuzzyLogic.MIN);				
		   nfl.defineRule(new String[] {"whiteleft"}, "MNB", NXTFuzzyLogic.MIN);
		   nfl.defineRule(new String[] {"whitegreyleft"}, "MPA", NXTFuzzyLogic.MIN);				
		   nfl.defineRule(new String[] {"whitegreyleft"}, "NB", NXTFuzzyLogic.MIN);
		   nfl.defineRule(new String[] {"greyleft"}, "MPA", NXTFuzzyLogic.MIN);				
		   nfl.defineRule(new String[] {"greyleft"}, "MPB", NXTFuzzyLogic.MIN);
		   nfl.defineRule(new String[] {"blackgreyleft"}, "NA", NXTFuzzyLogic.MIN);				
		   nfl.defineRule(new String[] {"blackgreyleft"}, "MPB", NXTFuzzyLogic.MIN);
		   nfl.defineRule(new String[] {"blackleft"}, "MNA", NXTFuzzyLogic.MIN);				
		   nfl.defineRule(new String[] {"blackleft"}, "MPB", NXTFuzzyLogic.MIN);
		   nfl.init();
		   LightSensor LightLeft = new LightSensor(SensorPort.S1, true);
		   while (!Button.ESCAPE.isPressed())
		   {
		       nfl.setInputValue("LightLeft",LightLeft.readValue());
		       LCD.drawInt(LightLeft.readValue(), 1, 0);
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
