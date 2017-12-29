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
  <script src="js/script.js"></script>

</head>
<body>

<%
	Personne personne=(Personne)session.getAttribute("userPersonne");
	Compte compte=(Compte)session.getAttribute("userCompte");
	
	if(personne==null||compte==null){
		response.sendRedirect("index.jsp");
		return;
	}
%>

<%
	String alertMsg=(String)request.getAttribute("alertMsg");
%>

<%
	GestionBiblio GB=new GestionBiblio();
	if(compte.getStatut().equals(GB.PERSONNE_STATUT_BLOQUE)){
		application.getRequestDispatcher("/WEB-INF/Agent/AgentBloque.jsp").forward(request,
				response);
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





<%if(alertMsg!=null){ %>
<div class="container navbar-fixed-top" style="margin-top: 60px">
<div class="alert alert-success">
    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
    <strong>Succès ! </strong> <%=alertMsg %>
  </div>
</div>

<%}%>


<div id="about" class="container">

	<h1>Gestion des prets</h1>
      <div class="row">
		<a href="ControleBiblio?appelerPour=swithPageAgent&page=preterDocument">
			<div class="col-md-4">
				<span class="glyphicon glyphicon-open-file"></span> <br>
				<h3>Preter Document</h3>
			</div>
		 </a>
        
		<a href="ControleBiblio?appelerPour=swithPageAgent&page=retounerDocument">
			<div class="col-md-4">
				<span class="glyphicon glyphicon-save-file"></span> <br>
				<h3>Retourner Document</h3>
			</div>
		 </a>
		 
		 <a href="ControleBiblio?appelerPour=swithPageAgent&page=suivrePrets">
			<div class="col-md-4">
				<span class="glyphicon glyphicon-list-alt"></span> <br>
				<h3>Suivre les prets</h3>
			</div>
		 </a>
      </div>





	<h1>Gestion des documents</h1>
      <div class="row">
		<a href="ControleBiblio?appelerPour=swithPageAgent&page=ajouterDocument">
			<div class="col-md-4">
				<span class="glyphicon glyphicon-plus"></span> <br>
				<h3>Ajouter Document</h3>
			</div>
		 </a>
        
		<a href="ControleBiblio?appelerPour=swithPageAgent&page=modifierDocument">
			<div class="col-md-4">
				<span class="glyphicon glyphicon-edit"></span> <br>
				<h3>Modifier Document</h3>
			</div>
		 </a>
		 
		 <a href="ControleBiblio?appelerPour=swithPageAgent&page=supprimerDocument">
			<div class="col-md-4">
				<span class="glyphicon glyphicon-trash"></span> <br>
				<h3>Supprimer Document</h3>
			</div>
		 </a>
      </div>
	  
	  
	  
	  
	  
	  <h1>Gestion des adherants</h1>
      <div class="row">
		<a href="ControleBiblio?appelerPour=swithPageAgent&page=adhesion">
			<div class="col-md-4">
				<span class="glyphicon glyphicon-education"></span> <br>
				<h3>Adhesion</h3>
			</div>
		 </a>
        
		<a href="ControleBiblio?appelerPour=swithPageAgent&page=reAdhesion">
			<div class="col-md-4">
				<span class="glyphicon glyphicon-retweet"></span> <br>
				<h3>Ré-adhesion</h3>
			</div>
		 </a>
		 
		 <a href="ControleBiblio?appelerPour=swithPageAgent&page=etablissementQuitus">
			<div class="col-md-4">
				<span class="glyphicon glyphicon-tags"></span> <br>
				<h3>Etablissement des Quitus</h3>
			</div>
		 </a>
      </div>

	  
	  
	  
</div>
  
  
  
  

	
	
	
	



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
    
	
	
	
});
</script>


</body>
</html>
