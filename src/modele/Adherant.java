package modele;

import java.sql.Date;

public class Adherant {

	private int NoCarte;
	private Date dateInscreption;
	private String etat;
	private String typeAdherant;
	private String EMprofession;
	private String ETniveau;
	private Date ETdateEntr;
	private String ENgrade;
	private int idPersonne;
	private Date DateFinPenalisation;
	
	


	public Adherant() {
		
	}

	

	public Adherant(String typeAdherant, String eMprofession, String eTniveau,
			Date eTdateEntr, String eNgrade) {
		this.typeAdherant = typeAdherant;
		EMprofession = eMprofession;
		ETniveau = eTniveau;
		ETdateEntr = eTdateEntr;
		ENgrade = eNgrade;
	}





	public int getNoCarte() {
		return NoCarte;
	}

	public void setNoCarte(int noCarte) {
		NoCarte = noCarte;
	}

	public Date getDateInscreption() {
		return dateInscreption;
	}

	public void setDateInscreption(Date dateInscreption) {
		this.dateInscreption = dateInscreption;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public String getTypeAdherant() {
		return typeAdherant;
	}

	public void setTypeAdherant(String typeAdherant) {
		this.typeAdherant = typeAdherant;
	}

	public String getEMprofession() {
		return EMprofession;
	}

	public void setEMprofession(String eMprofession) {
		EMprofession = eMprofession;
	}

	public String getETniveau() {
		return ETniveau;
	}

	public void setETniveau(String eTniveau) {
		ETniveau = eTniveau;
	}

	public Date getETdateEntr() {
		return ETdateEntr;
	}

	public void setETdateEntr(Date eTdateEntr) {
		ETdateEntr = eTdateEntr;
	}

	public String getENgrade() {
		return ENgrade;
	}

	public void setENgrade(String eNgrade) {
		ENgrade = eNgrade;
	}

	public int getIdPersonne() {
		return idPersonne;
	}

	public void setIdPersonne(int idPersonne) {
		this.idPersonne = idPersonne;
	}
	
	
	public Date getDateFinPenalisation() {
		return DateFinPenalisation;
	}



	public void setDateFinPenalisation(Date dateFinPenalisation) {
		DateFinPenalisation = dateFinPenalisation;
	}

	
}
