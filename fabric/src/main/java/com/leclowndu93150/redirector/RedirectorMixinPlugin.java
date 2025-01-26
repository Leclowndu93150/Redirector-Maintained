package com.leclowndu93150.redirector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class RedirectorMixinPlugin implements IMixinConfigPlugin {
    private final RedirectorTransformer transformer = new RedirectorTransformer();

    @Override
    public void onLoad(String mixinPackage) {
        Constants.LOG.info("RedirectorMixinPlugin loaded");
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        if ("java/lang/Enum".equals(targetClass.superName)) {
            try {
                ClassWriter cw = new ClassWriter(0);
                targetClass.accept(cw);
                byte[] transformed = transformer.transform(null, targetClassName, null, null, cw.toByteArray());
                if (transformed != null) {
                    Constants.LOG.info("Transformed enum class: {}", targetClassName);
                }
            } catch (Exception e) {
                Constants.LOG.error("Failed transforming: {}", targetClassName, e);
            }
        }
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return true;
    }
}