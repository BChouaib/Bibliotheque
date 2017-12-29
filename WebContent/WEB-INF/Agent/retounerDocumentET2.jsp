<%@page import="modele.Reservation"%>
<%@page import="modele.Emprunt"%>
<%@page import="modele.Document"%>
<%@page import="modele.Exemplaire"%>
<%@page import="java.util.ArrayList"%>
<%@page import="modele.Adherant"%>
<%@page import="controle.GestionBiblio"%>
<%@page import="modele.Compte"%>
<%@page import="modele.Personne"%>
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
	Adherant adherant=(Adherant)request.getAttribute("adherant");
	if(adherant==null)
		adherant=new Adherant();
	
	Exemplaire exemplaire;
	Document document;
	Reservation reservation;
	
	GestionBiblio GB=new GestionBiblio();
	ArrayList<Emprunt> empruntListe=GB.getListeAdherantEmpruntNoRemis(adherant.getNoCarte());
	
	Personne apersonne=GB.getPersonne(adherant.getIdPersonne());
	
%>



<%
	Personne personne=(Personne)session.getAttribute("userPersonne");
	Compte compte=(Compte)session.getAttribute("userCompte");
	
	if(personne==null||compte==null){
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
	  	<li><a href="ControleBiblio?appelerPour=configurationCompte"><span class="glyphicon glyphicon-cog"></span>  <%=personne.getNom().charAt(0)+"."+personne.getPrenom() %></a></li>
        <li><a href="ControleBiblio?appelerPour=Sortire" id="myBtn"><span class="glyphicon glyphicon-log-out" ></span> Sortire</a></li>
      </ul>
    </div>
  </div>
</nav>









<div class="jumbotron" >
  <div class="container text-left"  >
    <h1><span class="glyphicon glyphicon-save-file"></span> Retourner Document</h1>      
  </div>
</div>
  
  
  
  


  
 	
		
<div class="container">
  <h3 style="color: #428BCA"> <%=apersonne.getNom()+" "+apersonne.getPrenom() %> </h3>
  <h2>Liste des pretes</h2>
  <br>          
  <table class="table table-hover">
    <thead>
      <tr>
        <th>Cote du document</th>
        <th>Type du document</th>
        <th>Titre du document</th>
        <th>Auteur</th>
        <th>Annee d'edition</th>
        <th>Date d'emprunt</th>
        <th>Date de Remis Prevue</th>
        <th></th>
      </tr>
    </thead>
    <tbody>
    
    <%
    	for(int i=0;i<empruntListe.size();i++){
    		reservation=GB.getReservation(empruntListe.get(i).getIdReservation());
			exemplaire=GB.getExemplaire(reservation.getIdExemplaire());
			document=GB.getDocument(exemplaire.getCoteDocument());
			
    			out.print("<tr>");
    				out.print("<td>"+document.getCoteDocument()+"</td>");
    				out.print("<td>"+document.getTypeDocument()+"</td>");
    				out.print("<td>"+document.getTitre()+"</td>");
    				out.print("<td>"+document.getAuteur()+"</td>");
    				out.print("<td>"+document.getAnneEdition()+"</td>");
    				out.print("<td>"+empruntListe.get(i).getDateEmprunt()+"</td>");
    				out.print("<td>"+empruntListe.get(i).getDateRemisPrevue()+"</td>");
        			
    				
    				out.print("<td><button type=\"button\" class=\"btn btn-success bntRemis\" value=\""+
    					empruntListe.get(i).getIdEmprunt()+"\">Remis</button></td>");
    			
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
   
	
	
	$( ".bntRemis").click(function() {
		  var remis=$(this);
		  $.ajax({  
	            type: "GET",  
	            url: "ControleBiblio",  
	            data: "appelerPour=retounerDocumentET2&IdEmprunt="+remis.val() , 
	            async:false,
	            success: function(msg){  
	            	remis.parent().parent().hide(500);
	               	          
	            }
	        });
		});
	
	
	
});
</script>


</body>
</html>
