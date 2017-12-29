<%@page import="modele.Personne"%>
<%@page import="modele.Reservation"%>
<%@page import="modele.Emprunt"%>
<%@page import="modele.Document"%>
<%@page import="modele.Exemplaire"%>
<%@page import="java.util.ArrayList"%>
<%@page import="modele.Adherant"%>
<%@page import="controle.GestionBiblio"%>
<%@page import="modele.Compte"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>Bibliotheque</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="css/bootstrap.min.css">
  <link rel="stylesheet" href="css/style.css">
  <script src="js/jquery-1.12.3.min.js"></script>
  <script src="js/bootstrap.min.js"></script>

</head>
<body>





<%
	
	
	Exemplaire exemplaire;
	Document document;
	Reservation reservation;
	Adherant adherant;
	Personne personne;
	
	GestionBiblio GB=new GestionBiblio();
	ArrayList<Emprunt> empruntListe=GB.getListeEmprunt();
	
	
%>


<%
	Personne upersonne=(Personne)session.getAttribute("userPersonne");
	Compte compte=(Compte)session.getAttribute("userCompte");
	
	if(upersonne==null||compte==null){
		response.sendRedirect("index.jsp");
		return;
	}
%>




<nav class="navbar navbar-default navbar-fixed-top"  >
  <div class="container-fluid" >
    <div class="navbar-header" >
    	<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
							<span class="icon-bar"></span>
							<span class="icon-bar"></span>
							<span class="icon-bar"></span>
		</button>
      <a class="navbar-brand" href="index.jsp"><span class="glyphicon glyphicon-book"></span> Bibliotheque</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      
      <ul class="nav navbar-nav navbar-right">
	  	<li><a href="ControleBiblio?appelerPour=configurationCompte"><span class="glyphicon glyphicon-cog"></span>  <%=upersonne.getNom().charAt(0)+"."+upersonne.getPrenom() %></a></li>
        <li><a href="ControleBiblio?appelerPour=Sortire" id="myBtn"><span class="glyphicon glyphicon-log-out" ></span> Sortire</a></li>
      </ul>
    </div>
  </div>
</nav>














<div class="jumbotron" >
  <div class="container text-left"  >
    <h1><span class="glyphicon glyphicon-list-alt"></span> Suivre les prets</h1>
    
     
     
  </div>
</div>
  
  
  
  

  
	
			
<div class="container">

	<a href="ControleBiblio?appelerPour=swithPageAgent&page=suivrePretsStat" class="btn btn-info btn-lg">
      <span class="glyphicon glyphicon-stats"></span> Statistiques
     </a> 
     
     
  <h2>Liste des pretes</h2>
  <br>      
  <div class="row">
			<div class="col-md-9">
				<div class="input-group">
					<input type="text" class="form-control" name="motCle" id="motCle"
					placeholder="Rechercher sur les pretes">
					<span class="input-group-addon" style="background-color: white"><span class="glyphicon glyphicon-search"></span></span>
				</div>
			</div>

		</div>
	<br>    
  <table class="table table-hover">
    <thead>
      <tr>
     	<th>NoCarte d'adherant</th>
     	<th>Nom Prenom</th>
        <th>Cote du document</th>
        <th>Type du document</th>
        <th>Titre du document</th>
        <th>Date d'emprunt</th>
        <th>Date de Remis </th>
        <th>Date de Remis Prevue</th>
      </tr>
    </thead>
    <tbody>
    
    <%
    	for(int i=0;i<empruntListe.size();i++){
    		reservation=GB.getReservation(empruntListe.get(i).getIdReservation());
			exemplaire=GB.getExemplaire(reservation.getIdExemplaire());
			document=GB.getDocument(exemplaire.getCoteDocument());
			adherant=GB.getAdherant(reservation.getNoCarte());
			personne=GB.getPersonne(adherant.getIdPersonne());
			
			
			
    			out.print("<tr class=\"AdherantLine\">");
    				out.print("<td>"+String.format("%05d",adherant.getNoCarte())+"</td>");
    				out.print("<td>"+personne.getNom()+" "+personne.getPrenom()+"</td>");
    				out.print("<td>"+document.getCoteDocument()+"</td>");
    				out.print("<td>"+document.getTypeDocument()+"</td>");
    				out.print("<td>"+document.getTitre()+"</td>");
    				out.print("<td>"+empruntListe.get(i).getDateEmprunt()+"</td>");
    				out.print("<td>"+(empruntListe.get(i).getDateRemis()!=null?empruntListe.get(i).getDateRemis():" --- ")+"</td>");
    				out.print("<td>"+empruntListe.get(i).getDateRemisPrevue()+"</td>");
        			
    				
    				
    			out.print("</tr>");
    		
    	}
    
    %>
    
    </tbody>
  </table>
</div>
<br>






<footer class="container-fluid text-center" >
		<div class="row">
		© Copyright. All Rights Reserved.
		</div>
		<div class="row">
			<div class="col-md-4">
				<p style="text-align: left; float: left;">Créé par : <br> Bourahla Chouaib<br> Benlabed Nour-el-islem</p>
			</div>
		
			<div class="col-md-4">
				
			</div>
			<div class="col-md-4">
				<p style="text-align: right;">Adresse : Université Abdelhamid Mehri Constantine 2 
				Nouvelle Ville Ali Mendjeli - BP : 67A, Constantine – Algérie</p>
			</div>
		</div>
	</footer>







<script>
$(document).ready(function(){
   
	$("#motCle").keyup(
			function() {
				var motCles=$(this).val().split(" ");
				$(".AdherantLine" ).hide();
				$.each(motCles, function( i, v ) {
					$("td:contains("+ v + ")").closest(".AdherantLine" ).show();
					});
				
			});
	
	
});
</script>


</body>
</html>
