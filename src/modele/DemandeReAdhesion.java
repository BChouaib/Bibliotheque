package modele;

import java.sql.Date;

public class DemandeReAdhesion {

	private int idDemandeReAdhesion;
	private Date dateDemande;
	private int NoCarte;
	
	
	public DemandeReAdhesion() {
		
	}


	public DemandeReAdhesion( int noCarte) {
		NoCarte = noCarte;
	}


	public int getIdDemandeReAdhesion() {
		return idDemandeReAdhesion;
	}


	public void setIdDemandeReAdhesion(int idDemandeReAdhesion) {
		this.idDemandeReAdhesion = idDemandeReAdhesion;
	}


	public Date getDateDemande() {
		return dateDemande;
	}


	public void setDateDemande(Date dateDemande) {
		this.dateDemande = dateDemande;
	}


	public int getNoCarte() {
		return NoCarte;
	}


	public void setNoCarte(int noCarte) {
		NoCarte = noCarte;
	}
	
	
	
	
}
