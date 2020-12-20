/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author rob
 */
enum Direction {
    FORWARD, BACKWARD, UP, DOWN, LEFT, RIGHT
}

public class ItemWrapper {

    protected Vector3f v;
    protected Spatial geometry;
    protected String name;

    public Spatial getGeometry() {
        return this.geometry;
    }

    //rotates on its own axis
    public void rotate(final float x, final float y, final float z) {
        this.getGeometry().rotate(x, y, z);
    }

    public void setMaterial(final Material mat) {
        this.getGeometry().setMaterial(mat);
    }

    public void attachToNode(final Node n) {
        n.attachChild(this.getGeometry());
    }

    public Vector3f getPosition() {
        return this.v;
    }

    //sets the the value of v as well
    public void setPosition(final Vector3f pos) {
        this.v.x = pos.x;
        this.v.y = pos.y;
        this.v.z = pos.z;

        this.getGeometry().setLocalTranslation(this.v);
    }

    public void move(final float x, final float y, final float z) {
        this.v.x += x;
        this.v.y += y;
        this.v.z += z;

        this.getGeometry().setLocalTranslation(this.v);
    }

    public void move(Direction d, float step) {
        switch (d) {
            case FORWARD:
                this.v.z += step;
                break;
            case BACKWARD:
                this.v.z -= step;
                break;
            case UP:
                this.v.y += step;
                break;
            case DOWN:
                this.v.y -= step;
                break;
            case RIGHT:
                this.v.x += step;
                break;
            case LEFT:
                this.v.x -= step;
                break;
        }

        this.getGeometry().setLocalTranslation(this.v);

    }

    public void moveOnPlane(final float x, final float y) {
        this.move(x, this.v.y, y);
    }

    /*
    public void moveToPoint(final Vector3f point, final float tpf) {
        final Vector3f toFind = new Vector3f();
        
        for(toFind.z = 0.0f; toFind.z < point.z; toFind.z++){
            for(toFind.y = 0.0f; toFind.y < point.y; toFind.y++){
                for(toFind.x = 0.0f; toFind.x < point.x; toFind.x++){
                    if(isInStroke3D(this.v, point, toFind)){
                        this.setPosition(toFind);
                    }
                }
            }
        }

    }

    private boolean isInStroke3D(final Vector3f v1, final Vector3f v2, final Vector3f toVerify) {
        final float eq = (toVerify.x - v1.x) / (v2.x - v1.x) + (toVerify.y - v1.y) / (v2.y - v1.y) + (toVerify.z - v1.z) / (v2.z - v1.z);
        
        return eq == 0;
    }
    */
}
