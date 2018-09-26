import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.MotorPort;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.ColorHTSensor;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;

public class FollowWalls implements Behavior {
	
	private static final int VERY_CLOSE_LEFT =     20;
    private static final int SOMEWHAT_CLOSE_LEFT = 24;
    private static final int NOT_THAT_CLOSE_LEFT = 30;
    private static final int SOMEWHAT_FAR_RIGHT =  36;
    private static final int VERY_FAR_RIGHT =      43;
    private static final int ASSIGNED_SPEED =      55;
    
    private static MotorPort leftMotor = MotorPort.B;
    private static MotorPort rightMotor = MotorPort.C;
    UltrasonicSensor us = new UltrasonicSensor(SensorPort.S2);
    ColorHTSensor cls =new ColorHTSensor(SensorPort.S4);
    magnetics ms = new magnetics();
    magnetics ms1 = new magnetics();
    LightSensor ls = new LightSensor(SensorPort.S1);
    private boolean suppressed = false;
    int blk=38;

	public boolean takeControl() {
		return (cls.getColorID()==2 || cls.getColorID()==4);
	}

	public void action() {
		suppressed=false;
		LCD.clear();
		LCD.drawString("Following wall...   ", 0, 1);
		int distance = 0,
                lmotor_speed = ASSIGNED_SPEED,
                lmotor_mode = 1,
                rmotor_speed = ASSIGNED_SPEED,
                rmotor_mode = 1;
		
		//rightMotor.controlMotor(0,3);
       // leftMotor.controlMotor(0,3);
        //Delay.msDelay(100);
        
		while(us.getDistance() > 27)						//22
        {
        	rightMotor.controlMotor(70,1);					//70
            leftMotor.controlMotor(70,1);
        }
       // rightMotor.controlMotor(0,3);
       // leftMotor.controlMotor(0,3);
        ms.turn_right(270);
      //  Delay.msDelay(100);
        
        Motor.A.rotateTo(-50);
        while (!suppressed)
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
            if(ls.readValue()<=blk || cls.getColorID()==7)
            {
            	LCD.drawString("..light..##", 0, 6);
    			suppressed=true;
            }
        }
        
        		rightMotor.controlMotor(0,3);
        		leftMotor.controlMotor(0,3);
        		ms1.turn_right(270);
        		Delay.msDelay(200);
        		Motor.A.rotateTo(0);
	}

	public void suppress() {
		suppressed=true;
	}
}
