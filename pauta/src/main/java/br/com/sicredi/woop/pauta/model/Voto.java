package br.com.sicredi.woop.pauta.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.sicredi.woop.pauta.enums.SimNaoEnum;

@Document
public class Voto {
	
    @Id
    private String id;
    private String idEleitor;
    private SimNaoEnum voto;
    
    public Voto() {
    }

    public Voto(String idEleitor, SimNaoEnum voto) {
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

	public SimNaoEnum getVoto() {
		return voto;
	}

	public void setVoto(SimNaoEnum voto) {
		this.voto = voto;
	}
}
