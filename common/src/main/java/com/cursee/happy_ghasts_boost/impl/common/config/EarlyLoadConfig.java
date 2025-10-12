package com.cursee.happy_ghasts_boost.impl.common.config;

import com.cursee.happy_ghasts_boost.Constants;
import com.cursee.happy_ghasts_boost.platform.Services;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public class EarlyLoadConfig implements IMixinConfigPlugin {

  public static final String LOG_PREFIX = "[Faster Happy Ghasts] ";

  public static boolean debugging = false;
  public static int secondsUntilBoost = 4;
  public static float forwardMultiplier = 2.0f;
  public static float potionMultiplier = 2.5f;
  public static float potionMultiplierTwo = 3.5f;
  public static boolean displayBoostBar = true;

  public static void createOrLoadConfiguration() {

    String configDirectoryPathString = Services.PLATFORM.getGameDirectory() + File.separator + "config";
    File configDirectory = new File(configDirectoryPathString);
    if (!configDirectory.isDirectory() && !configDirectory.mkdirs()) {
      System.out.println(LOG_PREFIX + "Failed to create config directory, Faster Happy Ghasts retaining default values.");
      return;
    }

    String configFileNameString = Constants.MOD_ID + "-common.toml";
    String configFilePathString = configDirectoryPathString + File.separator + configFileNameString;
    File configFile = new File(configFilePathString);

    if (!configFile.exists()) {
      try (InputStream inputStream = EarlyLoadConfig.class.getClassLoader().getResourceAsStream("assets/" + configFileNameString)) {

        if (inputStream == null) {
          System.out.println(LOG_PREFIX + "Failed to initialize InputStream instance for assets/" + configFileNameString);
          return;
        }

        try (BufferedInputStream bis = new BufferedInputStream(inputStream); BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(configFile))) {
          byte[] buffer = new byte[8192];

          int bytesRead;
          while ((bytesRead = bis.read(buffer)) != -1) {
            bos.write(buffer, 0, bytesRead);
          }
        }

        System.out.println(LOG_PREFIX + "Created new configuration file: " + configFilePathString);
      } catch (IOException e) {
        System.out.println(LOG_PREFIX + "Failed to read internal resource: assets/" + configFileNameString);
        System.out.println(LOG_PREFIX + e.getMessage());
      }
    } else {

      // config file exists at expected location

      System.out.println(LOG_PREFIX + "Reading configuration file: " + configFilePathString);

      try {
        SimpleConfig config = new SimpleConfig(configFilePathString);

        debugging = config.getBoolean("debugging", debugging);
        secondsUntilBoost = config.getInteger("seconds_until_boost", secondsUntilBoost);
        forwardMultiplier = config.getFloat("forward_multiplier", forwardMultiplier);
        potionMultiplier = config.getFloat("potion_multiplier", potionMultiplier);
        potionMultiplierTwo = config.getFloat("potion_multiplier_two", potionMultiplierTwo);
        displayBoostBar = config.getBoolean("display_boost_bar", displayBoostBar);

//        System.out.println(LOG_PREFIX + "Configured Values of seconds_until_boost forward_multiplier potion_multiplier");
//        System.out.println(secondsUntilBoost);
//        System.out.println(forwardMultiplier);
//        System.out.println(potionMultiplier);
//        System.out.println(potionMultiplierTwo);
      } catch (IOException ignored) {
        System.out.println(LOG_PREFIX + "Failed to read " + configFilePathString + ", retaining default config values");
      }

//            Toml toml = new Toml().read(configFile);
//
//            render_block_particles = toml.getBoolean("render_block_particles");
//            render_experience_cost = toml.getBoolean("render_experience_cost");
//            render_table_item = toml.getBoolean("render_table_item");
    }
  }

  @Override
  public void onLoad(String s) {
    createOrLoadConfiguration();
  }

  @Override
  public String getRefMapperConfig() {
    return "";
  }

  @Override
  public boolean shouldApplyMixin(String s, String s1) {
    return true;
  }

  @Override
  public void acceptTargets(Set<String> set, Set<String> set1) {

  }

  @Override
  public List<String> getMixins() {
    return List.of();
  }

  @Override
  public void preApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) {

  }

  @Override
  public void postApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) {

  }
}