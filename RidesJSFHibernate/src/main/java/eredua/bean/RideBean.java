package eredua.bean;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import businessLogic.BLFacade;
import eredua.nagusia.GertaerakDataAccess;

import java.util.Date;


@ManagedBean(name = "rideBean")
@SessionScoped
public class RideBean {
    private String origin;
    private String destination;
    private int seats;
    private double price;
    private Date date;
    private String message;
    private GertaerakDataAccess da = new GertaerakDataAccess();
    
   
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
   

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public void back() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
        navigationHandler.handleNavigation(facesContext, null, "back");
    }

    public void createRide() {
        try {
           
           // BLFacade facadeBL = FacadeBean.getBusinessLogic();
        	
            
           
            da.createRide(origin, destination,date,seats,(float) price,"driver1@gmail.com");
            
            message ="Bidaia sortua!";
            System.out.println("¡Bidaia sortua!");
        } catch (Exception e) {
           
            System.out.println("Errorea bidaia sortzean: " + e.getMessage());
        }
    }
  

   
}
