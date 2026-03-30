package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.CANFuelSubsystem;
import frc.robot.subsystems.CANDriveSubsystem;

@SuppressWarnings("unused")
public final class Autos {
  public static final Command exampleAuto(CANDriveSubsystem driveSubsystem, CANFuelSubsystem ballSubsystem) {
    return new SequentialCommandGroup(
        // Drive forward for 0.25 seconds (use a value between -1 and 1)
        //driveSubsystem.driveArcade(() -> -.5, () -> 0).withTimeout(.65),
         //Stop driving (IMPORTANT: this ends immediately)
        //driveSubsystem.stopCommand(),
        // Spin up for 1 seconds
        ballSubsystem.spinUpCommand().withTimeout(1),
        // Launch for 6 seconds
        ballSubsystem.launchCommand().withTimeout(5),
        // Stop shooter
        ballSubsystem.runOnce(ballSubsystem::stop),
        //drive backwards and spin a little bit.
        driveSubsystem.driveArcade(() -> -.5,() ->.75).withTimeout(.45),
        //spining stop.
        driveSubsystem.stopCommand(),
        //drive backwards into the middle
        driveSubsystem.driveArcade(() -> .85,() ->0).withTimeout(1.5),
        //spining stop.
        driveSubsystem.stopCommand(),
        //turn to face middle from the front
        driveSubsystem.driveArcade(() -> 0,() ->.8).withTimeout(.65),
        driveSubsystem.stopCommand(),
        //get the hell outta the way of the center and intake fuel
        ballSubsystem.intakeCommand(),
        driveSubsystem.driveArcade(()->-.5, ()->0).withTimeout(1.5),
        ballSubsystem.runOnce(ballSubsystem::stop),
        driveSubsystem.stopCommand());
  }

  // Example autonomous command which drives while intaking in parallel.
  // This keeps the same sequence as exampleAuto but runs intake during the
  // initial drive period so fuel will be collected while moving.
  public static final Command exampleAutoWithIntake(CANDriveSubsystem driveSubsystem, CANFuelSubsystem ballSubsystem) {
    return new ParallelCommandGroup(
        // Drive and intake simultaneously for .25 seconds. The driveArcade
        // command is used as the deadline so the intake runs only while
        // the drive command is active.
        new ParallelDeadlineGroup(
            driveSubsystem.driveArcade(() -> 0.5, () -> 0).withTimeout(.25),
            ballSubsystem.runOnce(() -> ballSubsystem.intake())),
          // Stop driving.
          driveSubsystem.driveArcade(() -> 0, () -> 0),
          // Spin up the launcher and then launch as before
          ballSubsystem.spinUpCommand().withTimeout(1),
          ballSubsystem.launchCommand().withTimeout(9),
          // Stop running the launcher
          ballSubsystem.runOnce(() -> ballSubsystem.stop()));
  }
}