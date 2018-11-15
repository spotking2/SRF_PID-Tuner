package org.usfirst.frc.team3826.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

 
public class Robot extends IterativeRobot {
	Joystick xbox;
	
	SRF_PID pid1 = new SRF_PID(xbox);
	SRF_PID pid2 = new SRF_PID(xbox);
	SRF_PID pid3 = new SRF_PID(xbox);
	SRF_PID pid4 = new SRF_PID(xbox);
	SRF_PID[] pidArray = new SRF_PID[]{pid1,pid2,pid3,pid4};
	int selectedPID;
	
	int kValue;
	int adjustP;
	int adjustI;
	int adjustD;
	boolean adjusted;
	int[] multPID = new int[] {adjustP, adjustI, adjustD};
	
	boolean letUp1;						//A
	boolean letUp2;						//B
	boolean letUp3;						//X
	boolean letUp4;
	boolean letUp5;
	boolean letUp6;
	
	SendableChooser selectPID = new SendableChooser();
	String kValueName = "P";
	
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
		if( (int) selectPID.getSelected() == 0) {
			selectedPID = 0;
		} else if( (int) selectPID.getSelected() == 1) {
			selectedPID = 1;
		} else if( (int) selectPID.getSelected() == 2) {
			selectedPID = 2;
		} else {
			selectedPID = 3;
		}
		
		kValue = 0;
		
		letUp1 = true;
		letUp2 = true;
		letUp3 = true;
		
		pid1.setPID(1,1,1,true);
		pid2.setPID(1,1,1,true);
		pid3.setPID(1,1,1,true);
		pid4.setPID(1,1,1,true);
		
		adjustP = 0;
		adjustI = 0;
		adjustD = 0;
		adjusted = false;
	}

	public void teleopPeriodic() {
		if(xbox.getRawButton(1) && letUp1) 		//A
		{
			multPID[kValue] += 2;
			letUp1 = false;	
		} 
		else if(xbox.getRawButton(2) && letUp2) 	//B
		{
			multPID[kValue] += .5;
			letUp2 = false;
		}
		else if(xbox.getRawButton(3) && letUp3) 	//X
		{
			kValue++;
			
			if(kValue==0)
				kValueName = "P";
			else if(kValue==1)
				kValueName = "I";
			else if(kValue==2)
				kValueName = "D";
			else {
				kValue = 0;
				kValueName = "P";
			}
			letUp3 = false;
			
		}
	
		if(adjusted){
			pidArray[selectedPID].adjustMult(adjustP, adjustI, adjustD);
			adjustP = 0;
			adjustI = 0;
			adjustD = 0;
		}
		
		if(!xbox.getRawButton(1))			//A
			letUp1 = true;
		if(!xbox.getRawButton(2))			//B
			letUp2 = true;
		if(!xbox.getRawButton(3))			//X
			letUp3 = true;
		
		SmartDashboard.putString("Value Being Changed", kValueName);
		pidArray[selectedPID].smartDashPrint();
	}

	
	public void testPeriodic() {
	}
}
