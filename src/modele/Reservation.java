package modele;

import java.sql.Date;

public class Reservation {

	private int idReservation;
	private Date DateReservation;
	private int NoCarte;
	private int idExemplaire;
	
	
	public Reservation() {
		
	}


	


	public Reservation(Date dateReservation, int noCarte, int idExemplaire) {
		
		DateReservation = dateReservation;
		NoCarte = noCarte;
		this.idExemplaire = idExemplaire;
	}





	public int getIdReservation() {
		return idReservation;
	}


	public void setIdReservation(int idReservation) {
		this.idReservation = idReservation;
	}


	public Date getDateReservation() {
		return DateReservation;
	}


	public void setDateReservation(Date dateReservation) {
		DateReservation = dateReservation;
	}


	public int getNoCarte() {
		return NoCarte;
	}


	public void setNoCarte(int noCarte) {
		NoCarte = noCarte;
	}


	public int getIdExemplaire() {
		return idExemplaire;
	}


	public void setIdExemplaire(int idExemplaire) {
		this.idExemplaire = idExemplaire;
	}
	
	
	
	
}
