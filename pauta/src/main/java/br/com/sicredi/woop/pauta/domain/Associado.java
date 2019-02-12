package br.com.sicredi.woop.pauta.domain;

public class Associado {
    private String id;
    private String nome;
    private String numeroMatricula;

    public Associado() {
    }
    
    public Associado(final String nome, final String numeroMatricula) {
        this.nome = nome;
        this.numeroMatricula = numeroMatricula;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNumeroMatricula() {
		return numeroMatricula;
	}

	public void setNumeroMatricula(String numeroMatricula) {
		this.numeroMatricula = numeroMatricula;
	}
}
