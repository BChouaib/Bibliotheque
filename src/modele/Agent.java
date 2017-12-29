package modele;

public class Agent {

	private int NoCartePro;
	private int idPersonne;
	
	public Agent() {
		
	}

	

	public Agent(int noCartePro) {
		NoCartePro = noCartePro;
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
