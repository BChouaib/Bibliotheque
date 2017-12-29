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
    <h1><span class="glyphicon glyphicon-plus"></span> Ajouter un compte d'agent</h1>      
  </div>
</div>
  
  
  
  



 
  <div class="container">
  <form class="form-horizontal" role="form" method="post" action="ControleBiblio" id="ajouterCompteAgentForm" >
          <input type="hidden" name="appelerPour" value="ajouterCompteAgent">
          
    <div class="form-group">
      <label class="control-label col-sm-4" for="Nom">Nom :</label>
      <div class="col-sm-4">
        <input type="text" class="form-control" name="Nom" id="Nom" placeholder="Entrer le nom d'agent" maxlength="20" required>
      </div>
    </div>
	 <div class="form-group">
      <label class="control-label col-sm-4" for="Prenom">Prenom :</label>
      <div class="col-sm-4">
        <input type="text" class="form-control" name="Prenom" id="Prenom" placeholder="Enter le prenom d'agent" maxlength="15" required>
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-4" for="Email">Email :</label>
      <div class="col-sm-4">          
        <input type="email" class="form-control" name="Email" id="Email" placeholder="Enter l'email d'agent" maxlength="30" required>
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-4" for="NoCartePro">NoCartePro :</label>
      <div class="col-sm-4">          
        <input type="number" class="form-control" name="NoCartePro" id="NoCartePro" placeholder="Enter le NoCartePro" min="0" required>
      </div>
    </div>
    

    <br>
	<div class="form-group">
      <label class="control-label col-sm-4" for="nomUtilisateur">Nom d'Utilisateur:</label>
      <div class="col-sm-4">
        <input type="text" class="form-control" name="nomUtilisateur" id="nomUtilisateur" placeholder="Entrer le nom Utilisateur d'agent" maxlength="40"required>
      	<h5 style="color: red ;" id="nomUtilisateurStatut"></h5>
      </div>
    </div>
	 <div class="form-group">
      <label class="control-label col-sm-4" for="motPasse">Mot de Passe :</label>
      <div class="col-sm-4">
        <input type="text" class="form-control" name="motPasse" id="motPasse" placeholder="Enter le mot de Passe d'agent" maxlength="40" required>
      </div>
    </div>
    <br>
    <div class="form-group">        
      <div class="col-sm-offset-4 col-sm-8">
		<button type="reset" class="btn btn-default"><span class="glyphicon glyphicon-remove"></span> Effacer</button>
        <button type="submit" id="submitBtn" class="btn btn-success"><span class="glyphicon glyphicon-ok"></span> Confirmer </button>
      </div>
    </div>
  </form>
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
   
	
	  
	$("#nomUtilisateur").keyup(function () {
		var nomUtilisateur=$("#nomUtilisateur").val();
	    	$.ajax({  
	            type: "GET",  
	            url: "ControleBiblio",  
	            data: "appelerPour=NomUtilisateurExistence&nomUtilisateur="+nomUtilisateur , 
	            async:false,
	            success: function(msg){  
	            	if(msg.localeCompare("0")==0) {
	            		$("#submitBtn").prop('disabled', false);
	            		$("#nomUtilisateur").parent().parent().removeClass("has-error");
	            		$("#nomUtilisateur").parent().parent().addClass("has-success");
	               	}else{
	               		$("#submitBtn").prop('disabled', true);
	               		$("#nomUtilisateur").parent().parent().removeClass("has-success");
	            		$("#nomUtilisateur").parent().parent().addClass("has-error");
	               	}         
	            }
	        });  
	    	
	    	
	        
	    });
   
	   
	
	
});
</script>


</body>
</html>
