package cz.muni.fi.pa165.airportmanager.backend.daos;

import cz.muni.fi.pa165.airportmanager.backend.entities.Airplane;
import cz.muni.fi.pa165.airportmanager.backend.entities.Flight;
import java.util.List;

/**
 * Data access object (DAO) layer for Airplane entity.
 * 
 * @author Filip
 */
public interface AirplaneDAO {
    
    /**
     * Creates new airplane.
     * 
     * @param airplane Airplane to be created 
     */
    public void createAirplane(Airplane airplane);
    
    /**
     * Updates an existing airplane.
     * 
     * @param airplane Airplane to be updated
     */
    public void updateAirplane(Airplane airplane);
    
    /**
     * Removes an existing airplane.
     * 
     * @param airplane Airplane to be removed
     */
    public void removeAirplane(Airplane airplane);
    
    /**
     * Returns Airplane with desired id.
     * 
     * @param id id of desired Airplane
     * @return desired Airplane
     */
    public Airplane getAirplane(Long id);
    
    /**
     * Returns all airplanes.
     * 
     * @return List of all airplanes.
     */
    public List<Airplane> getAllAirplanes();
    
    /**
     * Returns all airplanes for a flight.
     * 
     * @param airplane desired airplane
     * @return list of desired airplane flights
     */
    public List<Flight> getAllAirplanesFlights(Airplane airplane);
    
}
