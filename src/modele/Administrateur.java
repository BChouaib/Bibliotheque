package modele;

public class Administrateur {
	
	private int NoCartePro;
	private int idPersonne;
	
	public Administrateur() {
		
	}

	public Administrateur(int noCartePro, int idPersonne) {
		NoCartePro = noCartePro;
		this.idPersonne = idPersonne;
	}

	public int getNoCartePro() {
		return NoCartePro;
	}

	public void setNoCartePro(int noCartePro) {
		NoCartePro = noCartePro;
	}

	public int getIdPersonne() {
		return idPersonne;
	}

	public void setIdPersonne(int idPersonne) {
		this.idPersonne = idPersonne;
	}
	
	
}
