package com.leclowndu93150.redirector;

import cpw.mods.modlauncher.LaunchPluginHandler;
import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.api.IEnvironment;
import cpw.mods.modlauncher.api.ITransformationService;
import cpw.mods.modlauncher.api.ITransformer;
import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

@ParametersAreNonnullByDefault
@SuppressWarnings("unchecked")
public class RedirectorTransformer implements ITransformationService {
    @NotNull
    @Override
    public String name() {
        return Constants.MOD_ID;
    }

    @Override public void initialize(IEnvironment environment) {}
    @Override
    public List<Resource> beginScanning(IEnvironment environment) {
        return List.of();
    }
    @Override public void onLoad(IEnvironment env, Set<String> otherServices) {}

    @Override
    public @NotNull List<ITransformer> transformers() {
        return Collections.emptyList();
    }

    static {
        try {
            Field field = Launcher.class.getDeclaredField("launchPlugins");
            field.setAccessible(true);
            Object launchPluginHandler = field.get(Launcher.INSTANCE);
            field = LaunchPluginHandler.class.getDeclaredField("plugins");
            field.setAccessible(true);
            ((Map<String, ILaunchPluginService>)field.get(launchPluginHandler)).put(Constants.MOD_ID, new RedirectorLaunchPluginService());
            Constants.LOG.info("RedirectorForge CoreMod initialized successfully!");
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}