package digital.and.sunshine.location;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

public class LocationSeekerServiceTest {

    @Test
    void demoTestMethod() {
        assertTrue(true);
    }


    @Test
    void baseTest() {
        Coordinates coordinates = new Coordinates(0,0);
        RetrieveNearPlaces retrieveNearPlaces = RetrieveNearPlaces.builder().coordinates(coordinates).numberOfPlaces(2).radius(1).build();

        LocationSeekerService locationSeekerService = new LocationSeekerRandomAdapter();
        Places places = locationSeekerService.getNearPlaces(retrieveNearPlaces);
        assertNotNull(places);
        assertNotNull(places.coordinates());
        assertEquals(2, places.coordinates().size());
        assertNotNull(places.coordinates().get(0));
        assertTrue(places.coordinates().get(0).lat() < 0.01);
        assertTrue(places.coordinates().get(0).lon() < 0.01);
        assertTrue(places.coordinates().get(0).lat() > -0.01);
        assertTrue(places.coordinates().get(0).lon() > -0.01);
        assertNotNull(places.coordinates().get(0));
        assertTrue(places.coordinates().get(1).lat() < 0.01);
        assertTrue(places.coordinates().get(1).lon() < 0.01);
        assertTrue(places.coordinates().get(1).lat() > -0.01);
        assertTrue(places.coordinates().get(1).lon() > -0.01);
    }


    @Test
    void radiusTest() {
        double MAX_LIMIT = 5.005;
        double MIN_LIMIT = 4.995;
        Coordinates coordinates = new Coordinates(5,5);
        RetrieveNearPlaces retrieveNearPlaces = RetrieveNearPlaces.builder().coordinates(coordinates).numberOfPlaces(3).radius(1).build();

        LocationSeekerService locationSeekerService = new LocationSeekerRandomAdapter();
        Places places = locationSeekerService.getNearPlaces(retrieveNearPlaces);
        assertNotNull(places);
        assertNotNull(places.coordinates());
        assertEquals(3, places.coordinates().size());

        assertNotNull(places.coordinates().get(0));
        assertTrue(places.coordinates().get(0).lat() < MAX_LIMIT);
        assertTrue(places.coordinates().get(0).lon() < MAX_LIMIT);
        assertTrue(places.coordinates().get(0).lat() > MIN_LIMIT);
        assertTrue(places.coordinates().get(0).lon() > MIN_LIMIT);

        assertNotNull(places.coordinates().get(1));
        assertTrue(places.coordinates().get(1).lat() < MAX_LIMIT);
        assertTrue(places.coordinates().get(1).lon() < MAX_LIMIT);
        assertTrue(places.coordinates().get(1).lat() > MIN_LIMIT);
        assertTrue(places.coordinates().get(1).lon() > MIN_LIMIT);

        assertNotNull(places.coordinates().get(2));
        assertTrue(places.coordinates().get(2).lat() < MAX_LIMIT);
        assertTrue(places.coordinates().get(2).lon() < MAX_LIMIT);
        assertTrue(places.coordinates().get(2).lat() > MIN_LIMIT);
        assertTrue(places.coordinates().get(2).lon() > MIN_LIMIT);
    }

    void testSinglePrecision(double value) {
        double res = new BigDecimal(value).setScale(6, RoundingMode.HALF_UP).doubleValue();
        assertEquals(value, res);
    }

    @Test
    void precisionTest() {
        Coordinates coordinates = new Coordinates(5,5);
        RetrieveNearPlaces retrieveNearPlaces = RetrieveNearPlaces.builder().coordinates(coordinates).numberOfPlaces(3).radius(1).build();

        LocationSeekerService locationSeekerService = new LocationSeekerRandomAdapter();
        Places places = locationSeekerService.getNearPlaces(retrieveNearPlaces);
        assertNotNull(places);
        assertNotNull(places.coordinates());
        assertEquals(3, places.coordinates().size());

        assertNotNull(places.coordinates().get(0));
        testSinglePrecision(places.coordinates().get(0).lat());
        testSinglePrecision(places.coordinates().get(0).lon());

        assertNotNull(places.coordinates().get(1));
        testSinglePrecision(places.coordinates().get(1).lat());
        testSinglePrecision(places.coordinates().get(1).lon());

        assertNotNull(places.coordinates().get(2));
        testSinglePrecision(places.coordinates().get(2).lat());
        testSinglePrecision(places.coordinates().get(2).lon());
    }

}
