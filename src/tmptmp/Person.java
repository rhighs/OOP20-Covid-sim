package tmptmp;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Random;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.scene.Node;
import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.bullet.control.BetterCharacterControl;
import Components.PathCalculator;

public class Person implements tmptmp.Entity {
    
    public static class Mask {
        public enum Status {
            UP, DOWN,
        }
        public enum Protection {
            FP1, FP2, FP3,
        }
        
        public Status status = Status.UP;
        public Protection protection = Protection.FP1;
        
        public Mask() { }
        
        public Mask(Status status, Protection protection) {
            this.status = status;
            this.protection = protection;
        }
    }
    
    final private GraphicsComponent gfxComp;
    final private PhysicsComponent physComp;
    final private MovementComponent movComp;
    private Spatial spatial;
    private BetterCharacterControl spatialControl;
    private boolean infected;
    private Mask mask;
    
    private Set<Person> lastNearPeople;

    public Person(final Vector3f spawnPoint, BulletAppState bState,
                  Node rootNode, PathCalculator pathCalc, AssetManager assetManager) {
        this.gfxComp  = new CubeGFXComponent(this);
        this.physComp = new PhysicsComponent(this, bState);
        this.physComp.initProximityBox(2);
        this.movComp  = new PathFindingMovement(this, pathCalc);
        this.spatial  = this.gfxComp.create("this should be an unique name", assetManager);
        this.spatial.setLocalTranslation(spawnPoint);
        Random rng = new Random();
        this.spatialControl = new BetterCharacterControl(1f, 9f, (rng.nextInt(10) + 1));
        this.spatial.addControl(spatialControl);
        this.spatialControl.setGravity(new Vector3f(0, -40, 0));
        this.spatialControl.setJumpForce(new Vector3f(0, 1, 0));
        bState.getPhysicsSpace().add(spatialControl);
        bState.getPhysicsSpace().add(spatial);
        //this.wearMask(new MaskImpl(protection, Mask.MaskStatus.UP));
    }

    @Override
    public Spatial getSpatial() {
        return this.spatial;
    }

    @Override
    public void update(float tpf) {
        movComp.update(tpf);
        physComp.update();
    }

    @Override
    public Vector3f getPos() {
        return spatial.getLocalTranslation();
    }

    @Override
    public void adjustPosition(Vector3f distance) {
        spatialControl.setWalkDirection(distance.normalize().mult(10));
        spatialControl.setViewDirection(distance.negate());
    }

    @Override
    public void stopMoving() {
        this.spatialControl.setWalkDirection(Vector3f.ZERO);
    }

    public void maskDown() {
        this.mask.status = Mask.Status.DOWN;
    }

    public boolean isInfected() {
        return infected;
    }

    public void infect() {
        infected = true;
        gfxComp.setColor(ColorRGBA.Red);
    }

    public void setLastNear(Set<Person> people) {
        lastNearPeople = new HashSet<>(people);
    }

    public Set<Person> getLastNear() {
        return lastNearPeople;
    }

    public void setInfectionDistance(float distance) {
        physComp.initProximityBox(distance);
    }

    public Set<Entity> getNearEntities() {
        return physComp.getNearEntities();
    }

    public Set<Person> getNearPeople() {
        return getNearEntities()
                .stream()
                //.filter(e -> (Person) e) e.getIdentificator() == Entity.Identificator.PERSON)
                .map(e -> (Person) e)
                .collect(Collectors.toSet());
    }
}
