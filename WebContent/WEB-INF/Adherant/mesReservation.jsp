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
  <script src="js/script.js"></script>

</head>
<body>


<%
	Personne personne=(Personne)session.getAttribute("userPersonne");
	if(personne==null)
		personne=new Personne();
	
	
	GestionBiblio GB=new GestionBiblio();
	
	Adherant adherant=GB.getAdherantWithIdPersonne(personne.getIdPersonne());
	ArrayList<Reservation> empruntListe=GB.getListeAdherantReservation(adherant.getNoCarte());
	
	Exemplaire exemplaire;
	Document document;
	Reservation reservation;
	
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
    <h1><span class="glyphicon glyphicon-copy"></span> Mes Reservations</h1>      
  </div>
</div>
  
  
  
  
			
<div class="container">
  <h2>Ma Liste des Reservations</h2>
  <br>          
  <table class="table table-hover">
    <thead>
      <tr>
        <th>Cote du document</th>
        <th>Type du document</th>
        <th>Titre du document</th>
        <th>Date de Resevation</th>
        <th></th>
      </tr>
    </thead>
    <tbody>
    
    <%
    	for(int i=empruntListe.size()-1;i>=0;i--){
    		reservation=GB.getReservation(empruntListe.get(i).getIdReservation());
			exemplaire=GB.getExemplaire(reservation.getIdExemplaire());
			document=GB.getDocument(exemplaire.getCoteDocument());
			
			
			
			
				out.print("<tr>");
			
    				out.print("<td>"+document.getCoteDocument()+"</td>");
    				out.print("<td>"+document.getTypeDocument()+"</td>");
    				out.print("<td>"+document.getTitre()+"</td>");
    				out.print("<td>"+empruntListe.get(i).getDateReservation()+"</td>");
    				
    				out.print("<td><button type=\"button\" class=\"btn btn-danger bntAnullerRes\" value=\""+
    						empruntListe.get(i).getIdReservation()+"\">Anuller</button></td>");
        			
    				
    				
    			out.print("</tr>");
    		
    	}
    
    %>
    
    </tbody>
  </table>
</div>


	
<br>
<br>
<br>
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
   
	$( ".bntAnullerRes").click(function() {
		  var anullerRes=$(this);
		  $.ajax({  
	            type: "GET",  
	            url: "ControleBiblio",  
	            data: "appelerPour=AnullerReservation&IdReservation="+anullerRes.val() , 
	            async:false,
	            success: function(msg){  
	            	anullerRes.parent().parent().hide(500);
	               	          
	            }
	        });
		});
	
	
});
</script>


</body>
</html>
