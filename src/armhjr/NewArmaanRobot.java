package armhjr;
import robocode.*;
import java.awt.Color;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;
import robocode.TeamRobot;
import static robocode.util.Utils.normalRelativeAngleDegrees;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * ArmaanRobot - a robot by (Armaan Hajarizadeh, 2022)
 */

public class NewArmaanRobot extends TeamRobot {
	
	public void run() {
		setColors(Color.magenta,Color.green,Color.yellow); // body,gun,radar

		// Robot main loop
		while(true) {
			turnGunRight(360);
		}
	}

	/**
	 * turnToAngle: Turns tank to a specific angle rather than a bearing
	 */
	public void turnToAngle(double angle) {
		double heading = getHeading();
		setTurnRight(angle - heading);
		}
	
	public void armaanMovement() {
		double a = getGunHeading();

		System.out.println();
		System.out.println(a);
		
		turnToAngle(a);
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		if (isTeammate(e.getName())) {
	           System.out.println("Teammate Detected - Firing Halted");;
	       } 
		else {
			
		armaanMovement();
		
		setAhead(100);
		// Calculate exact location of the robot
		double absoluteBearing = getHeading() + e.getBearing();
		double bearingFromGun = normalRelativeAngleDegrees(absoluteBearing - getGunHeading());

		// If it's close enough, fire!
		if (Math.abs(bearingFromGun) <= 3) {
			turnGunRight(bearingFromGun);
			// We check gun heat here, because calling fire()
			// uses a turn, which could cause us to lose track
			// of the other robot.
			if (getGunHeat() == 0) {
				fire(Math.min(3 - Math.abs(bearingFromGun), getEnergy() - .1));
			}
		} // otherwise just set the gun to turn.
		// Note:  This will have no effect until we call scan()
		else {
			turnGunRight(bearingFromGun);
		}
		// Generates another scan event if we see a robot.
		// We only need to call this if the gun (and therefore radar)
		// are not turning.  Otherwise, scan is called automatically.
		if (bearingFromGun == 0) {
			scan();
			}
		}
	}

	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {	
		double arenaHeight = getBattleFieldHeight();
		double arenaWidth = getBattleFieldWidth();
		
		double x = getX();
		double y = getY();
		
		System.out.println();
		System.out.println("x = " + x);
		System.out.println("y = " + y);
		System.out.println("Arena Width = " + arenaWidth);
		System.out.println("Arena Height = " + arenaHeight);
		
		// corners
		if (x <= 50 && y <= 50) { // bottom left corner
			turnToAngle(45);
			System.out.println("Bottom Left Corner");
			System.out.println();
		}
		else if (x >= arenaWidth - 50 && y <= 50) { // bottom right corner
			turnToAngle(315);
			System.out.println("Bottom Right Corner");
			System.out.println();
		}
		else if (x >= arenaWidth - 50 && y >= arenaHeight - 50) { // top right corner
			turnToAngle(225);
			System.out.println("Top Right Corner");
			System.out.println();
		}
		else if (x <= 50 && y >= arenaHeight - 50) { // top left corner
			turnToAngle(135);
			System.out.println("Top Left Corner");
			System.out.println();
		}
		else {
			// walls		
			if (y <= 50) { // bottom wall
				double temp = 315 + (Math.random() * 91);
				double temp1 = 0;
				if (temp >= 360) {
					temp1 = temp - 360;
				}
				System.out.println("Going in Angle " + temp1);
				turnToAngle(temp1);
				System.out.println("Bottom Wall");
				System.out.println();
			}
			else if (x >= arenaWidth - 50) { // right wall
				double temp = 225 + (Math.random() * 91);
				System.out.println("Going in Angle " + temp);
				turnToAngle(temp);
				System.out.println("Right Wall");
				System.out.println();
			}
			else if (y >= arenaHeight - 50) { // top wall
				double temp = 135 + (Math.random() * 91);
				System.out.println("Going in Angle " + temp);
				turnToAngle(temp);
				System.out.println("Top Wall");
				System.out.println();
			}
			else if (x <= 50) { // left wall
				double temp = 45 + (Math.random() * 91);
				System.out.println("Going in Angle " + temp);
				turnToAngle(temp);
				System.out.println("Left Wall");
				System.out.println();
			}
		}
		execute();
		waitFor(new TurnCompleteCondition(this));
	}
	
	public void onDeath (DeathEvent event) {
		System.out.println("I bless the rains down in Africa");
		execute();
	}
	
	public void onWin(WinEvent e) {
		turnGunRight(360);
	}
}