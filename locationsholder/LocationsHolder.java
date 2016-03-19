package locationsholder;

import locationsholder.manager.VariableManager;
import com.google.common.collect.Maps;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

/**
 * Created by Felipe de Lucca on 18/03/2016.
 */
public class LocationsHolder extends JavaPlugin {

    public static Map<String, Object> variables = Maps.newHashMap();

    @Override
    public void onEnable() {
        VariableManager.getInstance().setup(this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        VariableManager.getInstance().saveVariables();
        super.onDisable();
    }
}
