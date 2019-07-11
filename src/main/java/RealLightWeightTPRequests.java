import org.bukkit.plugin.java.JavaPlugin;

public class RealLightWeightTPRequests extends JavaPlugin{

    public static RealLightWeightTPRequests realLightWeightTPRequests;

    private TP tp;

    @Override
    public void onEnable() {

        realLightWeightTPRequests = this;

        this.tp = new TP();
        getCommand("TP").setExecutor(this.tp);
        getCommand("TPA").setExecutor(this.tp);
        getCommand("TPD").setExecutor(this.tp);
    }

    @Override
    public void onDisable() {

        this.tp.onDisable();
    }

}
