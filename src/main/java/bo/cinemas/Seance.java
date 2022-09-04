package bo.cinemas;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;

import bo.personnes.Client;
import bo.util.Reservation;

@Entity
@Table(name="seances")
@NamedQueries({
	
	@NamedQuery(name = "findSeancesByFilm",
			query = "select p from Seance p Join p.film f where f.id =:varId"),
	@NamedQuery(name = "findAllSeancesBySalleByFilm",
			query = "select p from Salle sa Join sa.seances p Join p.film f where sa.id =:varIdSalle and f.id =:varIdFilm ")
})
public class Seance implements Comparable<Seance>{
	@Transient
	private LocalDateTime heureDebut;
	private LocalTime heure;
	private LocalDate date_seance;
	@Column(name="nb_inscrits")
	private int nbInscrits;
	@ManyToOne
	@JoinColumn(name="id_film")
	@JsonBackReference
	private Film film;
	@ManyToOne
	@JoinColumn(name="id_salle")
	@JsonManagedReference
	private Salle salle;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Transient
	private Map<Client, Integer> clientsInscrits;
	
	
	public Seance() {
		// TODO Auto-generated constructor stub
	}
	
	public Seance(LocalDateTime heureDebut) {
		this.heureDebut = heureDebut;
		this.clientsInscrits = new HashMap<>();
	}
	
	public void reserver(Client client, int nbPlaces) throws Exception {
		
		if (clientsInscrits.containsKey(client)) {
			int nouveauNombre = nbInscrits - clientsInscrits.get(client) + nbPlaces;
			if (nouveauNombre > salle.getNombreDePlaces()) {
				throw new Exception("Plus de place disponible");
			}
			clientsInscrits.replace(client, nbPlaces);
			nbInscrits = nouveauNombre;
			client.getReservations().remove(this);
			
			
		} else {
			int nouveauNombre = nbInscrits + nbPlaces;
			if (nouveauNombre > salle.getNombreDePlaces()) {
				throw new Exception("Le nombre de places disponibles est inférieur au nombre de places que vous avez demandées");
			}
			clientsInscrits.put(client, nbPlaces);
			nbInscrits = nouveauNombre;
			
		}
		client.getReservations().put(this, nbPlaces);
		
	}
	
    public String formatHeureMinute() {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    	
    	System.out.println(this.heureDebut.format(formatter));
    	return this.heureDebut.format(formatter);
    }

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Seance other = (Seance) obj;
		return id == other.id;
	}

	public Date getHeureDebutDate() {
		
		Instant instant = this.heureDebut.atZone(ZoneId.systemDefault()).toInstant();
		Date date = Date.from(instant);
		System.out.println(date); 
		return date;
		
	}
	
	@Override
	public int compareTo(Seance o) {
		// TODO Auto-generated method stub
		return this.getHeureDebut().compareTo(o.getHeureDebut());
	}


	public LocalDateTime getHeureDebut() {
		if(this.heureDebut == null) {
			this.heureDebut = LocalDateTime.of(date_seance, heure);
		}
		return heureDebut;
		
	}

	public void setHeureDebut(LocalDateTime heureDebut) {
		this.heureDebut = heureDebut;
		this.date_seance = heureDebut.toLocalDate();
		this.heure =heureDebut.toLocalTime();
	}

	public int getNbInscrits() {
		return nbInscrits;
	}

	public void setNbInscrits(int nbInscrits) {
		this.nbInscrits = nbInscrits;
	}
	
	public Film getFilm() {
		return film;
	}
	
	public void setFilm(Film film) {
		this.film = film;
	}
	
	public Salle getSalle() {
		return salle;
	}

	public void setSalle(Salle salle) {
		this.salle = salle;
	}

	public Map<Client, Integer> getClientsInscrits() {
		return clientsInscrits;
	}

	public void setClientsInscrits(Map<Client, Integer> clientsInscrits) {
		this.clientsInscrits = clientsInscrits;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	



	
	
}
