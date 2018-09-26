import lejos.nxt.*;
import lejos.nxt.LCD;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTMotor;
import lejos.nxt.Button;

public class PRobo extends Thread{

	NXTMotor mB = new NXTMotor(MotorPort.B);
	NXTMotor mC = new NXTMotor(MotorPort.C);
	BlackWhiteSensor bw =new BlackWhiteSensor(SensorPort.S1);
	int color,ncolor;
	double cTurn;
	double bTurn;
	int threshold;
	String message;

	public PRobo() {
	}

	public void run() {

		LCD.clear();
		LCD.drawString("Started Cruiser", 0, 2);
		bw.calibrate();
		Button.ENTER.waitForPress();
		threshold = bw.Threshold;	//offset
		double kp = 8;								//15
		int error;
		int lastError = 0;
		double correction = 0;
		int flag=0,time=0;
		int tp=45;					//45(.)
		int errors=0;
		
		while (!Button.ESCAPE.isPressed()) {

			color = bw.light();
			error = color - threshold;
			LCD.drawInt(error,1,4);
			LCD.refresh();
			
			correction = kp * error;
			
			if(error<-6)
			{
				bTurn = (tp - correction)/3;
				cTurn = (tp + correction)*3;
			}
			else if(error>6)
			{
				bTurn = (tp - correction)*3;
				cTurn = (tp + correction)*4;
			}
			else
			{
				bTurn = (tp*2.3 - correction);
				cTurn = (tp*2.3 + correction);
			}

			mB.setPower(new Double(bTurn).intValue());
			mB.forward();

			mC.setPower(new Double(cTurn).intValue());
			mC.forward();
			}
		}
	
	public static void main(String a[])
	{
		Thread cruiser = new Thread(new PRobo());
		cruiser.start();
	}

}
