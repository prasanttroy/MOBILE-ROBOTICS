import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTMotor;
import lejos.nxt.SensorPort;
import java.*;

public class RoboPController
{
	NXTMotor mB = new NXTMotor(MotorPort.B);
	NXTMotor mC = new NXTMotor(MotorPort.C);
	BlackWhiteSensor bw =new BlackWhiteSensor(SensorPort.S1);
	BlackWhiteSensor bw1=new BlackWhiteSensor(SensorPort.S2);
	int color,ncolor;
	double cTurn;
	double bTurn;
	int threshold;
	String message;

	public RoboPController() {
	}

	public void run() {

		LCD.clear();
		LCD.drawString("Started Cruiser", 0, 2);
		bw.calibrate();		
		bw1.calibrate();
		threshold = bw.Threshold;	//offset
		double kp = 10;								//15
		int error;
		int lastError = 0;
		double correction = 0;
		int flag=0,time=0;
		int tp=20;
		int errors=0;
		int sp=30 , fp=40;
		int er=2;
		while (!Button.ESCAPE.isPressed()) 
		{
			color = bw.light();			
			error = color - threshold;
			LCD.clear();
			LCD.drawString("error", 0, 1);
			LCD.drawInt(error, 0, 2);
			LCD.refresh();
			if(error <= er && error >= -er )
			{
				System.out.println("error :"+error);
				mB.setPower(fp);
				mB.forward();
				mC.setPower(fp);
				mC.forward();
			}
			else
			{
				if(error < -er)
				{
					time=0;
					flag=0;
				}
				else if(error > er)
				{
					if(flag==0)
						flag=1;
					else time++;
				}
				//LCD.drawString("Time:", 0, 1);
				//LCD.drawInt(time, 10, 1);
				if(time>5000)
				{
					System.out.println(time);
					//LCD.drawString("inside acute", 0, 1);
					//LCD.clear();
					do{
						ncolor = bw.light();
						errors=ncolor-threshold;
						//LCD.drawString("Error:", 0, 3);
						LCD.drawInt(errors, 0, 3);
						LCD.clear();
						mB.setPower(sp);
						mB.forward();
						mC.setPower(sp);
						mC.backward();
						}while(ncolor>threshold);
					do{
						ncolor = bw.light();
						errors=ncolor-threshold;
						//LCD.drawString("Error:", 0, 3);
						LCD.drawInt(errors, 0, 3);
						LCD.clear();
						mB.setPower(sp);
						mB.forward();
						mC.setPower(sp);
						mC.backward();
						}while(ncolor>=threshold);
					do{
						ncolor = bw.light();
						errors=ncolor-threshold;
						//LCD.drawString("Error:", 0, 3);
						LCD.drawInt(errors, 9, 3);
						LCD.clear();
						mB.setPower(sp);
						mB.forward();
						mC.setPower(sp);
						mC.backward();
						}while(ncolor<threshold);
					
					}
			else
				{
					correction = kp * error;
					bTurn = tp - correction;
					cTurn = tp + correction;
		
					mB.setPower(new Double(bTurn).intValue());
					mB.forward();
					
					mC.setPower(new Double(cTurn).intValue());
					mC.forward();
				}
		}
		}
	}
	
	public static void main(String a[])
	{
		Thread cruiser = new Thread(new Cruiser());
		cruiser.start();
	}

}
