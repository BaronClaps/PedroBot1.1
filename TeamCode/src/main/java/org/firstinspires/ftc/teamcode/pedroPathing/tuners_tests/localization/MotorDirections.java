package org.firstinspires.ftc.teamcode.pedroPathing.tuners_tests.localization;

import com.bylazar.ftcontrol.panels.Panels;
import com.bylazar.ftcontrol.panels.configurables.annotations.Configurable;
import com.bylazar.ftcontrol.panels.integration.TelemetryManager;
import com.pedropathing.drivetrain.MecanumConstants;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import java.util.Arrays;
import java.util.List;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

//@Configurable
@TeleOp(name = "Motor Directions", group = "Teleop Test")
public class MotorDirections extends OpMode {
    public static MecanumConstants constants;
    public static double test = 1;

    private TelemetryManager telemetryM;

    private DcMotorEx leftFront;
    private DcMotorEx leftRear;
    private DcMotorEx rightFront;
    private DcMotorEx rightRear;
    private List<DcMotorEx> motors;

    @Override
    public void init() {
        constants = Constants.driveConstants;

        leftFront = hardwareMap.get(DcMotorEx.class, constants.leftFrontMotorName);
        leftRear = hardwareMap.get(DcMotorEx.class, constants.leftRearMotorName);
        rightRear = hardwareMap.get(DcMotorEx.class, constants.rightRearMotorName);
        rightFront = hardwareMap.get(DcMotorEx.class, constants.rightFrontMotorName);
        leftFront.setDirection(constants.leftFrontMotorDirection);
        leftRear.setDirection(constants.leftRearMotorDirection);
        rightFront.setDirection(constants.rightFrontMotorDirection);
        rightRear.setDirection(constants.rightRearMotorDirection);

        motors = Arrays.asList(leftFront, leftRear, rightFront, rightRear);

        for (DcMotorEx motor : motors) {
            MotorConfigurationType motorConfigurationType = motor.getMotorType().clone();
            motorConfigurationType.setAchieveableMaxRPMFraction(1.0);
            motor.setMotorType(motorConfigurationType);
        }

        for (DcMotorEx motor : motors) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }

        telemetryM = Panels.getTelemetry();
        telemetryM.debug("This will allow you to test the directions of your motors. You can change the directions in FTCDashboard -> FollowerConstants.");
        telemetryM.update(telemetry);
    }

    @Override
    public void loop() {
        leftFront.setDirection(constants.leftFrontMotorDirection);
        leftRear.setDirection(constants.leftRearMotorDirection);
        rightFront.setDirection(constants.rightFrontMotorDirection);
        rightRear.setDirection(constants.rightRearMotorDirection);

        if(gamepad1.a)
            leftFront.setPower(1);
        else
            leftFront.setPower(0);

        if(gamepad1.y)
            leftRear.setPower(1);
        else
            leftRear.setPower(0);

        if(gamepad1.b)
            rightFront.setPower(1);
        else
            rightFront.setPower(0);

        if(gamepad1.x)
            rightRear.setPower(1);
        else
            rightRear.setPower(0);

        telemetryM.debug("Press A to spin the left front motor at 100% power");
        telemetryM.debug("Press Y to spin the left rear motor at 100% power");
        telemetryM.debug("Press B to spin the right front motor at 100% power");
        telemetryM.debug("Press X to spin the right rear motor at 100% power");
        telemetryM.debug("Left Front Motor Direction: " + constants.leftFrontMotorDirection);
        telemetryM.debug("Left Rear Motor Direction: "+ constants.leftRearMotorDirection);
        telemetryM.debug("Right Front Motor Direction: "+ constants.rightFrontMotorDirection);
        telemetryM.debug("Right Rear Motor Direction: "+ constants.rightRearMotorDirection);
        telemetryM.update(telemetry);
    }
}
