package com.snobot.simulator.simulator_components.navx;

import org.junit.Assert;
import org.junit.Test;

import com.kauailabs.navx.frc.AHRS;
import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.jni.RegisterCallbacksJni;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorTest;

import edu.wpi.first.wpilibj.I2C;;

public class TestI2CNavx extends BaseSimulatorTest
{

    @Test
    public void testConstruction() throws InterruptedException
    {
        for (I2C.Port port : I2C.Port.values())
        {
            RegisterCallbacksJni.sI2C_FACTORY.setDefaultWrapper(port.ordinal(), I2CNavxSimulator.class);
        }

        // Port = 0
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().reset();
        Assert.assertEquals(0, SensorActuatorRegistry.get().getGyros().size());

        new AHRS(I2C.Port.kOnboard);
        Assert.assertEquals(3, SensorActuatorRegistry.get().getGyros().size());
        Assert.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getPortList().contains(250));
        Assert.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getPortList().contains(251));
        Assert.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getPortList().contains(252));

        // Port = 1
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().reset();
        Assert.assertEquals(0, SensorActuatorRegistry.get().getGyros().size());

        new AHRS(I2C.Port.kMXP);
        Assert.assertEquals(3, SensorActuatorRegistry.get().getGyros().size());
        Assert.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getPortList().contains(253));
        Assert.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getPortList().contains(254));
        Assert.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getPortList().contains(255));
    }

    @Test
    public void testI2CNavx() throws InterruptedException
    {
        RegisterCallbacksJni.sI2C_FACTORY.setDefaultWrapper(0, I2CNavxSimulator.class);

        final int sleepTime = 100;
        AHRS navx = new AHRS(I2C.Port.kOnboard);
        navx.enableLogging(true);

        int yawHandle = 250;
        int pitchHandle = 251;
        int rollHandle = 252;

        Assert.assertEquals(3, SensorActuatorRegistry.get().getGyros().size());

        Thread.sleep(500);
        Assert.assertEquals(0, DataAccessorFactory.getInstance().getGyroAccessor().getAngle(yawHandle), DOUBLE_EPSILON);
        Assert.assertEquals(0, DataAccessorFactory.getInstance().getGyroAccessor().getAngle(pitchHandle), DOUBLE_EPSILON);
        Assert.assertEquals(0, DataAccessorFactory.getInstance().getGyroAccessor().getAngle(rollHandle), DOUBLE_EPSILON);
        Assert.assertEquals(0, navx.getYaw(), DOUBLE_EPSILON);
        Assert.assertEquals(0, navx.getPitch(), DOUBLE_EPSILON);
        Assert.assertEquals(0, navx.getRoll(), DOUBLE_EPSILON);

        DataAccessorFactory.getInstance().getGyroAccessor().setAngle(yawHandle, 180);
        DataAccessorFactory.getInstance().getGyroAccessor().setAngle(pitchHandle, -180);
        DataAccessorFactory.getInstance().getGyroAccessor().setAngle(rollHandle, 30);
        Thread.sleep(sleepTime);

        Assert.assertEquals(180, DataAccessorFactory.getInstance().getGyroAccessor().getAngle(yawHandle), DOUBLE_EPSILON);
        Assert.assertEquals(-180, DataAccessorFactory.getInstance().getGyroAccessor().getAngle(pitchHandle), DOUBLE_EPSILON);
        Assert.assertEquals(30, DataAccessorFactory.getInstance().getGyroAccessor().getAngle(rollHandle), DOUBLE_EPSILON);
        Assert.assertEquals(180, navx.getYaw(), DOUBLE_EPSILON);
        Assert.assertEquals(-180, navx.getPitch(), DOUBLE_EPSILON);
        Assert.assertEquals(30, navx.getRoll(), DOUBLE_EPSILON);

        // Test wrap around
        DataAccessorFactory.getInstance().getGyroAccessor().setAngle(yawHandle, -181);
        DataAccessorFactory.getInstance().getGyroAccessor().setAngle(pitchHandle, 700);
        DataAccessorFactory.getInstance().getGyroAccessor().setAngle(rollHandle, -470);
        Thread.sleep(sleepTime);
        Assert.assertEquals(-181, DataAccessorFactory.getInstance().getGyroAccessor().getAngle(yawHandle), DOUBLE_EPSILON);
        Assert.assertEquals(700, DataAccessorFactory.getInstance().getGyroAccessor().getAngle(pitchHandle), DOUBLE_EPSILON);
        Assert.assertEquals(-470, DataAccessorFactory.getInstance().getGyroAccessor().getAngle(rollHandle), DOUBLE_EPSILON);
        Assert.assertEquals(179, navx.getYaw(), DOUBLE_EPSILON);
        Assert.assertEquals(-20, navx.getPitch(), DOUBLE_EPSILON);
        Assert.assertEquals(-110, navx.getRoll(), DOUBLE_EPSILON);
    }
}
