<%@page import="java.sql.Date"%>
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

	GestionBiblio GB=new GestionBiblio();
	Adherant adherant=GB.getAdherantWithIdPersonne(personne.getIdPersonne());
	
	if(adherant.getEtat().equals(GB.ADHERANT_ETAT_BLOQUE)){
		application.getRequestDispatcher("/WEB-INF/Adherant/AdherantBloque.jsp").forward(request,
				response);
	}else{
	
		GB.verifierEmpruntAdherant(adherant.getNoCarte());
		Date dateToday=new Date(System.currentTimeMillis());
		if(adherant.getDateFinPenalisation().after(dateToday)){
			application.getRequestDispatcher("/WEB-INF/Adherant/AdherantPenalise.jsp").forward(request,
					response);
		}
	}
%>


<%
	String alertMsg=(String)request.getAttribute("alertMsg");
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

	<h1></h1>
	
      <div class="row">
		<a href="ControleBiblio?appelerPour=swithPageAdherant&page=mesPretes">
			<div class="col-md-4">
				<span class="glyphicon glyphicon-inbox"></span> <br>
				<h3>Mes Pretes</h3>
			</div>
		 </a>
        
		<a href="ControleBiblio?appelerPour=swithPageAdherant&page=mesReservation">
			<div class="col-md-4">
				<span class="glyphicon glyphicon-copy"></span> <br>
				<h3>Mes Reservations</h3>
			</div>
		 </a>
		 

		 <a href="ControleBiblio?appelerPour=demanderQuitus&NoCarte=<%=adherant.getNoCarte()%>" <%if(GB.isAdherantDemanderQuitus(adherant.getNoCarte())){%>class="disLink"<%} %>>
			<div class="col-md-4">
				<span class="glyphicon glyphicon-modal-window"></span> <br>
				<h3>Demande Quitus</h3>
			</div>
		 </a>
		 
      </div>

	

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
