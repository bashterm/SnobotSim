/*
 * AModuleWrapper.h
 *
 *  Created on: May 3, 2017
 *      Author: PJ
 */

#ifndef SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_AMODULEWRAPPER_H_
#define SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_AMODULEWRAPPER_H_

#include <string>

#include "SnobotSim/ExportHelper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/ISensorWrapper.h"

class AModuleWrapper : public virtual ISensorWrapper
{
public:
    explicit AModuleWrapper(const std::string& aName) :
            mName(aName),
            mWantsHidden(false),
            mInitialized(false)
    {
    }
    virtual ~AModuleWrapper()
    {
    }

    bool IsInitialized() override
    {
        return mInitialized;
    }

    void SetInitialized(bool aIsInitialized) override
    {
        mInitialized = aIsInitialized;
    }

    const std::string& GetName() override
    {
        return mName;
    }

    void SetName(const std::string& aName) override
    {
        mName = aName;
    }

    bool WantsHidden() override
    {
        return mWantsHidden;
    }

    void SetWantsHidden(bool aVisible) override
    {
        mWantsHidden = aVisible;
    }

protected:
    std::string mName;
    bool mWantsHidden;
    bool mInitialized;
};

#endif // SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_AMODULEWRAPPER_H_
