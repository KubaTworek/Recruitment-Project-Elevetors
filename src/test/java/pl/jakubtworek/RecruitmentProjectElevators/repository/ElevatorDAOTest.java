package pl.jakubtworek.RecruitmentProjectElevators.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import pl.jakubtworek.RecruitmentProjectElevators.data.Elevators;
import pl.jakubtworek.RecruitmentProjectElevators.data.ElevatorsTest;
import pl.jakubtworek.RecruitmentProjectElevators.model.Elevator;
import pl.jakubtworek.RecruitmentProjectElevators.model.TypeOfTarget;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ElevatorDAOTest {
    @Mock
    private Elevators elevators;

    private ElevatorDAO elevatorDAO;

    @BeforeEach
    void setup() {
        elevators = mock(Elevators.class);

        elevatorDAO = new ElevatorDAOImpl(elevators);

        ElevatorsTest elevatorsTest = new ElevatorsTest();
        when(elevators.getElevators()).thenReturn(elevatorsTest.getElevatorsWithChanges());
    }

    @Test
    void shouldReturnAllElevators() {
        // when
        List<Elevator> returnedElevators = elevatorDAO.findAll();

        // then
        assertEquals(16, returnedElevators.size());
    }

    @Test
    void shouldReturnElevatorById() {
        // when
        Elevator returnedElevator = elevatorDAO.findById(2).orElse(null);

        // then
        assertEquals(2, returnedElevator.getId());
        assertEquals(8, returnedElevator.getNumberOfFloor());
        assertEquals(6, returnedElevator.getPlannedFloors().peek().numberOfFloor());
        assertEquals(TypeOfTarget.DESTINATION, returnedElevator.getPlannedFloors().peek().typeOfTarget());
        assertEquals(1, returnedElevator.getPlannedFloors().size());
    }

    @Test
    void shouldReturnNullByWrongId() {
        // when
        Elevator returnedElevator = elevatorDAO.findById(99).orElse(null);

        // then
        assertNull(returnedElevator);
    }

    @Test
    void shouldReturnAllElevatorsToMove() {
        // when
        List<Elevator> returnedElevators = elevatorDAO.findElevatorToMove();

        // then
        assertEquals(2, returnedElevators.size());
    }
}