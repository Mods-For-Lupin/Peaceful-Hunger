package com.cursee.peaceful_hunger;

import com.cursee.monolib.api.common.sailing.SailingApi;

public class PeacefulHunger {

    public static void init() {
        SailingApi.register(Constants.MOD_ID, Constants.MOD_NAME, Constants.MOD_VERSION, Constants.MOD_PUBLISHER, Constants.MOD_URL);
    }
}