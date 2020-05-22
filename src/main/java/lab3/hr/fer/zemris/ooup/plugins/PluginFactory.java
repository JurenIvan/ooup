package lab3.hr.fer.zemris.ooup.plugins;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PluginFactory {

    public static final String PLUGIN_PATH = "lab3.hr.fer.zemris.ooup.plugins.impl.";
    public static final String PLUGIN_BUILD_PATH = "build\\classes\\java\\main\\lab3\\hr\\fer\\zemris\\ooup\\plugins\\impl";

    public static Plugin newInstance(String pluginName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<Plugin> clazz = (Class<Plugin>) Class.forName(PLUGIN_PATH + pluginName);

        Constructor<?> ctr = clazz.getConstructor();
        return (Plugin) ctr.newInstance();
    }

    public static List<String> availablePlugins() {
        try (Stream<Path> walk = Files.walk(Paths.get(PLUGIN_BUILD_PATH))) {

            return walk
                    .filter(Files::isRegularFile)
                    .map(e -> e.getName(e.getNameCount() - 1).toString())
                    .filter(e -> e.endsWith(".class"))
                    .map(e -> e.substring(0, e.length() - 6))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return List.of();
    }
}
