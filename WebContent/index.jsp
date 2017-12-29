<%@page import="java.sql.Date"%>
<%@page import="modele.Reservation"%>
<%@page import="modele.Exemplaire"%>
<%@page import="modele.Adherant"%>
<%@page import="modele.Document"%>
<%@page import="java.util.ArrayList"%>
<%@page import="controle.GestionBiblio"%>
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
		Personne upersonne = (Personne) session.getAttribute("userPersonne");

		if (upersonne != null)
			if (upersonne.getTypePersonne().equals("Agent"))
				application.getRequestDispatcher("/WEB-INF/Agent/Agent.jsp").forward(request, response);
			else if (upersonne.getTypePersonne().equals("Administrateur"))
				application.getRequestDispatcher("/WEB-INF/Administrateur/Administrateur.jsp").forward(request,
						response);
	%>




	<%
		GestionBiblio GB = new GestionBiblio();
		ArrayList<Document> listeDocument = GB.getListeDocument();
		int resDispo = 0;
		if (upersonne != null) {
			Adherant uadherant = GB.getAdherantWithIdPersonne(upersonne.getIdPersonne());
			resDispo = GB.MAX_NMBR_DE_RESERVATION - (GB.getListeAdherantReservation(uadherant.getNoCarte()).size()
					+ GB.getListeAdherantEmpruntNoRemis(uadherant.getNoCarte()).size());
		}
	%>








	<nav class="navbar navbar-default navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-collapse">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="index.jsp"><span
				class="glyphicon glyphicon-book"></span> Bibliotheque</a>
		</div>
		<div class="collapse navbar-collapse" id="myNavbar">
			<ul class="nav navbar-nav">
				<li class="active"><a href="index.jsp"><span
						class="glyphicon glyphicon-home"></span> Home</a></li>
				<li><a href="About.jsp"><span
						class="glyphicon glyphicon-info-sign"></span> About</a></li>
			</ul>

			<ul class="nav navbar-nav navbar-right">
				
				<%
					if (upersonne != null) {
						out.print(
								"<li><a href=\"ControleBiblio?appelerPour=swithPageAdherant&page=Adherant\" ><span class=\"glyphicon glyphicon-user\" ></span> "+upersonne.getNom().charAt(0)+"."+upersonne.getPrenom()+"</a></li>");
						out.print(" <li><a href=\"ControleBiblio?appelerPour=Sortire\" id=\"myBtn\"><span class=\"glyphicon glyphicon-log-out\" ></span> Sortire</a></li>");
					} else {
						out.print(
								"<li><a href=\"#\" id=\"myBtn\"><span class=\"glyphicon glyphicon-log-in\" ></span> S'identifier</a></li>");
					}
				%>
			</ul>
		</div>
	</div>
	</nav>











	<!-- Modal -->
	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">

				<div class="modal-header"
					style="padding: 15px 50px; background-color: #5cb85c; color: white;">

					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4>
						<span class="glyphicon glyphicon-lock"></span> S'identifier
					</h4>
				</div>
				<div class="modal-body" style="padding: 40px 50px;">

					<form role="form" method="post" action="ControleBiblio"
						id="authForm">
						<input type="hidden" name="appelerPour" value="authentifier">
						<div class="form-group">
							<label for="nomUtilisateur"><span
								class="glyphicon glyphicon-user"></span> Nom d'utilisateur</label> <input
								type="text" class="form-control" id="nomUtilisateur"
								name="nomUtilisateur" placeholder="Entrer Nom d'utilisateur"
								required>
						</div>
						<div class="form-group">
							<label for="motpasse"><span
								class="glyphicon glyphicon-eye-open"></span> Mot de passe</label> <input
								type="password" class="form-control" id="motpasse"
								name="motpasse" placeholder="Entrer Mot de passe" required>
						</div>
						<button type="submit" class="btn btn-success btn-block">
							<span class="glyphicon glyphicon-off"></span> Connecter
						</button>
					</form>
				</div>
			</div>

		</div>
	</div>







	<div class="jumbotron"
		style="background-image: url('img/long-row-of-books.jpg'); background-size:100%; height: 380px; ">
		<div class="container text-left">
			<img src="img/univ.png" align="right" alt="Cinque Terre" width="237"
				height="150">
			<h1 class="titleIntro" style="color: #428BCA;">BIBLOTHEQUE</h1>
			<h3 class="titleIntro">Université Abdelhamid Mehri Constantine 2</h3>
		</div>
	</div>




	<div class="container">

		<div class="row">
			<div class="col-md-9">
				<div class="input-group">
					<input type="text" class="form-control" name="motCle" id="motCle"
					placeholder="Rechercher sur un document">
					<span class="input-group-addon" style="background-color: white"><span class="glyphicon glyphicon-search"></span></span>
				</div>
			</div>

		</div>

	</div>

<br>
<br>

	<div class="container">
		<div class="documentListe">
			<%
				for (int i = 0; i < listeDocument.size(); i++) {
			%>

			<div class="itemDoc ">
				<div class="col-md-3 ">
					<a data-toggle="modal" href="#D<%=i%>" class="documentView"> <figure>
						<img
							src="img/documentImg/<%=listeDocument.get(i).getSrcImage() == null ? "book.jpg" : listeDocument.get(i).getSrcImage()%>"
							class="img-Document " alt=""> <figcaption>
						<h5><%=listeDocument.get(i).getTitre()%></h5>
						</figcaption> </figure>
					</a>
				</div>

				<div class="docTags hidden"><%=listeDocument.get(i).getTitre().toLowerCase()+" "
												+listeDocument.get(i).getAuteur().toLowerCase()+" "
												+listeDocument.get(i).getAnneEdition()+" "
												+listeDocument.get(i).getTypeDocument().toLowerCase()+" "
												+listeDocument.get(i).getLVnombreDePage()+" "
												+listeDocument.get(i).getCDtype().toLowerCase()+" "
												+listeDocument.get(i).getMRencadreur().toLowerCase()+" "
												+listeDocument.get(i).getDescription().toLowerCase()+" "
												+listeDocument.get(i).getCoteDocument()+" "
												
												%></div>


				<div class="modal fade docView" id="D<%=i%>" tabindex="-1"
					role="dialog">
					<div class="modal-dialog modal-lg">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
								<h4 class="modal-title"><%=listeDocument.get(i).getTitre()%></h4>
							</div>
							<div class="modal-body">
								<img
									src="img/documentImg/<%=listeDocument.get(i).getSrcImage() == null ? "book.jpg" : listeDocument.get(i).getSrcImage()%>"
									class="img-DocumentView " alt="Image"
									style="float: left; margin-right: 50px">
								<div>



									<h5>
										<strong>Auteur : </strong><%=listeDocument.get(i).getAuteur()%></h5>
									<h5>
										<strong>Anne d'edition : </strong><%=listeDocument.get(i).getAnneEdition()%></h5>
									<h5>
										<strong>Type du Document : </strong><%=listeDocument.get(i).getTypeDocument()%></h5>

									<%
										if (listeDocument.get(i).getTypeDocument().equals("Livre")) {
									%>
									<h5>
										<strong>Nombre De Page de livre : </strong><%=listeDocument.get(i).getLVnombreDePage()%></h5>
									<%
										}
									%>

									<%
										if (listeDocument.get(i).getTypeDocument().equals("CD")) {
									%>
									<h5>
										<strong>Type de CD : </strong>
										<%=listeDocument.get(i).getCDtype()%></h5>
									<%
										}
									%>

									<%
										if (listeDocument.get(i).getTypeDocument().equals("Memoire")) {
									%>
									<h5>
										<strong>Memoire encadreur : </strong><%=listeDocument.get(i).getMRencadreur()%></h5>
									<%
										}
									%>

									<h5>
										<strong>Description du document : </strong>
										<%=listeDocument.get(i).getDescription()%></h5>

									<%
										int IdExemplaireDis = GB.getIdExemplaireDisponible(listeDocument.get(i).getCoteDocument());
									%>

									<%
										if (IdExemplaireDis != 0) {
												Exemplaire exemplaire = (GB.getExemplaire(IdExemplaireDis));
												if (exemplaire.getEtat().equals(GB.DOCUMENT_ETAT_DISPONIBLE)) {
									%>
									<h5 class="text-success">Document Disponible</h5>
									<%
										} else {
													Reservation reservation = GB.getReservationWithIdExemplaire(IdExemplaireDis);
													Date dateRemisPret = new Date(reservation.getDateReservation().getTime()
															+ (GB.getConfigurationDeSysteme().getDureeReservation() * 24 * 60 * 60 * 1000));
									%>
									<h5 class="text-info">
										Document Disponible jusqu'à
										<%=dateRemisPret.toString()%></h5>
									<%
										}
									%>
									<%
										} else {
									%>
									<h5 class="text-danger">Document n'est pas Disponible</h5>
									<%
										}
									%>
									<button type="button" class="btn btn-success btnReserver "
										value="<%=IdExemplaireDis%>"
										<%if (resDispo <= 0 || IdExemplaireDis == 0) {%> disabled
										<%}%>>
										<span class="glyphicon glyphicon-book"></span> Réserver le
										document
									</button>


								</div>
							</div>
							<div class="modal-footer">
								<h4 class="modal-cote">
									Cote :
									<%=listeDocument.get(i).getCoteDocument()%></h4>
							</div>
						</div>
					</div>
				</div>
			</div>

			<%
				}
			%>



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
		$(document)
				.ready(
						function() {
							$("#myBtn").click(function() {
								$("#myModal").modal();
							});
							

							$("#motCle").keyup(
									function() {
										var motCles=$(this).val().split(" ");
										$(".itemDoc" ).hide();
										$.each(motCles, function( i, v ) {
											$(".docTags:contains("+ v + ")").closest(".itemDoc" ).show();
											});
										
									});

							$(".btnReserver")
									.click(
											function() {
												var IdExemplaireDis = $(this).val();
												$.ajax({
															type : "GET",
															url : "ControleBiblio",
															data : "appelerPour=reserverDocument&IdExemplaireDis="
																	+ IdExemplaireDis,
															async : false,
															success : function(msg) {

																location.reload();
															}
														});

											});

						});
	</script>


</body>
</html>
