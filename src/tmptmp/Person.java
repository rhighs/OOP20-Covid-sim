package Simulation;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.io.IOException;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.scene.Node;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.math.ColorRGBA;
import com.jme3.export.Savable;

public class Person implements Entity {
    final private GraphicsComponent gfxComp;
    final private PhysicsComponent physComp;
    final private MovementComponent movComp;
    private Spatial spatial;
    private Set<Person> lastNearPeople;
    private boolean infected;
    private Mask mask;
    private Vector3f pos;

    public Person(Mask.MaskProtection protection, final Vector3f spawnPoint, BulletAppState bState,
                  Node rootNode, PathCalculator pathCalc, AssetManager assetManager) {
        this.gfxComp  = new CubeGFXComponent(this);
        this.physComp = new PhysicsComponent(this);
        this.physComp.initProximityBox(2);
        this.movComp  = new PathFindingMovement(this);
        this.spatial  = this.gfxComp.create("this should be an unique name", assetManager);
        this.spatial.setLocalTranslation(spawnPoint);
        this.wearMask(new MaskImpl(protection, Mask.MaskStatus.UP));
    }

    @Override
    public Spatial getSpatial() {
        return this.spatial;
    }

    @Override
    public Mask getMask() {
        return mask;
    }

    @Override
    public void wearMask(Mask m) {
        this.mask = m;
    }

    @Override
    public boolean isInfected() {
        return infected;
    }

    @Override
    public void infect() {
        infected = true;
        gfx.changeColor(ColorRGBA.Red);
    }

    public void setLastNear(Set<Person> people) {
        lastNearPeople = new HashSet<Person>(people);
    }

    public Set<Person> getLastNear() {
        return lastNearPeople;
    }

    public void setInfectionDistance(float distance) {
        phyc.initProximityBox(distance);
    }

    @Override
    public void update(float tpf) {
        mov.update(tpf);
        phyc.update();
    }

    @Override
    public void maskDown() {
        mask.maskDown();
    }

    @Override
    public Vector3f getPos() {
        return spatial.getLocalTranslation();
    }

    public Set<Entity> getNearEntities() {
        return phyc.getNearEntities();
    }

    public Set<Person> getNearPeople() {
        return getNearEntities()
                .stream()
                .filter(e -> e.getIdentificator() == Entity.Identificator.PERSON)
                .map(e -> (Person) e)
                .collect(Collectors.toSet());
    }

    // @Override
    // public void write(JmeExporter arg0) throws IOException {
    //     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    // }

    // @Override
    // public void read(JmeImporter arg0) throws IOException {
    //     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    // }
}
