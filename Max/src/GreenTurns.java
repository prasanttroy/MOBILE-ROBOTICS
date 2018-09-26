import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.MotorPort;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorHTSensor;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;

public class GreenTurns implements Behavior {
	ColorHTSensor cls =new ColorHTSensor(SensorPort.S4);
	LightSensor ls= new LightSensor(SensorPort.S1);
	private static MotorPort leftMotor = MotorPort.B;
	 private static MotorPort rightMotor = MotorPort.C;
	magnetics ms2 = new magnetics();
	private boolean suppressed = false;
	
	public boolean takeControl() {
		return (cls.getColorID()==1);
	}

	public void action() {
		suppressed = false;
		LCD.clear();
		LCD.drawString("In green ..", 2, 3);
		if(Max3.green_flag==false)
		{
			LCD.drawString("before left", 2, 2);
			rightMotor.controlMotor(60,1);
       		leftMotor.controlMotor(60,1); 
       		Delay.msDelay(280);
			ms2.turn_left(80);
			Delay.msDelay(100);
			
			/*while(!(ls.readValue()<=37))
			{
				leftMotor.controlMotor(60, 2);
				rightMotor.controlMotor(70, 1);
				
				
			}*/
			Max3.green_flag=true;
		}
		else
		{	
			LCD.drawString("before right", 2, 2);
			rightMotor.controlMotor(60,1);
			leftMotor.controlMotor(60,1); 
			Delay.msDelay(280);
			ms2.turn_right(270);
			Delay.msDelay(100);
			/*while(!(ls.readValue()>48 && cls.getColorID()==7))
			{
				leftMotor.controlMotor(80, 1);
				rightMotor.controlMotor(55, 2);
				
			}*/
			Max3.green_flag=false;
		}
		while(cls.getColorID()==1)
		{
			rightMotor.controlMotor(60,1);
       		leftMotor.controlMotor(60,1); 
		}
		rightMotor.controlMotor(0,3);
   		leftMotor.controlMotor(0,3); 
   		Delay.msDelay(100);
		suppressed=true;
	}

	public void suppress() {
		suppressed=true;	
	}
}
