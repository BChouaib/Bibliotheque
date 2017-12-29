package modele;

import java.sql.Date;

public class ConfigurationDeSysteme {

	private int idConfigurationDeSysteme;
	private Date dateOuvertureDePretes;
	private Date dateFermetureDePretes;
	private Date dateOuvertureDeDemandeQuitus;
	private Date dateFetmetureDeDemandeQuitus;
	private Date dateOuvertureDeDemanDeAdhesionEtReAdhesion;
	private Date dateFermetureDeDemandeDeAdhesionEtReAdhesion;
	private int dureePenalisation;
	private int dureeReservation;
	
	public ConfigurationDeSysteme() {
	}





	public ConfigurationDeSysteme(Date dateOuvertureDePretes, Date dateFermetureDePretes,
			Date dateOuvertureDeDemandeQuitus, Date dateFetmetureDeDemandeQuitus,
			Date dateOuvertureDeDemanDeAdhesionEtReAdhesion, Date dateFermetureDeDemandeDeAdhesionEtReAdhesion,
			int dureePenalisation, int dureeReservation) {
		
		this.dateOuvertureDePretes = dateOuvertureDePretes;
		this.dateFermetureDePretes = dateFermetureDePretes;
		this.dateOuvertureDeDemandeQuitus = dateOuvertureDeDemandeQuitus;
		this.dateFetmetureDeDemandeQuitus = dateFetmetureDeDemandeQuitus;
		this.dateOuvertureDeDemanDeAdhesionEtReAdhesion = dateOuvertureDeDemanDeAdhesionEtReAdhesion;
		this.dateFermetureDeDemandeDeAdhesionEtReAdhesion = dateFermetureDeDemandeDeAdhesionEtReAdhesion;
		this.dureePenalisation = dureePenalisation;
		this.dureeReservation = dureeReservation;
	}











	public int getIdConfigurationDeSysteme() {
		return idConfigurationDeSysteme;
	}


	public void setIdConfigurationDeSysteme(int idConfigurationDeSysteme) {
		this.idConfigurationDeSysteme = idConfigurationDeSysteme;
	}


	public Date getDateOuvertureDePretes() {
		return dateOuvertureDePretes;
	}


	public void setDateOuvertureDePretes(Date dateOuvertureDePretes) {
		this.dateOuvertureDePretes = dateOuvertureDePretes;
	}


	public Date getDateFermetureDePretes() {
		return dateFermetureDePretes;
	}


	public void setDateFermetureDePretes(Date dateFermetureDePretes) {
		this.dateFermetureDePretes = dateFermetureDePretes;
	}


	public Date getDateOuvertureDeDemandeQuitus() {
		return dateOuvertureDeDemandeQuitus;
	}


	public void setDateOuvertureDeDemandeQuitus(Date dateOuvertureDeDemandeQuitus) {
		this.dateOuvertureDeDemandeQuitus = dateOuvertureDeDemandeQuitus;
	}


	public Date getDateFetmetureDeDemandeQuitus() {
		return dateFetmetureDeDemandeQuitus;
	}


	public void setDateFetmetureDeDemandeQuitus(Date dateFetmetureDeDemandeQuitus) {
		this.dateFetmetureDeDemandeQuitus = dateFetmetureDeDemandeQuitus;
	}


	public Date getDateOuvertureDeDemanDeAdhesionEtReAdhesion() {
		return dateOuvertureDeDemanDeAdhesionEtReAdhesion;
	}


	public void setDateOuvertureDeDemanDeAdhesionEtReAdhesion(Date dateOuvertureDeDemanDeAdhesionEtReAdhesion) {
		this.dateOuvertureDeDemanDeAdhesionEtReAdhesion = dateOuvertureDeDemanDeAdhesionEtReAdhesion;
	}


	public Date getDateFermetureDeDemandeDeAdhesionEtReAdhesion() {
		return dateFermetureDeDemandeDeAdhesionEtReAdhesion;
	}


	public void setDateFermetureDeDemandeDeAdhesionEtReAdhesion(Date dateFermetureDeDemandeDeAdhesionEtReAdhesion) {
		this.dateFermetureDeDemandeDeAdhesionEtReAdhesion = dateFermetureDeDemandeDeAdhesionEtReAdhesion;
	}


	public int getDureePenalisation() {
		return dureePenalisation;
	}


	public void setDureePenalisation(int dureePenalisation) {
		this.dureePenalisation = dureePenalisation;
	}





	public int getDureeReservation() {
		return dureeReservation;
	}





	public void setDureeReservation(int dureeReservation) {
		this.dureeReservation = dureeReservation;
	}
	
	
	
	
	
	
}
