package com.snobot.simulator.simulator_components.ctre;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJavaTest;

@Tag("CTRE")
public class TestCtreCanTalonControlAppliedThrottle extends BaseSimulatorJavaTest
{
    private static final double sDOUBLE_EPSILON = 1.0 / 1023;

    public static Collection<Integer> getData()
    {
        Collection<Integer> output = new ArrayList<>();

        for (int i = 0; i < 64; ++i)
        {
            output.add(i);
        }

        return output;
    }

    @ParameterizedTest
    @MethodSource("getData")
    public void testSetWithAppliedThrottle(int aCanHandle)
    {
        int rawHandle = aCanHandle + 100;

        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().size());
        TalonSRX canTalon1 = new TalonSRX(aCanHandle);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().size());

        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVoltagePercentage(rawHandle), sDOUBLE_EPSILON);

        canTalon1.set(ControlMode.PercentOutput, -1.0);
        Assertions.assertEquals(-1.0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVoltagePercentage(rawHandle), sDOUBLE_EPSILON);
        Assertions.assertEquals(-1.0, canTalon1.getMotorOutputPercent(), sDOUBLE_EPSILON);

        canTalon1.set(ControlMode.PercentOutput, -0.5);
        Assertions.assertEquals(-0.5, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVoltagePercentage(rawHandle), sDOUBLE_EPSILON);
        Assertions.assertEquals(-0.5, canTalon1.getMotorOutputPercent(), sDOUBLE_EPSILON);

        canTalon1.set(ControlMode.PercentOutput, -0.1);
        Assertions.assertEquals(-0.1, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVoltagePercentage(rawHandle), sDOUBLE_EPSILON);
        Assertions.assertEquals(-0.1, canTalon1.getMotorOutputPercent(), sDOUBLE_EPSILON);

        canTalon1.set(ControlMode.PercentOutput, 0);
        Assertions.assertEquals(0.0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVoltagePercentage(rawHandle), sDOUBLE_EPSILON);
        Assertions.assertEquals(0.0, canTalon1.getMotorOutputPercent(), sDOUBLE_EPSILON);

        canTalon1.set(ControlMode.PercentOutput, .1);
        Assertions.assertEquals(0.1, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVoltagePercentage(rawHandle), sDOUBLE_EPSILON);
        Assertions.assertEquals(0.1, canTalon1.getMotorOutputPercent(), sDOUBLE_EPSILON);

        canTalon1.set(ControlMode.PercentOutput, .5);
        Assertions.assertEquals(0.5, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVoltagePercentage(rawHandle), sDOUBLE_EPSILON);
        Assertions.assertEquals(0.5, canTalon1.getMotorOutputPercent(), sDOUBLE_EPSILON);

        canTalon1.set(ControlMode.PercentOutput, 1);
        Assertions.assertEquals(1.0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVoltagePercentage(rawHandle), sDOUBLE_EPSILON);
        Assertions.assertEquals(1.0, canTalon1.getMotorOutputPercent(), sDOUBLE_EPSILON);
    }
}
