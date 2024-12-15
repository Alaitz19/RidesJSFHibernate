package eredua.bean;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import eredua.domeinua.Ride;
import eredua.nagusia.GertaerakDataAccess;

import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;

@ManagedBean
@ApplicationScoped
public class QueryBean implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String departCity;
    private String arrivalCity;
    private Date date;
    private List<String> departCities;
    private List<String> arrivalCities;
    private List<Ride> rides;

    private GertaerakDataAccess da = new GertaerakDataAccess();

    public QueryBean() {
       departCities = da.getDepartCities();
    }

    @PostConstruct
    public void init() {
    	 resetData();
    }
    public void resetData() {
          this.date = null;
          this.rides = null;
    }

    public String getDepartCity() {
        return departCity;
    }

    public void setDepartCity(String departCity) {
        this.departCity = departCity;
        if (departCity != null && !departCity.isEmpty()) {
            updateArrivalCities(null);
        }
    }

    public String getArrivalCity() {
    	System.out.println(arrivalCity);
        return arrivalCity;
    }

    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<String> getDepartCities() {
        return departCities;
    }

    public List<String> getArrivalCities() {
        return arrivalCities;
    }

    public List<Ride> getRides() {
        return rides;
    }

    public void updateArrivalCities(AjaxBehaviorEvent event) {
        if (departCity != null && !departCity.isEmpty()) {
            arrivalCities = da.getDestinationsByArrival(departCity);
            if (!arrivalCities.contains(arrivalCity)) {
                arrivalCity = null; 
            }
        } else {
            arrivalCities = new ArrayList<>();
            arrivalCity = null;
        }
    }


    private void loadRides() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (departCity != null && arrivalCity != null && date != null) {
            rides = da.getRides(departCity, arrivalCity, date);
            
            if (rides.isEmpty()) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "No rides found", "Ez dira bidairik aurkitu.");
                context.addMessage(null, message);
            }
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Incomplete parameters", "Mesedez, parametro guztiak ipini.");
            context.addMessage(null, message);
        }
    }


    public void back() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
        navigationHandler.handleNavigation(facesContext, null, "back");
    }

   
    public void searchRides() {
        loadRides(); 
    }
   
}
