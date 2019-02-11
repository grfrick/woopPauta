package br.com.sicredi.woop.pauta.domain;

public class Resultado {
    public Long votosSim;
    public Long votosNao;
    public Long votosTotalizados;
    public String vencedor;

    public Resultado(Long votosTotalizados, Long sim, Long nao) {
        this.votosTotalizados = votosTotalizados;
        this.votosSim = sim;
        this.votosNao = nao;
    }

	public Long getVotosTotalizados() {
		return votosTotalizados;
	}

	public void setVotosTotalizados(Long votosTotalizados) {
		this.votosTotalizados = votosTotalizados;
	}

	public Long getVotosSim() {
		return votosSim;
	}

	public void setVotosSim(Long votosSim) {
		this.votosSim = votosSim;
	}

	public Long getVotosNao() {
		return votosNao;
	}

	public void setVotosNao(Long votosNao) {
		this.votosNao = votosNao;
	}

	public String getVencedor() {
		return vencedor;
	}

	public void setVencedor(String vencedor) {
		this.vencedor = vencedor;
	}
}
