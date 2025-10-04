package net.justlearning.arslaan3102.arslaansmagichax.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleManager {
    private static List<Module> modules = new ArrayList<>();
    public static ModuleManager INSTANCE = new ModuleManager();
    public void register(Module module) {
        modules.add(module);
    }

    public static List<Module> getAllModules() {
        return modules;
    }

    public static List<Module> getEnabledModules() {
        return modules.stream().filter(Module::isEnabled).collect(Collectors.toList());
    }

    public static void tick() {
        for (Module module : modules) {
            module.tick();
        }
    }
}
