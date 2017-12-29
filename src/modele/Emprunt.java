package modele;

import java.sql.Date;

public class Emprunt {

	private int idEmprunt;
	private Date dateEmprunt;
	private Date dateRemis;
	private Date dateRemisPrevue;
	private int idReservation;
	
	public Emprunt() {
		
	}

	public Emprunt(int idReservation) {
		this.idReservation = idReservation;
	}

	public int getIdEmprunt() {
		return idEmprunt;
	}

	public void setIdEmprunt(int idEmprunt) {
		this.idEmprunt = idEmprunt;
	}

	public Date getDateEmprunt() {
		return dateEmprunt;
	}

	public void setDateEmprunt(Date dateEmprunt) {
		this.dateEmprunt = dateEmprunt;
	}

	public Date getDateRemis() {
		return dateRemis;
	}

	public void setDateRemis(Date dateRemis) {
		this.dateRemis = dateRemis;
	}

	public Date getDateRemisPrevue() {
		return dateRemisPrevue;
	}

	public void setDateRemisPrevue(Date dateRemisPrevue) {
		this.dateRemisPrevue = dateRemisPrevue;
	}

	public int getIdReservation() {
		return idReservation;
	}

	public void setIdReservation(int idReservation) {
		this.idReservation = idReservation;
	}
	
	
	
	
}
