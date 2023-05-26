package ch.zhaw.carewe;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ch.zhaw.carewe.controller.NeedyController;
import ch.zhaw.carewe.model.Needy;
import ch.zhaw.carewe.model.NeedyCreateDTO;
import ch.zhaw.carewe.model.NeedyState;
import ch.zhaw.carewe.repository.NeedyRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
class NeedyControllerTest {
    @InjectMocks
    private NeedyController needyController;

    @Mock
    private NeedyRepository needyRepository;

    @Test
    void createNeedyTest() {
        // Erstellen Sie ein Mock NeedyCreateDTO
        NeedyCreateDTO mockNDTO = new NeedyCreateDTO();
        mockNDTO.setName("Test Name");
        mockNDTO.setEmail("test@example.com");
        mockNDTO.setAddress("Test Address");
        mockNDTO.setNumber("1234567890");
        mockNDTO.setNeeds(Arrays.asList("Need1", "Need2"));
        mockNDTO.setNotes("Test Notes");
        mockNDTO.setNeedyState(NeedyState.UNALLOCATED);

        // Erstellen Sie ein Mock Needy
        Needy mockNeedy = new Needy(mockNDTO.getName(), mockNDTO.getEmail(), mockNDTO.getAddress(),
                mockNDTO.getNumber(),
                mockNDTO.getNeeds(), mockNDTO.getNotes(), mockNDTO.getNeedyState());

        // Mocken Sie das Verhalten der save-Methode
        Mockito.when(needyRepository.save(any(Needy.class))).thenReturn(mockNeedy);

        // Testen Sie die Methode
        ResponseEntity<Needy> response = needyController.createNeedy(mockNDTO);

        // Überprüfen Sie das Ergebnis
        assertEquals(mockNeedy, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Überprüfen Sie, ob die save-Methode aufgerufen wurde
        verify(needyRepository).save(any(Needy.class));
    }

}
