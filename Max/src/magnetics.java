import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.MotorPort;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.CompassHTSensor;
import lejos.util.Delay;

public class magnetics {
	
	CompassHTSensor cs = new CompassHTSensor(SensorPort.S3);
	 private static MotorPort leftMotor = MotorPort.B;
	 private static MotorPort rightMotor = MotorPort.C;
	 private int power=65;
	public void turn_left(int angle)
	{
		rightMotor.controlMotor(0,3);
        leftMotor.controlMotor(0,3);
        Delay.msDelay(400);
        cs.resetCartesianZero();
    float value;
    while(true)
	{
		value=cs.getDegreesCartesian();
		 if(value<=angle+6 && value>=angle-6 )break;
	      else if(value<angle)
	      {
	    	  rightMotor.controlMotor(power,1);
	          leftMotor.controlMotor(power+20,2);			    	  
	      }
	      else {
	    	  rightMotor.controlMotor(0,3);
	          leftMotor.controlMotor(0,3);	
		}
	     }
    rightMotor.controlMotor(0,3);
    leftMotor.controlMotor(0,3);
    Delay.msDelay(200);
	}
	public void turn_right(int angle)
	{
		rightMotor.controlMotor(0,3);
        leftMotor.controlMotor(0,3);
        Delay.msDelay(400);
        cs.resetCartesianZero();
        float value;
    while(true)
	{
		value=cs.getDegreesCartesian();
		 if(value<=angle+6 && value>=angle-6 )break;
	      else if(value<angle && value!=0)
	      {
	    	  rightMotor.controlMotor(0,3);
	          leftMotor.controlMotor(0,3);				    	  
	      }
	      else {
	    	  rightMotor.controlMotor(power+20,2);
	          leftMotor.controlMotor(power,1);	
		}
	}
    rightMotor.controlMotor(0,3);
    leftMotor.controlMotor(0,3);
    Delay.msDelay(200);
    
	}
}


