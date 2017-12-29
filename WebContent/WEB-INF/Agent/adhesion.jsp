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
    <h1><span class="glyphicon glyphicon-education"> </span> Adhesion</h1>      
  </div>
</div>
  
  
  
  

  
  
  
  <div class="container">
  <form class="form-horizontal" role="form" method="post" action="ControleBiblio" id="adhForm" >
          <input type="hidden" name="appelerPour" value="adhesion">
          
    <div class="form-group">
      <label class="control-label col-sm-3" for="Nom">Nom :</label>
      <div class="col-sm-5">
        <input type="text" class="form-control" name="Nom" id="Nom" placeholder="Entrer le nom d'adherant" maxlength="20" required>
      </div>
    </div>
	 <div class="form-group">
      <label class="control-label col-sm-3" for="Prenom">Prenom :</label>
      <div class="col-sm-5">
        <input type="text" class="form-control" name="Prenom" id="Prenom" placeholder="Enter le prenom d'adherant" maxlength="15" required>
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-3" for="Email">Email :</label>
      <div class="col-sm-5">          
        <input type="email" class="form-control" name="Email" id="Email" placeholder="Enter l'email d'adherant" maxlength="30" required>
      </div>
    </div>
    <div class="form-group">
  		<label class="control-label col-sm-3" for="typeAdherant">Type d'adhrant :</label>
  		<div class="col-sm-4"> 
  			<select class="form-control" name="typeAdherant" id="typeAdherant">
   				 <option >Etudiant</option>
   			 	<option >Enseignant</option>
    			<option >Employee</option>
  			</select>
  		</div>
	</div>
	<div class="form-group" id="eTniveauG">
  		<label class="control-label col-sm-3" for="eTniveau">Niveau d'etudiant :</label>
  		<div class="col-sm-2"> 
  			<select class="form-control" name="eTniveau" id="eTniveau">
   				<option >L1</option>
   			 	<option >L2</option>
    			<option >L3 GL</option>
    			<option >L3 TI</option>
    			<option >L3 SI</option>
    			<option >M1 GL</option>
    			<option >M1 TI</option>
    			<option >M1 SI</option>
    			<option >M2 GL</option>
    			<option >M2 TI</option>
    			<option >M2 SI</option>
  			</select>
  		</div>
	</div>
    <div class="form-group" id="eTdateEntrG">
      <label class="control-label col-sm-3" for="eTdateEntr">Date entrée :</label>
      <div class="col-sm-4">          
        <input type="date" class="form-control" name="eTdateEntr" id="eTdateEntr" value="2013-01-01" required>
      </div>
    </div>
    <div class="form-group" id="eNgradeG">
  		<label class="control-label col-sm-3" for="eNgrade">Enseignant grade  :</label>
  		<div class="col-sm-4"> 
  			<select class="form-control" name="eNgrade" id="eNgrade">
   				 <option >Docteur</option>
   			 	<option >Professeur</option>
    			<option >Chercheur</option>
    			</select>
  		</div>
	</div>
	<div class="form-group" id="eMprofessionG">
  		<label class="control-label col-sm-3" for="eMprofession">Employée profession :</label>
  		<div class="col-sm-5"> 
  			<select class="form-control" name="eMprofession" id="eMprofession">
   				<option >Agent de securite</option>
   			 	<option >Agent de bibliotheque</option>
    			<option >Agent d administration</option>
    			</select>
  		</div>
	</div>
    <br>
	<div class="form-group">
      <label class="control-label col-sm-3" for="nomUtilisateur">Nom d'Utilisateur:</label>
      <div class="col-sm-5">
        <input type="text" class="form-control" name="nomUtilisateur" id="nomUtilisateur" placeholder="Entrer le nom Utilisateur d'adherant" maxlength="40"required>
      </div>
    </div>
	 <div class="form-group">
      <label class="control-label col-sm-3" for="motPasse">Mot de Passe :</label>
      <div class="col-sm-5">
        <input type="text" class="form-control" name="motPasse" id="motPasse" placeholder="Enter le mot de Passe d'adherant" maxlength="40" required>
      </div>
    </div>
    <div class="form-group">        
      <div class="col-sm-offset-3 col-sm-8">
		<button type="reset" class="btn btn-default"><span class="glyphicon glyphicon-remove"></span> Effacer</button>
        <button type="submit" id="submitBtn" class="btn btn-success"><span class="glyphicon glyphicon-ok"></span> Confirmer </button>
      </div>
    </div>
  </form>
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
	
   var typeAdherantListe =$("#typeAdherant");
	$("#eNgradeG").hide();
	$("#eMprofessionG").hide();
   typeAdherantListe.change(function() {
		if(typeAdherantListe.val().localeCompare("Etudiant")==0){
			$("#eTniveauG").show();
			$("#eTdateEntrG").show();
			$("#eNgradeG").hide();
			$("#eMprofessionG").hide();
		}
		if(typeAdherantListe.val().localeCompare("Enseignant")==0){
			$("#eTniveauG").hide();
			$("#eTdateEntrG").hide();
			$("#eNgradeG").show();
			$("#eMprofessionG").hide();
		}
		if(typeAdherantListe.val().localeCompare("Employee")==0){
			$("#eTniveauG").hide();
			$("#eTdateEntrG").hide();
			$("#eNgradeG").hide();
			$("#eMprofessionG").show();
		}
	});
	
	
   
   
   
   
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
