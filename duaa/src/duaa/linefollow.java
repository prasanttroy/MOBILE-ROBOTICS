package duaa;
import lejos.nxt.*;
import lejos.nxt.addon.CompassHTSensor;
import lejos.nxt.SensorPort;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
public class linefollow {
	private boolean suppressed = false;
	   
	   public boolean takeControl() {
	      return true;
	   }

	   public void suppress() {
	      suppressed = true;
	   }
	   public void action() {
		   
		   
	   }
	   
	   
	   
	   
	   
	   
	   
	   
}
