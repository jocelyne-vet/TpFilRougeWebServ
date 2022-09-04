package bo.cinemas;



import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonManagedReference;
@Entity
@Table(name="films")
@NamedQueries({
	@NamedQuery(name = "findAllFilm",
			query = "select p from Film p ")
})
public class Film {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="titre")
	private String nom;
	
	@OneToMany( fetch=FetchType.EAGER, cascade = CascadeType.ALL,mappedBy = "film",orphanRemoval = true)
	@JsonManagedReference
	private List<Seance> seances;

	private String description;
	private int duree;
	private String affiche;
	@Column(name="age_minimum")
	private int ageMinimum;
	
	
	
	public Film() {
		// TODO Auto-generated method stub

	}
	
	public Film(String nom, String description, int duree) {
		this.nom = nom;
		this.description = description;
		this.duree = duree;
	}
	
	

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAffiche() {
		return affiche;
	}

	public void setAffiche(String affiche) {
		this.affiche = affiche;
	}

	public int getAgeMinimum() {
		return ageMinimum;
	}

	public void setAgeMinimum(int ageMinimum) {
		this.ageMinimum = ageMinimum;
	}



	public List<Seance> getSeances() {
		return seances;
	}

	public void setSeances(List<Seance> seances) {
		this.seances = seances;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Film other = (Film) obj;
		return id == other.id;
	}
	
	
}
