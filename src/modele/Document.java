package modele;

public class Document {

	private String coteDocument;
	private String Titre;
	private String Auteur;
	private int anneEdition;
	private String typeDocument;
	private String description;
	private String srcImage;
	private int LVnombreDePage;
	private String CDtype;
	private String MRencadreur;
	private int nmbrExemplaire;
	
	
	public Document() {
	}


	


	public Document(String coteDocument, String titre, String auteur, int anneEdition, String typeDocument,
			String description, String srcImage, int lVnombreDePage, String cDtype, String mRencadreur,
			int nmbrExemplaire) {
		
		this.coteDocument = coteDocument;
		Titre = titre;
		Auteur = auteur;
		this.anneEdition = anneEdition;
		this.typeDocument = typeDocument;
		this.description = description;
		this.srcImage = srcImage;
		LVnombreDePage = lVnombreDePage;
		CDtype = cDtype;
		MRencadreur = mRencadreur;
		this.nmbrExemplaire = nmbrExemplaire;
	}





	public String getCoteDocument() {
		return coteDocument;
	}


	public void setCoteDocument(String coteDocument) {
		this.coteDocument = coteDocument;
	}


	public String getTitre() {
		return Titre;
	}


	public void setTitre(String titre) {
		Titre = titre;
	}


	public String getAuteur() {
		return Auteur;
	}


	public void setAuteur(String auteur) {
		Auteur = auteur;
	}


	public int getAnneEdition() {
		return anneEdition;
	}


	public void setAnneEdition(int anneEdition) {
		this.anneEdition = anneEdition;
	}


	public String getTypeDocument() {
		return typeDocument;
	}


	public void setTypeDocument(String typeDocument) {
		this.typeDocument = typeDocument;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getSrcImage() {
		return srcImage;
	}


	public void setSrcImage(String srcImage) {
		this.srcImage = srcImage;
	}


	public int getLVnombreDePage() {
		return LVnombreDePage;
	}


	public void setLVnombreDePage(int lVnombreDePage) {
		LVnombreDePage = lVnombreDePage;
	}


	public String getCDtype() {
		return CDtype;
	}


	public void setCDtype(String cDtype) {
		CDtype = cDtype;
	}


	public String getMRencadreur() {
		return MRencadreur;
	}


	public void setMRencadreur(String mRencadreur) {
		MRencadreur = mRencadreur;
	}





	public int getNmbrExemplaire() {
		return nmbrExemplaire;
	}





	public void setNmbrExemplaire(int nmbrExemplaire) {
		this.nmbrExemplaire = nmbrExemplaire;
	}
	
	
	
	
	
}
