package ch.zhaw.carewe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ch.zhaw.carewe.controller.RatingController;
import ch.zhaw.carewe.model.Rating;
import ch.zhaw.carewe.repository.RatingRepository;

@ExtendWith(MockitoExtension.class)
public class RatingControllerTest {

    @Mock
    RatingRepository ratingRepository;

    @InjectMocks
    RatingController ratingController;

    private Rating rating;

    @BeforeEach
    void setUp() {

        rating = new Rating();
        rating.setId("1");
        rating.setTitel("Excellent Service");
        rating.setName("John Doe");
        rating.setZahl(5);
        rating.setRezession("John provided excellent service.");
        rating.setHelperId("helperId");

        when(ratingRepository.findAll()).thenReturn(Arrays.asList(rating));
    }

    @Test
    void getRatingsTest() {
        ResponseEntity<List<Rating>> response = ratingController.getRatings();

        assertEquals(Arrays.asList(rating), response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getRatingsTest2() {
        ResponseEntity<List<Rating>> response = ratingController.getRatings();

        // Verify that the findAll method was called
        verify(ratingRepository).findAll();

        assertEquals(Arrays.asList(rating), response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
