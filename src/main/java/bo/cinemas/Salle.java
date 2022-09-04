package bo.cinemas;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
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

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;
@Entity
@Table(name = "salles")
@NamedQueries({
	
	@NamedQuery(name = "existNumero",
			query = "select sa from Cinema c Join c.salles sa where c.id =:varId and numero =:varNumero")
	
})

public class Salle {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private int numero;
	@Column(name = "nb_places")
	private int nombreDePlaces;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "salle",orphanRemoval = true, cascade = CascadeType.REMOVE)
	@JsonBackReference
	private List<Seance> seances;
	
	@ManyToOne
	@JoinColumn(name="id_cinema")
	@JsonManagedReference
	private Cinema cinema;
	
	

	public Salle() {
		this.seances = new ArrayList<>();
	}

	public Salle(int numero, int nombreDePlaces) {
		this.numero = numero;
		this.nombreDePlaces = nombreDePlaces;
	}

	public List<Film> tousLesFilms() {
		List<Film> films = new ArrayList<>();

		for (Seance currentSeance : this.getSeances()) {
			if (currentSeance.getFilm() != null && !films.contains(currentSeance.getFilm())) {

				films.add(currentSeance.getFilm());
			}
		}

		return films;
	}

	public List<Film> tousLesFilms(LocalDate dateSeance) {
		List<Film> films = new ArrayList<>();

		for (Seance currentSeance : this.getSeances()) {
			if (dateSeance.equals(currentSeance.getHeureDebut().toLocalDate())) {
				if (currentSeance.getFilm() != null && !films.contains(currentSeance.getFilm())) {
					films.add(currentSeance.getFilm());
				}
			}
		}

		return films;
	}

	public List<Film> tousLesFilms(LocalDate dateSeance, LocalTime heureSeance) {
		List<Film> films = new ArrayList<>();
		LocalDateTime heureDebut = LocalDateTime.of(dateSeance, heureSeance);

		for (Seance currentSeance : this.getSeances()) {
			if (heureDebut.equals(currentSeance.getHeureDebut())) {
				if (currentSeance.getFilm() != null && !films.contains(currentSeance.getFilm())) {
					films.add(currentSeance.getFilm());
				}
			}
		}

		return films;
	}

	public List<Film> tousLesFilmsValides(LocalDate dateSeance, LocalTime heureSeance) {
		List<Film> films = new ArrayList<>();
		LocalDateTime heureDebut = LocalDateTime.of(dateSeance, heureSeance);

		for (Seance currentSeance : this.getSeances()) {
			if (heureDebut.compareTo(currentSeance.getHeureDebut()) <= 0) {
				if (currentSeance.getFilm() != null && !films.contains(currentSeance.getFilm())) {
					films.add(currentSeance.getFilm());
				}
			}
		}

		return films;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getNombreDePlaces() {
		return nombreDePlaces;
	}

	public void setNombreDePlaces(int nombreDePlaces) {
		this.nombreDePlaces = nombreDePlaces;
	}

	public List<Seance> getSeances() {
		return seances;
	}

	public void setSeances(List<Seance> seances) {
		this.seances = seances;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	

	public Cinema getCinema() {
		return cinema;
	}

	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
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
		Salle other = (Salle) obj;
		return id == other.id;
	}

}
