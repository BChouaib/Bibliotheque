package controle;

import java.sql.Date;

import com.sun.corba.se.impl.javax.rmi.CORBA.Util;

import modele.Adherant;
import modele.Agent;
import modele.Compte;
import modele.ConfigurationDeSysteme;
import modele.DemandeQuitus;
import modele.DemandeReAdhesion;
import modele.Document;
import modele.Emprunt;
import modele.Exemplaire;
import modele.Personne;
import modele.Quitus;
import modele.Reservation;

public class Testing {

	public static void main(String[] args) {
		
		
		Date date=new Date(System.currentTimeMillis());
		Adherant adherant=new Adherant("Etudiant", null, "2eme", null, null);
		Compte compte=new Compte("is", "123");
		Personne personne=new Personne("islam", "benlabed", "ma3andouche@live.fr");
		
		new GestionBiblio().adhesioner(adherant, personne, compte);
		
		
		
		
		/*
		ConfigurationDeSysteme configurationDeSysteme=new ConfigurationDeSysteme(null, null, null, null, null, null,15);
		new GestionBiblio().creeConfigurationDeSysteme(configurationDeSysteme);
		*/
		
		
		/*
		Adherant adherant=new Adherant();
		adherant.setNoCarte(4);
		new GestionBiblio().penaliseAdherant(adherant);
		*/
		
		/*
		Adherant adherant=new Adherant();
		adherant.setNoCarte(4);
		adherant.setETniveau("3eme GL");
		new GestionBiblio().reAdhesioner(adherant);
		*/
		
		
		/*
		Compte compte=new Compte("chouj", "1234" );
		Personne personne=new GestionBiblio().authentifier(compte);
		System.out.println(personne.getNom());
		*/
		
		
		/*
		Compte compte=new Compte("chouj", "1234" );
		new GestionBiblio().authentifier(compte);
		compte.setStatut("Normale");
		new GestionBiblio().modifierStatutCompte(compte);
		*/
		
		
		/*
		Compte compte=new Compte("chouj", "1234" );
		new GestionBiblio().authentifier(compte);
		System.out.println(new GestionBiblio().authentifier(compte).getIdPersonne());
		System.out.println(compte.getIdPersonne());
		compte.setNomUtilisateur("chou");
		compte.setMotPasse("0000");
		new GestionBiblio().modifierCompte(compte);
		System.out.println(compte.getNomUtilisateur());
		*/
		
		
		/*
		DemandeReAdhesion demandeReAdhesion=new DemandeReAdhesion(4);
		new GestionBiblio().demanderReAdhesion(demandeReAdhesion);
		*/
		
		/*
		DemandeQuitus demandeQuitus=new DemandeQuitus(4); 
		new GestionBiblio().demanderQuitus(demandeQuitus);
		*/
		
		/*
		Quitus quitus=new Quitus(1);
		new GestionBiblio().etablirQuitus(quitus);
		*/
		
		
		/*
		Document document=new Document("LBSA11", "Java", "Site de zero", 2009, "Livre", "java prog", null, 350, null, null, 3);
		new GestionBiblio().ajouterDocument(document);
		*/
		
		/*
		Document document=new Document("LBSA11", "C++", "Site de zero", 2009, "Livre", "C++ prog", null, 350, null, null, 4);
		new GestionBiblio().supprimerDocument(document);
		*/
		
		/*
		Agent agent=new Agent();
		Personne personne =new Personne("salime", "ben", null);
		Compte compte=new Compte("sal", "123");
		new GestionBiblio().ajouterAgent(agent, personne, compte);
		*/
		
		/*
		Reservation reservation=new Reservation( 4, 1);
		new GestionBiblio().reserver(reservation);
		*/
		
		/*
		Reservation reservation=new Reservation(new Date(System.currentTimeMillis()+15*24*60*60*1000), 4, 1);
		reservation.setIdReservation(2);
		new GestionBiblio().anullerReservation(reservation);
		*/
		
		/*
		Emprunt emprunt=new Emprunt(1);
		emprunt.setIdEmprunt(1);
		new GestionBiblio().remis(emprunt);
		*/
		
		/*
		
		Reservation reservation = new Reservation();
		Exemplaire	exemplaire = new Exemplaire();
		Document document = new Document();
		Adherant adherant = new Adherant();
		Personne personne = new Personne();
		Emprunt emprunt=new Emprunt(1);
		emprunt.setIdEmprunt(1);
		new GestionBiblio().suivrePretes(emprunt, reservation, exemplaire, document, adherant, personne);
		
		System.out.println(personne.getNom());
		*/
		
		/*
		Compte compte=new Compte(null, null);
		System.out.println(new GestionBiblio().authentifier(compte).getNom());
		*/
	}

}
