package eredua.bean;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import businessLogic.BLFacade;

import java.util.List;

@ManagedBean(name = "mainBean")
@SessionScoped
public class MainBean {
    private BLFacade facadeBL;
    private List<String> gertaerak; 

    public MainBean() {
        facadeBL = FacadeBean.getBusinessLogic();
        gertaerak = facadeBL.getDepartCities();
    }

    public List<String> getGertaerak() {
        return gertaerak;
    }

    public String goToCreateRides() {
        return "createRides?faces-redirect=true";
    }

    public String goToQueryRides() {
        return "queryRides?faces-redirect=true";
    }
}

