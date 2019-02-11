package br.com.sicredi.woop.pauta.model;

public class Resultado {
    public Long totalVotos;
    public Long votosSim;
    public Long votosNao;

    public Resultado(Long totalVotos, Long sim, Long nao) {
        this.totalVotos = totalVotos;
        this.votosSim = sim;
        this.votosNao = nao;
    }

	public Long getTotalVotos() {
		return totalVotos;
	}

	public void setTotalVotos(Long totalVotos) {
		this.totalVotos = totalVotos;
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
}
