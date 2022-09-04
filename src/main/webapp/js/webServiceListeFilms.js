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
		
		var input_duree = clone.querySelector('#t_duree_film');
		var input_ageMinimum = clone.querySelector('#t_ageMinimum_film');
		var input_titre = clone.querySelector('#t_titre_film');
		var input_affiche = clone.querySelector('#t_affiche_film');
		var ta_synopsis = clone.querySelector('#t_synopsis_film');
		
		
		input_titre.id = 't_titre_film' + contenuJSON[i].id;
		ta_synopsis.id = 't_synopsis_film' + contenuJSON[i].id;
		input_duree.id = 't_duree_film'+ contenuJSON[i].id;
		input_ageMinimum.id = 't_ageMinimum_film'+contenuJSON[i].id;
		input_affiche.id = 't_affiche_film'+contenuJSON[i].id;
		
		input_titre.value = contenuJSON[i].nom;
		input_affiche.value = contenuJSON[i].affiche;
		ta_synopsis.value = contenuJSON[i].description;
		input_duree.value = contenuJSON[i].duree;
		input_ageMinimum.value = contenuJSON[i].ageMinimum;
		
		
		var btn_modifier = clone.querySelector('#btn_modifier');
		btn_modifier.setAttribute("onclick", "modifier_film("+contenuJSON[i].id+")");
		
		var btn_supprimer = clone.querySelector('#btn_supprimer');
		btn_supprimer.setAttribute("onclick", "supprimer_film("+contenuJSON[i].id+")");
		
		div_films_container.appendChild(clone);
	}
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
	
	var titre = document.getElementById('t_titre_film' + id).value;
	var affiche = document.getElementById('t_affiche_film' + id).value;
	var synopsis = document.getElementById('t_synopsis_film' + id).value;
	var duree = document.getElementById('t_duree_film' + id).value;
	var ageMinimum = document.getElementById('t_ageMinimum_film' + id).value;
	
	var data = "titre="+titre+"&description="+synopsis+"&affiche="+affiche+"&duree="+duree+"&ageMinimum="+ageMinimum;
	request.open("PUT", "http://localhost:8080/TPFilRougeHiber/rest/films/ " + id, true);
	request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	request.send(data);
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
	
	
	
	var data = "titre="+titre+"&description="+synopsis+"&affiche="+affiche+"&duree="+duree+"&ageMinimum="+ageMinimum;
	request.open("POST", "http://localhost:8080/TPFilRougeHiber/rest/films", true);
	request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	request.send(data);
}
