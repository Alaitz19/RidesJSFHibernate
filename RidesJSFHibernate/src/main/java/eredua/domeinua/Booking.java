package eredua.domeinua;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;




@Entity
@TableGenerator(name = "BookingGen", initialValue = 0, allocationSize = 1)
public class Booking implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "BookingGen")
	private int bookNumber;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Ride ride;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Bidaiaria traveler;
	private int seats;
	private String status;
	private double deskontua;
	

	public Booking(Ride ride, Bidaiaria traveler, int seats) {
		this.ride = ride;
		this.traveler = traveler;
		this.seats = seats;
		this.status = "NotDefined";
		this.deskontua = 0;
		
	}

	public int getBookNumber() {
		return bookNumber;
	}

	public void setBookNumber(int bookNumber) {
		this.bookNumber = bookNumber;
	}

	public Ride getRide() {
		return ride;
	}

	public void setRide(Ride ride) {
		this.ride = ride;
	}

	public Bidaiaria getTraveler() {
		return traveler;
	}

	public void setTraveler(Bidaiaria traveler) {
		this.traveler = traveler;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getDeskontua() {
		return deskontua;
	}

	public void setDeskontua(double deskontua) {
		this.deskontua = deskontua;
	}

	public double prezioaKalkulatu() {
		return (this.ride.getPrice() - this.deskontua)*this.seats;
	}


}