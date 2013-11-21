/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager.web.beans;

import cz.muni.fi.pa165.airportmanager.services.AirplaneService;
import cz.muni.fi.pa165.airportmanager.services.DestinationService;
import cz.muni.fi.pa165.airportmanager.services.FlightService;
import cz.muni.fi.pa165.airportmanager.services.StewardService;
import cz.muni.fi.pa165.airportmanager.transferobjects.AirplaneTO;
import cz.muni.fi.pa165.airportmanager.transferobjects.DestinationTO;
import cz.muni.fi.pa165.airportmanager.transferobjects.FlightTO;
import cz.muni.fi.pa165.airportmanager.transferobjects.StewardTO;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.SimpleError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Chorke
 */
@UrlBinding("/stewards/{$event}/{steward.id}")
public class StewardsActionBean extends BaseActionBean{
    
    private static boolean dbFilled = false;
    
    @SpringBean
    private StewardService stewService;
    
    @SpringBean
    private FlightService flightService;
    
    @SpringBean
    private DestinationService desService;
    
    @SpringBean
    private AirplaneService airService;
    
    private List<StewardTO> stewards;
    private List<FlightTO> flights;
    @ValidateNestedProperties({
            @Validate(on = {"add", "save"}, field = "firstName", 
                    required = true, trim = true, minlength = 1),
            @Validate(on = {"add", "save"}, field = "lastName", 
                    required = true, trim = true, minlength = 1)
            })
    private StewardTO steward;

    public List<FlightTO> getFlights() {
        return flights;
    }

    public StewardTO getSteward() {
        return steward;
    }

    public List<StewardTO> getStewards() {
        return stewards;
    }

    public void setSteward(StewardTO steward) {
        this.steward = steward;
    }

    @Before(stages = LifecycleStage.BindingAndValidation, on = {"edit", "save", "flights", "addflight"})
    public void loadSteward(){
        String id = getContext().getRequest().getParameter("steward.id");
        if(id == null){
            return;
        }
        steward = stewService.findSteward(Long.parseLong(id));
        flights = stewService.getAllStewardsFlights(steward);
    }
    
    public FlightTO loadFlight(){
        String id = getContext().getRequest().getParameter("flight.id");
        if(id == null){
            return null;
        }
        return flightService.getFlight(Long.parseLong(id));
    }
    
    @DefaultHandler
    @HandlesEvent("list")
    public Resolution showStewardsList(){
        try{
            prepareDB();
            stewards = stewService.findAllStewards();
        } catch (DataAccessException ex){
            SimpleError err = new SimpleError("Error service providing " + ex);
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex){
            SimpleError err = new SimpleError("Unknown error" + ex);
            getContext().getValidationErrors().addGlobalError(err);
            System.out.println(ex);
            ex.printStackTrace();
        }
        return new ForwardResolution("/steward/list.jsp");
    }
    
    @HandlesEvent("add")
    public Resolution addNewSteward(){
        try{
            stewService.createSteward(steward);
            getContext().getMessages().add(new SimpleMessage("added steward", escapeHTML(steward.toString())));
        } catch (DataAccessException ex){
            SimpleError err = new SimpleError("Error service providing ", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex){
            SimpleError err = new SimpleError("Unknown error", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        return new RedirectResolution(this.getClass(), "list");
    }
    
    @HandlesEvent("edit")
    public Resolution editCreateFormular(){
        System.out.println("edit called");
        return new ForwardResolution("/steward/edit.jsp");
    }
    
    @HandlesEvent("save")
    public Resolution saveStewardsEdit(){
        try{
            stewService.updateSteward(steward);
            getContext().getMessages().add(new SimpleMessage("updated steward", escapeHTML(steward.toString())));
        } catch (DataAccessException ex){
            SimpleError err = new SimpleError("Error service providing ", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex){
            SimpleError err = new SimpleError("Unknown error", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        return new RedirectResolution(this.getClass(), "list");
    }
    
    @HandlesEvent("delete")
    public Resolution removeSteward(){
        try{
            stewService.removeSteward(steward);
        } catch (DataAccessException ex){
            SimpleError err = new SimpleError("Error service providing ", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex){
            SimpleError err = new SimpleError("Unknown error", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        return new RedirectResolution(this.getClass(),"list");
    }
    
    @HandlesEvent("flights")
    public Resolution showAllStewardsFlights(){
        //TODO
//        System.out.println("steward: " + steward);
//        loadSteward();
//        System.out.println(getContext().getRequest().getParameter("steward.id"));
//        System.out.println("steward (after load): " + steward);
//        if(steward == null){
//            System.out.println("steward null");
//        } else {
//            flights = stewService.getAllStewardsFlights(steward);
//        }
        try{
            flights = stewService.getAllStewardsFlights(steward);
        } catch (DataAccessException ex){
            SimpleError err = new SimpleError("Error service providing ", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex){
            SimpleError err = new SimpleError("Unknown error", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        return new ForwardResolution("/steward/flights.jsp?add=false");
    }
    
    @HandlesEvent("addflight")
    public Resolution showStewardsFlightToAdd(){
        try{
//            flights = stewService.getAllStewardsFlights(steward);
            List<FlightTO> allFlights = flightService.getAllFlights();
            flights = getRemainingFights(flights, allFlights);
        } catch (DataAccessException ex){
            SimpleError err = new SimpleError("Error service providing ", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex){
            SimpleError err = new SimpleError("Unknown error", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        return new ForwardResolution("/steward/flights.jsp?add=true");
    }
    
    @HandlesEvent("removeflight")
    public Resolution removeStewardsFlight(){
       try{
            FlightTO flight = loadFlight();
            loadSteward();
            System.out.println("flight: " + flight);
            if(flight == null){
                throw new IllegalStateException("Missing flight id");
            }
            System.out.println("stew list: " + flight.getStewList());
            System.out.println("steward: " + steward);
            flight.getStewList().remove(steward);
            flightService.updateFlight(flight);
//            flights = stewService.getAllStewardsFlights(steward);
        } catch (DataAccessException ex){
            SimpleError err = new SimpleError("Error service providing ", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex){
            SimpleError err = new SimpleError("Unknown error", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        if(getContext().getRequest().getParameter("event").equals("edit")){
            ForwardResolution res = new ForwardResolution(this.getClass(), "edit");
            res.addParameter("formtitle", "steward.edit.title");
            return res;
        } else {
            return new ForwardResolution(this.getClass(), "flights");
        }
    }
    
    @HandlesEvent("addflighttolist")
    public Resolution addStewardsFlight(){
        try{
            FlightTO flight = loadFlight();
            if(flight == null){
                throw new IllegalStateException("Missing flight id");
            }
            flight.getStewList().add(steward);
            flightService.updateFlight(flight);
        } catch (DataAccessException ex){
            SimpleError err = new SimpleError("Error service providing ", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex){
            SimpleError err = new SimpleError("Unknown error", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
       return new ForwardResolution(this.getClass(), "addflight");
    }
    
    @HandlesEvent("cancel")
    public Resolution doNothing(){
        return new RedirectResolution(this.getClass());
    }

    private List<FlightTO> getRemainingFights(List<FlightTO> stewFlights, List<FlightTO> allFlights) {
        List<FlightTO> out = new LinkedList<>();
        for(FlightTO f : allFlights){
            if(!stewFlights.contains(f)){
                out.add(f);
            }
        }
        return out;
    }
    
    private void prepareDB(){
        System.out.println("prepareDB " + dbFilled);
        if(dbFilled){
            dbFilled = true;
            return;
        }
        dbFilled = true;
        DestinationTO d1 = new DestinationTO();
        DestinationTO d2 = new DestinationTO();
        d1.setCity("pp1");
        d1.setCountry("sk1");
        d1.setCode("bla1");
        d2.setCity("pp2");
        d2.setCountry("sk2");
        d2.setCode("bla2");
        
        AirplaneTO a = new AirplaneTO();
        a.setCapacity(100);
        a.setName("moje");
        a.setType("747");
        
        long time = System.currentTimeMillis();
        
        FlightTO f1 = new FlightTO();
        FlightTO f2 = new FlightTO();
        FlightTO f3 = new FlightTO();
        FlightTO f4 = new FlightTO();
        FlightTO f5 = new FlightTO();
        f1.setAirplaneTO(a);
        f1.setOrigin(d1);
        f1.setTarget(d2);
        f1.setArrivalTime(new Timestamp(time + 100000));
        f1.setDepartureTime(new Timestamp(time));
        f2.setAirplaneTO(a);
        f2.setOrigin(d1);
        f2.setTarget(d2);
        f2.setArrivalTime(new Timestamp(time + 300000));
        f2.setDepartureTime(new Timestamp(time + 200000));
        f3.setAirplaneTO(a);
        f3.setOrigin(d1);
        f3.setTarget(d2);
        f3.setArrivalTime(new Timestamp(time + 500000));
        f3.setDepartureTime(new Timestamp(time + 400000));
        f4.setAirplaneTO(a);
        f4.setOrigin(d1);
        f4.setTarget(d2);
        f4.setArrivalTime(new Timestamp(time + 700000));
        f4.setDepartureTime(new Timestamp(time + 600000));
        f5.setAirplaneTO(a);
        f5.setOrigin(d1);
        f5.setTarget(d2);
        f5.setArrivalTime(new Timestamp(time + 900000));
        f5.setDepartureTime(new Timestamp(time + 800000));
        
        StewardTO s = new StewardTO();
        s.setFirstName("first");
        s.setLastName("last");
        
        List<StewardTO> ls = new ArrayList<>();
        List<StewardTO> empty = new ArrayList<>();
        ls.add(s);
        
        f1.setStewList(ls);
        f3.setStewList(ls);
        f2.setStewList(empty);
        f4.setStewList(empty);
        f5.setStewList(empty);
        
        desService.createDestination(d1);
        desService.createDestination(d2);
        
        airService.createAirplane(a);
        
        stewService.createSteward(s);
        
        flightService.createFlight(f1);
        flightService.createFlight(f2);
        flightService.createFlight(f3);
        flightService.createFlight(f4);
        flightService.createFlight(f5);
    }
}
