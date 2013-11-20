package cz.muni.fi.pa165.airportmanager.web.beans;

import cz.muni.fi.pa165.airportmanager.services.DestinationService;
import cz.muni.fi.pa165.airportmanager.transferobjects.DestinationTO;
import java.util.List;
import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Filip
 */
@UrlBinding("/destinations/{$event}/{destination.id}")
public class DestinationsActionBean extends BaseActionBean {
    
    final static Logger log = LoggerFactory.getLogger(DestinationsActionBean.class);
    @SpringBean
    protected DestinationService destinationService;
    private List<DestinationTO> destinations;
    private DestinationTO destination;

    public List<DestinationTO> getDestinations() {
        return destinations;
    }
    
    public DestinationTO getDestination() {
        return destination;
    }

    public void setDestination(DestinationTO destination) {
        this.destination = destination;
    }
    
    @DefaultHandler
    public Resolution list() {
        log.debug("list()");
        destinations = destinationService.getAllDestinations();
        return new ForwardResolution("/destination/list.jsp");
    }

    //--- part for adding a destination ----
    public Resolution create() {
        return new ForwardResolution("/destination/create.jsp");
    }
    
    @ValidateNestedProperties(value = {
            @Validate(on = {"add", "save"}, field = "country", required = true),
            @Validate(on = {"add", "save"}, field = "city", required = true),
            @Validate(on = {"add", "save"}, field = "code", required = true, minvalue = 800)
    })
    public Resolution add() {
        log.debug("add() destination={}", destination);
        destinationService.createDestination(destination);
        getContext().getMessages().add(
                new LocalizableMessage("destination.add."+"message",
                escapeHTML(destination.getCountry()),
                escapeHTML(destination.getCity()),
                escapeHTML(destination.getCode())));
        return new RedirectResolution(this.getClass(), "list");
    }

    //@Override
    public Resolution handleValidationErrors(ValidationErrors errors) throws Exception {
        //fill up the data for the table if validation errors occured
        destinations = destinationService.getAllDestinations();
        //return null to let the event handling continue
        return null;
    }

    //--- part for deleting a destination ----

    public Resolution delete() {
        log.debug("delete({})", destination.getId());
        //only id is filled by the form
        destination = destinationService.getDestination(destination.getId());
        destinationService.removeDestination(destination);
        getContext().getMessages().add(new LocalizableMessage("destination.delete.message",
                escapeHTML(destination.getCountry()),escapeHTML(escapeHTML(destination.getCity())),
                escapeHTML(destination.getCode())));
        return new RedirectResolution(this.getClass(), "list");
    }

    //--- part for editing a destination ----

    @Before(stages = LifecycleStage.BindingAndValidation, on = {"edit", "save"})
    public void loadDestinationFromDatabase() {
        String ids = getContext().getRequest().getParameter("destination.id");
        if (ids == null) return;
        destination = destinationService.getDestination(Long.parseLong(ids));
    }

    public Resolution edit() {
        log.debug("edit() destination={}", destination);
        return new ForwardResolution("/destination/edit.jsp");
    }
    
    public Resolution save() {
        log.debug("save() destination={}", destination);
        destinationService.updateDestination(destination);
        return new RedirectResolution(this.getClass(), "list");
    }
}
