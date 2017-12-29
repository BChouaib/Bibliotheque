package controle;

import java.io.IOException;
import java.io.Writer;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import modele.Reservation;


@WebServlet("/ControleBiblio")
public class ControleBiblio extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private GestionBiblio GB;
	
    @Override
    public void init() throws ServletException {
    	super.init();
    	
    	GB=new GestionBiblio();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String appelerPour=request.getParameter("appelerPour");
		
		switch (appelerPour) {
		
		case "authentifier":authentifier(request, response); break;
		case "swithPageAgent":redirectPageAgent(request, response); break;
		case "swithPageAdministrateur":redirectPageAdministrateur(request, response); break;
		case "swithPageAdherant":redirectPageAdherant(request, response); break;
		case "adhesion":adhesioner(request, response); break;
		case "NomUtilisateurExistence":NomUtilisateurExistence(request, response); break;
		case "reAdhesionDecision":reAdhesionDecision(request, response); break;
		case "ajouterDocument":ajouterDocument(request, response); break;
		case "modifierDocument":modifierDocument(request, response); break;
		case "modifierDocumentET2":modifierDocumentET2(request, response); break;
		case "supprimerDocument":supprimerDocument(request, response); break;
		case "supprimerDocumentET2":supprimerDocumentET2(request, response); break;
		case "coteDocumentExistence":coteDocumentExistence(request, response); break;
		case "NoCarteExistence":NoCarteExistence(request, response); break;
		case "preterDocument":preterDocument(request, response); break;
		case "preterDocumentET2":preterDocumentET2(request, response); break;
		case "retounerDocument":retounerDocument(request, response); break;
		case "retounerDocumentET2":retounerDocumentET2(request, response); break;
		case "ajouterCompteAgent":ajouterCompteAgent(request, response); break;
		case "idPersonneExistence":idPersonneExistence(request, response); break;
		case "modifierStatutCompteAgent":modifierStatutCompteAgent(request, response); break;
		case "modifierStatutCompteAgentET2":modifierStatutCompteAgentET2(request, response); break;
		case "configurationSystem":configurationSystem(request, response); break;
		case "Sortire":Sortire(request, response); break;
		case "configurationCompte":configurationCompte(request, response); break;
		case "reserverDocument":reserverDocument(request, response); break;
		case "AnullerReservation":AnullerReservation(request, response); break;
		case "bloqueToutAdherant":bloqueToutAdherant(request, response); break;
		case "demanderReAdhesion":demanderReAdhesion(request, response); break;
		case "demanderQuitus":demanderQuitus(request, response); break;
		
		case "configurationCompteET2":configurationCompteET2(request, response); break;

		
		default: break;
		}
		
		
	}
	
	
	public void authentifier(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String nomUtilisateur=request.getParameter("nomUtilisateur");
		String motpasse=request.getParameter("motpasse");
		Compte compte=new Compte(nomUtilisateur, motpasse);
		Personne personne=GB.authentifier(compte);

		String pageCharger="/index.jsp";
		if(personne.getIdPersonne()!=0){
			switch (personne.getTypePersonne()) {
			case "Adherant": pageCharger="/WEB-INF/Adherant/Adherant.jsp"; break;
			case "Agent": pageCharger="/WEB-INF/Agent/Agent.jsp"; break;
			case "Administrateur": pageCharger="/WEB-INF/Administrateur/Administrateur.jsp"; break;
			}
			request.getSession().setAttribute("userPersonne", personne);
			request.getSession().setAttribute("userCompte", compte);
			getServletContext().getRequestDispatcher(pageCharger).forward(request, response);
		}else{
			response.getWriter().print(personne.getIdPersonne());
		}
		
		
		
	}

	
	public void adhesioner(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String Nom=request.getParameter("Nom");
		String Prenom=request.getParameter("Prenom");
		String Email=request.getParameter("Email");
		String typeAdherant=request.getParameter("typeAdherant");
		String eTniveau=request.getParameter("eTniveau");
		String eTdateEntr=request.getParameter("eTdateEntr");
		String eNgrade=request.getParameter("eNgrade");
		String eMprofession=request.getParameter("eMprofession");
		String nomUtilisateur=request.getParameter("nomUtilisateur");
		String motPasse=request.getParameter("motPasse");


		Adherant adherant=new Adherant(typeAdherant, eMprofession, eTniveau, Date.valueOf(eTdateEntr), eNgrade);
		Personne personne=new Personne(Nom, Prenom, Email);
		Compte compte=new Compte(nomUtilisateur, motPasse);

		GB.adhesioner(adherant, personne, compte);

		request.setAttribute("alertMsg", "L'adhérent est ajouté");
		getServletContext().getRequestDispatcher("/WEB-INF/Agent/Agent.jsp").forward(request, response);
		
	}
	
	public void ajouterCompteAgent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String Nom=request.getParameter("Nom");
		String Prenom=request.getParameter("Prenom");
		String Email=request.getParameter("Email");
		int NoCartePro=Integer.parseInt(request.getParameter("NoCartePro"));
		String nomUtilisateur=request.getParameter("nomUtilisateur");
		String motPasse=request.getParameter("motPasse");

		Agent agent=new Agent(NoCartePro);
		Personne personne=new Personne(Nom, Prenom, Email);
		Compte compte=new Compte(nomUtilisateur, motPasse);

		GB.ajouterAgent(agent, personne, compte);
		
		
		request.setAttribute("alertMsg", "Le compte d'agent est ajouté");
		getServletContext().getRequestDispatcher("/WEB-INF/Administrateur/Administrateur.jsp").forward(request, response);
		
	}
	
	public void NomUtilisateurExistence(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String nomUtilisateur=request.getParameter("nomUtilisateur");
		
		if(GB.isNomUtilisateurExist(nomUtilisateur)){
			response.getWriter().print("1");
		}else{
			response.getWriter().print("0");
		}
	}
	
	public void idPersonneExistence(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		if(!request.getParameter("idPersonne").equals("")){
			int idPersonne=Integer.parseInt(request.getParameter("idPersonne"));
			if(GB.isIdPersonneAgentExist(idPersonne)){
				response.getWriter().print("1");
			}else{
				response.getWriter().print("0");
			}
		}
	}
	
	public void coteDocumentExistence(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String coteDocument=request.getParameter("coteDocument");
		
		if(GB.isCoteDocumentExist(coteDocument)){
			response.getWriter().print("1");
		}else{
			response.getWriter().print("0");
		}
	}
	
	public void NoCarteExistence(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String NoCarte=request.getParameter("NoCarte");
		
		if(!NoCarte.equals("")){
			if(GB.isNoCarteExist(Integer.parseInt(NoCarte))){
				response.getWriter().print("1");
			}else{
				response.getWriter().print("0");
			}
		}
	}
	
	public void reAdhesionDecision(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String Decision=request.getParameter("Decision");
		char d =Decision.charAt(0);
		Decision=Decision.substring(1);
		
		Adherant adherant=new Adherant();
		adherant.setNoCarte(Integer.parseInt(Decision));
		
		if(d=='A'){
			GB.reAdhesioner(adherant);
		}
		GB.suppDemanderReAdhesion(adherant.getNoCarte());
		
	}
		
	public void ajouterDocument(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String coteDocument=request.getParameter("coteDocument");
		String Titre=request.getParameter("Titre");
		String Auteur=request.getParameter("Auteur");
		int anneEdition=Integer.parseInt(request.getParameter("anneEdition"));
		String typeDocument=request.getParameter("typeDocument");
		int LVnombreDePage=Integer.parseInt(request.getParameter("LVnombreDePage"));
		String CDtype=request.getParameter("CDtype");
		String MRencadreur=request.getParameter("MRencadreur");
		String description=request.getParameter("description");
		String srcImage=request.getParameter("srcImage");
		int nmbrExemplaire=Integer.parseInt(request.getParameter("nmbrExemplaire"));
		
		
		Document document=new Document(coteDocument, Titre, Auteur, anneEdition, typeDocument, description, srcImage, LVnombreDePage, CDtype, MRencadreur, nmbrExemplaire);
		GB.ajouterDocument(document);

		request.setAttribute("alertMsg", "Le document est ajouté");
		getServletContext().getRequestDispatcher("/WEB-INF/Agent/Agent.jsp").forward(request, response);
						
	}
	
	public void modifierDocument(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String coteDocument=request.getParameter("coteDocument");
		
		Document document=GB.getDocument(coteDocument);
		
		if(document.getCoteDocument()!=null){
			request.setAttribute("Document", document);
			getServletContext().getRequestDispatcher("/WEB-INF/Agent/modifierDocumentET2.jsp").forward(request, response);
			
		}else{
			getServletContext().getRequestDispatcher("/WEB-INF/Agent/modifierDocument.jsp").forward(request, response);
			
		}
					
	}
	
	public void modifierDocumentET2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String coteDocument=request.getParameter("coteDocument");
		String Titre=request.getParameter("Titre");
		String Auteur=request.getParameter("Auteur");
		int anneEdition=Integer.parseInt(request.getParameter("anneEdition"));
		String typeDocument=request.getParameter("typeDocument");
		int LVnombreDePage=Integer.parseInt(request.getParameter("LVnombreDePage"));
		String CDtype=request.getParameter("CDtype");
		String MRencadreur=request.getParameter("MRencadreur");
		String description=request.getParameter("description");
		String srcImage=request.getParameter("srcImage");
		int nmbrExemplaire=Integer.parseInt(request.getParameter("nmbrExemplaire"));


		request.setAttribute("alertMsg", "Le document est modifié");
		Document document=new Document(coteDocument, Titre, Auteur, anneEdition, typeDocument, description, srcImage, LVnombreDePage, CDtype, MRencadreur, nmbrExemplaire);
		GB.modifierDocument(document);


		
		getServletContext().getRequestDispatcher("/WEB-INF/Agent/Agent.jsp").forward(request, response);
						
	}
	
	public void supprimerDocument(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String coteDocument=request.getParameter("coteDocument");
		
		Document document=GB.getDocument(coteDocument);
		
		if(document.getCoteDocument()!=null){
			request.setAttribute("Document", document);
			getServletContext().getRequestDispatcher("/WEB-INF/Agent/supprimerDocumentET2.jsp").forward(request, response);
			
		}else{
			getServletContext().getRequestDispatcher("/WEB-INF/Agent/supprimerDocument.jsp").forward(request, response);
			
		}
					
	}
	
	public void supprimerDocumentET2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String coteDocument=request.getParameter("coteDocument");


		Document document=new Document();
		document.setCoteDocument(coteDocument);
		GB.supprimerDocument(document);


		request.setAttribute("alertMsg", "Le document est supprimé");
		getServletContext().getRequestDispatcher("/WEB-INF/Agent/Agent.jsp").forward(request, response);
						
	}
	
	public void preterDocument(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int NoCarte=Integer.parseInt(request.getParameter("NoCarte"));

		Adherant adherant=GB.getAdherant(NoCarte);
		request.setAttribute("adherant", adherant);
		


		
		getServletContext().getRequestDispatcher("/WEB-INF/Agent/preterDocumentET2.jsp").forward(request, response);
						
	}
	
	public void preterDocumentET2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int IdReservation=Integer.parseInt(request.getParameter("IdReservation"));
		Emprunt emprunt=new Emprunt(IdReservation);
		GB.emprunter(emprunt);
					
	}
	
	public void retounerDocument(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int NoCarte=Integer.parseInt(request.getParameter("NoCarte"));

		Adherant adherant=GB.getAdherant(NoCarte);
		request.setAttribute("adherant", adherant);
		


		
		getServletContext().getRequestDispatcher("/WEB-INF/Agent/retounerDocumentET2.jsp").forward(request, response);
						
	}
	
	public void retounerDocumentET2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int IdEmprunt=Integer.parseInt(request.getParameter("IdEmprunt"));
		Emprunt emprunt=new Emprunt();
		emprunt.setIdEmprunt(IdEmprunt);
		
		GB.remis(emprunt);
					
	}
	
	public void modifierStatutCompteAgent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int idPersonne=Integer.parseInt(request.getParameter("idPersonne"));
		
		Personne personne=GB.getPersonne(idPersonne);
		
		
		request.setAttribute("Personne", personne);
		getServletContext().getRequestDispatcher("/WEB-INF/Administrateur/modifierStatutCompteAgentET2.jsp").forward(request, response);
			
		
					
	}
	
	public void modifierStatutCompteAgentET2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int idPersonne=Integer.parseInt(request.getParameter("idPersonne"));
		String Statut=request.getParameter("Statut");
		
		Compte compte=new Compte();
		compte.setIdPersonne(idPersonne);
		compte.setStatut(Statut);
		
		GB.modifierStatutCompte(compte);
		
		request.setAttribute("alertMsg", "Le statut de compte d'agent est modifié");
		getServletContext().getRequestDispatcher("/WEB-INF/Administrateur/Administrateur.jsp").forward(request, response);
						
	}
	
	public void configurationSystem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Date dateOuvertureDePretes=Date.valueOf(request.getParameter("dateOuvertureDePretes"));
		Date dateFermetureDePretes=Date.valueOf(request.getParameter("dateFermetureDePretes"));
		Date dateOuvertureDeDemandeQuitus=Date.valueOf(request.getParameter("dateOuvertureDeDemandeQuitus"));
		Date dateFetmetureDeDemandeQuitus=Date.valueOf(request.getParameter("dateFetmetureDeDemandeQuitus"));
		Date dateOuvertureDeDemanDeAdhesionEtReAdhesion=Date.valueOf(request.getParameter("dateOuvertureDeDemanDeAdhesionEtReAdhesion"));
		Date dateFermetureDeDemandeDeAdhesionEtReAdhesion=Date.valueOf(request.getParameter("dateFermetureDeDemandeDeAdhesionEtReAdhesion"));
		
		int dureePenalisation=Integer.parseInt(request.getParameter("dureePenalisation"));
		int dureeReservation=Integer.parseInt(request.getParameter("dureeReservation"));
		
		ConfigurationDeSysteme configurationDeSysteme=new ConfigurationDeSysteme(dateOuvertureDePretes, dateFermetureDePretes, dateOuvertureDeDemandeQuitus, dateFetmetureDeDemandeQuitus, dateOuvertureDeDemanDeAdhesionEtReAdhesion, dateFermetureDeDemandeDeAdhesionEtReAdhesion, dureePenalisation, dureeReservation);
		
		GB.modifierConfigurationDeSysteme(configurationDeSysteme);
		
		request.setAttribute("alertMsg", "La configuration est mis à jour");
		getServletContext().getRequestDispatcher("/WEB-INF/Administrateur/Administrateur.jsp").forward(request, response);
						
	}
	
	public void reserverDocument(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int IdExemplaireDis=Integer.parseInt(request.getParameter("IdExemplaireDis"));
		Exemplaire exemplaire=GB.getExemplaire(IdExemplaireDis);
		Date dateRemisPret;
		if(exemplaire.getEtat().equals(GB.DOCUMENT_ETAT_DISPONIBLE)){
			dateRemisPret=new Date(System.currentTimeMillis());
		}else{
			Reservation reservation=GB.getReservationWithIdExemplaire(IdExemplaireDis);
			dateRemisPret=new Date(reservation.getDateReservation().getTime()+(GB.getConfigurationDeSysteme().getDureeReservation()*24*60*60*1000));
			
		}
		Personne upersonne=(Personne)request.getSession().getAttribute("userPersonne");
		Adherant uadherant=GB.getAdherantWithIdPersonne(upersonne.getIdPersonne());
		Reservation newReservation=new Reservation(dateRemisPret, uadherant.getNoCarte(), IdExemplaireDis);
		
		
		GB.reserver(newReservation);
	}
	
	public void AnullerReservation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int IdReservation=Integer.parseInt(request.getParameter("IdReservation"));
		
		Reservation reservation=new Reservation();
		reservation.setIdReservation(IdReservation);
		
		GB.anullerReservation(reservation);
	}
	
	public void bloqueToutAdherant(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		GB.bloqueAllAdherant();

		request.setAttribute("alertMsg", "Toute les adhérents est bloqué");
		getServletContext().getRequestDispatcher("/WEB-INF/Administrateur/Administrateur.jsp").forward(request, response);
		
	}

	public void demanderReAdhesion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int NoCarte=Integer.parseInt(request.getParameter("NoCarte"));
		DemandeReAdhesion demandeReAdhesion=new DemandeReAdhesion(NoCarte);
		
		GB.demanderReAdhesion(demandeReAdhesion);

		getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
		
	}
	
	public void demanderQuitus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int NoCarte=Integer.parseInt(request.getParameter("NoCarte"));
		DemandeQuitus demandeQuitus=new DemandeQuitus(NoCarte);
		
		GB.demanderQuitus(demandeQuitus);

		request.setAttribute("alertMsg", "La demande de quitus est envoyé");
		getServletContext().getRequestDispatcher("/WEB-INF/Adherant/Adherant.jsp").forward(request, response);
	}
	
	
	public void redirectPageAgent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String page=request.getParameter("page");
		
		String pageCharger="/WEB-INF/Agent/"+page+".jsp";
		getServletContext().getRequestDispatcher(pageCharger).forward(request, response);
		
	}
	
	public void redirectPageAdministrateur(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String page=request.getParameter("page");
		
		String pageCharger="/WEB-INF/Administrateur/"+page+".jsp";
		getServletContext().getRequestDispatcher(pageCharger).forward(request, response);
		
	}

	public void redirectPageAdherant(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String page=request.getParameter("page");
		
		String pageCharger="/WEB-INF/Adherant/"+page+".jsp";
		getServletContext().getRequestDispatcher(pageCharger).forward(request, response);
		
	}
	
	public void Sortire(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.getSession().invalidate();
		//request.getSession().setAttribute("userPersonne", null);
		//request.getSession().setAttribute("userCompte", null);
		
		getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
		
	}
	
	public void configurationCompte(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		getServletContext().getRequestDispatcher("/WEB-INF/configurationCompte.jsp").forward(request, response);
		
	}
	
	public void configurationCompteET2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int idPersonne=Integer.parseInt(request.getParameter("idPersonne"));
		String motPasse=request.getParameter("motPasse");
		
		Compte compte=new Compte();
		compte.setIdPersonne(idPersonne);
		compte.setMotPasse(motPasse);
		
		GB.modifierCompte(compte);
		
		request.setAttribute("alertMsg", "La modification d'un compte est enregistré");
		getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
		
	}
	
	
	
}
