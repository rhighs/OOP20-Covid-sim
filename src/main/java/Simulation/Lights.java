package Simulation;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import Environment.Services.Graphical.Ambient;
import Environment.Services.Graphical.SimulationCamera;

public class Lights {

    private SimulationCamera simulationCamera;

    private Ambient ambient;

    private final Vector3f sunLightDirection = new Vector3f(0.5f, 0.5f, 0.5f).normalizeLocal();

    private final ColorRGBA AMBIENT_LIGHT_COLOR = ColorRGBA.White.mult(0.3f);

    private final ColorRGBA BACKGROUND_COLOR = ColorRGBA.Cyan;

    public Lights(final Ambient ambient, final SimulationCamera simulationCamera){
        this.simulationCamera = simulationCamera;
        this.ambient = ambient;
        this.ambient.addSunLight(sunLightDirection);
        this.ambient.addAmbientLight(AMBIENT_LIGHT_COLOR);
        this.ambient.setBackgroundColor(BACKGROUND_COLOR);
    }

    public void update(){
        ambient.setCamLightDirection(simulationCamera.getDirection());
    }
}
