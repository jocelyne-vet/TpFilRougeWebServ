package bo.util;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="adresses")
public class Adresse {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="numero_voie")
	private int numero;
	@Column(name="type_voie")
	private String typeRue;
	@Column(name="nom_voie")
	private String nomRue;
	@Column(name="code_postal")
	private String cpo;
	private String ville;
	
	public Adresse() {}
	
	public Adresse(int numero, String nomRue, String cpo, String ville) {
		this.numero = numero;
		this.nomRue = nomRue;
		this.cpo = cpo;
		this.ville = ville;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getNomRue() {
		return nomRue;
	}

	public void setNomRue(String nomRue) {
		this.nomRue = nomRue;
	}

	public String getCpo() {
		return cpo;
	}

	public void setCpo(String cpo) {
		this.cpo = cpo;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTypeRue() {
		return typeRue;
	}

	public void setTypeRue(String typeRue) {
		this.typeRue = typeRue;
	}
	
	
}
