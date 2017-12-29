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
	  	<li><a href="#"><span class="glyphicon glyphicon-cog"></span>  <%=personne.getNom().charAt(0)+"."+personne.getPrenom() %></a></li>
        <li><a href="ControleBiblio?appelerPour=Sortire" id="myBtn"><span class="glyphicon glyphicon-log-out" ></span> Sortire</a></li>
      </ul>
    </div>
  </div>
</nav>





	
	
	
	

<div class="jumbotron" >
  <div class="container text-left"  >
    <h1><span class="glyphicon glyphicon-cog"></span> Modifier mon compte</h1>      
  </div>
</div>
  
  
  
	
	
 
  <div class="container">
  <form class="form-horizontal" role="form" method="post" action="ControleBiblio" id="configurationCompteET2Form" >
          <input type="hidden" name="appelerPour" value="configurationCompteET2">
          <input type="hidden" name="idPersonne" value="<%=compte.getIdPersonne()%>">
          
   <div class="form-group">
      <label class="control-label col-sm-4" for="Nom">Nom :</label>
      <div class="col-sm-5">
        <h4><%=personne.getNom() %></h4>
        </div>
    </div>
	 <div class="form-group">
      <label class="control-label col-sm-4" for="Prenom">Prenom :</label>
      <div class="col-sm-5">
        <h4><%=personne.getPrenom() %></h4>
        </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-4" for="Email">Email :</label>
      <div class="col-sm-5">          
       <h4><%=personne.getEmail() %></h4>
       </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-4" for="Email">Nom d'Utilisateur :</label>
      <div class="col-sm-5">          
       <h4><%=compte.getNomUtilisateur() %></h4>
       </div>
    </div>
    <br>
	
	 <div class="form-group">
      <label class="control-label col-sm-4" for="motPasse">Mot de Passe :</label>
      <div class="col-sm-4">
        <input type="text" class="form-control" name="motPasse" id="motPasse" value="<%=compte.getMotPasse()%>" placeholder="Enter le mot de Passe d'adherant" maxlength="40" required>
      </div>
    </div>
    <div class="form-group">        
      <div class="col-sm-offset-4 col-sm-8">
		<button type="submit" class="btn btn-success"><span class="glyphicon glyphicon-floppy-save"></span> Enregistré</button>
      </div>
    </div>
  </form>
</div>
	
	
	
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
    
	

	
	
	
});
</script>


</body>
</html>
