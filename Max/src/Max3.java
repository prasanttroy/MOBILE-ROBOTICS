import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.robotics.subsumption.*;
import lejos.util.Delay;

public class Max3 {
	public static boolean green_flag=false;
	public static void main(String Args[])
	{
		Button.ENTER.waitForPress();
		
		Behavior Line = new FollowLine();
		Behavior Objec = new AvoidObject();
		Behavior Wall = new FollowWalls();
		Behavior Turn = new GreenTurns();
		Behavior Stop = new RedStop();
		Behavior behaves[]= {Line,Objec,Turn,Wall,Stop};
		
		Arbitrator RoboMax = new Arbitrator(behaves);
		RoboMax.start();
	}
	
}
