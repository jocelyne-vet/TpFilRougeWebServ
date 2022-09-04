package rest;

import java.time.LocalDate;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import bll.FilmBLL;

import bo.cinemas.Film;

@Path("/films")
public class GestionFilm {
	private FilmBLL bll;
	
	public GestionFilm() {
		bll = new FilmBLL();
	}
	
	@GET
	public List<Film> selectAll(){
		return bll.selectAll();
	}
	
	@GET @Path("/{id : \\d+}")
	public Film selectById(@PathParam("id") int id) {
		return bll.selectById(id);
	}
	
	
	@POST
	public Film addFilm(
			@FormParam("titre") String title,
			@FormParam("description") String description,
			@FormParam("affiche") String affiche,
			@FormParam("duree") String duree,
			@FormParam("ageMinimum") String age_minimum) {
		Film film = new Film();
		film.setNom(title);
		film.setDescription(description);
		film.setDuree(Integer.valueOf(duree));
		film.setAffiche(affiche);
		if(age_minimum==null || age_minimum.isEmpty()) {
			age_minimum="0";
		}
		film.setAgeMinimum(Integer.valueOf(age_minimum));
		bll.insert(film);
		return film;
	}
	
	
	@DELETE @Path("/{id : \\d+}")
	public Film deleteFilm(@PathParam("id") int id) {
		Film film = bll.selectById(id);
		bll.delete(id);
		return film;
	}

	
	@PUT  @Path("/{id : \\d+}")
	public Film updateFilm(
			@PathParam("id") int id,
			@FormParam("titre") String title,
			@FormParam("description") String description,
			@FormParam("affiche") String affiche,
			@FormParam("duree") String duree,
			@FormParam("ageMinimum") String age_minimum) {
		Film film = bll.selectById(id);
		film.setNom(title);
		film.setDescription(description);
		film.setDuree(Integer.valueOf(duree));
		film.setAffiche(affiche);
		film.setAgeMinimum(Integer.valueOf(age_minimum));
		bll.update(film);
		return film;
	}
}
