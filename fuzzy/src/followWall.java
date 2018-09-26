import fuzzy.LeftMove;
import lejos.robotics.subsumption.*;
import lejos.nxt.*;
import lejos.util.Delay;

public class followWall implements Behavior
{
	
    private static final int VERY_CLOSE_LEFT =     20;
    private static final int SOMEWHAT_CLOSE_LEFT = 24;
    private static final int NOT_THAT_CLOSE_LEFT = 30;
    private static final int SOMEWHAT_FAR_RIGHT =  36;
    private static final int VERY_FAR_RIGHT =      43;
    private static final int ASSIGNED_SPEED =      55;
    private static MotorPort leftMotor = MotorPort.B;
    private static MotorPort rightMotor = MotorPort.C;
    UltrasonicSensor us = new UltrasonicSensor(SensorPort.S2);
    LeftMove lm=new LeftMove();
    private boolean suppressed = false;
    
    public followWall()
    {
    	
    }
    
    public boolean takeControl() {
		return (us.getDistance()<=25);
	}

	public void action() {
		while(!suppressed)
			//ob
		
	}

	
	public void suppress() {
		suppressed=true;
		
	}

   
    public void obj_avoidance()
        throws Exception
        {
                int distance = 0,
                lmotor_speed = ASSIGNED_SPEED,
                lmotor_mode = 1,
                rmotor_speed = ASSIGNED_SPEED,
                rmotor_mode = 1;
            
            Motor.A.rotateTo(-54);
            int dis_left=us.getDistance();
            Delay.msDelay(100);
            Motor.A.rotateTo(0);
            Delay.msDelay(100);
            Motor.A.rotateTo(54);
            Delay.msDelay(100);
            int dis_right=us.getDistance();
            if(dis_left>=dis_right)
            {
            	//go left;
            	Delay.msDelay(100);
                Motor.A.rotateTo(0);
                Delay.msDelay(100);
                Motor.A.rotateTo(-54);
            	int i=1;
            	while(true)
            	{
            		
            		rmotor_speed = ASSIGNED_SPEED-10; rmotor_mode = 1;
                    lmotor_speed = ASSIGNED_SPEED+5; lmotor_mode = 2;//sharp left
            		Delay.msDelay(5);
            		i++;
            		if(i==100)
            		{
            			break;
            		}
            	}
            	lm.left();
            }
            else{
            	Delay.msDelay(100);
                Motor.A.rotateTo(0);
                Delay.msDelay(100);
                Motor.A.rotateTo(54);
            	int i=1;
            	while(true)
            	{
            		
            		rmotor_speed = ASSIGNED_SPEED+5; rmotor_mode = 2;
                    lmotor_speed = ASSIGNED_SPEED-10; lmotor_mode = 1;//sharp right
            		Delay.msDelay(5);
            		i++;
            		if(i==100)
            		{
            			break;
            		}
            	}
            	lm.right();
            	//go right; 
            }
            Motor.B.stop();
            Motor.C.stop();
            LCD.refresh();
        }	
}