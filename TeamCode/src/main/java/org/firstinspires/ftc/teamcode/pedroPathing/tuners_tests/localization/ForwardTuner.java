package org.firstinspires.ftc.teamcode.pedroPathing.tuners_tests.localization;

import com.bylazar.ftcontrol.panels.Panels;
import com.bylazar.ftcontrol.panels.configurables.annotations.Configurable;
import com.bylazar.ftcontrol.panels.integration.TelemetryManager;
import com.bylazar.ftcontrol.panels.json.Look;
import com.pedropathing.follower.Follower;
import com.pedropathing.util.Drawing;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.pedropathing.util.DashboardPoseTracker;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

/**
 * This is the ForwardTuner OpMode. This tracks the forward movement of the robot and displays the
 * necessary ticks to inches multiplier. This displayed multiplier is what's necessary to scale the
 * robot's current distance in ticks to the specified distance in inches. So, to use this, run the
 * tuner, then pull/push the robot to the specified distance using a ruler on the ground. When you're
 * at the end of the distance, record the ticks to inches multiplier. Feel free to run multiple trials
 * and average the results. Then, input the multiplier into the forward ticks to inches in your
 * localizer of choice.
 *
 * @author Anyi Lin - 10158 Scott's Bots
 * @version 1.0, 5/6/2024
 */
//@Configurable
@Autonomous(name = "Forward Localizer Tuner", group = ".Localization")
public class ForwardTuner extends OpMode {
    private Follower follower;
    private DashboardPoseTracker dashboardPoseTracker;

    private TelemetryManager telemetryM;

    public static double DISTANCE = 48;

    /**
     * This initializes the PoseUpdater as well as the FTC Dashboard telemetry.
     */
    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);

        dashboardPoseTracker = follower.getDashboardPoseTracker();

        telemetryM = Panels.getTelemetry();
        telemetryM.debug("Pull your robot forward " + DISTANCE + " inches. Your forward ticks to inches will be shown on the telemetry.");
        telemetryM.update(telemetry);
    }

    /**
     * This updates the robot's pose estimate, and updates the FTC Dashboard telemetry with the
     * calculated multiplier and draws the robot.
     */
    @Override
    public void loop() {
        follower.update();

        telemetryM.debug("distance moved", follower.getPose().getX());
        telemetryM.debug("The multiplier will display what your forward ticks to inches should be to scale your current distance to " + DISTANCE + " inches.");
        telemetryM.debug("multiplier", DISTANCE / (follower.getPose().getX() / follower.getPoseTracker().getLocalizer().getForwardMultiplier()));
        telemetryM.update(telemetry);

        Drawing.drawPoseHistory(dashboardPoseTracker);
        Drawing.drawRobot(follower.getPose());
        Drawing.sendPacket();
    }
}
