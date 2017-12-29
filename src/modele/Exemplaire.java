package modele;

public class Exemplaire {

	private int idExemplaire;
	private String coteDocument;
	private String etat;
	
	
	public Exemplaire() {
		
	}


	public Exemplaire(int idExemplaire, String coteDocument, String etat) {
		this.idExemplaire = idExemplaire;
		this.coteDocument = coteDocument;
		this.etat = etat;
	}


	public int getIdExemplaire() {
		return idExemplaire;
	}


	public void setIdExemplaire(int idExemplaire) {
		this.idExemplaire = idExemplaire;
	}


	public String getCoteDocument() {
		return coteDocument;
	}


	public void setCoteDocument(String coteDocument) {
		this.coteDocument = coteDocument;
	}


	public String getEtat() {
		return etat;
	}


	public void setEtat(String etat) {
		this.etat = etat;
	}

	
	
	
}
