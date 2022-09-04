package bo.cinemas;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;

import bo.personnes.Gerant;
import bo.util.Adresse;
@Entity
@Table(name="cinemas")
@NamedQueries({
	@NamedQuery(name = "findCinemasByCritere",
			query = "select p from Cinema p where UPPER(p.nom) like :varNom  or UPPER(p.adresse.ville) like :varVille"),
	@NamedQuery(name = "findFilmsByCinemaByCritere",
			query = "select p from Cinema p Join p.salles sa join   sa.seances se where UPPER(se.film.nom) like :varNom  and p.id =:varId"),
	@NamedQuery(name = "findFilmsByCinemaByIdGerant",
			query = "select p from Cinema p where  p.gerant.id =:varId"),
	@NamedQuery(name = "findAllCinema",
			query = "select p from Cinema p ")
})
public class Cinema {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nom;
	@OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinColumn(name="id_adresse")
	private Adresse adresse;
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_gerant")
	private Gerant gerant;

	private String affiche;
	
	@OneToMany(fetch=FetchType.EAGER,cascade = CascadeType.REMOVE)
	@JoinColumn(name="id_cinema")
	@JsonBackReference
	public List<Salle> salles;
	
	public Cinema() {
		this.salles = new ArrayList<Salle>();
	}
	
	
	
	public List<Film> tousLesFilms() {
		List<Film> films = new ArrayList<>();
		for (Salle currentSalle : salles) {
			for (Seance currentSeance : currentSalle.getSeances()) {
				if (currentSeance.getFilm() != null
						&& !films.contains(currentSeance.getFilm())) {
					
					films.add(currentSeance.getFilm());
				}
			}
		}
		return films;
	}
	
	public List<Film> tousLesFilms(LocalDate dateSeance) {
		List<Film> films = new ArrayList<>();
		for (Salle currentSalle : salles) {
			for (Seance currentSeance : currentSalle.getSeances()) {
				if (dateSeance.equals(currentSeance.getHeureDebut().toLocalDate())) {
					if (currentSeance.getFilm() != null
							&& !films.contains(currentSeance.getFilm())) {
						films.add(currentSeance.getFilm());
					}
				}
			}
		}
		return films;
	}
	
	public List<Film> tousLesFilms(LocalDate dateSeance, LocalTime heureSeance) {
		List<Film> films = new ArrayList<>();
		LocalDateTime heureDebut = LocalDateTime.of(dateSeance, heureSeance);
		for (Salle currentSalle : salles) {
			for (Seance currentSeance : currentSalle.getSeances()) {
				if (heureDebut.equals(currentSeance.getHeureDebut())) {
					if (currentSeance.getFilm() != null
							&& !films.contains(currentSeance.getFilm())) {
						films.add(currentSeance.getFilm());
					}
				}
			}
		}
		return films;
	}
	
	public List<Film> tousLesFilmsValides(LocalDate dateSeance, LocalTime heureSeance) {
		List<Film> films = new ArrayList<>();
		LocalDateTime heureDebut = LocalDateTime.of(dateSeance, heureSeance);
		for (Salle currentSalle : salles) {
			for (Seance currentSeance : currentSalle.getSeances()) {
				if (heureDebut.compareTo(currentSeance.getHeureDebut())<=0) {
					if (currentSeance.getFilm() != null
							&& !films.contains(currentSeance.getFilm())) {
						films.add(currentSeance.getFilm());
					}
				}
			}
		}
		return films;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}
	
	public List<Salle> getSalles() {
		return salles;
	}
	
	public void setSalles(List<Salle> salles) {
		this.salles = salles;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Gerant getGerant() {
		return gerant;
	}

	public void setGerant(Gerant gerant) {
		this.gerant = gerant;
	}

	

	public String getAffiche() {
		return affiche;
	}

	public void setAffiche(String affiche) {
		this.affiche = affiche;
	}
	
	
}
