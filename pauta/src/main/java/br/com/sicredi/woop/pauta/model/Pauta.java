package br.com.sicredi.woop.pauta.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Pauta {

    @Id
    private String id;
    private String titulo;
    private String descricao;
    private Sessao sessao;

    public Pauta() {
    }
    
    public Pauta(String titulo) {
        this.titulo = titulo;
    }

    public Pauta(String titulo, String descricao) {
    	this.titulo = titulo;
    	this.descricao = descricao;
	}

	public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

	public Sessao getSessao() {
		return sessao;
	}

	public void setSessao(Sessao sessao) {
		this.sessao = sessao;
	}
}
