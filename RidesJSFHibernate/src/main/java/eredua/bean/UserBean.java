package eredua.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.xml.crypto.Data;

import eredua.domeinua.LoginGertaera;
import eredua.nagusia.GertaerakDataAccess;

@ManagedBean(name = "userBean")
@SessionScoped
public class UserBean {

	private String username;
	private String password;
	private String confirmPassword;
	private String email;
	private String mota;
	private GertaerakDataAccess dataAccess = new GertaerakDataAccess();

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMota() {
		return mota;
	}

	public void setMota(String mota) {
		this.mota = mota;
	}

	public String login() {
		System.out.println(email);
		System.out.println(password);
		if (dataAccess.isUserOrDriverValid(email, password)) {

			boolean isDriver = dataAccess.isDriver(email);

			if (isDriver) {

				return "CreateRide?faces-redirect=true";
			} else {

				return "QueryRide?faces-redirect=true";
			}
		} else {

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login ezinezkoa", "Emaila edo pasahitza okerra."));
			return null;
		}
	}

	public String register() {
		System.out.println(email);
		System.out.println("Mota: " + mota);
		if (!password.equals(confirmPassword)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Pasahitzek ez dute koinziditzen", ""));
			return null;
		}
		if (password.length() < 8) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Pasahitzak 8 karaktere izan behar ditu", ""));
			return null;
		}

		if (dataAccess.isUserExists(email)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erabiltzailea dagoeneko existitzen da", ""));
			return null;
		}

		boolean isRegistered = dataAccess.addUser(username, password, email, mota);

		if (isRegistered) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Ondo erregistratua", ""));

			return "Menua?faces-redirect=true";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Errore bat egon da erregistroan", ""));
			return null;
		}
	}

}
