<%@page import="modele.Personne"%>
<%@page import="modele.Adherant"%>
<%@page import="modele.DemandeQuitus"%>
<%@page import="modele.Quitus"%>
<%@page import="java.util.ArrayList"%>
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
  <script src="js/jspdf.min.js"></script>

</head>
<body>




<% 
	
	GestionBiblio GB=new GestionBiblio();
	GB.etablirListeQuitus();	

	Adherant adherant;
	DemandeQuitus demandeQuitus;
	Personne personne;
	ArrayList<Quitus> listeQuitus=GB.getListeQuitus();
	
	
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
    <h1><span class="glyphicon glyphicon-tags"></span> Etablissement des Quitus</h1>      
  </div>
</div>
  
  
  
  

  
  
	
	
<div class="container" id="docu">
  <h2>Liste des Quitus etablir</h2>
  <br>          
  <table class="table table-hover">
    <thead>
      <tr>
        <th>Nom d'adherant</th>
        <th>Prenom d'adherant</th>
        <th>Type d'adherant</th>
        <th>NoCarte</th>
        <th>Etudiant niveau</th>
        <th>Date d'etablire</th>
      </tr>
    </thead>
    <tbody>
    
    <%
    	for(int i=0;i<listeQuitus.size();i++){
			demandeQuitus=GB.getDemandeQuitus(listeQuitus.get(i).getIdDemandeQuitus());
			adherant=GB.getAdherant(demandeQuitus.getNoCarte());
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
    			
    			out.print("<td>"+listeQuitus.get(i).getDateEtablire()+"</td>");
    			
    		out.print("</tr>");
    		
    	}
    
    %>
    
    </tbody>
  </table>
</div>
<br>




<div class="hidden" id="quitusPDF">
<%
    	for(int i=0;i<listeQuitus.size();i++){
			demandeQuitus=GB.getDemandeQuitus(listeQuitus.get(i).getIdDemandeQuitus());
			adherant=GB.getAdherant(demandeQuitus.getNoCarte());
    		personne=GB.getPersonne(adherant.getIdPersonne());
    		%>

<h3>Université Abdelhamid Mehri Constantine 2</h3>
<h4> Quitus</h4>
<h5><strong>Nom : </strong> <%=personne.getNom() %></h5>
<h5><strong>Prenom : </strong> <%=personne.getPrenom() %></h5>
<h5><strong>Niveau : </strong> <%=(adherant.getTypeAdherant().equals("Etudiant"))?adherant.getETniveau():"Enseignant" %></h5>
<h5><strong>NoCarte : </strong> <%=adherant.getNoCarte() %></h5>
<h5><strong>Date : </strong> <%=listeQuitus.get(i).getDateEtablire() %></h5>

<br>
<br>
<br>



	
<%}%>
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
   
	
	<%if(listeQuitus.size()>0){%>
	var doc = new jsPDF();
	
	    doc.fromHTML($('#quitusPDF').html(), 20, 15, {
	        'width': 180
	    });
	    
	   doc.save('samplele.pdf');
	<%}%>
	
	
	
});
</script>



<%GB.suppAllDemandeQuitusAndQuitus(); %>

</body>
</html>
