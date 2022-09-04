package bll;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bo.util.Reservation;
import dal.GenericDAO;
import dal.GenericDAOHibernateImpl;

public class ReservationBLL {
	
	private GenericDAO<Reservation> dao;
	
	public ReservationBLL() {
		dao = new GenericDAOHibernateImpl<>(Reservation.class);
	}

	public List<Reservation> findAllReservation(int idClient) {
		Map<String,Object> critere = new HashMap<>();
		critere.put("varClientId", idClient);
		return dao.findWithParam("findAllReservationByClient", critere);
	}
	
	public void update(Reservation res) {
		dao.update(res);
	}
	
	public void insert(Reservation res) {
		dao.insert(res);
	}
}
