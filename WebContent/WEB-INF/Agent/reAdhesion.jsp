<%@page import="modele.Adherant"%>
<%@page import="modele.Compte"%>
<%@page import="modele.Personne"%>
<%@page import="controle.GestionBiblio"%>
<%@page import="modele.DemandeReAdhesion"%>
<%@page import="java.util.ArrayList"%>
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
	GestionBiblio GB=new GestionBiblio();
	DemandeReAdhesion demandeReAdhesion;
	Personne personne;
	Adherant adherant;
	ArrayList<DemandeReAdhesion> listeDemandeReAdhesiont=GB.getListeDemandeReAdhesion();
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
    <h1><span class="glyphicon glyphicon-retweet"></span> Ré-adhesion</h1>      
  </div>
</div>
  
  
  
  

  
  
	
	

	
<div class="container">
  <h2>Liste des Demandes de ReAdhesion</h2>
  <br>          
  <table class="table table-hover">
    <thead>
      <tr>
        <th>Nom d'adherant</th>
        <th>Prenom d'adherant</th>
        <th>Type d'adherant</th>
        <th>NoCarte</th>
        <th>Etudiant niveau</th>
        <th>Decision</th>
      </tr>
    </thead>
    <tbody>
    
    <%
    	for(int i=0;i<listeDemandeReAdhesiont.size();i++){
    		adherant=GB.getAdherant(listeDemandeReAdhesiont.get(i).getNoCarte());
    		personne=GB.getPersonne(adherant.getIdPersonne());
    		
    		out.print("<tr>");
    			out.print("<td>"+personne.getNom()+"</td>");
    			out.print("<td>"+personne.getPrenom()+"</td>");
    			out.print("<td>"+adherant.getTypeAdherant()+"</td>");
    			out.print("<td>"+String.format("%05d",adherant.getNoCarte())+"</td>");
    			if(adherant.getTypeAdherant().equals("Etudiant"))
    				out.print("<td>"+adherant.getETniveau()+"</td>");
    			else
    				out.print("<td></td>");
    			
    			out.print("<td><button type=\"button\" class=\"btn btn-success bntDecision\" value=\"A"+
    					adherant.getNoCarte()+"\">Accepter</button></td>");
    			out.print("<td><button type=\"button\" class=\"btn btn-danger bntDecision\" value=\"R"+
    					adherant.getNoCarte()+"\">Refuser</button></td>");
    			
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
   
	$( ".bntDecision").click(function() {
		  var Decision=$(this);
		  $.ajax({  
	            type: "GET",  
	            url: "ControleBiblio",  
	            data: "appelerPour=reAdhesionDecision&Decision="+Decision.val() , 
	            async:false,
	            success: function(msg){  
	            	Decision.parent().parent().hide(500);
	               	          
	            }
	        });
		});
	
	
});
</script>


</body>
</html>
