// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.FRCNetComm.tResourceType;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the
 * build.gradle file in the
 * project.
 */
@SuppressWarnings("unused")
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  /**
   * This function is run when the robot is first started up and should be used
   * for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer. This will perform all our button bindings,
    // and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();

    // Used to track usage of Kitbot code, please do not remove.
    HAL.report(tResourceType.kResourceType_Framework, 10);
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items
   * like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler. This is responsible for polling buttons, adding
    // newly-scheduled
    // commands, running already-scheduled commands, removing finished or
    // interrupted commands,
    // and running subsystem periodic() methods. This must be called from the
    // robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
 @Override
public void disabledInit() {
  System.out.println("MODE: DISABLED INIT");

  if (m_autonomousCommand != null) {
    System.out.println("AUTO: canceling (disabled) " + m_autonomousCommand.getName());
    m_autonomousCommand.cancel();
    m_autonomousCommand = null;
  }
}

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your
   * {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
  System.out.println("MODE: AUTONOMOUS INIT");

  m_autonomousCommand = m_robotContainer.getAutonomousCommand();

  // Only schedule if we are REALLY in autonomous enabled
  if (m_autonomousCommand != null && DriverStation.isAutonomousEnabled()) {
    m_autonomousCommand.schedule();
    System.out.println("AUTO: scheduled " + m_autonomousCommand.getName());
  } else {
    System.out.println("AUTO: NOT scheduled (null command or not autonomous enabled)");
  }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
  }

 @Override
public void teleopInit() {
  System.out.println("MODE: TELEOP INIT");

  if (m_autonomousCommand != null) {
    System.out.println("AUTO: canceling " + m_autonomousCommand.getName());
    m_autonomousCommand.cancel();
    m_autonomousCommand = null; // IMPORTANT: prevents re-use
  }
}

  /** This function is called periodically during operator control. */
 @Override
public void teleopPeriodic() {
  // If auto ever gets scheduled during teleop, kill it immediately
  if (m_autonomousCommand != null && m_autonomousCommand.isScheduled()) {
    System.out.println("AUTO: was scheduled in TELEOP - canceling!");
    m_autonomousCommand.cancel();
  }
}

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
  }

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {
  }

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {
  }
}
