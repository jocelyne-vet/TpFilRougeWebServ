package bo.personnes;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import bo.cinemas.Seance;
import bo.util.Adresse;

@Entity
@Table(name="personnes" )
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role")

@NamedQueries({
	
	@NamedQuery(name = "existPersonne",
			query = "select p from Personne p where p.email =:varEmail and p.motdePasse =:varMotDePasse"),
	@NamedQuery(name = "findAll",
			query = "select p from Personne p "),
	@NamedQuery(name = "findGerants",
			query = "select g from Gerant g where g.id not in ( select c.gerant.id from Cinema c) or g.id in (select q.id from Gerant q where q.id =:varGerantId )")
})

public abstract class Personne {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int id;
	protected String nom;
    @Transient
	protected String roleLibelle;
	protected String email;
	protected String prenom;
	@Column(name="mdp")
	protected String motdePasse;
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name="id_adresse")
	protected Adresse adresse;
	@Column(name="date_naissance")
	protected LocalDate dateNaissance;
	
	
	public Personne() {
		super();
	
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
		Personne other = (Personne) obj;
		return id == other.id;
	}
	
	
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getMotdePasse() {
		return motdePasse;
	}
	public void setMotdePasse(String motdePasse) {
		this.motdePasse = motdePasse;
	}
	public Adresse getAdresse() {
		return adresse;
	}
	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}


	public LocalDate getDateNaissance() {
		return dateNaissance;
	}




	public void setDateNaissance(LocalDate dateNaissance) {
		this.dateNaissance = dateNaissance;
	}


	public String getRoleLibelle() {
		return roleLibelle;
	}



	public void setRoleLibelle(String roleLibelle) {
		this.roleLibelle = roleLibelle;
	}
}
