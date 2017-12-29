package modele;

public class Compte {

	private String nomUtilisateur;
	private String motPasse;
	private String statut;
	private int idPersonne;
	
	public Compte() {
		
	}

	public Compte(String nomUtilisateur, String motPasse) {
		this.nomUtilisateur = nomUtilisateur;
		this.motPasse = motPasse;
	}

	public String getNomUtilisateur() {
		return nomUtilisateur;
	}

	public void setNomUtilisateur(String nomUtilisateur) {
		this.nomUtilisateur = nomUtilisateur;
	}

	public String getMotPasse() {
		return motPasse;
	}

	public void setMotPasse(String motPasse) {
		this.motPasse = motPasse;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public int getIdPersonne() {
		return idPersonne;
	}

	public void setIdPersonne(int idPersonne) {
		this.idPersonne = idPersonne;
	}
	
	
}
