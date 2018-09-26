package fuzzy;

import lejos.nxt.*;
import lejos.nxt.addon.CompassHTSensor;
public class wally
{
    private static final int VERY_CLOSE_LEFT =     20;
    private static final int SOMEWHAT_CLOSE_LEFT = 24;
    private static final int NOT_THAT_CLOSE_LEFT = 30;
    private static final int SOMEWHAT_FAR_RIGHT =  36;
    private static final int VERY_FAR_RIGHT =      43;
    
    
    

    private static final int ASSIGNED_SPEED =      55;
    private static MotorPort leftMotor = MotorPort.B;
    private static MotorPort rightMotor = MotorPort.C;
    public static void main (String[] aArg)
        throws Exception
        {
            UltrasonicSensor us = new UltrasonicSensor(SensorPort.S2);
            CompassHTSensor cs = new CompassHTSensor(SensorPort.S3); 
            LightSensor ls = new LightSensor(SensorPort.S1);
            
                int distance = 0,
                lmotor_speed = ASSIGNED_SPEED,
                lmotor_mode = 1,
                rmotor_speed = ASSIGNED_SPEED,
                rmotor_mode = 1;
                int sp=75;
                float value;
                int dis;
            Button.ENTER.waitForPress();
            //wall follower mode after getting blue!!
            dis=us.getDistance();
            while(dis>25)
            {
            	rightMotor.controlMotor(sp,1);
                leftMotor.controlMotor(sp,1);
                dis=us.getDistance();
            }
            rightMotor.controlMotor(0,3);
            leftMotor.controlMotor(0,3);
            cs.resetCartesianZero();
            int angle=270;
            while(true)
			{
				value=cs.getDegreesCartesian();
				 if(value==angle)break;
			      else if(value<angle && value!=0)
			      {
			    	  rightMotor.controlMotor(sp,1);
			          leftMotor.controlMotor(sp,2);			    	  
			      }
			      else {
			    	  rightMotor.controlMotor(sp,2);
			          leftMotor.controlMotor(sp,1);	
				}
			}
            
            //start following wall
            Motor.A.rotateTo(-54);

           while (ls.readValue()>34)
            {
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
                	if (distance >= VERY_FAR_RIGHT)
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
                    }
                }

                LCD.drawString("Distance: "+distance+"   ", 0, 1);
                LCD.drawString("L:"+lmotor_speed+"@"+lmotor_mode+", R:"+
                        rmotor_speed+"@"+rmotor_mode+".", 0, 3);
                rightMotor.controlMotor(rmotor_speed,rmotor_mode);
                leftMotor.controlMotor(lmotor_speed,lmotor_mode);
                Thread.sleep(300);
            }
           
           //get back to the line....
           rightMotor.controlMotor(0,3);
           leftMotor.controlMotor(0,3);
           
            Motor.A.rotateTo(0);
            cs.resetCartesianZero();
            angle=270;
            while(true)
			{
				value=cs.getDegreesCartesian();
				 if(value==angle)break;
			      else if(value<angle && value!=0)
			      {
			    	  rightMotor.controlMotor(sp,1);
			          leftMotor.controlMotor(sp,2);			    	  
			      }
			      else {
			    	  rightMotor.controlMotor(sp,2);
			          leftMotor.controlMotor(sp,1);	
				}
			}
            //now start following the line
           
            LCD.clear();
            LCD.drawString("Program stopped", 0, 0);
            LCD.refresh();
        }
}