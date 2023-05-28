package ch.zhaw.carewe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ch.zhaw.carewe.controller.HelperController;
import ch.zhaw.carewe.model.Helper;
import ch.zhaw.carewe.model.HelperCreateDTO;
import ch.zhaw.carewe.model.HelperState;
import ch.zhaw.carewe.repository.HelperRepository;

@ExtendWith(MockitoExtension.class)
public class HelperControllerTest {

    @Mock
    HelperRepository helperRepository;

    @InjectMocks
    HelperController helperController;

    private Helper helper;
    private Pageable pageable;
    private Page<Helper> pageHelpers;

    @BeforeEach
    void setUp() {
        helper = new Helper();
        pageable = PageRequest.of(0, 10);
        pageHelpers = new PageImpl<>(Arrays.asList(helper));

        lenient().when(helperRepository.findAll(pageable)).thenReturn(pageHelpers);
    }

    @Test
    void getHelpersTest() {
        ResponseEntity<Page<Helper>> response = helperController.getHelper(null, null, 1, 10);
        verify(helperRepository).findAll(pageable);

        assertEquals(pageHelpers, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void createHelperTest() {
        HelperCreateDTO mockHelperCreateDTO = new HelperCreateDTO();
        mockHelperCreateDTO.setName("Test Name");
        mockHelperCreateDTO.setAddress("Test Address");
        mockHelperCreateDTO.setEmail("test@example.com");
        mockHelperCreateDTO.setSkills(Arrays.asList("Skill1", "Skill2"));
        mockHelperCreateDTO.setBio("Test Bio");
        mockHelperCreateDTO.setHelperState(HelperState.UNALLOCATED);

        Helper mockHelper = new Helper(mockHelperCreateDTO.getName(), mockHelperCreateDTO.getAddress(),
                mockHelperCreateDTO.getEmail(), mockHelperCreateDTO.getSkills(),
                mockHelperCreateDTO.getBio(), mockHelperCreateDTO.getHelperState());

        Mockito.when(helperRepository.save(any(Helper.class))).thenReturn(mockHelper);
        ResponseEntity<Helper> response = helperController.createHelper(mockHelperCreateDTO);

        assertEquals(mockHelper, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        verify(helperRepository).save(any(Helper.class));
    }
}
