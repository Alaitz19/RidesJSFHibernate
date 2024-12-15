package eredua.domeinua;

import javax.persistence.*;
import java.util.Date;

@Entity
public class LoginGertaera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Date data;
    
    

    @Column(nullable = true)
    private String email;

    @OneToOne
    @JoinColumn(name = "erabiltzaile_izena", referencedColumnName = "izena", insertable = false, updatable = false)
    private Erabiltzailea erabiltzailea;

    @OneToOne
    @JoinColumn(name = "driver_email", referencedColumnName = "email", insertable = false, updatable = false)
    private Driver driver;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getEmail() {
        if (erabiltzailea != null) {
            return erabiltzailea.getEmail(); 
        } else if (driver != null) {
            return driver.getEmail(); 
        }
        return null;
    }
    public String getName() {
        if (driver != null) {
            return driver.getName();
        } else if (erabiltzailea != null) {
            return erabiltzailea.getIzena(); 
        }
        return null;
    }

    public void setEmail(String email) {
    	if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Emaila ezin du null izan");
        }
        this.email = email;
    }

    public Erabiltzailea getErabiltzailea() {
        return erabiltzailea;
    }

    public void setErabiltzailea(Erabiltzailea erabiltzailea) {
        this.erabiltzailea = erabiltzailea;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}
