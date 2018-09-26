

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.MotorPort;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

public class LeftMove 
{
	private static MotorPort leftMotor = MotorPort.B;
    private static MotorPort rightMotor = MotorPort.C;
	public void left() throws InterruptedException
	{
		int distance=0;
		 int VERY_CLOSE_LEFT =     20;
	       int SOMEWHAT_CLOSE_LEFT = 24;
	       int NOT_THAT_CLOSE_LEFT = 30;
	       int SOMEWHAT_FAR_RIGHT =  36;
	       int VERY_FAR_RIGHT =      43;

	     int ASSIGNED_SPEED =      55;
	    int lmotor_speed = ASSIGNED_SPEED,
                lmotor_mode = 1,
                rmotor_speed = ASSIGNED_SPEED,
                rmotor_mode = 1;
         while(! Button.ESCAPE.isPressed())
         {
		 UltrasonicSensor us = new UltrasonicSensor(SensorPort.S2);
		 distance = us.getDistance();
		 if (distance <= NOT_THAT_CLOSE_LEFT)
	     {
	     	if (distance <= VERY_CLOSE_LEFT)
	         {
	             rmotor_speed = ASSIGNED_SPEED+5; rmotor_mode = 2;
	             lmotor_speed = ASSIGNED_SPEED-10; lmotor_mode = 1;//sharp right
	         }

	         else if (distance <= SOMEWHAT_CLOSE_LEFT)
	         {
	             rmotor_speed = ASSIGNED_SPEED-20; rmotor_mode = 2; 
	             lmotor_speed = ASSIGNED_SPEED-25; lmotor_mode = 1;// right
	         }
	         else if (distance >SOMEWHAT_CLOSE_LEFT)
	         {
	         	rmotor_speed = ASSIGNED_SPEED+10; rmotor_mode = 1;
	             lmotor_speed = ASSIGNED_SPEED+10; lmotor_mode = 1;//slow forward
	         }
	     }
	     else
	     {
	     	/*if (distance >= VERY_FAR_RIGHT)
	         {
	             rmotor_speed = ASSIGNED_SPEED-15; rmotor_mode = 1;
	             lmotor_speed = 0; lmotor_mode = 3;//sharp left
	         }

	         else if (distance >= SOMEWHAT_FAR_RIGHT)
	         {
	             rmotor_speed = ASSIGNED_SPEED-25; rmotor_mode = 1; 
	             lmotor_speed = ASSIGNED_SPEED-20; lmotor_mode = 2;// left
	         }
	         else if (distance <SOMEWHAT_FAR_RIGHT)
	         {
	         	rmotor_speed = ASSIGNED_SPEED+10; rmotor_mode = 1;
	             lmotor_speed = ASSIGNED_SPEED+10; lmotor_mode = 1;//slow forward
	         }*/
	     	 rmotor_speed = ASSIGNED_SPEED+5; rmotor_mode = 1; 
	          lmotor_speed = ASSIGNED_SPEED-35; lmotor_mode = 1;// left
	     }
		 LCD.drawString("Distance: "+distance+"   ", 0, 1);
         LCD.drawString("L:"+lmotor_speed+"@"+lmotor_mode+", R:"+
                 rmotor_speed+"@"+rmotor_mode+".", 0, 3);
         rightMotor.controlMotor(rmotor_speed,rmotor_mode);
         leftMotor.controlMotor(lmotor_speed,lmotor_mode);
         Thread.sleep(300);
         }

		 
	}
	public void right() throws InterruptedException
	{
		int distance=0;
		 int VERY_CLOSE_LEFT =     20;
	       int SOMEWHAT_CLOSE_LEFT = 24;
	       int NOT_THAT_CLOSE_LEFT = 30;
	       int SOMEWHAT_FAR_RIGHT =  36;
	       int VERY_FAR_RIGHT =      43;

	     int ASSIGNED_SPEED =      55;
	    int lmotor_speed = ASSIGNED_SPEED,
                lmotor_mode = 1,
                rmotor_speed = ASSIGNED_SPEED,
                rmotor_mode = 1;
          while(! Button.ESCAPE.isPressed())
          {
		 UltrasonicSensor us = new UltrasonicSensor(SensorPort.S2);
		 distance = us.getDistance();
		 if (distance <= NOT_THAT_CLOSE_LEFT)
	     {
	     	if (distance <= VERY_CLOSE_LEFT)
	         {
	             rmotor_speed = ASSIGNED_SPEED-10; rmotor_mode = 1;
	             lmotor_speed = ASSIGNED_SPEED+5; lmotor_mode = 2;//sharp right
	         }

	         else if (distance <= SOMEWHAT_CLOSE_LEFT)
	         {
	             rmotor_speed = ASSIGNED_SPEED-25; rmotor_mode = 1; 
	             lmotor_speed = ASSIGNED_SPEED-20; lmotor_mode = 2;// right
	         }
	         else if (distance >SOMEWHAT_CLOSE_LEFT)
	         {
	         	rmotor_speed = ASSIGNED_SPEED+10; rmotor_mode = 1;
	             lmotor_speed = ASSIGNED_SPEED+10; lmotor_mode = 1;//slow forward
	         }
	     }
	     else
	     {
	     	/*if (distance >= VERY_FAR_RIGHT)
	         {
	             rmotor_speed = ASSIGNED_SPEED-15; rmotor_mode = 1;
	             lmotor_speed = 0; lmotor_mode = 3;//sharp left
	         }

	         else if (distance >= SOMEWHAT_FAR_RIGHT)
	         {
	             rmotor_speed = ASSIGNED_SPEED-25; rmotor_mode = 1; 
	             lmotor_speed = ASSIGNED_SPEED-20; lmotor_mode = 2;// left
	         }
	         else if (distance <SOMEWHAT_FAR_RIGHT)
	         {
	         	rmotor_speed = ASSIGNED_SPEED+10; rmotor_mode = 1;
	             lmotor_speed = ASSIGNED_SPEED+10; lmotor_mode = 1;//slow forward
	         }*/
	     	 rmotor_speed = ASSIGNED_SPEED-35; rmotor_mode = 1; 
	          lmotor_speed = ASSIGNED_SPEED+5; lmotor_mode = 1;// left
	     }
		 LCD.drawString("Distance: "+distance+"   ", 0, 1);
         LCD.drawString("L:"+lmotor_speed+"@"+lmotor_mode+", R:"+
                 rmotor_speed+"@"+rmotor_mode+".", 0, 3);
         rightMotor.controlMotor(rmotor_speed,rmotor_mode);
         leftMotor.controlMotor(lmotor_speed,lmotor_mode);
         Thread.sleep(300);
          }
	}


}
