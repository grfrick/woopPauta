package br.com.sicredi.woop.pauta.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Sessao {

    @Id
    private String id;
    private LocalDateTime inicio;
    private LocalDateTime fim;
    private Collection<Voto> votos = new LinkedList<>();

    public Sessao() {
        this.fim = LocalDateTime.now().plusMinutes(1);
    }

    public Sessao(LocalDateTime inicioSessao, LocalDateTime fimSessao) {
        this.inicio = null == inicioSessao ? LocalDateTime.now() : inicioSessao;
        this.fim    = null == fimSessao    ? this.inicio.plusMinutes(1) : fimSessao.compareTo(this.inicio) > 0 ? fimSessao : this.inicio;
    }

    public String getId() {
        return id;
    }
    
	public LocalDateTime getInicio() {
		return inicio;
	}

	public void setInicio(LocalDateTime inicio) {
		this.inicio = inicio;
	}

	public LocalDateTime getFim() {
		return fim;
	}

	public void setFim(LocalDateTime fim) {
		this.fim = fim;
	}

	public Collection<Voto> getVotos() {
		return votos;
	}

	public void setVotos(Collection<Voto> votos) {
		this.votos = votos;
	}
}
