package org.firstinspires.ftc.teamcode.pedroPathing;

import com.bylazar.ftcontrol.panels.configurables.annotations.Configurable;
import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerBuilder;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.drivetrain.MecanumConstants;
import com.pedropathing.localization.GoBildaPinpointDriver;
import com.pedropathing.localization.constants.OTOSConstants;
import com.pedropathing.localization.constants.PinpointConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

//@Configurable
public class Constants {

    public static FollowerConstants followerConstants = new FollowerConstants()
            .centripetalScaling(0.005)
            .forwardZeroPowerAcceleration(-41.278)
            .lateralZeroPowerAcceleration(-59.7819)
            .translationalPIDFCoefficients(new PIDFCoefficients(0.1,0,0.01,0))
            .headingPIDFCoefficients(new PIDFCoefficients(2,0,0.1,0))
            .drivePIDFCoefficients(new FilteredPIDFCoefficients(0.1,0,0,0.6,0))
            .mass(5);

    public static MecanumConstants driveConstants = new MecanumConstants()
            .xMovement(57.8741)
            .yMovement(52.295)
            .maxPower(1)
            .leftFrontMotorName("lf")
            .leftFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .leftRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightRearMotorDirection(DcMotorSimple.Direction.FORWARD)
            .motorCachingThreshold(0.01)
            .useBrakeModeInTeleOp(false);

    public static OTOSConstants otosConstants = new OTOSConstants()
            .offset(new SparkFunOTOS.Pose2D(3,2,Math.toRadians(270)));

    public static PinpointConstants pinpointConstants = new PinpointConstants()
            .forwardY(-5)
            .strafeX(0.5)
            .distanceUnit(DistanceUnit.INCH)
            .hardwareMapName("pinpoint")
            .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.REVERSED);

    public static PathConstraints pathConstraints = new PathConstraints(0.99, 100, 3, 1);

    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .mecanumDrivetrain(driveConstants)
//                .OTOSLocalizer(otosConstants)
                .pinpointLocalizer(pinpointConstants)
                .pathConstraints(pathConstraints)
                .build();
    }
}