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
    <h1><span class="glyphicon glyphicon-plus"></span> Ajouter Document</h1>      
  </div>
</div>
  
  
  
  
  
  
  
  
<div class="container">
  <form class="form-horizontal" role="form" method="post" action="ControleBiblio" id="ajouterDocumentForm" >
          <input type="hidden" name="appelerPour" value="ajouterDocument">
          
    <div class="form-group">
      <label class="control-label col-sm-4" for="coteDocument">Cote du Document :</label>
      <div class="col-sm-5">
        <input type="text" class="form-control" name="coteDocument" id="coteDocument" placeholder="Entrer le Cote du Document" maxlength="15" required>
      </div>
    </div>
	 <div class="form-group">
      <label class="control-label col-sm-4" for="Titre">Titre :</label>
      <div class="col-sm-5">
        <input type="text" class="form-control" name="Titre" id="Prenom" placeholder="Enter le Titre du document" maxlength="80" required>
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-4" for="Auteur">Auteur :</label>
      <div class="col-sm-5">          
        <input type="text" class="form-control" name="Auteur" id="Auteur" placeholder="Enter l'Auteur du document" maxlength="20" required>
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-4" for="anneEdition">anneEdition :</label>
      <div class="col-sm-5">          
        <input type="number" class="form-control" name="anneEdition" id="anneEdition"  min="1600" max="2030" required>
      </div>
    </div>
    <div class="form-group">
  		<label class="control-label col-sm-4" for="typeDocument">Type du Document :</label>
  		<div class="col-sm-4"> 
  			<select class="form-control" name="typeDocument" id="typeDocument">
   				 <option >Livre</option>
   			 	<option >Memoire</option>
    			<option >CD</option>
  			</select>
  		</div>
	</div>
    <div class="form-group" id="LVnombreDePageG">
      <label class="control-label col-sm-4"   for="LVnombreDePage">Nombre De Page de livre :</label>
      <div class="col-sm-4">          
        <input type="number" class="form-control" name="LVnombreDePage" id="LVnombreDePage" value="0" min="0" required>
      </div>
    </div>
    <div class="form-group" id="CDtypeG">
  		<label class="control-label col-sm-4"  for="CDtype">Type de CD :</label>
  		<div class="col-sm-4"> 
  			<select class="form-control" name="CDtype" id="CDtype">
   				 <option >CD-ROM</option>
   			 	<option >DVD</option>
    			<option >BlueRay</option>
    			</select>
  		</div>
	</div>
	<div class="form-group" id="MRencadreurG">
      <label class="control-label col-sm-4"  for="MRencadreur">Memoire encadreur :</label>
      <div class="col-sm-5">          
        <input type="text" class="form-control" name="MRencadreur" id="MRencadreur" placeholder="Enter l'encadreur de memoire" maxlength="30" >
      </div>
    </div>
	
    <br>
    
    <div class="form-group">
      <label class="control-label col-sm-4" for="description">Description du document :</label>
      <div class="col-sm-5">          
        <textarea class="form-control" name="description" id="description" placeholder="Enter la description du document" maxlength="250" cols="40" rows="5" ></textarea>
      </div>
    </div>
	<div class="form-group">
      <label class="control-label col-sm-4" for="srcImage">L'image du document:</label>
      <div class="col-sm-5">
        <input type="file" class="form-control" name="srcImage" id="srcImage"  required>
      </div>
    </div>
	 <div class="form-group">
      <label class="control-label col-sm-4" for="nmbrExemplaire">Nmbr d'Exemplaire :</label>
      <div class="col-sm-5">
        <input type="number" class="form-control" name="nmbrExemplaire" id="nmbrExemplaire"  min="1" required>
      </div>
    </div>
    <div class="form-group">        
      <div class="col-sm-offset-4 col-sm-8">
		<button type="reset" class="btn btn-default"><span class="glyphicon glyphicon-remove"></span> Effacer</button>
        <button type="submit" id="submitBtn" class="btn btn-success"><span class="glyphicon glyphicon-floppy-save"></span> Enregistré</button>
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
   
	var typeDocument =$("#typeDocument");
	$("#MRencadreurG").hide();
	$("#CDtypeG").hide();
	typeDocument.change(function() {
		if(typeDocument.val().localeCompare("Livre")==0){
			$("#LVnombreDePageG").show();
			$("#MRencadreurG").hide();
			$("#CDtypeG").hide();
		}
		if(typeDocument.val().localeCompare("Memoire")==0){
			$("#LVnombreDePageG").hide();
			$("#MRencadreurG").show();
			$("#CDtypeG").hide();
		}
		if(typeDocument.val().localeCompare("CD")==0){
			$("#LVnombreDePageG").hide();
			$("#MRencadreurG").hide();
			$("#CDtypeG").show();
		}
	});
	
	
	$("#coteDocument").keyup(function () {
		var coteDocument=$("#coteDocument").val();
	    	$.ajax({  
	            type: "GET",  
	            url: "ControleBiblio",  
	            data: "appelerPour=coteDocumentExistence&coteDocument="+coteDocument , 
	            async:false,
	            success: function(msg){  
	            	if(msg.localeCompare("0")==0) {
	            		$("#submitBtn").prop('disabled', false);
	            		$("#coteDocument").parent().parent().removeClass("has-error");
	            		$("#coteDocument").parent().parent().addClass("has-success");
	               	}else{
	               		$("#submitBtn").prop('disabled', true);
	               		$("#coteDocument").parent().parent().removeClass("has-success");
	            		$("#coteDocument").parent().parent().addClass("has-error");
	               	}         
	            }
	        });  
	    	
	    	
	        
	    });
	
	
	
});
</script>


</body>
</html>
