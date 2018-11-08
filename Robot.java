
package org.usfirst.frc.team3826.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

 
public class Robot extends IterativeRobot {
	Joystick xbox;
	
	SRF_PID pid1 = new SRF_PID();
	SRF_PID pid2 = new SRF_PID();
	SRF_PID pid3 = new SRF_PID();
	SRF_PID pid4 = new SRF_PID();
	SRF_PID[] pidArray = new SRF_PID[]{pid1,pid2,pid3,pid4};
	int selectedPID;
	int kValue;
	
	boolean letUp1;						//A
	boolean letUp2;						//B
	boolean letUp3;						//X
	boolean letUp4;
	boolean letUp5;
	boolean letUp6;
	
	SendableChooser selectPID = new SendableChooser();
	
	public void robotInit() {
		xbox = new Joystick(0);
		
		selectPID.addDefault("PID1", 0);
		selectPID.addObject("PID2", 1);
		selectPID.addObject("PID3", 2);
		selectPID.addObject("PID4", 3);
		SmartDashboard.putData("selectPID", selectPID);
		
	}

	
	public void autonomousInit() {
		
	}

	public void autonomousPeriodic() {
		
	}
	
	public void teleopInit() {
		String pidName;
		if( (int) selectPID.getSelected() == 0) {
			pidName = "pid1";
			selectedPID = 0;
		} else if( (int) selectPID.getSelected() == 1) {
			pidName = "pid2";
			selectedPID = 1;
		} else if( (int) selectPID.getSelected() == 2) {
			pidName = "pid3";
			selectedPID = 2;
		} else {
			pidName = "pid4";
			selectedPID = 3;
		}
		SmartDashboard.putString("SelectedPID", pidName);
		
		kValue = 1;
		
		letUp1 = true;
		letUp2 = true;
		letUp3 = true;
	}

	public void teleopPeriodic() {
		if(xbox.getRawButton(1) && letUp1) 		//A
		{
			pidArray[selectedPID].k[kValue] *= 2;
			letUp1 = false;	
		} 
		else if(xbox.getRawButton(2) && letUp2) 	//B
		{
			pidArray[selectedPID].k[kValue] *= .5;
			letUp2 = false;
		}
		else if(xbox.getRawButton(3) && letUp3) 	//X
		{
			kValue++;
			
			if(kValue > 3)
				kValue = 1;
			
			letUp3 = false;
		}
	
		
		
		if(!xbox.getRawButton(1))			//A
			letUp1 = true;
		else if(!xbox.getRawButton(2))			//B
			letUp2 = true;
		else if(!xbox.getRawButton(3))			//X
			letUp3 = true;
		
		SmartDashboard.putNumber("P value",pidArray[selectedPID].k[1]);
		SmartDashboard.putNumber("I value",pidArray[selectedPID].k[2]);
		SmartDashboard.putNumber("D value",pidArray[selectedPID].k[3]);
	}

	
	public void testPeriodic() {
	}
}
