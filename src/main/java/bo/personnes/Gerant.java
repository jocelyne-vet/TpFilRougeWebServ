package bo.personnes;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "Gerant")
public class Gerant extends Personne{
	public Gerant() {
		super();
		roleLibelle="Gerant";
		
	}
}
