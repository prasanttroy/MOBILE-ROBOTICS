import lejos.nxt.*;
import lejos.nxt.addon.ColorHTSensor;
import lejos.robotics.subsumption.*;
import lejos.util.Delay;

public class FollowLine implements Behavior {	
	
	NXTMotor mB = new NXTMotor(MotorPort.B);
	NXTMotor mC = new NXTMotor(MotorPort.C);
	LightSensor ls =new LightSensor(SensorPort.S1);
	ColorHTSensor cls =new ColorHTSensor(SensorPort.S4);
	UltrasonicSensor us = new UltrasonicSensor(SensorPort.S2);
	private boolean suppressed = false;
	int color,BlackColor,WhiteColor,Threshold,error,my_col;
	double cTurn,bTurn,kp = 4,tp=24,correction=0;//5,40,0
	static boolean flag=false;
	
	public FollowLine()
	{
		if(flag==false)
		{
			
			calibration();
			flag=true;
		}
	}
	
	public boolean takeControl() {		
		return true;
	}

	public void action() {
		suppressed=false;
		//Delay.msDelay(200);
		LCD.clear();
		while(!suppressed)
			move();
	}

	public void suppress() {
		suppressed=false;
	}
	
	public void calibration()
	{
		   LCD.clear();
		   ls.setFloodlight(true);
		   LCD.drawString("Press ENTER", 0, 0);
		   LCD.drawString("to calibrate", 0, 1);
		   BlackColor=get_light("Black");
		   WhiteColor=get_light("White");
		   Threshold=(BlackColor+WhiteColor)/2;
		   LCD.clear();
		  }
	
	public int get_light(String col)
	{
		while (Button.ENTER.isDown());
		LCD.drawString(col, 0, 2);
		int lightValue=0;
		while( !Button.ENTER.isPressed() ){	
		      lightValue = ls.readValue();
		      LCD.drawInt(lightValue, 4, 10, 2);
		      LCD.refresh();
		   	}
		 return lightValue;
	}
	
	public void move()
	{
		color = ls.readValue();
		error = color - Threshold;
		LCD.drawInt(error,1,4);
		LCD.refresh();
		correction = kp * error;
		if(error<-5)//-6
		{
			bTurn = (tp - correction)*2;//1.7
			cTurn = (tp + correction);
		}
		else if(error>5)//+6
		{
			bTurn = (tp - correction)*2.3;//2.6
			cTurn = (tp + correction)*1.5;//*1.5
		}
		else
		{
			bTurn = (tp*1.4 + correction);
			cTurn = (tp*1.4 + correction);
		}
		mB.setPower(new Double(bTurn).intValue());
		mB.forward();
		mC.setPower(new Double(cTurn).intValue());
		mC.forward();
		my_col=cls.getColorID();
		LCD.drawInt(my_col,8,2);
		if(my_col==0 || my_col==1 || my_col==2 || my_col==4 || us.getDistance()<=35)
			suppressed=true;
	}
}
