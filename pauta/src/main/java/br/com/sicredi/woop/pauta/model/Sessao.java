package br.com.sicredi.woop.pauta.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Sessao {

    private static final long DURACAO_DEFAULT = 1;
    
	@Id
    private String id;
    private LocalDateTime inicio;
    private LocalDateTime fim;
    private Collection<Voto> votos = new LinkedList<>();
    private long duracao;
    
    public Sessao() {
    	this.inicio = LocalDateTime.now();
        this.fim = LocalDateTime.now().plusMinutes(DURACAO_DEFAULT);
    }

    public Sessao(long duracao) {
    	this.duracao = duracao;
        this.inicio = LocalDateTime.now();
        this.fim = duracao == 0 ? this.inicio.plusMinutes(DURACAO_DEFAULT) : 
	        						this.inicio.plusMinutes(this.duracao);
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

	public long getDuracao() {
		return duracao;
	}

	public void setDuracao(long duracao) {
		this.duracao = duracao;
	}
}
