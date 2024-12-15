package eredua.nagusia;

import eredua.HibernateUtil;
import eredua.domeinua.Driver;
import eredua.domeinua.Erabiltzailea;
import eredua.domeinua.LoginGertaera;

import eredua.domeinua.Bidaiaria;
import eredua.domeinua.Ride;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import configuration.UtilDate;

import java.util.*;

public class GertaerakDataAccess {
	public GertaerakDataAccess() {

	}
	public List<Ride> getAllRides(String from) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			Query query = session.createQuery(
					"SELECT r FROM Ride r WHERE r.from_city = :from");
			query.setParameter("from", from);
		
			List<Ride> rides = query.list();
			session.getTransaction().commit();
			return rides;
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw e;
		}
		 
	}

	public boolean isDriver(String email) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		String jpql = "SELECT COUNT(d) FROM Driver d WHERE d.email = :email";

		try {
			Query query = session.createQuery(jpql);
			query.setParameter("email", email);
			Long count = (Long) query.uniqueResult();

			return count > 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.getTransaction().commit();
		}

		return false;
	}

	public boolean isUserOrDriverValid(String email, String pasahitza) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		try {
			session.beginTransaction();

			String jpqlErabiltzailea = "SELECT COUNT(e) FROM Erabiltzailea e WHERE e.email = :email AND e.pasahitza = :pasahitza";
			String jpqlDriver = "SELECT COUNT(d) FROM Driver d WHERE d.email = :email AND d.pasahitza = :pasahitza";

			Query queryErabiltzailea = session.createQuery(jpqlErabiltzailea);
			queryErabiltzailea.setParameter("email", email);
			queryErabiltzailea.setParameter("pasahitza", pasahitza);
			Long countErabiltzailea = (Long) queryErabiltzailea.uniqueResult();

			Query queryDriver = session.createQuery(jpqlDriver);
			queryDriver.setParameter("email", email);
			queryDriver.setParameter("pasahitza", pasahitza);
			Long countDriver = (Long) queryDriver.uniqueResult();

			if (countErabiltzailea > 0 || countDriver > 0) {

				String jpqlLoginGertaera = "FROM LoginGertaera l WHERE l.email = :email";
				Query queryLoginGertaera = session.createQuery(jpqlLoginGertaera);
				queryLoginGertaera.setParameter("email", email);

				List<LoginGertaera> loginEventos = queryLoginGertaera.list();
				if (!loginEventos.isEmpty()) {

					session.delete(loginEventos.get(0));
				}

				LoginGertaera nuevoLogin = new LoginGertaera();

				if (countErabiltzailea > 0) {
					Erabiltzailea usuario = (Erabiltzailea) session
							.createQuery("FROM Erabiltzailea e WHERE e.email = :email").setParameter("email", email)
							.uniqueResult();
					nuevoLogin.setErabiltzailea(usuario);
					nuevoLogin.setEmail(usuario.getEmail());
				} else if (countDriver > 0) {
					Driver conductor = (Driver) session.createQuery("FROM Driver d WHERE d.email = :email")
							.setParameter("email", email).uniqueResult();
					nuevoLogin.setDriver(conductor);
					nuevoLogin.setEmail(conductor.getEmail());
				}

				nuevoLogin.setData(new Date());
				session.save(nuevoLogin);

				session.getTransaction().commit();
				return true;
			}
		} catch (Exception e) {
			if (session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}

		return false;
	}

	public boolean isUserExists(String email) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		String jpql = "SELECT COUNT(u) FROM Erabiltzailea u WHERE u.email = :email";

		try {
			Query query = session.createQuery(jpql);
			query.setParameter("email", email);
			Long count = (Long) query.uniqueResult();

			return count > 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.getTransaction().commit();
		}

		return false;
	}

	public boolean addUser(String userName, String pasa, String email, String userType) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		try {
			

		
			if ("bidaiaria".equals(userType)) {
				Bidaiaria traveler = new Bidaiaria();
				traveler.setIzena(userName);
				traveler.setEmail(email);
				traveler.setPasahitza(pasa);
				traveler.setMota(userType);

				

				session.save(traveler); 
			} else if ("gidaria".equals(userType)) {
				Driver driver = new Driver();
				driver.setEmail(email);
				driver.setName(userName);
				driver.setPasahitza(pasa);

			

				session.save(driver); 
			} else {
				System.out.println("Mota ez du balio.");
				return false; 
			}

			
			
			session.getTransaction().commit(); 
			return true; 

		} catch (Exception e) {
			if (session != null) {
				session.getTransaction().rollback(); 
			}
			e.printStackTrace();
			return false; 
		}
	}

	public List<String> getDestinationsByArrival(String arrival) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {

			String hql = "SELECT DISTINCT r.to_city FROM Ride r WHERE r.from_city = :arrival";
			Query query = session.createQuery(hql);
			query.setParameter("arrival", arrival);

			List<String> destinations = query.list();
			session.getTransaction().commit();
			return destinations;
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw e;
		}
	}

	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			Date firstDayMonthDate = UtilDate.firstDayMonth(date);
			Date lastDayMonthDate = UtilDate.lastDayMonth(date);
			Query query = session.createQuery(
					"SELECT DISTINCT r.date FROM Ride r WHERE r.from_city = :from AND r.to_city = :to AND r.date BETWEEN :startDate AND :endDate");
			query.setParameter("from", from);
			query.setParameter("to", to);
			query.setParameter("startDate", firstDayMonthDate);
			query.setParameter("endDate", lastDayMonthDate);
			List<Date> dates = query.list();
			session.getTransaction().commit();
			return dates;
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw e;
		}
	}

	public List<Ride> getRides(String from, String to, Date date) {
		System.out.println(">> DataAccess: getRides=> from= " + from + " to= " + to + " date " + date);
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			Query query = session.createQuery(
					"SELECT r FROM Ride r WHERE r.from_city = :from AND r.to_city = :to AND r.date = :date");
			query.setParameter("from", from);
			query.setParameter("to", to);
			query.setParameter("date", date);
			List<Ride> rides = query.list();
			session.getTransaction().commit();
			return rides;
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw e;
		}
	}

	public Ride createRide(String from_city, String to_city, Date date, int nPlaces, float price, String driverEmail)
			throws RideAlreadyExistException, RideMustBeLaterThanTodayException {
		System.out.println(">> DataAccess: createRide=> from= " + from_city + " to= " + to_city + " driver="
				+ driverEmail + " date " + date);
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			if (new Date().compareTo(date) > 0) {
				throw new RideMustBeLaterThanTodayException(
						ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorRideMustBeLaterThanToday"));
			}
			Driver driver = (Driver) session.get(Driver.class, driverEmail);
			if (driver.doesRideExists(from_city, to_city, date)) {
				throw new RideAlreadyExistException(
						ResourceBundle.getBundle("Etiquetas").getString("DataAccess.RideAlreadyExist"));
			}
			Ride ride = driver.addRide(from_city, to_city, date, nPlaces, price);
			session.persist(ride);
			session.getTransaction().commit();
			return ride;
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw e;
		}
	}

	public List<String> getArrivalCities(String from) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			Query query = session
					.createQuery("SELECT DISTINCT r.to_city FROM Ride r WHERE r.from_city = :from ORDER BY r.to_city");
			query.setParameter("from", from);
			List<String> arrivingCities = query.list();
			session.getTransaction().commit();
			return arrivingCities;
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw e;
		}
	}

	public List<String> getDepartCities() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			Query query = session.createQuery("SELECT DISTINCT r.from_city FROM Ride r ORDER BY r.from_city");
			List cities = query.list();
			session.getTransaction().commit();
			return cities;
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw e;
		}
	}

	public List<Ride> getRidesByDriver(String driveremail) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("from Ride r where r.driver.email = :driveremail");
		query.setParameter("driverId", driveremail);
		List<Ride> rides = query.list();
		session.getTransaction().commit();
		return rides;
	}

	public Driver createAndStoreDriver(String name) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Driver driver = new Driver();
			driver.setName(name);
			session.persist(driver);
			transaction.commit();
			return driver;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.err.println("Error creating and storing driver: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public List<Ride> getAllRides() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<Ride> rides = session.createQuery("from Ride").list();
		session.getTransaction().commit();
		return rides;
	}

	public boolean deleteErabiltzailea(String erabiltzailea) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			Erabiltzailea e = (Erabiltzailea) session.get(Erabiltzailea.class, erabiltzailea);
			// Query q = session.createQuery("delete from LoginGertaera where erabiltzailea
			// = :erab");
			// q.setParameter("erab", e);
			// q.executeUpdate();
			session.delete(e);
			session.getTransaction().commit();
		} catch (Exception ex) {
			System.out.println("Errorea: " + ex.toString());
			return false;
		}
		return true;
	}

	public List<LoginGertaera> getLoginGertaerakv3(String erabiltzaileaIzena) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Criteria c = session.createCriteria(LoginGertaera.class).createCriteria("erabiltzailea")
				.add(Restrictions.eq("izena", erabiltzaileaIzena));
		List<LoginGertaera> result = c.list();
		session.getTransaction().commit();
		return result;
	}

	public List<LoginGertaera> getLoginGertaerak() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List result = session.createQuery("from LoginGertaera").list();
		// System.out.println("getLoginGertaerak() : "+result);
		session.getTransaction().commit();
		return result;
	}

	public List<Erabiltzailea> getErabiltzaileak() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List result = session.createQuery("from Erabiltzailea").list();
		session.getTransaction().commit();
		return result;
	}

	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {

			transaction = session.beginTransaction();

			Calendar today = Calendar.getInstance();
			int month = today.get(Calendar.MONTH);
			int year = today.get(Calendar.YEAR);
			if (month == 12) {
				month = 1;
				year += 1;
			}

			Driver driver1 = new Driver("driver1@gmail.com", "Aitor Fernandez", "1");
			Driver driver2 = new Driver("driver2@gmail.com", "Ane Gaztanaga", "2");
			Driver driver3 = new Driver("driver3@gmail.com", "Test driver", "3");

			driver1.addRide("Donostia", "Bilbo", UtilDate.newDate(year, month, 15), 4, 7);
			driver1.addRide("Donostia", "Gazteiz", UtilDate.newDate(year, month, 6), 4, 8);
			driver1.addRide("Bilbo", "Donostia", UtilDate.newDate(year, month, 25), 4, 4);
			driver1.addRide("Donostia", "Iruna", UtilDate.newDate(year, month, 7), 4, 8);

			driver2.addRide("Donostia", "Bilbo", UtilDate.newDate(year, month, 15), 3, 3);
			driver2.addRide("Bilbo", "Donostia", UtilDate.newDate(year, month, 25), 2, 5);
			driver2.addRide("Eibar", "Gasteiz", UtilDate.newDate(year, month, 6), 2, 5);

			driver3.addRide("Bilbo", "Donostia", UtilDate.newDate(year, month, 14), 1, 3);

			session.persist(driver1);
			session.persist(driver2);
			session.persist(driver3);

			transaction.commit();
			System.out.println("Datu basean, datuak sartuak");

		} catch (Exception e) {

			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {

			session.close();
		}
	}

}
