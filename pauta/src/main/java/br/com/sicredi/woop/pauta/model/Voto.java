package br.com.sicredi.woop.pauta.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.sicredi.woop.pauta.enums.SimNaoEnum;

@Document
public class Voto {
	
    @Id
    private String id;
    private String numeroMatricula;
    private SimNaoEnum voto;
    
    public Voto() {
    }

    public Voto(String numeroMatricula, SimNaoEnum voto) {
        this.numeroMatricula = numeroMatricula;
        this.voto = voto;
    }

    public String getId() {
        return id;
    }
    
	public String getNumeroMatricula() {
		return numeroMatricula;
	}

	public void setNumeroMatricula(String numeroMatricula) {
		this.numeroMatricula = numeroMatricula;
	}

	public SimNaoEnum getVoto() {
		return voto;
	}

	public void setVoto(SimNaoEnum voto) {
		this.voto = voto;
	}
}
