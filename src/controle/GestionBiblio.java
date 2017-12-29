package controle;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


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

public class GestionBiblio {

	public final String ADHERANT_ETAT_NORMALE="Normale";
	public final String ADHERANT_ETAT_PENALISE="Penalise";
	public final String ADHERANT_ETAT_BLOQUE="Bloque";
	public final String PERSONNE_TYPE_ADHERANT="Adherant";
	public final String PERSONNE_TYPE_AGENT="Agent";
	public final String PERSONNE_TYPE_ADMINISTRATEUR="Administrateur";
	public final String PERSONNE_STATUT_NORMALE="Normale";
	public final String PERSONNE_STATUT_BLOQUE="Bloque";
	public final String DOCUMENT_ETAT_DISPONIBLE="Disponible";
	public final String DOCUMENT_ETAT_RESERVER="Reserver";
	public final String DOCUMENT_ETAT_SUPPRIMER="Supprimer";
	public final String DOCUMENT_ETAT_PRETE="Prete";
	
	public final int MAX_NMBR_DE_RESERVATION=2;
	
	
	private ConnecterBDD cBdd;
	private Connection connection;
	private PreparedStatement preparedStatement;

	public GestionBiblio() {
		cBdd = new ConnecterBDD();
		connection = cBdd.getConnection();
	}
	
	
	//Personne 
	private void ajouterPersonne(Personne personne, Compte compte) {
		String sql = "INSERT INTO public.\"Personne\"(\"Nom\", \"Prenom\", email, \"typePersonne\")"
				+ "VALUES ( ?, ?, ?, ?);";

		try {
			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, personne.getNom());
			preparedStatement.setString(2, personne.getPrenom());
			preparedStatement.setString(3, personne.getEmail());
			preparedStatement.setString(4, personne.getTypePersonne());

			preparedStatement.executeUpdate();

			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			resultSet.next();

			personne.setIdPersonne(resultSet.getInt(1));

		} catch (SQLException e) {
			e.printStackTrace();
		}

		compte.setIdPersonne(personne.getIdPersonne());
		ajouterCompte(compte);

	}

	
	//Compte
	private void ajouterCompte(Compte compte) {
		String sql = "INSERT INTO public.\"Compte\"( \"nomUtilisateur\", \"motPasse\",  \"idPersonne\")"
				+ "VALUES (?, ?,  ?);";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, compte.getNomUtilisateur());
			preparedStatement.setString(2, compte.getMotPasse());
			preparedStatement.setInt(3, compte.getIdPersonne());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public Personne authentifier(Compte compte){
		Personne personne=new Personne();

		String sql = "SELECT * FROM public.\"Personne\", public.\"Compte\" "
				+ "WHERE \"nomUtilisateur\"=? and  \"motPasse\"=? and \"Compte\".\"idPersonne\" = \"Personne\".\"idPersonne\" ;";

		try {

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, compte.getNomUtilisateur());
			preparedStatement.setString(2, compte.getMotPasse());
			
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()){
				compte.setStatut(resultSet.getString("statut"));
				compte.setIdPersonne(resultSet.getInt("idPersonne"));
				
				personne.setIdPersonne(resultSet.getInt("idPersonne"));
				personne.setNom(resultSet.getString("Nom"));
				personne.setPrenom(resultSet.getString("Prenom"));
				personne.setEmail(resultSet.getString("email"));
				personne.setTypePersonne(resultSet.getString("typePersonne"));
				
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return personne;
		
	}
	
	public void modifierStatutCompte(Compte compte) {
		String sql = "UPDATE public.\"Compte\" SET  statut=? "
				+ " WHERE \"nomUtilisateur\"=? OR  \"idPersonne\"=? ";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, compte.getStatut());
			preparedStatement.setString(2, compte.getNomUtilisateur());
			preparedStatement.setInt(3, compte.getIdPersonne());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void modifierCompte(Compte compte) {
		String sql = "UPDATE public.\"Compte\" SET  \"motPasse\"=? "
				+ " WHERE  \"idPersonne\"=? ; ";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, compte.getMotPasse());
			preparedStatement.setInt(2, compte.getIdPersonne());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	
	//Adherant
	public void adhesioner(Adherant adherant, Personne personne, Compte compte) {
		Date date=new Date(System.currentTimeMillis());
		
		personne.setTypePersonne(PERSONNE_TYPE_ADHERANT);
		ajouterPersonne(personne, compte);
		adherant.setIdPersonne(personne.getIdPersonne());
		adherant.setDateInscreption(date);
		
		String sql = "INSERT INTO public.\"Adherant\"(\"dateInscreption\", \"typeAdherant\", \"EMprofession\",\"ETniveau\", \"ETdateEntr\", \"ENgrade\", \"idPersonne\")"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?);";

		try {

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setDate(1, adherant.getDateInscreption());
			preparedStatement.setString(2, adherant.getTypeAdherant());
			preparedStatement.setString(3, adherant.getEMprofession());
			preparedStatement.setString(4, adherant.getETniveau());
			preparedStatement.setDate(5, adherant.getETdateEntr());
			preparedStatement.setString(6, adherant.getENgrade());
			preparedStatement.setInt(7, adherant.getIdPersonne());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void penaliseAdherant(Adherant adherant) {
		int dureePenalisation = getConfigurationDeSysteme().getDureePenalisation();
		Date dateFinPenalisation = new Date(System.currentTimeMillis() + (dureePenalisation * 24 * 60 * 60 * 1000));


		adherant.setDateFinPenalisation(dateFinPenalisation);

		String sql = "UPDATE public.\"Adherant\"  SET  \"DateFinPenalisation\"=?" + "WHERE \"NoCarte\"=? ";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setDate(1, adherant.getDateFinPenalisation());
			preparedStatement.setInt(2, adherant.getNoCarte());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void reAdhesioner(Adherant adherant) {

		adherant.setEtat(ADHERANT_ETAT_NORMALE);

		String sql = "UPDATE public.\"Adherant\"  SET etat=? " + "WHERE \"NoCarte\"=? ";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, adherant.getEtat());
			preparedStatement.setInt(2, adherant.getNoCarte());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	//Agent
	public void ajouterAgent(Agent agent, Personne personne, Compte compte) {
		
		personne.setTypePersonne(PERSONNE_TYPE_AGENT);
		ajouterPersonne(personne, compte);
		agent.setIdPersonne(personne.getIdPersonne());
		

		String sql = "INSERT INTO public.\"Agent\"( \"idPersonne\") "
				+ " VALUES (?);";

		try {

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, agent.getIdPersonne());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	
	
	//DemandeReAdhesion
	public void demanderReAdhesion(DemandeReAdhesion demandeReAdhesion) {
		Date date=new Date(System.currentTimeMillis());
		
		String sql = "INSERT INTO public.\"DemandeReAdhesion\"( \"dateDemande\", \"NoCarte\")"
				+ " VALUES (?, ?); ";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setDate(1, date);
			preparedStatement.setInt(2, demandeReAdhesion.getNoCarte());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	
	//DemandeQuitus
	public void demanderQuitus(DemandeQuitus demandeQuitus) {
		Date date=new Date(System.currentTimeMillis());
		
		String sql = "INSERT INTO public.\"DemandeQuitus\"( \"dateDemande\", \"NoCarte\")"
				+ " VALUES (?, ?); ";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setDate(1, date);
			preparedStatement.setInt(2, demandeQuitus.getNoCarte());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	
	//Quitus()
	public void etablirQuitus(Quitus quitus) {
		Date date=new Date(System.currentTimeMillis());
		
		String sql = "INSERT INTO public.\"Quitus\"( \"dateEtablire\", \"idDemandeQuitus\")"
				+ " VALUES (?, ?); ";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setDate(1, date);
			preparedStatement.setInt(2, quitus.getIdDemandeQuitus());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void etablirListeQuitus() {
		suppAllQuitus();
		
		ArrayList<DemandeQuitus> list=getListeDemandeQuitus();
		Quitus quitus;
		for (int i = 0; i < list.size(); i++) {
			quitus=new Quitus();
			quitus.setIdDemandeQuitus(list.get(i).getIdDemandeQuitus()); 
			etablirQuitus(quitus);
		}
	}
	
	
	
	//Document
	public void ajouterDocument(Document document) {
		
		String sql = "INSERT INTO public.\"Document\"( \"coteDocument\", \"Titre\", \"Auteur\", \"anneEdition\", \"typeDocument\","
				+ " description, \"srcImage\", \"LVnombreDePage\", \"CDtype\", \"MRencadreur\",  \"nmbrExemplaire\")"
						+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, document.getCoteDocument());
			preparedStatement.setString(2, document.getTitre());
			preparedStatement.setString(3, document.getAuteur());
			preparedStatement.setInt(4, document.getAnneEdition());
			preparedStatement.setString(5, document.getTypeDocument());
			preparedStatement.setString(6, document.getDescription());
			preparedStatement.setString(7, document.getSrcImage());
			preparedStatement.setInt(8, document.getLVnombreDePage());
			preparedStatement.setString(9, document.getCDtype());
			preparedStatement.setString(10, document.getMRencadreur());
			preparedStatement.setInt(11, document.getNmbrExemplaire());
			

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(document!=null){
			Exemplaire exemplaire=new Exemplaire();
			exemplaire.setCoteDocument(document.getCoteDocument());
			for (int i = 0; i < document.getNmbrExemplaire(); i++) {
				ajouterExemplaire(exemplaire);
			}
		}

	}
	
	public void supprimerDocument(Document document) {
		
		String sql = "UPDATE public.\"Document\" SET  \"nmbrExemplaire\"=? "
				+ " WHERE \"coteDocument\"=? ;";
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, 0);
			preparedStatement.setString(2, document.getCoteDocument());
			

			preparedStatement.executeUpdate();
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		if(document!=null){
			Exemplaire exemplaire = new Exemplaire();
			exemplaire.setCoteDocument(document.getCoteDocument());
			supprimerAllExemplaire(exemplaire);
		}

	}
	
	
	public void modifierDocument(Document document) {
		int nmbrExmp=document.getNmbrExemplaire();
		
		String sql0 = "SELECT \"nmbrExemplaire\" FROM public.\"Document\" "
				+ "WHERE \"coteDocument\"=? ;";
		
		try {
			preparedStatement = connection.prepareStatement(sql0);
			preparedStatement.setString(1, document.getCoteDocument());
			
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()){
				nmbrExmp=resultSet.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		String sql = "UPDATE public.\"Document\" SET  \"Titre\"=?, \"Auteur\"=?, \"anneEdition\"=?, \"typeDocument\"=?, "
				+ " description=?, \"srcImage\"=?, \"LVnombreDePage\"=?, \"CDtype\"=?, \"MRencadreur\"=?, \"nmbrExemplaire\"=? "
				+ " WHERE \"coteDocument\"=? ;";
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, document.getTitre());
			preparedStatement.setString(2, document.getAuteur());
			preparedStatement.setInt(3, document.getAnneEdition());
			preparedStatement.setString(4, document.getTypeDocument());
			preparedStatement.setString(5, document.getDescription());
			preparedStatement.setString(6, document.getSrcImage());
			preparedStatement.setInt(7, document.getLVnombreDePage());
			preparedStatement.setString(8, document.getCDtype());
			preparedStatement.setString(9, document.getMRencadreur());
			preparedStatement.setInt(10, document.getNmbrExemplaire());
			preparedStatement.setString(11, document.getCoteDocument());
			
			

			preparedStatement.executeUpdate();
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		if(document!=null){
			Exemplaire exemplaire=new Exemplaire();
			exemplaire.setCoteDocument(document.getCoteDocument());
			if(nmbrExmp>document.getNmbrExemplaire()){
				for (int i = 0; i < nmbrExmp-document.getNmbrExemplaire(); i++) {
					supprimerExemplaire(exemplaire);
				}
			}else
				if(nmbrExmp<document.getNmbrExemplaire()){
					for (int i = 0; i < document.getNmbrExemplaire()-nmbrExmp; i++) {
						ajouterExemplaire(exemplaire);
					}
				}
		}

	}
	
	
	
	
	
	
	//Exemplaire
	private void ajouterExemplaire(Exemplaire exemplaire) {
		
		String sql = "INSERT INTO public.\"Exemplaire\"( \"coteDocument\")"
				+ " VALUES (?);";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, exemplaire.getCoteDocument());
			

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	private void supprimerExemplaire(Exemplaire exemplaire) {
		
		String sql = "SELECT \"idExemplaire\" FROM public.\"Exemplaire\" "
				+ "WHERE  \"coteDocument\"=? AND etat!=?;";

		String sql2 = "UPDATE public.\"Exemplaire\"  SET etat=?"
				+ " WHERE \"idExemplaire\"=? ;";
		
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, exemplaire.getCoteDocument());
			preparedStatement.setString(2, DOCUMENT_ETAT_SUPPRIMER);
			

			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()){
				int idExmp=resultSet.getInt(1);
				
				preparedStatement = connection.prepareStatement(sql2);
				preparedStatement.setString(1, DOCUMENT_ETAT_SUPPRIMER);
				preparedStatement.setInt(2, idExmp);
			

				preparedStatement.executeUpdate();
				
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private void supprimerAllExemplaire(Exemplaire exemplaire) {
		
		String sql = "UPDATE public.\"Exemplaire\"  SET etat=?"
				+ " WHERE \"coteDocument\"=? ;";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, DOCUMENT_ETAT_SUPPRIMER);
			preparedStatement.setString(2, exemplaire.getCoteDocument());
			

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void modifierEtatExemplaire(Exemplaire exemplaire) {
		
		String sql = "UPDATE public.\"Exemplaire\"  SET etat=?"
				+ " WHERE \"coteDocument\"=? ;";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, exemplaire.getEtat());
			preparedStatement.setString(2, exemplaire.getCoteDocument());
			

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	
	
	
	
	//Reservation
	public void reserver(Reservation reservation) {
		
		String sql = "INSERT INTO public.\"Reservation\"( \"DateReservation\", \"NoCarte\", \"idExemplaire\")"
				+ " VALUES (?, ?, ?);";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setDate(1, reservation.getDateReservation());
			preparedStatement.setInt(2, reservation.getNoCarte());
			preparedStatement.setInt(3, reservation.getIdExemplaire());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String sql2 = "UPDATE public.\"Exemplaire\" SET etat=?"
				+ " WHERE \"idExemplaire\"=?;";

		try {
			preparedStatement = connection.prepareStatement(sql2);
			preparedStatement.setString(1, DOCUMENT_ETAT_RESERVER);
			preparedStatement.setInt(2, reservation.getIdExemplaire());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}


	}
	
	
	public void anullerReservation(Reservation reservation) {
		
		String sql = "DELETE FROM public.\"Reservation\" "
				+ " WHERE \"idReservation\"=? ;";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, reservation.getIdReservation());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	
	
	//Emprunt
	@SuppressWarnings("deprecation")
	public void emprunter(Emprunt emprunt) {
		Date dateEm=new Date(System.currentTimeMillis());
		Date dateRmPr=new Date(System.currentTimeMillis()+(getConfigurationDeSysteme().getDureeReservation()*24*60*60*1000));

		if(dateRmPr.getDay()==5)
			dateRmPr.setTime(dateRmPr.getTime()+(2*24*60*60*1000));
		else
			if(dateRmPr.getDay()==6)
				dateRmPr.setTime(dateRmPr.getTime()+(1*24*60*60*1000));
		
		
		String sql = "INSERT INTO public.\"Emprunt\"(\"dateEmprunt\", \"dateRemisPrevue\", \"idReservation\")"
				+ "VALUES (?, ?, ?);";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setDate(1, dateEm);
			preparedStatement.setDate(2, dateRmPr);
			preparedStatement.setInt(3, emprunt.getIdReservation());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		int idExemplaire=getReservation(emprunt.getIdReservation()).getIdExemplaire();
		
		String sql2 = "UPDATE public.\"Exemplaire\" SET etat=?"
				+ " WHERE \"idExemplaire\"=?;";

		try {
			preparedStatement = connection.prepareStatement(sql2);
			preparedStatement.setString(1, DOCUMENT_ETAT_PRETE);
			preparedStatement.setInt(2, idExemplaire);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	
	public void remis(Emprunt emprunt) {
		Date dateR=new Date(System.currentTimeMillis());
		
		
		
		String sql = "UPDATE public.\"Emprunt\" SET  \"dateRemis\"=? "
				+ " WHERE \"idEmprunt\"=? ;";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setDate(1, dateR);
			preparedStatement.setInt(2, emprunt.getIdEmprunt());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		emprunt=getEmprunt(emprunt.getIdEmprunt());
		int idExemplaire=getReservation(emprunt.getIdReservation()).getIdExemplaire();
		
		String sql2 = "UPDATE public.\"Exemplaire\" SET etat=?"
				+ " WHERE \"idExemplaire\"=?;";

		try {
			preparedStatement = connection.prepareStatement(sql2);
			preparedStatement.setString(1, DOCUMENT_ETAT_DISPONIBLE);
			preparedStatement.setInt(2, idExemplaire);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		

	}
	
	
	public void suivrePretes(Emprunt emprunt,Reservation reservation,Exemplaire exemplaire,Document document,Adherant adherant,Personne personne) {
		
		String sql = "SELECT  * FROM  public.\"Emprunt\",  public.\"Reservation\",  public.\"Exemplaire\",  "
				+ "public.\"Document\",  public.\"Adherant\", public.\"Personne\" "
						+ "WHERE  \"Reservation\".\"idReservation\" = \"Emprunt\".\"idReservation\" AND"
								+ " \"Reservation\".\"idExemplaire\" = \"Exemplaire\".\"idExemplaire\" AND"
										+ "  \"Exemplaire\".\"coteDocument\" = \"Document\".\"coteDocument\" AND"
												+ " \"Adherant\".\"idPersonne\" = \"Personne\".\"idPersonne\" AND"
														+ " \"Adherant\".\"NoCarte\" = \"Reservation\".\"NoCarte\" AND"
																+ " \"Emprunt\".\"idEmprunt\" = ? ;";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, emprunt.getIdEmprunt());

			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()){
				emprunt.setDateEmprunt(resultSet.getDate("dateEmprunt"));
				emprunt.setDateRemis(resultSet.getDate("dateRemis"));
				emprunt.setDateRemisPrevue(resultSet.getDate("dateRemisPrevue"));
				emprunt.setIdReservation(resultSet.getInt("idReservation"));
				
				reservation.setIdReservation(resultSet.getInt("idReservation"));
				reservation.setDateReservation(resultSet.getDate("DateReservation"));
				reservation.setNoCarte(resultSet.getInt("NoCarte"));
				reservation.setIdExemplaire(resultSet.getInt("idExemplaire"));

				exemplaire.setIdExemplaire(resultSet.getInt("idExemplaire"));
				exemplaire.setCoteDocument(resultSet.getString("coteDocument"));
				exemplaire.setEtat(resultSet.getString("etat"));

				document.setCoteDocument(resultSet.getString("coteDocument"));
				document.setTitre(resultSet.getString("Titre"));
				document.setAuteur(resultSet.getString("Auteur"));
				document.setAnneEdition(resultSet.getInt("anneEdition"));
				document.setTypeDocument(resultSet.getString("typeDocument"));
				document.setDescription(resultSet.getString("description"));
				document.setSrcImage(resultSet.getString("srcImage"));
				document.setLVnombreDePage(resultSet.getInt("LVnombreDePage"));
				document.setCDtype(resultSet.getString("CDtype"));
				document.setMRencadreur(resultSet.getString("MRencadreur"));
				document.setNmbrExemplaire(resultSet.getInt("nmbrExemplaire"));
				
				adherant.setNoCarte(resultSet.getInt("NoCarte"));
				adherant.setDateInscreption(resultSet.getDate("dateInscreption"));
				adherant.setEtat(resultSet.getString("etat"));
				adherant.setTypeAdherant(resultSet.getString("typeAdherant"));
				adherant.setEMprofession(resultSet.getString("EMprofession"));
				adherant.setETniveau(resultSet.getString("ETniveau"));
				adherant.setETdateEntr(resultSet.getDate("ETdateEntr"));
				adherant.setENgrade(resultSet.getString("ENgrade"));
				adherant.setIdPersonne(resultSet.getInt("idPersonne"));
				adherant.setDateFinPenalisation(resultSet.getDate("DateFinPenalisation"));
				
				personne.setIdPersonne(resultSet.getInt("idPersonne"));
				personne.setNom(resultSet.getString("Nom"));
				personne.setPrenom(resultSet.getString("Prenom"));
				personne.setEmail(resultSet.getString("email"));
				personne.setTypePersonne(resultSet.getString("typePersonne"));
				
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	
	
	
	//ConfigurationDeSystem
	public void creeConfigurationDeSysteme(ConfigurationDeSysteme configurationDeSysteme) {
		String sql = "INSERT INTO public.\"ConfigurationDeSysteme\"( \"dateOuvertureDePretes\", \"dateFermetureDePretes\", "
				+ " \"dateOuvertureDeDemandeQuitus\", \"dateFetmetureDeDemandeQuitus\",  \"dateOuvertureDeDemanDeAdhesionEtReAdhesion\","
				+ " \"dateFermetureDeDemandeDeAdhesionEtReAdhesion\", \"dureePenalisation\" , \"dureeReservation\")"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?));";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setDate(1, configurationDeSysteme.getDateOuvertureDePretes());
			preparedStatement.setDate(2, configurationDeSysteme.getDateFermetureDePretes());
			preparedStatement.setDate(3, configurationDeSysteme.getDateOuvertureDeDemandeQuitus());
			preparedStatement.setDate(4, configurationDeSysteme.getDateFetmetureDeDemandeQuitus());
			preparedStatement.setDate(5, configurationDeSysteme.getDateOuvertureDeDemanDeAdhesionEtReAdhesion());
			preparedStatement.setDate(6, configurationDeSysteme.getDateFermetureDeDemandeDeAdhesionEtReAdhesion());
			preparedStatement.setInt(7, configurationDeSysteme.getDureePenalisation());
			preparedStatement.setInt(8, configurationDeSysteme.getDureeReservation());
			

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void modifierConfigurationDeSysteme(ConfigurationDeSysteme configurationDeSysteme) {
		String sql = "UPDATE public.\"ConfigurationDeSysteme\" "
				+ " SET  \"dateOuvertureDePretes\"=?, \"dateFermetureDePretes\"=?, "
				+ " \"dateOuvertureDeDemandeQuitus\"=?, \"dateFetmetureDeDemandeQuitus\"=?,  "
				+ " \"dateOuvertureDeDemanDeAdhesionEtReAdhesion\"=?, \"dateFermetureDeDemandeDeAdhesionEtReAdhesion\"=?, "
				+ " \"dureePenalisation\"=? , \"dureeReservation\"=?" + " WHERE \"idConfigurationDeSysteme\"=1 ;";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setDate(1, configurationDeSysteme.getDateOuvertureDePretes());
			preparedStatement.setDate(2, configurationDeSysteme.getDateFermetureDePretes());
			preparedStatement.setDate(3, configurationDeSysteme.getDateOuvertureDeDemandeQuitus());
			preparedStatement.setDate(4, configurationDeSysteme.getDateFetmetureDeDemandeQuitus());
			preparedStatement.setDate(5, configurationDeSysteme.getDateOuvertureDeDemanDeAdhesionEtReAdhesion());
			preparedStatement.setDate(6, configurationDeSysteme.getDateFermetureDeDemandeDeAdhesionEtReAdhesion());
			preparedStatement.setInt(7, configurationDeSysteme.getDureePenalisation());
			preparedStatement.setInt(8, configurationDeSysteme.getDureeReservation());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// les Getters
	public ConfigurationDeSysteme getConfigurationDeSysteme() {
		ConfigurationDeSysteme configurationDeSysteme = new ConfigurationDeSysteme();

		String sql = "SELECT \"idConfigurationDeSysteme\", \"dateOuvertureDePretes\", \"dateFermetureDePretes\","
				+ " \"dateOuvertureDeDemandeQuitus\", \"dateFetmetureDeDemandeQuitus\","
				+ "\"dateOuvertureDeDemanDeAdhesionEtReAdhesion\", \"dateFermetureDeDemandeDeAdhesionEtReAdhesion\","
				+ " \"dureePenalisation\" , \"dureeReservation\"" + " FROM public.\"ConfigurationDeSysteme\";";

		try {
			preparedStatement = connection.prepareStatement(sql);

			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				configurationDeSysteme.setDateOuvertureDePretes(resultSet.getDate("dateOuvertureDePretes"));
				configurationDeSysteme.setDateFermetureDePretes(resultSet.getDate("dateFermetureDePretes"));
				configurationDeSysteme
						.setDateOuvertureDeDemandeQuitus(resultSet.getDate("dateOuvertureDeDemandeQuitus"));
				configurationDeSysteme
						.setDateFetmetureDeDemandeQuitus(resultSet.getDate("dateFetmetureDeDemandeQuitus"));
				configurationDeSysteme.setDateOuvertureDeDemanDeAdhesionEtReAdhesion(
						resultSet.getDate("dateOuvertureDeDemanDeAdhesionEtReAdhesion"));
				configurationDeSysteme.setDateFermetureDeDemandeDeAdhesionEtReAdhesion(
						resultSet.getDate("dateFermetureDeDemandeDeAdhesionEtReAdhesion"));
				configurationDeSysteme.setDureePenalisation(resultSet.getInt("dureePenalisation"));
				configurationDeSysteme.setDureeReservation(resultSet.getInt("dureeReservation"));
				

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return configurationDeSysteme;
	}

	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean isNomUtilisateurExist(String nomUtilisateur){
		String sql = "SELECT * FROM public.\"Compte\" "
				+ "WHERE \"nomUtilisateur\"=? ;";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, nomUtilisateur);

			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()){
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean isIdPersonneAgentExist(int idPersonne){
		String sql = "SELECT * FROM public.\"Agent\" "
				+ "WHERE \"idPersonne\"=? ;";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idPersonne);

			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()){
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean isCoteDocumentExist(String coteDocument){
		String sql = "SELECT * FROM public.\"Document\" "
				+ "WHERE \"coteDocument\"=? ;";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, coteDocument);

			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()){
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean isNoCarteExist(int NoCarte){
		String sql = "SELECT * FROM public.\"Adherant\" "
				+ "WHERE \"NoCarte\"=? ;";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, NoCarte);

			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()){
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean isAdherantDemanderQuitus(int NoCarte){
		String sql = "SELECT * FROM public.\"DemandeQuitus\" "
				+ "WHERE \"NoCarte\"=? ;  ";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, NoCarte);
			
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()){
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean isAdherantDemanderReAdhesion(int NoCarte){
		String sql = "SELECT * FROM public.\"DemandeReAdhesion\" "
				+ "WHERE \"NoCarte\"=? ;  ";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, NoCarte);
			
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()){
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public Adherant getAdherant(int NoCarte){
		Adherant adherant=new Adherant();
		String sql = "SELECT * FROM public.\"Adherant\" "
				+ "WHERE \"NoCarte\"=? ;  ";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, NoCarte);
			
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()){
				adherant.setNoCarte(resultSet.getInt("NoCarte"));
				adherant.setDateInscreption(resultSet.getDate("dateInscreption"));
				adherant.setEtat(resultSet.getString("etat"));
				adherant.setTypeAdherant(resultSet.getString("typeAdherant"));
				adherant.setEMprofession(resultSet.getString("EMprofession"));
				adherant.setETniveau(resultSet.getString("ETniveau"));
				adherant.setETdateEntr(resultSet.getDate("ETdateEntr"));
				adherant.setENgrade(resultSet.getString("ENgrade"));
				adherant.setIdPersonne(resultSet.getInt("idPersonne"));
				adherant.setDateFinPenalisation(resultSet.getDate("DateFinPenalisation"));
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return adherant;
	}

	
	public ArrayList<DemandeReAdhesion> getListeDemandeReAdhesion(){
		ArrayList<DemandeReAdhesion> list=new ArrayList<DemandeReAdhesion>();
		DemandeReAdhesion demandeReAdhesion;
		String sql = "SELECT * FROM public.\"DemandeReAdhesion\", public.\"Adherant\" "
				+ " WHERE \"DemandeReAdhesion\".\"NoCarte\" = \"Adherant\".\"NoCarte\" AND \"Adherant\".etat = 'Bloque'  ";

		try {
			preparedStatement = connection.prepareStatement(sql);

			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()){
				demandeReAdhesion =new DemandeReAdhesion();
				demandeReAdhesion.setIdDemandeReAdhesion(resultSet.getInt("idDemandeReAdhesion"));
				demandeReAdhesion.setDateDemande(resultSet.getDate("dateDemande"));
				demandeReAdhesion.setNoCarte(resultSet.getInt("NoCarte"));
				list.add(demandeReAdhesion);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public ArrayList<DemandeQuitus> getListeDemandeQuitus(){
		ArrayList<DemandeQuitus> list=new ArrayList<DemandeQuitus>();
		DemandeQuitus demandeQuitus;
		String sql = "SELECT * FROM public.\"DemandeQuitus\", public.\"Adherant\" "
				+ " WHERE \"DemandeQuitus\".\"NoCarte\" = \"Adherant\".\"NoCarte\" AND \"Adherant\".etat = 'Normale'  ";

		try {
			preparedStatement = connection.prepareStatement(sql);

			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()){
				demandeQuitus =new DemandeQuitus();
				demandeQuitus.setIdDemandeQuitus(resultSet.getInt("idDemandeQuitus"));
				demandeQuitus.setDateDemande(resultSet.getDate("dateDemande"));
				demandeQuitus.setNoCarte(resultSet.getInt("NoCarte"));
				list.add(demandeQuitus);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public ArrayList<Emprunt> getListeEmprunt(){
		Emprunt emprunt;
		ArrayList<Emprunt> list=new ArrayList<Emprunt>();
		
		String sql = "SELECT * FROM public.\"Emprunt\" ";

		try {
			preparedStatement = connection.prepareStatement(sql);
			
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()){
				emprunt=new Emprunt();
				emprunt.setIdEmprunt(resultSet.getInt("idEmprunt"));
				emprunt.setDateEmprunt(resultSet.getDate("dateEmprunt"));
				emprunt.setDateRemis(resultSet.getDate("dateRemis"));
				emprunt.setDateRemisPrevue(resultSet.getDate("dateRemisPrevue"));
				emprunt.setIdReservation(resultSet.getInt("idReservation"));
				
				
				list.add(emprunt);
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public ArrayList<Document> getListeDocument(){
		Document document;
		ArrayList<Document> list=new ArrayList<Document>();
		
		String sql = "SELECT * FROM public.\"Document\" "
				+ "WHERE \"nmbrExemplaire\">0 ";

		try {
			preparedStatement = connection.prepareStatement(sql);

			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()){
				document=new Document();
				document.setCoteDocument(resultSet.getString("coteDocument"));
				document.setTitre(resultSet.getString("Titre"));
				document.setAuteur(resultSet.getString("Auteur"));
				document.setAnneEdition(resultSet.getInt("anneEdition"));
				document.setTypeDocument(resultSet.getString("typeDocument"));
				document.setDescription(resultSet.getString("description"));
				document.setSrcImage(resultSet.getString("srcImage"));
				document.setLVnombreDePage(resultSet.getInt("LVnombreDePage"));
				document.setCDtype(resultSet.getString("CDtype"));
				document.setMRencadreur(resultSet.getString("MRencadreur"));
				document.setNmbrExemplaire(resultSet.getInt("nmbrExemplaire"));
				
				
				list.add(document);
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public ArrayList<Personne> getListePersonne(){
		Personne personne;
		ArrayList<Personne> list=new ArrayList<Personne>();
		
		String sql = "SELECT * FROM public.\"Personne\" "
				+ "WHERE \"idPersonne\"!=1 ";

		try {
			preparedStatement = connection.prepareStatement(sql);

			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()){
				personne=new Personne();
				personne.setIdPersonne(resultSet.getInt("idPersonne"));
				personne.setNom(resultSet.getString("Nom"));
				personne.setPrenom(resultSet.getString("Prenom"));
				personne.setEmail(resultSet.getString("email"));
				personne.setTypePersonne(resultSet.getString("typePersonne"));
				
				
				list.add(personne);
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	
	public ArrayList<Quitus> getListeQuitus(){
		ArrayList<Quitus> list=new ArrayList<Quitus>();
		Quitus quitus;
		String sql = "SELECT  * FROM   public.\"Quitus\" ";

		try {
			preparedStatement = connection.prepareStatement(sql);

			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()){
				quitus =new Quitus();
				quitus.setIdQuitus(resultSet.getInt("idQuitus"));
				quitus.setDateEtablire(resultSet.getDate("dateEtablire"));
				quitus.setIdDemandeQuitus(resultSet.getInt("idDemandeQuitus"));
				list.add(quitus);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	public Personne getPersonne(int idPersonne){
		Personne personne=new Personne();
		String sql = "SELECT * FROM public.\"Personne\" "
				+ "WHERE \"idPersonne\"=? ;  ";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idPersonne);
			
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()){
				personne.setIdPersonne(resultSet.getInt("idPersonne"));
				personne.setNom(resultSet.getString("Nom"));
				personne.setPrenom(resultSet.getString("Prenom"));
				personne.setEmail(resultSet.getString("email"));
				personne.setTypePersonne(resultSet.getString("typePersonne"));
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return personne;
	}
	
	public Compte getCompte(int idPersonne){
		Compte compte=new Compte();
		String sql = "SELECT * FROM public.\"Compte\" "
				+ "WHERE \"idPersonne\"=? ;  ";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idPersonne);
			
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()){
				compte.setNomUtilisateur(resultSet.getString("nomUtilisateur"));
				compte.setMotPasse(resultSet.getString("motPasse"));
				compte.setStatut(resultSet.getString("statut"));
				compte.setIdPersonne(resultSet.getInt("idPersonne"));
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return compte;
	}
	
	
	public void bloqueAllAdherant(){
		String sql = "UPDATE public.\"Adherant\" SET  etat=?  ;";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, ADHERANT_ETAT_BLOQUE);
			
			preparedStatement.executeUpdate();
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

	public void verifierEmpruntAdherant(int NoCarte){
		Date dateToday=new Date(System.currentTimeMillis());
		Adherant adherant=new Adherant();
		adherant.setNoCarte(NoCarte);
		
		ArrayList<Emprunt> listEmpruntNoRemis=getListeAdherantEmpruntNoRemis(NoCarte);
		for (Emprunt emprunt : listEmpruntNoRemis) {
			if(emprunt.getDateRemisPrevue().before(dateToday)){
				penaliseAdherant(adherant);
				break;
			}
		}
		
		
	}

	
	
	public Adherant getAdherantWithIdPersonne(int idPersonne){
		Adherant adherant=new Adherant();
		String sql = "SELECT * FROM public.\"Adherant\" "
				+ "WHERE \"idPersonne\"=? ;  ";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idPersonne);
			
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()){
				adherant.setNoCarte(resultSet.getInt("NoCarte"));
				adherant.setDateInscreption(resultSet.getDate("dateInscreption"));
				adherant.setEtat(resultSet.getString("etat"));
				adherant.setTypeAdherant(resultSet.getString("typeAdherant"));
				adherant.setEMprofession(resultSet.getString("EMprofession"));
				adherant.setETniveau(resultSet.getString("ETniveau"));
				adherant.setETdateEntr(resultSet.getDate("ETdateEntr"));
				adherant.setENgrade(resultSet.getString("ENgrade"));
				adherant.setIdPersonne(resultSet.getInt("idPersonne"));
				adherant.setDateFinPenalisation(resultSet.getDate("DateFinPenalisation"));
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return adherant;
	}

	public DemandeQuitus getDemandeQuitus(int idDemandeQuitus){
		DemandeQuitus  demandeQuitus=new DemandeQuitus();
		String sql = "SELECT * FROM  public.\"DemandeQuitus\" "
				+ " WHERE  \"idDemandeQuitus\"=? ;  ";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idDemandeQuitus);
			
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()){
				demandeQuitus.setIdDemandeQuitus(resultSet.getInt("idDemandeQuitus"));
				demandeQuitus.setDateDemande(resultSet.getDate("dateDemande"));
				demandeQuitus.setNoCarte(resultSet.getInt("NoCarte"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return demandeQuitus;
	}

	public Document getDocument(String coteDocument){
		Document document=new Document();
		
		String sql = "SELECT * FROM public.\"Document\" "
				+ "WHERE \"coteDocument\"=? ;  ";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, coteDocument);
			
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()){
				document.setCoteDocument(resultSet.getString("coteDocument"));
				document.setTitre(resultSet.getString("Titre"));
				document.setAuteur(resultSet.getString("Auteur"));
				document.setAnneEdition(resultSet.getInt("anneEdition"));
				document.setTypeDocument(resultSet.getString("typeDocument"));
				document.setDescription(resultSet.getString("description"));
				document.setSrcImage(resultSet.getString("srcImage"));
				document.setLVnombreDePage(resultSet.getInt("LVnombreDePage"));
				document.setCDtype(resultSet.getString("CDtype"));
				document.setMRencadreur(resultSet.getString("MRencadreur"));
				document.setNmbrExemplaire(resultSet.getInt("nmbrExemplaire"));
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return document;
	}

	public Exemplaire getExemplaire(int idExemplaire){
		Exemplaire exemplaire=new Exemplaire();
		
		String sql = "SELECT * FROM public.\"Exemplaire\" "
				+ "WHERE \"idExemplaire\"=? ;  ";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idExemplaire);
			
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()){
				exemplaire.setIdExemplaire(resultSet.getInt("idExemplaire"));
				exemplaire.setEtat(resultSet.getString("etat"));
				exemplaire.setCoteDocument(resultSet.getString("coteDocument"));
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return exemplaire;
	}

	public Emprunt getEmprunt(int idEmprunt){
		Emprunt emprunt=new Emprunt();
		
		String sql = "SELECT * FROM public.\"Emprunt\" "
				+ "WHERE \"idEmprunt\"=? ;  ";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idEmprunt);
			
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()){
				emprunt.setIdEmprunt(resultSet.getInt("idEmprunt"));
				emprunt.setDateEmprunt(resultSet.getDate("dateEmprunt"));
				emprunt.setDateRemis(resultSet.getDate("dateRemis"));
				emprunt.setDateRemisPrevue(resultSet.getDate("dateRemisPrevue"));
				emprunt.setIdReservation(resultSet.getInt("idReservation"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return emprunt;
	}

	public Reservation getReservation(int idReservation){
		Reservation reservation=new Reservation();
		
		String sql = "SELECT * FROM public.\"Reservation\" "
				+ "WHERE \"idReservation\"=? ;  ";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idReservation);
			
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()){
				reservation.setIdReservation(resultSet.getInt("idReservation"));
				reservation.setDateReservation(resultSet.getDate("DateReservation"));
				reservation.setNoCarte(resultSet.getInt("NoCarte"));
				reservation.setIdExemplaire(resultSet.getInt("idExemplaire"));
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return reservation;
	}

	public Reservation getReservationWithIdExemplaire(int idExemplaire){
		Reservation reservation=new Reservation();
		
		String sql = "SELECT * FROM public.\"Reservation\" "
				+ "WHERE \"idExemplaire\"=? ;  ";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idExemplaire);
			
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()){
				reservation.setIdReservation(resultSet.getInt("idReservation"));
				reservation.setDateReservation(resultSet.getDate("DateReservation"));
				reservation.setNoCarte(resultSet.getInt("NoCarte"));
				reservation.setIdExemplaire(resultSet.getInt("idExemplaire"));
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return reservation;
	}

	public Date getDateFinPenalisation(int NoCarte){
		Date date=new Date(0);
		
		String sql = "SELECT \"DateFinPenalisation\" FROM public.\"Adherant\"  " 
					+ "WHERE \"NoCarte\"=?  ";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, NoCarte);
			
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()){
				date=resultSet.getDate("DateFinPenalisation");
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return date;
	}

	
	public int getIdExemplaireDisponible(String coteDocument){
		int idExemplaire=0;
		
		String sql = "SELECT * FROM public.\"Exemplaire\" "
				+ "WHERE \"coteDocument\"=? and etat=? ;  ";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, coteDocument);
			preparedStatement.setString(2, DOCUMENT_ETAT_DISPONIBLE);
			
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()){
				idExemplaire=resultSet.getInt("idExemplaire");
				return idExemplaire;
			}
			
			
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, coteDocument);
			preparedStatement.setString(2, DOCUMENT_ETAT_PRETE);
			
			resultSet=preparedStatement.executeQuery();
			if(resultSet.next()){
				idExemplaire=resultSet.getInt("idExemplaire");
				return idExemplaire;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return idExemplaire;
	}

	
	
	
	public ArrayList<Reservation> getListeAdherantReservation(int NoCarte){
		Reservation reservation;
		ArrayList<Reservation> list=new ArrayList<Reservation>();
		
		String sql = "SELECT  * FROM  public.\"Reservation\""
				+ "  LEFT JOIN public.\"Emprunt\" "
				+ " ON   \"Reservation\".\"idReservation\" = \"Emprunt\".\"idReservation\""
						+ "WHERE \"NoCarte\"=? AND \"Emprunt\".\"idEmprunt\" IS NULL ;  ";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, NoCarte);
			
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()){
				reservation=new Reservation();
				reservation.setIdReservation(resultSet.getInt("idReservation"));
				reservation.setDateReservation(resultSet.getDate("DateReservation"));
				reservation.setNoCarte(resultSet.getInt("NoCarte"));
				reservation.setIdExemplaire(resultSet.getInt("idExemplaire"));
				
				Date dateFinRes=new Date(reservation.getDateReservation().getTime()+(getConfigurationDeSysteme().getDureeReservation()*24*60*60*1000));
				Date dateAujourdhui=new Date(System.currentTimeMillis());
				
				if(dateFinRes.after(dateAujourdhui)){
					list.add(reservation);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	
	
	public ArrayList<Emprunt> getListeAdherantEmprunt(int NoCarte){
		Emprunt emprunt;
		ArrayList<Emprunt> list=new ArrayList<Emprunt>();
		
		String sql = "SELECT * FROM public.\"Emprunt\" , public.\"Reservation\" "
				+ "WHERE \"NoCarte\"=? and \"Emprunt\".\"idReservation\" = \"Reservation\".\"idReservation\" ;  ";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, NoCarte);
			
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()){
				emprunt=new Emprunt();
				emprunt.setIdEmprunt(resultSet.getInt("idEmprunt"));
				emprunt.setDateEmprunt(resultSet.getDate("dateEmprunt"));
				emprunt.setDateRemis(resultSet.getDate("dateRemis"));
				emprunt.setDateRemisPrevue(resultSet.getDate("dateRemisPrevue"));
				emprunt.setIdReservation(resultSet.getInt("idReservation"));
				
				
				list.add(emprunt);
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public ArrayList<Emprunt> getListeAdherantEmpruntNoRemis(int NoCarte){
		Emprunt emprunt;
		ArrayList<Emprunt> list=new ArrayList<Emprunt>();
		
		String sql = "SELECT * FROM public.\"Emprunt\" , public.\"Reservation\" "
				+ "WHERE \"NoCarte\"=? and \"dateRemis\"IS NULL and \"Emprunt\".\"idReservation\" = \"Reservation\".\"idReservation\" ;  ";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, NoCarte);
			
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()){
				emprunt=new Emprunt();
				emprunt.setIdEmprunt(resultSet.getInt("idEmprunt"));
				emprunt.setDateEmprunt(resultSet.getDate("dateEmprunt"));
				emprunt.setDateRemis(resultSet.getDate("dateRemis"));
				emprunt.setDateRemisPrevue(resultSet.getDate("dateRemisPrevue"));
				emprunt.setIdReservation(resultSet.getInt("idReservation"));
				
				
				list.add(emprunt);
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	
	
	public int getNmbrDePretesDeDocument(String coteDocument){
		int nmbrDePretes=0;
		Reservation reservation;
		Exemplaire exemplaire;
		
		ArrayList<Emprunt> empruntListe=getListeEmprunt();
		
		for(int i=0;i<empruntListe.size();i++){
    		reservation = getReservation(empruntListe.get(i).getIdReservation());
			exemplaire = getExemplaire(reservation.getIdExemplaire());
			if(exemplaire.getCoteDocument().equals(coteDocument)){
				nmbrDePretes++;
			}
		}
		
		return nmbrDePretes;
	}
	
	
	@SuppressWarnings("deprecation")
	public int getNmbrDePretesDeMois(int Mois){
		int nmbrDePretes=0;
		
		ArrayList<Emprunt> empruntListe=getListeEmprunt();
		
		for(int i=0;i<empruntListe.size();i++){
    		
			if(empruntListe.get(i).getDateEmprunt().getMonth()==Mois){
				nmbrDePretes++;
			}
		}
		
		return nmbrDePretes;
	}
	
	
	
	public void suppDemanderReAdhesion(int NoCarte){

		String sql = "DELETE FROM public.\"DemandeReAdhesion\""
				+ " WHERE \"NoCarte\"=?;  ";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, NoCarte);
			
			preparedStatement.executeUpdate();
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void suppAllQuitus(){

		String sql = "DELETE FROM public.\"Quitus\""
				+ " WHERE true ;  ";

		try {
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.executeUpdate();
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void suppAllDemandeQuitusAndQuitus(){
		suppAllQuitus();
		String sql = "DELETE FROM public.\"DemandeQuitus\""
				+ " WHERE true ;  ";

		try {
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.executeUpdate();
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
