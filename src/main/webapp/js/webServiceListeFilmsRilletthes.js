function charger_films() {
	
	var request = new XMLHttpRequest();

	request.onreadystatechange = function() {
		/* Permet de verifier que je viens de recevoir la reponse du serveur */
		if (this.readyState == 4) {
			/* Permet de verifier que le traitement s'est bien passe */
			if (this.status == 200) {
				afficher_films(request.responseText);
			}
		}
	};

	request.open("GET", "http://localhost:8080/TPFilRougeHiber/rest/films", true);
	request.send();
}



function afficher_films(reponse) {
	var contenuJSON = JSON.parse(reponse);
	var div_films_container = document.getElementById('container_film');
	var template = document.getElementById('template_film');

	div_films_container.innerHTML = "";
	for (i = 0; i < contenuJSON.length; i++) {
		var clone = document.importNode(template.content, true);

		var p_duree = clone.querySelector('#t_duree_film');
		var p_ageMinimum = clone.querySelector('#t_ageMinimum_film');
		var p_titre = clone.querySelector('#t_titre_film');
		var img_affiche = clone.querySelector('#t_affiche_film');
		var p_synopsis = clone.querySelector('#t_synopsis_film');


		p_titre.id = 't_titre_film' + contenuJSON[i].id;
		p_synopsis.id = 't_synopsis_film' + contenuJSON[i].id;
		p_duree.id = 't_duree_film' + contenuJSON[i].id;
		p_ageMinimum.id = 't_ageMinimum_film' + contenuJSON[i].id;
		img_affiche.id = 't_affiche_film' + contenuJSON[i].id;

		p_titre.innerHTML = contenuJSON[i].nom;
		img_affiche.src = contenuJSON[i].affiche;
		p_synopsis.innerHTML = contenuJSON[i].description;
		p_duree.innerHTML = contenuJSON[i].duree;
		p_ageMinimum.innerHTML = contenuJSON[i].ageMinimum;


		var btn_edit = clone.querySelector('#btn_edit');
		btn_edit.setAttribute("onclick", "edit_film(" + contenuJSON[i].id + ")");

		var btn_supprimer = clone.querySelector('#btn_supprimer');
		btn_supprimer.setAttribute("onclick", "supprimer_film(" + contenuJSON[i].id + ")");

		div_films_container.appendChild(clone);
	}
}


function edit_film(id) {
	
	var titre = document.getElementById('t_titre_film' + id).innerHTML;
	var affiche = document.getElementById('t_affiche_film' + id).src;
	var synopsis = document.getElementById('t_synopsis_film' + id).innerHTML;
	var duree = document.getElementById('t_duree_film' + id).innerHTML;
	var ageMinimum = document.getElementById('t_ageMinimum_film' + id).innerHTML;
	

	var input_titre = document.getElementById('film_titre');
	var input_affiche = document.getElementById('film_affiche');
	var ta_synopsis = document.getElementById('film_description');
	var input_duree = document.getElementById('film_duree');
	var input_ageMinimum = document.getElementById('film_ageMinimum');

	input_titre.value = titre;
	input_affiche.value = affiche;
	ta_synopsis.value = synopsis;
	input_duree.value = duree;
	input_ageMinimum.value = ageMinimum;

	var btn_valider = document.getElementById('btn_valider');
	btn_valider.value = "modifier";
	btn_valider.setAttribute("onclick", "modifier_film(" + id + ")");

}

function initialiser_film(){
	var input_titre = document.getElementById('film_titre');
	var input_affiche = document.getElementById('film_affiche');
	var ta_synopsis = document.getElementById('film_description');
	var input_duree = document.getElementById('film_duree');
	var input_ageMinimum = document.getElementById('film_ageMinimum');

	input_titre.value = "";
	input_affiche.value = "";
	ta_synopsis.value = "";
	input_duree.value = "";
	input_ageMinimum.value = "";
	
	var btn_valider = document.getElementById('btn_valider');
	btn_valider.value="Créer";
	btn_valider.setAttribute("onclick", "ajouter_film()");
}

function modifier_film(id) {
	var request = new XMLHttpRequest();

	request.onreadystatechange = function() {
		if (this.readyState == 4) {
			if (this.status == 200) {
				charger_films();
			}
		}
	}

	var titre = document.getElementById('film_titre').value;
	var affiche = document.getElementById('film_affiche').value;
	var synopsis = document.getElementById('film_description').value;
	var duree = document.getElementById('film_duuree').value;
	var ageMinimum = document.getElementById('film_ageMinimum').value;

	var data = "titre=" + titre + "&description=" + synopsis + "&affiche=" + affiche + "&duree=" + duree + "&ageMinimum=" + ageMinimum;
	request.open("PUT", "http://localhost:8080/TPFilRougeHiber/rest/films/ " + id, true);
	request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	request.send(data);
	initialiser_film();
}

function supprimer_film(id) {
	var request = new XMLHttpRequest();

	request.onreadystatechange = function() {
		if (this.readyState == 4) {
			if (this.status == 200) {
				charger_films();
			}
		}
	}

	request.open("DELETE", "http://localhost:8080/TPFilRougeHiber/rest/films/" + id, true);
	request.send();
}

function ajouter_film() {
	var request = new XMLHttpRequest();

	request.onreadystatechange = function() {
		if (this.readyState == 4) {
			if (this.status == 200) {
				charger_films();
			}
		}
	}

	var titre = document.getElementById('film_titre').value;
	var affiche = document.getElementById('film_affiche').value;
	var synopsis = document.getElementById('film_description').value;
	var duree = document.getElementById('film_duree').value;
	var ageMinimum = document.getElementById('film_ageMinimum').value;



	var data = "titre=" + titre + "&description=" + synopsis + "&affiche=" + affiche + "&duree=" + duree + "&ageMinimum=" + ageMinimum;
	request.open("POST", "http://localhost:8080/TPFilRougeHiber/rest/films", true);
	request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	request.send(data);
	initialiser_film();
}
