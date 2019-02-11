package br.com.sicredi.woop.pauta.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.sicredi.woop.pauta.enums.SimNao;

@Document
public class Voto {
	
    @Id
    private String id;
    private String idEleitor;
    private SimNao voto;
    
    public Voto() {
    }

    public Voto(String idEleitor, SimNao voto) {
        this.idEleitor = idEleitor;
        this.voto = voto;
    }

    public String getId() {
        return id;
    }
    
	public String getIdEleitor() {
		return idEleitor;
	}

	public void setIdEleitor(String idEleitor) {
		this.idEleitor = idEleitor;
	}

	public SimNao getVoto() {
		return voto;
	}

	public void setVoto(SimNao voto) {
		this.voto = voto;
	}
}
