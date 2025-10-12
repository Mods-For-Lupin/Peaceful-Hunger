package com.cursee.peaceful_hunger.platform;

import com.cursee.peaceful_hunger.platform.services.IPlatformHelper;
import java.nio.file.Path;
import net.fabricmc.loader.api.FabricLoader;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public Path getGameDirectory() {

        return FabricLoader.getInstance().getGameDir();
    }

    @Override
    public String getGameDirectoryString() {

        return getGameDirectory().toString();
    }
}
