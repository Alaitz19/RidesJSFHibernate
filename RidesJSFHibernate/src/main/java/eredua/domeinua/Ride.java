package eredua.domeinua;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "rides")
public class Ride implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ride_number")
    private Integer rideNumber;

    @Column(name = "from_city")
    private String from_city;

    @Column(name = "to_city")
    private String to_city;

    @Column(name = "n_places")
    private int nPlaces;

    
    @Column(name = "date")
    private Date date;

    @Column(name = "price")
    private float price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "driver_id")
    private Driver driver;

    public Ride() {
        super();
    }

    public Ride(Integer rideNumber, String from, String to, Date date, int nPlaces, float price, Driver driver) {
        this.rideNumber = rideNumber;
        this.from_city = from;
        this.to_city = to;
        this.nPlaces = nPlaces;
        this.date = date;
        this.price = price;
        this.driver = driver;
    }

    public Ride(String from, String to, Date date, int nPlaces, float price, Driver driver) {
        this.from_city = from;
        this.to_city = to;
        this.nPlaces = nPlaces;
        this.date = date;
        this.price = price;
        this.driver = driver;
    }

    public Integer getRideNumber() {
        return rideNumber;
    }

    public void setRideNumber(Integer rideNumber) {
        this.rideNumber = rideNumber;
    }

    public String getFrom() {
        return from_city;
    }

    public void setFrom(String from) {
        this.from_city = from;
    }

    public String getTo() {
        return to_city;
    }

    public void setTo(String to) {
        this.to_city = to;
    }

    public int getnPlaces() {
        return nPlaces;
    }

    public void setnPlaces(int nPlaces) {
        this.nPlaces = nPlaces;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    @Override
    public String toString() {
        return rideNumber + "; " + from_city + "; " + to_city + "; " + date;
    }
}
