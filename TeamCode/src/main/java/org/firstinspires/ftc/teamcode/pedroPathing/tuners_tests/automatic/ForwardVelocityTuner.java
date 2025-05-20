package org.firstinspires.ftc.teamcode.pedroPathing.tuners_tests.automatic;

import com.bylazar.ftcontrol.panels.Panels;
import com.bylazar.ftcontrol.panels.configurables.annotations.Configurable;
import com.bylazar.ftcontrol.panels.integration.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.MathFunctions;
import com.pedropathing.math.Vector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.util.ArrayList;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

/**
 * This is the ForwardVelocityTuner autonomous follower OpMode. This runs the robot forwards at max
 * power until it reaches some specified distance. It records the most recent velocities, and on
 * reaching the end of the distance, it averages them and prints out the velocity obtained. It is
 * recommended to run this multiple times on a full battery to get the best results. What this does
 * is, when paired with StrafeVelocityTuner, allows FollowerConstants to create a Vector that
 * empirically represents the direction your mecanum wheels actually prefer to go in, allowing for
 * more accurate following.
 *
 * @author Anyi Lin - 10158 Scott's Bots
 * @author Aaron Yang - 10158 Scott's Bots
 * @author Harrison Womack - 10158 Scott's Bots
 * @version 1.0, 3/13/2024
 */
//@Configurable
@Autonomous(name = "Forward Velocity Tuner", group = "Automatic Tuners")
public class ForwardVelocityTuner extends OpMode {
    private ArrayList<Double> velocities = new ArrayList<>();
    private Follower follower;
    private TelemetryManager telemetryM;
    
    public static double DISTANCE = 48;
    public static double RECORD_NUMBER = 10;

    private boolean end;

    /**
     * This initializes the drive motors as well as the cache of velocities and the FTC Dashboard
     * telemetry.
     */
    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);
        telemetryM = Panels.getTelemetry();

        for (int i = 0; i < RECORD_NUMBER; i++) {
            velocities.add(0.0);
        }

        telemetryM.debug("The robot will run at 1 power until it reaches " + DISTANCE + " inches forward.");
        telemetryM.debug("Make sure you have enough room, since the robot has inertia after cutting power.");
        telemetryM.debug("After running the distance, the robot will cut power from the drivetrain and display the forward velocity.");
        telemetryM.debug("Press CROSS or A on game pad 1 to stop.");
        telemetryM.debug("pose", follower.getPose());
        telemetryM.update(telemetry);

    }

    /**
     * This starts the OpMode by setting the drive motors to run forward at full power.
     */
    @Override
    public void start() {
        follower.startTeleopDrive(false);
        end = false;
    }

    /**
     * This runs the OpMode. At any point during the running of the OpMode, pressing CROSS or A on
     * game pad 1 will stop the OpMode. This continuously records the RECORD_NUMBER most recent
     * velocities, and when the robot has run forward enough, these last velocities recorded are
     * averaged and printed.
     */
    @Override
    public void loop() {
        follower.setTeleOpDrive(1,1,1,true);

        if (gamepad1.cross || gamepad1.a) {
            stopRobot();
            requestOpModeStop();
        }

        follower.update();

        if (!end) {
            if (Math.abs(follower.getPose().getX()) > DISTANCE) {
                end = true;
                stopRobot();
            } else {
                double currentVelocity = Math.abs(MathFunctions.dotProduct(follower.getVelocity(), new Vector(1, 0)));
                velocities.add(currentVelocity);
                velocities.remove(0);
            }
        } else {
            stopRobot();
            double average = 0;
            for (Double velocity : velocities) {
                average += velocity;
            }
            average /= velocities.size();
            telemetryM.debug("forward velocity:", average);
            telemetryM.update(telemetry);
        }
    }

    /**
     * This stops the OpMode by setting the drive motors to run at 0 power.
     */
    public void stopRobot() {
        follower.startTeleopDrive(true);
        follower.setTeleOpDrive(0,0,0,true);
    }
}
