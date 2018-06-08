package com.snobot.simulator.jni;

import com.snobot.simulator.JniLibraryResourceLoader;

import edu.wpi.first.wpilibj.hal.HAL;

public class BaseSnobotJni
{
    static
    {
        JniLibraryResourceLoader.loadLibrary("wpiutil");
        JniLibraryResourceLoader.loadLibrary("wpiHal");
        JniLibraryResourceLoader.loadLibrary("halsim_adx_gyro_accelerometer");
        // JniLibraryResourceLoader.loadLibrary("navx_simulator");
        // JniLibraryResourceLoader.loadLibrary("navx_simulator_jni");
        JniLibraryResourceLoader.loadLibrary("adx_family_jni");
        JniLibraryResourceLoader.loadLibrary("CTRE_PhoenixCCI");

        HAL.initialize(500, 0);
        RegisterCallbacksJni.reset();
    }
}
