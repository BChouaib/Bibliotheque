<%@page import="modele.ConfigurationDeSysteme"%>
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
	GestionBiblio GB=new GestionBiblio();
	ConfigurationDeSysteme configurationDeSysteme=GB.getConfigurationDeSysteme();
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
    <h1><span class="glyphicon glyphicon-cog"></span> Configuration de system</h1>      
  </div>
</div>
  
  
  
  
<br>  
  
  
  	
<div class="container">
  <form class="form-horizontal" role="form" method="post" action="ControleBiblio" id="configurationSystemForm" >
          <input type="hidden" name="appelerPour" value="configurationSystem">
          
    <div class="form-group">
      <label class="control-label col-sm-5" for="dateOuvertureDePretes">date d'Ouverture Des Pretes :</label>
      <div class="col-sm-4">
      <input type=date class="form-control" name="dateOuvertureDePretes" id="dateOuvertureDePretes" value="<%=configurationDeSysteme.getDateOuvertureDePretes() %>"   required>
	</div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-5" for="dateFermetureDePretes">date de Fermeture Des Pretes :</label>
      <div class="col-sm-4">
      <input type=date class="form-control" name="dateFermetureDePretes" id="dateFermetureDePretes" value="<%=configurationDeSysteme.getDateFermetureDePretes() %>"   required>
	</div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-5" for="dateOuvertureDeDemandeQuitus">date d'Ouverture De Deman de Quitus :</label>
      <div class="col-sm-4">
      <input type=date class="form-control" name="dateOuvertureDeDemandeQuitus" id="dateOuvertureDeDemandeQuitus" value="<%=configurationDeSysteme.getDateOuvertureDeDemandeQuitus() %>"   required>
	</div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-5" for="dateFetmetureDeDemandeQuitus">date de Fetmeture De Deman de Quitus :</label>
      <div class="col-sm-4">
      <input type=date class="form-control" name="dateFetmetureDeDemandeQuitus" id="dateFetmetureDeDemandeQuitus" value="<%=configurationDeSysteme.getDateFetmetureDeDemandeQuitus() %>"   required>
	</div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-5" for="dateOuvertureDeDemanDeAdhesionEtReAdhesion">date d'Ouverture De Demande D'Adhesion Et ReAdhesion :</label>
      <div class="col-sm-4">
      <input type=date class="form-control" name="dateOuvertureDeDemanDeAdhesionEtReAdhesion" id="dateOuvertureDeDemanDeAdhesionEtReAdhesion" value="<%=configurationDeSysteme.getDateOuvertureDeDemanDeAdhesionEtReAdhesion() %>"   required>
	</div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-5" for="dateFermetureDeDemandeDeAdhesionEtReAdhesion">date de Fermeture De Demande D'Adhesion Et ReAdhesion :</label>
      <div class="col-sm-4">
      <input type=date class="form-control" name="dateFermetureDeDemandeDeAdhesionEtReAdhesion" id="dateFermetureDeDemandeDeAdhesionEtReAdhesion" value="<%=configurationDeSysteme.getDateFermetureDeDemandeDeAdhesionEtReAdhesion() %>"   required>
	</div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-5" for="dureePenalisation">duree de Penalisation :</label>
      <div class="col-sm-4">
      <input type=number class="form-control" name="dureePenalisation" id="dureePenalisation" value="<%=configurationDeSysteme.getDureePenalisation() %>" min="0"  required>
	</div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-5" for="dureeReservation">duree de Reservation :</label>
      <div class="col-sm-4">
      <input type=number class="form-control" name="dureeReservation" id="dureeReservation" value="<%=configurationDeSysteme.getDureeReservation() %>" min="0"  required>
	</div>
    </div>
    <br>
    <div class="form-group">        
      <div class="col-sm-offset-5 col-sm-8">
		<button type="reset" class="btn btn-default"><span class="glyphicon glyphicon-remove"></span> Effacer </button>
        <button type="submit" class="btn btn-success"><span class="glyphicon glyphicon-floppy-save"></span> Enregistré </button>
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
   
	
	
	
});
</script>


</body>
</html>
