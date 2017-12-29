package modele;

public class Personne {

	 private int idPersonne;
	 private String Nom;
	 private String Prenom;
	 private String email;
	 private String typePersonne;
	
	 
	 public Personne() {
		
	}


	public Personne(String nom, String prenom, String email) {
		Nom = nom;
		Prenom = prenom;
		this.email = email;
	}

	




	public int getIdPersonne() {
		return idPersonne;
	}


	public void setIdPersonne(int idPersonne) {
		this.idPersonne = idPersonne;
	}


	public String getNom() {
		return Nom;
	}


	public void setNom(String nom) {
		Nom = nom;
	}


	public String getPrenom() {
		return Prenom;
	}


	public void setPrenom(String prenom) {
		Prenom = prenom;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getTypePersonne() {
		return typePersonne;
	}


	public void setTypePersonne(String typePersonne) {
		this.typePersonne = typePersonne;
	}
	 
	
	
	 
}
