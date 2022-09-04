package bo.personnes;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.MapKey;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import bo.cinemas.Seance;
import bo.util.Reservation;

@Entity
@DiscriminatorValue(value="Client")
public class Client extends Personne {
	@OneToMany(targetEntity = Reservation.class)
	@MapKey
	private Map<Seance, Integer> reservations;	
	
	public Client() {
		super();
		roleLibelle="Client";
		this.reservations = new HashMap<>();

	}

	public Map<Seance, Integer> getReservations() {
		return reservations;
	}

	public void setReservations(Map<Seance, Integer> reservations) {
		this.reservations = reservations;
	}

//	public List<Reservation> getReservationsC() {
//		return reservationsC;
//	}
//
//	public void setReservationsC(List<Reservation> reservationsC) {
//		this.reservationsC = reservationsC;
//	}
//	
	
}
