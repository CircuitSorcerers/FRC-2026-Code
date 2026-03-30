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
  public static final Command exampleAuto_MIRRORED(CANDriveSubsystem driveSubsystem, CANFuelSubsystem ballSubsystem){
        return new SequentialCommandGroup(  
        ballSubsystem.spinUpCommand().withTimeout(1),
        // Launch for 6 seconds
        ballSubsystem.launchCommand().withTimeout(5),
        // Stop shooter
        ballSubsystem.runOnce(ballSubsystem::stop),
        //drive backwards and spin a little bit.
        driveSubsystem.driveArcade(() -> -.5,() ->-.75).withTimeout(.45),
        //spining stop.
        driveSubsystem.stopCommand(),
        //drive backwards into the middle
        driveSubsystem.driveArcade(() -> .85,() ->0).withTimeout(1.5),
        //spining stop.
        driveSubsystem.stopCommand(),
        //turn to face middle from the front
        driveSubsystem.driveArcade(() -> 0,() ->-.8).withTimeout(.65),
        driveSubsystem.stopCommand(),
        //get the hell outta the way of the center and intake fuel
        ballSubsystem.intakeCommand(),
        driveSubsystem.driveArcade(()->-.5, ()->0).withTimeout(1.5),
        ballSubsystem.runOnce(ballSubsystem::stop),
        driveSubsystem.stopCommand());
  }
  public static final Command exampleAutoWithIntake_MIRRORED(CANDriveSubsystem driveSubsystem, CANFuelSubsystem ballSubsystem){
    return new SequentialCommandGroup(
        // Spin up
      ballSubsystem.spinUpCommand().withTimeout(1),
      // Launch
      ballSubsystem.launchCommand().withTimeout(5),
      // Stop shooter
      ballSubsystem.runOnce(ballSubsystem::stop),
      // Drive backwards + turn
      driveSubsystem.driveArcade(() -> -.5, () -> -.75).withTimeout(.45),
      driveSubsystem.stopCommand(),
      // Drive backwards
      driveSubsystem.driveArcade(() -> .85, () -> 0).withTimeout(1.5),
      driveSubsystem.stopCommand(),
      // Turn
      driveSubsystem.driveArcade(() -> 0, () -> -.8).withTimeout(.65),
      driveSubsystem.stopCommand(),
      // 🚀 THIS is the only parallel part (same as your sequential but improved)
      new ParallelDeadlineGroup(
          driveSubsystem.driveArcade(() -> -.5, () -> 0).withTimeout(1.5),
          ballSubsystem.intakeCommand()
      ),
      // Stop intake
      ballSubsystem.runOnce(ballSubsystem::stop),
      // Stop drive
      driveSubsystem.stopCommand()  );
  }
  // Example autonomous command which drives while intaking in parallel.
  // This keeps the same sequence as exampleAuto but runs intake during the
  // initial drive period so fuel will be collected while moving.
 public static final Command exampleAutoWithIntake(CANDriveSubsystem driveSubsystem,CANFuelSubsystem ballSubsystem) {
  return new SequentialCommandGroup(

      // Spin up
      ballSubsystem.spinUpCommand().withTimeout(1),
      // Launch
      ballSubsystem.launchCommand().withTimeout(5),
      // Stop shooter
      ballSubsystem.runOnce(ballSubsystem::stop),
      // Drive backwards + turn
      driveSubsystem.driveArcade(() -> -.5, () -> .75).withTimeout(.45),
      driveSubsystem.stopCommand(),
      // Drive backwards
      driveSubsystem.driveArcade(() -> .85, () -> 0).withTimeout(1.5),
      driveSubsystem.stopCommand(),
      // Turn
      driveSubsystem.driveArcade(() -> 0, () -> .8).withTimeout(.65),
      driveSubsystem.stopCommand(),
      // 🚀 THIS is the only parallel part (same as your sequential but improved)
      new ParallelDeadlineGroup(
          driveSubsystem.driveArcade(() -> -.5, () -> 0).withTimeout(1.5),
          ballSubsystem.intakeCommand()
      ),
      // Stop intake
      ballSubsystem.runOnce(ballSubsystem::stop),
      // Stop drive
      driveSubsystem.stopCommand()  );
 }

  public static final Command scoreReloadScoreAuto(CANDriveSubsystem driveSubsystem,CANFuelSubsystem ballSubsystem) {
    return new SequentialCommandGroup(
        // ---------- FIRST SCORE ----------
        ballSubsystem.spinUpCommand().withTimeout(1.0),
        ballSubsystem.launchCommand().withTimeout(5.0),
        ballSubsystem.runOnce(ballSubsystem::stop),
        // ---------- RELOAD ----------
        // Turn away from the scoring position
        driveSubsystem.driveArcade(() -> -.5, () -> .75).withTimeout(0.45),
        driveSubsystem.stopCommand(),
        // Drive toward reload / center area
        driveSubsystem.driveArcade(() -> .85, () -> 0).withTimeout(1.5),
        driveSubsystem.stopCommand(),
        // Turn to face the fuel/source area
        driveSubsystem.driveArcade(() -> 0, () -> .8).withTimeout(0.65),
        driveSubsystem.stopCommand(),
        // Intake while driving forward to collect
        new ParallelDeadlineGroup(
            driveSubsystem.driveArcade(() -> -.5, () -> 0).withTimeout(1.5),
            ballSubsystem.intakeCommand()
        ),
        ballSubsystem.runOnce(ballSubsystem::stop),
        driveSubsystem.stopCommand(),
        // ---------- COME BACK ----------
        // Back away / reorient
        driveSubsystem.driveArcade(() -> .5, () -> 0).withTimeout(1.2),
        driveSubsystem.stopCommand(),
        // Turn back toward the speaker/goal
        driveSubsystem.driveArcade(() -> 0, () -> -.8).withTimeout(0.65),
        driveSubsystem.stopCommand(),
        // Drive back into scoring position
        driveSubsystem.driveArcade(() -> -.85, () -> 0).withTimeout(1.5),
        driveSubsystem.stopCommand(),
        // Small aim adjustment if needed
        driveSubsystem.driveArcade(() -> 0, () -> -.75).withTimeout(0.45),
        driveSubsystem.stopCommand(),
        // ---------- SECOND SCORE ----------
        ballSubsystem.spinUpCommand().withTimeout(1.0),
        ballSubsystem.launchCommand().withTimeout(5.0),
        ballSubsystem.runOnce(ballSubsystem::stop)
    );
}
}