import lejos.robotics.subsumption.*;
import lejos.nxt.*;
import lejos.nxt.addon.ColorHTSensor;
import lejos.nxt.addon.CompassHTSensor;
import lejos.util.Delay;

public class AvoidObject implements Behavior
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
    magnetics ms = new magnetics();
    magnetics ms1 = new magnetics();
    LightSensor ls = new LightSensor(SensorPort.S1);
    FollowLine fl = new FollowLine();
    ColorHTSensor cls= new ColorHTSensor(SensorPort.S4);
    private boolean suppressed = false;
    int blk=40;
    
    public boolean takeControl() {
		return (us.getDistance()<=35);//change in distance
	}

	public void action() {
		
		suppressed = false;
		while(us.getDistance() >= 23 && us.getDistance() <= 35)
		{
			
	             rightMotor.controlMotor(50,1);
		         leftMotor.controlMotor(50,1);
		}
		int distance = 0,
	                lmotor_speed = ASSIGNED_SPEED,
	                lmotor_mode = 1,
	                rmotor_speed = ASSIGNED_SPEED,
	                rmotor_mode = 1;
		
		rightMotor.controlMotor(0,3);
        leftMotor.controlMotor(0,3);
		
		Motor.A.rotateTo(-50);
        int dis_left=us.getDistance();
        Delay.msDelay(100);
        Motor.A.rotateTo(0);
        Delay.msDelay(100);
        Motor.A.rotateTo(50);
        Delay.msDelay(100);
        int dis_right=us.getDistance();
        if(dis_left<=dis_right)
        {
        	//go right;
        	Delay.msDelay(100);
            Motor.A.rotateTo(0);
            Delay.msDelay(100);
            Motor.A.rotateTo(-50);
            ms.turn_right(270);							//change
           while(!suppressed)
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
		   	         	rmotor_speed = ASSIGNED_SPEED; rmotor_mode = 1;
		   	             lmotor_speed = ASSIGNED_SPEED; lmotor_mode = 1;//slow forward
		   	         }
		   	     }
		   	     else
		   	     {
		   	     	  rmotor_speed = ASSIGNED_SPEED+5; rmotor_mode = 1; 
		   	          lmotor_speed = ASSIGNED_SPEED-35; lmotor_mode = 1;// left
		   	     }
		   		 LCD.drawString("following right...... ", 0, 0);
		   		 LCD.drawString("Distance: "+distance+"   ", 0, 1);
		         LCD.drawString("L:"+lmotor_speed+"@"+lmotor_mode+", R:"+
		                    rmotor_speed+"@"+rmotor_mode+".", 0, 3);
		            rightMotor.controlMotor(rmotor_speed,rmotor_mode);
		            leftMotor.controlMotor(lmotor_speed,lmotor_mode);
		            //Thread.sleep(300);
		            Delay.msDelay(200);
		            if(ls.readValue()<=blk || cls.getColorID()==7)
		            {
		            	LCD.drawString("light..##", 0, 6);
		    			suppressed=true;
		    			//us.off();//Motor.A.rotateTo(0);
		            }
            	}
           		//LCD.drawString("object has been avoided...... ", 2, 5);
           		rightMotor.controlMotor(0,3);
           		leftMotor.controlMotor(0,3); 
           		Delay.msDelay(200);
           		rightMotor.controlMotor(60,1);
           		leftMotor.controlMotor(60,1); 
           		Delay.msDelay(280);
           		ms1.turn_right(270);
	   		    Delay.msDelay(350);
                
               
	     }
        else{
        	//turn left
        	Delay.msDelay(100);
            Motor.A.rotateTo(0);
            Delay.msDelay(100);
            Motor.A.rotateTo(50);
            ms.turn_left(90);
            while(!suppressed)
            {
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
			  	         	rmotor_speed = ASSIGNED_SPEED; rmotor_mode = 1;
			  	             lmotor_speed = ASSIGNED_SPEED; lmotor_mode = 1;//slow forward
			  	         }
			  	     }
			  	     else
			  	     {
			  	     	  rmotor_speed = ASSIGNED_SPEED-35; rmotor_mode = 1; 
			  	          lmotor_speed = ASSIGNED_SPEED+5; lmotor_mode = 1;// left
			  	     }
			  		LCD.drawString("following left...... ", 0, 0);
			   		 LCD.clear();
			  		 LCD.drawString("Distance: "+distance+"   ", 0, 1);
			           LCD.drawString("L:"+lmotor_speed+"@"+lmotor_mode+", R:"+
			                   rmotor_speed+"@"+rmotor_mode+".", 0, 3);
			           rightMotor.controlMotor(rmotor_speed,rmotor_mode);
			           leftMotor.controlMotor(lmotor_speed,lmotor_mode);
			           //Thread.sleep(300);
			           Delay.msDelay(200);
			           if(ls.readValue()<=blk || cls.getColorID()==7)
			            {
			            	LCD.drawString("light..##", 0, 6);
			    			suppressed=true;
			    			//Motor.A.rotateTo(0);
			    			//us.off();
			            }
			            }
            		
            		rightMotor.controlMotor(0,3);//change
            		leftMotor.controlMotor(0,3);
            		Delay.msDelay(200);
            		rightMotor.controlMotor(60,1);
               		leftMotor.controlMotor(60,1); 
               		Delay.msDelay(280);
            		ms1.turn_left(90);				
            		Delay.msDelay(350);          		
            		
        	}
        Motor.A.rotateTo(0);
        Delay.msDelay(200);
       // us.continuous();
        
	}

	public void suppress() {
		suppressed=true;
		}
}