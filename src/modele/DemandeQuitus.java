package modele;

import java.sql.Date;

public class DemandeQuitus {

	private int idDemandeQuitus;
	private Date dateDemande;
	private int NoCarte;
	
	
	public DemandeQuitus() {
		
	}


	public DemandeQuitus( int noCarte) {
		NoCarte = noCarte;
	}


	public int getIdDemandeQuitus() {
		return idDemandeQuitus;
	}


	public void setIdDemandeQuitus(int idDemandeQuitus) {
		this.idDemandeQuitus = idDemandeQuitus;
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
