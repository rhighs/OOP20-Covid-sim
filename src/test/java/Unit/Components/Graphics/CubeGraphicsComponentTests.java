package Unit.Components.Graphics;

import Components.Graphics.CubeGraphicsComponent;
import Components.Graphics.GraphicsComponent;
import Environment.Services.Graphical.Graphics;
import Simulation.Entity;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class CubeGraphicsComponentTests {

    private final Graphics mockedGraphics;

    private final Entity mockedEntity;

    private final GraphicsComponent graphicsComponent;

    public CubeGraphicsComponentTests() {
        mockedGraphics = mock(Graphics.class);
        mockedEntity = mock(Entity.class);

        graphicsComponent = new CubeGraphicsComponent(mockedGraphics, mockedEntity);
    }

    @Test
    public void show_success_noCondition() {
        verifyNoMoreInteractions(mockedGraphics);
        verifyNoMoreInteractions(mockedEntity);
    }
}
