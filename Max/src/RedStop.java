import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorHTSensor;
import lejos.robotics.subsumption.Behavior;

public class RedStop implements Behavior {
	ColorHTSensor cls =new ColorHTSensor(SensorPort.S4);
	NXTMotor mB = new NXTMotor(MotorPort.B);
	NXTMotor mC = new NXTMotor(MotorPort.C);
	private boolean suppressed = false;
		
	public boolean takeControl() {
		return (cls.getColorID()==0);
	}

	public void action() {
		suppressed = false;
		mB.stop();
		mC.stop();
		LCD.drawString("MISSION ACOMPLISHED", 2, 4);
		while(Button.ENTER.isDown());
		System.exit(0);
	}

	public void suppress() {
		suppressed=true;	
	}
}
