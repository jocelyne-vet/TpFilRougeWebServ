package bo.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import bo.cinemas.Seance;

public class Outils {
	private List<Seance> seances;
	
	public Outils() {
		// TODO Auto-generated constructor stub
	}

	public List<Seance> toutesLesSeancesValides(LocalDate dateSeance, LocalTime heureSeance) {
		
		List<Seance> listeSeances = new ArrayList<>();
		LocalDateTime heureDebut = LocalDateTime.of(dateSeance, heureSeance);

		for (Seance currentSeance : this.seances) {
			if (heureDebut.compareTo(currentSeance.getHeureDebut()) <= 0) {
				listeSeances.add(currentSeance);
			}
		}
		return listeSeances;
	}
	
	public List<Seance> getSeances() {
		return seances;
	}

	public void setSeances(List<Seance> seances) {
		this.seances = seances;
	}
	
	public static <T> List<T> intersection(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<T>();

        for (T t : list1) {
            if(list2.contains(t)) {
                list.add(t);
            }
        }

        return list;
    }
	
	public static <T> List<T> findDifference(List<T> first, List<T> second)
    {
        List<T> diff = new ArrayList<>(first);
        diff.removeAll(second);
        return diff;
    }

}
