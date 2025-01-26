package com.leclowndu93150.redirector;


import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class RedirectorNeo {

    public RedirectorNeo(IEventBus eventBus) {
        CommonClass.init();
    }
}