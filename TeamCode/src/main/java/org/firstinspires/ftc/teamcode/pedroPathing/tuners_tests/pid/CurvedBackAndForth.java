package org.firstinspires.ftc.teamcode.pedroPathing.tuners_tests.pid;

import com.bylazar.ftcontrol.panels.Panels;
import com.bylazar.ftcontrol.panels.configurables.annotations.Configurable;
import com.bylazar.ftcontrol.panels.integration.TelemetryManager;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.pedropathing.follower.Follower;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

/**
 * This is the CurvedBackAndForth autonomous OpMode. It runs the robot in a specified distance
 * forward and to the left. On reaching the end of the forward Path, the robot runs the backward
 * Path the same distance back to the start. Rinse and repeat! This is good for testing a variety
 * of Vectors, like the drive Vector, the translational Vector, the heading Vector, and the
 * centripetal Vector. Remember to test your tunings on StraightBackAndForth as well, since tunings
 * that work well for curves might have issues going in straight lines.
 *
 * @author Anyi Lin - 10158 Scott's Bots
 * @author Aaron Yang - 10158 Scott's Bots
 * @author Harrison Womack - 10158 Scott's Bots
 * @version 1.0, 3/13/2024
 */
//@Configurable
@Autonomous (name = "Curved Back And Forth", group = "PIDF Testing")
public class CurvedBackAndForth extends OpMode {
    private TelemetryManager telemetryM;

    public static double DISTANCE = 20;

    private boolean forward = true;

    private Follower follower;

    private Path forwards;
    private Path backwards;

    /**
     * This initializes the Follower and creates the forward and backward Paths. Additionally, this
     * initializes the FTC Dashboard telemetry.
     */
    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);

       /**blah forwards = new Path(new BezierCurve(new Pose(), new Pose(Math.abs(DISTANCE),0), new Pose(Math.abs(DISTANCE),DISTANCE)));
      /**blah  backwards = new Path(new BezierCurve(new Pose(Math.abs(DISTANCE),DISTANCE), new Pose(Math.abs(DISTANCE),0), new Pose(0,0)));

        backwards.setReversed(true);**/

        follower.followPath(forwards);

        telemetryM = Panels.getTelemetry();
        telemetryM.debug("This will run the robot in a curve going " + DISTANCE + " inches"
                            + " to the left and the same number of inches forward. The robot will go"
                            + "forward and backward continuously along the path. Make sure you have"
                            + "enough room.");
        telemetryM.update(telemetry);
    }

    /**
     * This runs the OpMode, updating the Follower as well as printing out the debug statements to
     * the Telemetry, as well as the FTC Dashboard.
     */
    @Override
    public void loop() {
        follower.update();
        if (!follower.isBusy()) {
            if (forward) {
                forward = false;
                follower.followPath(backwards);
            } else {
                forward = true;
                follower.followPath(forwards);
            }
        }

        telemetryM.debug("going forward", forward);
        telemetryM.update(telemetry);
    }
}
