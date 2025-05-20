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
 * This is the LateralZeroPowerAccelerationTuner autonomous follower OpMode. This runs the robot
 * to the right until a specified velocity is achieved. Then, the robot cuts power to the motors, setting
 * them to zero power. The deceleration, or negative acceleration, is then measured until the robot
 * stops. The accelerations across the entire time the robot is slowing down is then averaged and
 * that number is then printed. This is used to determine how the robot will decelerate in the
 * forward direction when power is cut, making the estimations used in the calculations for the
 * drive Vector more accurate and giving better braking at the end of Paths.
 *
 * @author Anyi Lin - 10158 Scott's Bots
 * @author Aaron Yang - 10158 Scott's Bots
 * @author Harrison Womack - 10158 Scott's Bots
 * @version 1.0, 3/13/2024
 */
//@Configurable
@Autonomous(name = "Lateral Zero Power Acceleration Tuner", group = "Automatic Tuners")
public class LateralZeroPowerAccelerationTuner extends OpMode {
    private Follower follower;
    private TelemetryManager telemetryM;

    private ArrayList<Double> accelerations = new ArrayList<>();
    public static double VELOCITY = 30;

    private double previousVelocity;
    private long previousTimeNano;

    private boolean stopping;
    private boolean end;

    /**
     * This initializes the drive motors as well as the FTC Dashboard telemetryM.
     */
    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);

        telemetryM = Panels.getTelemetry();
        telemetryM.debug("The robot will run to the right until it reaches " + VELOCITY + " inches per second.");
        telemetryM.debug("Then, it will cut power from the drivetrain and roll to a stop.");
        telemetryM.debug("Make sure you have enough room.");
        telemetryM.debug("After stopping, the lateral zero power acceleration (natural deceleration) will be displayed.");
        telemetryM.debug("Press CROSS or A on game pad 1 to stop.");
        telemetryM.update(telemetry);
    }

    /**
     * This starts the OpMode by setting the drive motors to run forward at full power.
     */
    @Override
    public void start() {
        stopRobot();
    }

    /**
     * This runs the OpMode. At any point during the running of the OpMode, pressing CROSS or A on
     * game pad 1 will stop the OpMode. When the robot hits the specified velocity, the robot will
     * record its deceleration / negative acceleration until it stops. Then, it will average all the
     * recorded deceleration / negative acceleration and print that value.
     */
    @Override
    public void loop() {
        if (gamepad1.cross || gamepad1.a) {
            stopRobot();
            requestOpModeStop();
        }

        follower.update();
        Vector heading = new Vector(1.0, follower.getPose().getHeading() - Math.PI / 2);
        if (!end) {
            if (!stopping) {
                if (MathFunctions.dotProduct(follower.getVelocity(), heading) > VELOCITY) {
                    previousVelocity = MathFunctions.dotProduct(follower.getVelocity(), heading);
                    previousTimeNano = System.nanoTime();
                    stopping = true;
                    stopRobot();
                }
            } else {
                double currentVelocity = MathFunctions.dotProduct(follower.getVelocity(), heading);
                accelerations.add((currentVelocity - previousVelocity) / ((System.nanoTime() - previousTimeNano) / Math.pow(10.0, 9)));
                previousVelocity = currentVelocity;
                previousTimeNano = System.nanoTime();
                if (currentVelocity < follower.getConstraints().velocityConstraint) {
                    end = true;
                }
            }
        } else {
            double average = 0;
            for (Double acceleration : accelerations) {
                average += acceleration;
            }
            average /= accelerations.size();

            telemetryM.debug("lateral zero power acceleration (deceleration):", average);
            telemetryM.update(telemetry);
        }
    }

    /**
     * This stops the OpMode by setting the drive motors to run at 0 power.
     */
    public void stopRobot() {
        follower.setTeleOpDrive(0, 0, 0, true);
    }
}
