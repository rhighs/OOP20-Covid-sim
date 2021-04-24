package Unit.Components.Movement;

import Components.Movement.MovementHandler;
import Components.Movement.MovementHandlerImpl;
import Components.Movement.PathManager;
import Components.Movement.StuckManager;
import com.jme3.ai.navmesh.Cell;
import com.jme3.ai.navmesh.Path;
import com.jme3.math.Vector3f;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class MovementHandlerImplTests {

    private final PathManager mockedPathManager;

    private final StuckManager mockedStuckManager;

    private final MovementHandler movementHandler;

    public MovementHandlerImplTests() {
        mockedPathManager = mock(PathManager.class);
        mockedStuckManager = mock(StuckManager.class);

        movementHandler = new MovementHandlerImpl(mockedPathManager, mockedStuckManager);
    }

    @Test
    void update_requestsANewPath_whenNoWaypointIsAvailableAndANewPathIsNotRequested() {
        doReturn(null).when(mockedPathManager).getWaypoint();
        doReturn(false).when(mockedPathManager).isPathRequested();
        doNothing().when(mockedPathManager).requestNewPath();

        movementHandler.update();

        verify(mockedPathManager, times(1)).requestNewPath();
        verify(mockedPathManager, times(1)).isPathRequested();
    }

    @Test
    void update_waits_whenANewPathIsRequestedButIsNotAvailable() {
        doReturn(null).when(mockedPathManager).getWaypoint();
        doReturn(true).when(mockedPathManager).isPathRequested();
        doReturn(false).when(mockedPathManager).isPathReady();

        movementHandler.update();

        verify(mockedPathManager, times(1)).isPathReady();
        verify(mockedPathManager, times(1)).isPathRequested();
    }

    @Test
    void update_requestsANewPath_whenTheComponentIsStuckInTheSamePosition() {
        var path = new Path();
        path.addWaypoint(new Vector3f(0, 0, 0), new Cell());

        doReturn(path.getFirst()).when(mockedPathManager).getWaypoint();
        doNothing().when(mockedPathManager).requestNewPath();
        doReturn(true).when(mockedStuckManager).isStuck();
        doNothing().when(mockedStuckManager).reset();

        movementHandler.update();

        verify(mockedPathManager, times(1)).requestNewPath();
        verify(mockedStuckManager, times(1)).isStuck();
        verify(mockedStuckManager, times(1)).reset();
    }

    @Test
    void update_success_whenTheComponentIsNearThePosition() {
        var path = new Path();
        path.addWaypoint(new Vector3f(0, 0, 0), new Cell());

        doReturn(path.getFirst()).when(mockedPathManager).getWaypoint();
        doReturn(true).when(mockedPathManager).isPositionNear(any(Path.Waypoint.class));
        doNothing().when(mockedPathManager).setPosition(eq(null));
        doReturn(false).when(mockedStuckManager).isStuck();
        doNothing().when(mockedStuckManager).toggle();

        movementHandler.update();

        verify(mockedPathManager, times(1)).isPositionNear(any(Path.Waypoint.class));
        verify(mockedPathManager, times(1)).setPosition(eq(null));
        verify(mockedStuckManager, times(1)).isStuck();
        verify(mockedStuckManager, times(1)).toggle();
    }

    @Test
    void update_success_noCondition() {
        var path = new Path();
        path.addWaypoint(new Vector3f(0, 0, 0), new Cell());

        doReturn(path.getFirst()).when(mockedPathManager).getWaypoint();
        doReturn(false).when(mockedPathManager).isPositionNear(any(Path.Waypoint.class));
        doNothing().when(mockedPathManager).setPosition(any(Path.Waypoint.class));
        doReturn(false).when(mockedStuckManager).isStuck();

        movementHandler.update();

        verify(mockedPathManager, times(1)).isPositionNear(any(Path.Waypoint.class));
        verify(mockedPathManager, times(1)).setPosition(any(Path.Waypoint.class));
        verify(mockedStuckManager, times(1)).isStuck();
    }

    @AfterEach
    void verifyAfterEachTest() {
        verify(mockedPathManager, times(1)).getWaypoint();

        verifyNoMoreInteractions(mockedPathManager);
        verifyNoMoreInteractions(mockedStuckManager);
    }
}
