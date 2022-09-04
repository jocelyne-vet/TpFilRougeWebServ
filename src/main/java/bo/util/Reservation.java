package bo.util;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import bo.cinemas.Seance;
import bo.personnes.Client;
@Entity
@Table(name="reservations")

@NamedQueries({
	
	@NamedQuery(name = "findAllReservationByClient",
			query = "select p from Reservation p  where p.client.id =:varClientId")
	
})
public class Reservation {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private int nb_places;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "id_personne")
	private Client client;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_seance")
	private Seance seance;

	public Reservation() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNb_places() {
		return nb_places;
	}

	public void setNb_places(int nb_places) {
		this.nb_places = nb_places;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Seance getSeance() {
		return seance;
	}

	public void setSeance(Seance seance) {
		this.seance = seance;
	}
	
	

}
