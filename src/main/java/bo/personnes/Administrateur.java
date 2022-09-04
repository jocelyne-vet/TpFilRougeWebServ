package bo.personnes;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="Administrateur")
public class Administrateur extends Personne {
	public Administrateur() {
		super();
		roleLibelle="Administrateur";
		
	}
}
