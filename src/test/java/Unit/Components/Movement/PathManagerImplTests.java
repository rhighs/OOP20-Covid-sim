package Unit.Components.Movement;

import Components.Movement.PathManager;
import Components.Movement.PathManagerImpl;
import Environment.Services.Map.MainMapImpl;
import Environment.Services.Map.PathFinderExecutor;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.scene.Spatial;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class PathManagerImplTests {

    private final MainMapImpl mockedMap;

    private final Spatial mockedSpatial;

    private final BetterCharacterControl mockedControl;

    private final PathFinderExecutor mockedPathFinder;

    private final PathManager pathManager;

    public PathManagerImplTests() {
        mockedMap = mock(MainMapImpl.class);
        mockedSpatial = mock(Spatial.class);
        mockedControl = mock(BetterCharacterControl.class);
        mockedPathFinder = mock(PathFinderExecutor.class);

        doReturn(mockedControl).when(mockedSpatial).getControl(eq(BetterCharacterControl.class));
        doReturn(mockedPathFinder).when(mockedMap).createPathFinderExecutor();

        pathManager = new PathManagerImpl(mockedMap, mockedSpatial);
    }

    @Test
    void requestNewPath_success_noCondition() {
        //  doReturn(mockedPathFinder).when().

        //pathManager.requestNewPath();

    }

    @AfterEach
    void VerifyNoOtherCalls() {
        verify(mockedSpatial, times(1)).getControl(eq(BetterCharacterControl.class));
        verify(mockedMap, times(1)).createPathFinderExecutor();

        verifyNoMoreInteractions(mockedMap);
        verifyNoMoreInteractions(mockedSpatial);
        verifyNoMoreInteractions(mockedControl);
    }
}
