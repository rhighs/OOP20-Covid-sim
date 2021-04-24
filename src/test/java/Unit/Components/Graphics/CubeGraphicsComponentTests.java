package Unit.Components.Graphics;

import Components.Graphics.CubeGraphicsComponent;
import Components.Graphics.GraphicsComponent;
import Environment.Services.Graphical.Graphics;
import Simulation.Entity;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class CubeGraphicsComponentTests {

    private final Graphics mockedGraphics;

    private final Entity mockedEntity;

    private final GraphicsComponent graphicsComponent;

    private final Geometry mockedGeometry;

    private int addToSceneInvocations = 1;

    public CubeGraphicsComponentTests() {
        mockedGraphics = mock(Graphics.class);
        mockedEntity = mock(Entity.class);
        mockedGeometry = mock(Geometry.class);

        doReturn(new Material()).when(mockedGraphics)
                .createShadedMaterial(any(ColorRGBA.class), any(ColorRGBA.class));

        doNothing().when(mockedGeometry).setMaterial(any(Material.class));
        doReturn(null).when(mockedGeometry).scale(anyFloat());
        doNothing().when(mockedGeometry).setShadowMode(any());

        graphicsComponent = new CubeGraphicsComponent(mockedGraphics, mockedEntity, mockedGeometry);
    }

    @Test
    public void show_success_noCondition() {
        doNothing().when(mockedGraphics).addToScene(any(Spatial.class));

        graphicsComponent.show();

        addToSceneInvocations++;
    }

    @Test
    public void getSpatial_success_noCondition() {
        final var spatial = graphicsComponent.getSpatial();

        assertNotNull(spatial);
    }

    @Test
    public void changeColor_success_noCondition() {
        doNothing().when(mockedGraphics).changeMaterialColor(eq(mockedGeometry), any(ColorRGBA.class));

        graphicsComponent.changeColor(new ColorRGBA());

        verify(mockedGraphics, times(1))
                .changeMaterialColor(eq(mockedGeometry), any(ColorRGBA.class));
    }

    @AfterEach
    public void verifyAfter() {
        verify(mockedGraphics, times(addToSceneInvocations)).addToScene(any(Spatial.class));
        verify(mockedGraphics, times(1))
                .createShadedMaterial(any(ColorRGBA.class), any(ColorRGBA.class));

        verify(mockedGeometry, times(1)).setMaterial(any(Material.class));
        verify(mockedGeometry, times(1)).scale(anyFloat());
        verify(mockedGeometry, times(1)).setShadowMode(any());

        verifyNoMoreInteractions(mockedGraphics);
        verifyNoMoreInteractions(mockedEntity);
        verifyNoMoreInteractions(mockedGeometry);
    }
}
