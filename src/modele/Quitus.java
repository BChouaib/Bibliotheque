package modele;

import java.sql.Date;

public class Quitus {

	private int idQuitus;
	private Date dateEtablire;
	private int idDemandeQuitus;
	
	public Quitus() {
		
	}

	public Quitus(int idDemandeQuitus) {
		this.idDemandeQuitus = idDemandeQuitus;
	}

	public int getIdQuitus() {
		return idQuitus;
	}

	public void setIdQuitus(int idQuitus) {
		this.idQuitus = idQuitus;
	}

	public Date getDateEtablire() {
		return dateEtablire;
	}

	public void setDateEtablire(Date dateEtablire) {
		this.dateEtablire = dateEtablire;
	}

	public int getIdDemandeQuitus() {
		return idDemandeQuitus;
	}

	public void setIdDemandeQuitus(int idDemandeQuitus) {
		this.idDemandeQuitus = idDemandeQuitus;
	}
	
	
	
	
}
