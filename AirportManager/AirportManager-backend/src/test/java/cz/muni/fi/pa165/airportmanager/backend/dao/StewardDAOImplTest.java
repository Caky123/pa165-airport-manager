package cz.muni.fi.pa165.airportmanager.backend.dao;

import cz.muni.fi.pa165.airportmanager.backend.JPAs.JPAException;
import cz.muni.fi.pa165.airportmanager.backend.JPAs.StewardDAOImpl;
import cz.muni.fi.pa165.airportmanager.backend.daos.AirplaneDAO;
import cz.muni.fi.pa165.airportmanager.backend.daos.DestinationDAO;
import cz.muni.fi.pa165.airportmanager.backend.daos.FlightDAO;
import cz.muni.fi.pa165.airportmanager.backend.daos.StewardDAO;
import cz.muni.fi.pa165.airportmanager.backend.entities.Airplane;
import cz.muni.fi.pa165.airportmanager.backend.entities.Destination;
import cz.muni.fi.pa165.airportmanager.backend.entities.Flight;
import cz.muni.fi.pa165.airportmanager.backend.entities.Steward;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static org.junit.Assert.*;
import junit.framework.TestCase;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Tests for StewardDAOImpl.
 *
 * @author Filip
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@TransactionConfiguration(defaultRollback = true, transactionManager = "txManager")
//@ContextConfiguration(locations={"file:src/main/resources/applicationContext.xml"})
public class StewardDAOImplTest extends AbstractTest {
    
    @Autowired
    private StewardDAO stewDAO;
    
//    @Autowired
//    private EntityManagerFactory emf;
//    
//    @Before
//    public void setup(){
//        EntityManager man = emf.createEntityManager();
//    }
    
//    @Autowired
//    private DestinationDAO destDAO;
    
//    @Autowired
//    private AirplaneDAO airplaneDAO;
    
//    @Autowired
//    private FlightDAO flightDAO;
    
    /**
     * Test for get steward.
     */
//    @Test
//    @Transactional
    public void testGetSteward() throws JPAException{
        Steward steward1 = newSteward("Elaine","Dickinson");
        Steward steward2 = newSteward("Joshua","Bloch");
        stewDAO.createSteward(steward1);
        stewDAO.createSteward(steward2);
        
        assertDeepEquals(stewDAO.getSteward(steward1.getId()),steward1);
        assertDeepEquals(stewDAO.getSteward(steward2.getId()),steward2);
    }
    
    /**
     * Test for getting steward with null id.
     */
    @Test
//    @Transactional
    public void testGetStewardWithNullId() {
        try {
            stewDAO.getSteward(null);
        } catch (JPAException ex) {
            fail("IllegalArgumentException not thrown.");
        } catch (IllegalArgumentException ex) {
            
        }
    }
    
    /**
     * Test for getting all stewards.
     */
    @Test
//    @Transactional
    public void testGetAllStewards() throws JPAException {
        assertTrue(stewDAO.getAllStewards().isEmpty());
        
        Steward steward1 = newSteward("Elaine","Dickinson");
        Steward steward2 = newSteward("Joshua","Bloch");
        stewDAO.createSteward(steward1);
        stewDAO.createSteward(steward2);

        List<Steward> expected = Arrays.asList(steward1,steward2);
        List<Steward> actual = stewDAO.getAllStewards();

        Collections.sort(actual,idComparator);
        Collections.sort(expected,idComparator);

        assertEquals(expected, actual);
        assertDeepEquals(expected, actual);
    }
    
    /**
     * Test for getting all stewards flights.
     */
//    @Test
//    @Transactional
//    public void testGetAllStewardsFlights() throws JPAException {
//        Airplane plane1 = newAirplane(700,"Jet3000","Passenger transport");
//        airplaneDAO.createAirplane(plane1);
//        Destination dest1 = newDestination("CZB","Czech Republic","Brno");
//        destDAO.createDestination(dest1);
//        Destination dest2 = newDestination("USN","United States","New York");
//        destDAO.createDestination(dest2);
//        Steward steward1 = newSteward("Elaine","Dickinson");
//        stewDAO.createSteward(steward1);
//
//        assertTrue(stewDAO.getAllStewardsFlights(steward1).isEmpty());
//        
//        List<Flight> expected = Collections.EMPTY_LIST;
//        List<Flight> actual = stewDAO.getAllStewardsFlights(steward1);
//        assertEquals(expected, actual);
//        
//        List<Steward> stewList = new ArrayList<Steward>();
//        stewList.add(steward1);
//        
//        Flight flight1 = newFlight(new Timestamp(100000),new Timestamp(500000),dest1,dest2,plane1,stewList);
//        flightDAO.createFlight(flight1);
//        List<Flight> flightList = new ArrayList<Flight>();
//        flightList.add(flight1);
//        
//        assertEquals(stewDAO.getAllStewardsFlights(steward1),flightList);
//    }
//    
    /**
     * Test for steward creation.
     */
    @Test
//    @Transactional
    public void testCreateSteward() throws JPAException {
        Steward steward = newSteward("Elaine","Dickinson");
        stewDAO.createSteward(steward);
        
        assertNotNull(steward.getId());
        assertNotNull(steward.getFirstName());
        assertNotNull(steward.getLastName());
        
        Steward sameSteward = stewDAO.getSteward(steward.getId());
        
        assertEquals(steward, sameSteward);
        assertNotSame(steward, sameSteward);
        assertDeepEquals(steward, sameSteward);
    }
    
    /**
     * Attempt of creating null steward.
     */
    @Test
//    @Transactional
    public void testCreateNullSteward() {
        Steward steward = null;
        try {
            stewDAO.createSteward(steward);
        } catch (JPAException ex) {
            fail("IllegalArgumentException not thrown.");
        } catch (IllegalArgumentException ex) {
            
        }
    }
    
    /**
     * Attempt of creating steward without last name.
     */
    @Test
//    @Transactional
    public void testCreateStewardWithoutLastName() {
        Steward steward = new Steward();
        steward.setFirstName("Elaine");
        try {
            stewDAO.createSteward(steward);
        } catch (JPAException ex) {
            fail("IllegalArgumentException not thrown.");
        } catch (IllegalArgumentException ex) {
            
        }
    }
    
    /**
     * Attempt of creating steward without first name.
     */
    @Test
//    @Transactional
    public void testCreateStewardWithoutFirstName() {
        Steward steward = new Steward();
        steward.setLastName("Dickinson");
        try {
            stewDAO.createSteward(steward);
        } catch (JPAException ex) {
            fail("IllegalArgumentException not thrown.");
        } catch (IllegalArgumentException ex) {
            
        }
    }
    
    
    /**
     * Test for steward updating.
     */
    @Test
//    @Transactional
    public void testUpdateSteward() throws JPAException {
        Steward steward = new Steward();
        steward.setFirstName("Elaine");
        steward.setLastName("Dickinson");
        stewDAO.createSteward(steward);
        
        Steward steward2 = stewDAO.getSteward(steward.getId());
        steward2.setFirstName("Jane");
        stewDAO.updateSteward(steward2);
        Steward steward3 = stewDAO.getSteward(steward.getId());
        
        assertEquals(steward2, steward3);
        assertNotSame(steward2, steward3);
    }
    
    
    /**
     * Test for removing steward.
     */
    @Test
//    @Transactional
    public void testRemoveSteward() throws JPAException{
        Steward steward1 = newSteward("Elaine","Dickinson");
        Steward steward2 = newSteward("Joshua","Bloch");
        stewDAO.createSteward(steward1);
        stewDAO.createSteward(steward2);

        assertNotNull(stewDAO.getSteward(steward1.getId()));
        assertNotNull(stewDAO.getSteward(steward2.getId()));

        stewDAO.removeSteward(steward1);
        assertNotNull(stewDAO.getSteward(steward2.getId()));
        
        try {
            Steward removedSteward = stewDAO.getSteward(steward1.getId());
        } catch (JPAException ex) {
            return;
        }
        fail();
    }
    
    /**
     * Constructor for Steward.
     */
    private static Steward newSteward(String firstName, String lastName) {
        Steward steward = new Steward();
        steward.setFirstName(firstName);
        steward.setLastName(lastName);
        return steward;
    }
    
    /**
     * Constructor for Flight.
     */
    private static Flight newFlight(Timestamp departureTime, 
            Timestamp arrivalTime, Destination origin, Destination target,
            Airplane airplane, List<Steward> stewardList) {
        Flight flight = new Flight();
        flight.setDepartureTime(departureTime);
        flight.setArrivalTime(arrivalTime);
        flight.setOrigin(origin);
        flight.setTarget(target);
        flight.setAirplane(airplane);
        flight.setStewardList(stewardList);
        return flight;
    }
    
    /**
     * Constructor for Destination.
     */
    private static Destination newDestination(String code, String country, 
            String city) {
        Destination destination = new Destination();
        destination.setCode(code);
        destination.setCountry(country);
        destination.setCity(city);
        return destination;
    }
    
    /**
     * Constructor for Airplane.
     */
    private static Airplane newAirplane(int capacity, String name, String type) {
        Airplane plane = new Airplane();
        plane.setCapacity(capacity);
        plane.setName(name);
        plane.setType(type);
        return plane;
    }
    
    /**
     * Check for equality of all steward parameters.
     * @param stew1 steward number one to be compared
     * @param stew2 steward number two to be compared
     */
    private void assertDeepEquals(Steward stew1, Steward stew2) {
        assertEquals(stew1.getId(), stew2.getId());
        assertEquals(stew1.getFirstName(), stew2.getFirstName());
        assertEquals(stew1.getLastName(), stew2.getLastName());
    }
    /**
     * Check for equality of two steward lists and all their parameters.
     * @param stewList1 steward list number one to be compared
     * @param stewList2 steward list number two to be compared
     */
    private void assertDeepEquals(List<Steward> stewList1, List<Steward> stewList2) {
        for (int i = 0; i < stewList1.size(); i++) {
            Steward expected = stewList1.get(i);
            Steward actual = stewList2.get(i);
            assertDeepEquals(expected, actual);
        }
    }
    /**
     * Comparator by id.
     */
    private static Comparator<Steward> idComparator = new Comparator<Steward>() {
        @Override
        public int compare(Steward s1, Steward s2) {
            return s1.getId().compareTo(s2.getId());
        }
    };
}
