package org.firstinspires.ftc.teamcode.pedroPathing.tuners_tests.localization;

import com.bylazar.ftcontrol.panels.Panels;
import com.bylazar.ftcontrol.panels.configurables.annotations.Configurable;
import com.bylazar.ftcontrol.panels.configurables.annotations.IgnoreConfigurable;
import com.bylazar.ftcontrol.panels.integration.TelemetryManager;
import com.bylazar.ftcontrol.panels.json.Look;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.pedropathing.util.DashboardPoseTracker;
import com.pedropathing.util.Drawing;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

/**
 * This is the LocalizationTest OpMode. This is basically just a simple mecanum drive attached to a
 * PoseUpdater. The OpMode will print out the robot's pose to telemetry as well as draw the robot.
 * You should use this to check the robot's localization.
 *
 * @author Anyi Lin - 10158 Scott's Bots
 * @version 1.0, 5/6/2024
 */
@Configurable
@TeleOp(group = "Teleop Test", name = "Localization Test")
public class LocalizationTest extends OpMode {
    public static Follower follower;
    public static String hello = "hello";
    private DashboardPoseTracker dashboardPoseTracker;
    private TelemetryManager telemetryM;

    /**
     * This initializes the PoseUpdater, the mecanum drive motors, and the FTC Dashboard telemetry.
     */
    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);

        dashboardPoseTracker = follower.getDashboardPoseTracker();

        telemetryM = Panels.getTelemetry();
        telemetryM.debug("This will print your robot's position to telemetry while "
                + "allowing robot control through a basic mecanum drive on gamepad 1.");
        telemetryM.update(telemetry);

        follower.update();

        Drawing.drawRobot(follower.getPose());
        Drawing.sendPacket();
    }

    @Override
    public void start() {
        follower.startTeleopDrive();
    }

    /**
     * This updates the robot's pose estimate, the simple mecanum drive, and updates the FTC
     * Dashboard telemetry with the robot's position as well as draws the robot's position.
     */
    @Override
    public void loop() {
        follower.setTeleOpDrive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, true);
        follower.update();

        dashboardPoseTracker.update();

        telemetryM.debug("x:" + follower.getPose().getX());
        telemetryM.debug("y:" + follower.getPose().getY());
        telemetryM.debug("heading:" + follower.getPose().getHeading());
        telemetryM.debug("total heading:" + follower.getTotalHeading());
        telemetryM.update(telemetry);

        Drawing.drawPoseHistory(dashboardPoseTracker);
        Drawing.drawRobot(follower.getPose());
        Drawing.sendPacket();
    }
}
