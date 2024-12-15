package eredua.domeinua;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@DiscriminatorValue("TRAVELER")
public class Bidaiaria extends Erabiltzailea {

    @OneToMany(mappedBy = "traveler", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Booking> bookedRides = new ArrayList<>();

    public Bidaiaria() {}

    public List<Booking> getBookedRides() {
        return bookedRides;
    }

    public void addBookedRide(Booking bookedRide) {
        bookedRides.add(bookedRide);
        bookedRide.setTraveler(this);
    }

    public void removeBookedRide(Booking bookedRide) {
        bookedRides.remove(bookedRide);
        bookedRide.setTraveler(null);
    }
}
