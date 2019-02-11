package br.com.sicredi.woop.pauta.domain;

public class Eleitor {

    private String id;
    private String nome;
    private String numeroTitulo;

    public Eleitor() {
    }
    
    public Eleitor(final String nome, final String numeroTitulo) {
        this.nome = nome;
        this.numeroTitulo = numeroTitulo;
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

	public String getNumeroTitulo() {
		return numeroTitulo;
	}

	public void setNumeroTitulo(String numeroTitulo) {
		this.numeroTitulo = numeroTitulo;
	}
}
