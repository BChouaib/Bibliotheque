<%@page import="java.util.Calendar"%>
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
  <script src="js/canvasjs.min.js"></script>

</head>
<body>





<%
	
	
	
	
	GestionBiblio GB=new GestionBiblio();
	ArrayList<Document> listeDocument=GB.getListeDocument();
	
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
    <h1><span class="glyphicon glyphicon-stats"></span> Statistiques des prets</h1>      
  </div>
</div>
  
  
  
  

  
	
			
<div class="container">
<br><br>

  <div id="TopDocumentPreteChart" style="height: 400px; width: 100%;">
    </div>
    
<br>
<br>
<br>
<br>
<br>

     <div id="PreteParMoisChart" style="height: 400px; width: 100%;">
    </div>
    
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
   
	function compareDataPointYAscend(dataPoint1, dataPoint2) {
		return dataPoint1.y - dataPoint2.y;
	}

	function compareDataPointYDescend(dataPoint1, dataPoint2) {
		return dataPoint2.y - dataPoint1.y;
	}
	
	
	    var chart = new CanvasJS.Chart("TopDocumentPreteChart",
	    {
	      title:{
	        text: "Top Document Prêté"    
	      },
	      animationEnabled: true,
	      axisX: {
	    	  labelWrap: false
		      },
	      axisY: {
	        title: "Nombre de prêtres"
	      },
	      theme: "theme1",
	      data: [

	      {        
	        type: "column",  
	        dataPoints: [
	         			<%for(int i=0;i<listeDocument.size();i++){%>
	         				{y: <%=GB.getNmbrDePretesDeDocument(listeDocument.get(i).getCoteDocument())%>, label: "<%=listeDocument.get(i).getTitre()+"("+listeDocument.get(i).getCoteDocument()+")"%>"},
	         			<%}%>        
	        ]
	      }   
	      ]
	    });

	    chart.options.data[0].dataPoints.sort(compareDataPointYDescend);
	    chart.render();
	    
	    
	    var chart = new CanvasJS.Chart("PreteParMoisChart",
	    	    {
	    	      title:{
	    	        text: "Document Prêté par Mois"    
	    	      },
	    	      animationEnabled: true,
	    	      axisY: {
	    	        title: "Nombre de prêtres"
	    	      },
	    	      theme: "theme2",
	    	      data: [

	    	      {        
	    	        type: "splineArea",  
	    	        dataPoints: [
	    	         			<%for(int i=0;i<12;i++){%>
	    	         				{y: <%=GB.getNmbrDePretesDeMois(i)%>, label: "<%=i+1%>"},
	    	         			<%}%>        
	    	        ]
	    	      }   
	    	      ]
	    	    });

	    	    chart.render();
	  
	  
	
});
</script>


</body>
</html>
