package org.firstinspires.ftc.teamcode.pedroPathing.tuners_tests.localization;

import com.bylazar.ftcontrol.panels.Panels;
import com.bylazar.ftcontrol.panels.configurables.annotations.Configurable;
import com.bylazar.ftcontrol.panels.integration.TelemetryManager;
import com.bylazar.ftcontrol.panels.json.Look;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.pedropathing.util.DashboardPoseTracker;
import com.pedropathing.util.Drawing;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

/**
 * This is the TurnTuner OpMode. This tracks the turning movement of the robot and displays the
 * necessary ticks to inches multiplier. This displayed multiplier is what's necessary to scale the
 * robot's current angle in ticks to the specified angle in radians. So, to use this, run the
 * tuner, then pull/push the robot to the specified angle using a protractor or lines on the ground.
 * When you're at the end of the angle, record the ticks to inches multiplier. Feel free to run
 * multiple trials and average the results. Then, input the multiplier into the turning ticks to
 * radians in your localizer of choice.
 * You can adjust the target angle on FTC Dashboard: 192/168/43/1:8080/dash
 *
 * @author Anyi Lin - 10158 Scott's Bots
 * @version 1.0, 5/6/2024
 */
//@Configurable
@Autonomous(name = "Turn Localizer Tuner", group = ".Localization")
public class TurnTuner extends OpMode {
    private Follower follower;
    private DashboardPoseTracker dashboardPoseTracker;

    private TelemetryManager telemetryM;

    public static double ANGLE = 2 * Math.PI;

    /**
     * This initializes the PoseUpdater as well as the FTC Dashboard telemetry.
     */
    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);

        dashboardPoseTracker = follower.getDashboardPoseTracker();

        telemetryM = Panels.getTelemetry();
        telemetryM.debug("Turn your robot " + ANGLE + " radians. Your turn ticks to inches will be shown on the telemetry.");
        telemetryM.update(telemetry);

        Drawing.drawRobot(follower.getPose());
        Drawing.sendPacket();
    }

    /**
     * This updates the robot's pose estimate, and updates the FTC Dashboard telemetry with the
     * calculated multiplier and draws the robot.
     */
    @Override
    public void loop() {
        follower.update();

        telemetryM.debug("total angle", follower.getTotalHeading());
        telemetryM.debug("The multiplier will display what your turn ticks to inches should be to scale your current angle to " + ANGLE + " radians.");
        telemetryM.debug("multiplier", ANGLE / (follower.getTotalHeading() / follower.getPoseTracker().getLocalizer().getTurningMultiplier()));
        telemetryM.update(telemetry);

        Drawing.drawPoseHistory(dashboardPoseTracker);
        Drawing.drawRobot(follower.getPose());
        Drawing.sendPacket();
    }
}
