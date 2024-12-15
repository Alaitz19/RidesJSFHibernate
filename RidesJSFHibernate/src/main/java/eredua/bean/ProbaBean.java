package eredua.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import eredua.domeinua.Ride;
import eredua.nagusia.GertaerakDataAccess;
@ManagedBean(name = "probaBean")
@SessionScoped
public class ProbaBean {
	  private String departCity;
	    private List<String> departCities;
	  
	    private List<Ride> rides;

	    private GertaerakDataAccess da = new GertaerakDataAccess();

	    public ProbaBean() {
	       
	        departCities = da.getDepartCities();
	      
	        rides = new ArrayList<>();
	    }

	 
	    public void init() {
	        
	    	departCities = da.getDepartCities();
	       
	    }

	    public String getDepartCity() {
	        return departCity;
	    }

	    public void setDepartCity(String departCity) {
	        this.departCity = departCity;
	     
	    }

	  


	    public List<String> getDepartCities() {
	        return departCities;
	    }


	    public List<Ride> getRides() {
	        return rides;
	    }

	

	    public void loadRides() {
	        FacesContext context = FacesContext.getCurrentInstance();
	        
	        if (departCity != null) {
	            rides = da.getAllRides(departCity); 
	            
	            
	            
	            if (rides.isEmpty()) {
	                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "No rides found", "Ez dira bidairik aurkitu.");
	                context.addMessage(null, message);
	            }
	        }
	        context.getApplication().getNavigationHandler().handleNavigation(context, null, "Proba2?faces-redirect=true");
	    }
	    


	    public void back() {
	        FacesContext facesContext = FacesContext.getCurrentInstance();
	        NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
	        navigationHandler.handleNavigation(facesContext, null, "back");
	    }
	    public void joan() {
	    	FacesContext context = FacesContext.getCurrentInstance();
	    	context.getApplication().getNavigationHandler().handleNavigation(context, null, "Proba?faces-redirect=true");
	    }

	   
	    public void searchRides() {
	        loadRides(); 
	    }
	 

}
