package br.com.sicredi.woop.pauta.domain;

import java.util.Collection;

import br.com.sicredi.woop.pauta.enums.SimNaoEnum;
import br.com.sicredi.woop.pauta.model.Voto;

public class Resultado {
	private static final String EMPATE = "EMPATE";
	
    public Long votosSim;
    public Long votosNao;
    public Long votosTotalizados;
    public String vencedor;

    public Resultado(Long votosTotalizados, Long sim, Long nao) {
        this.votosTotalizados = votosTotalizados;
        this.votosSim = sim;
        this.votosNao = nao;
        
        this.setVencedor(this.getVotosNao() == this.getVotosSim() ? 
        						EMPATE : 
        						this.getVotosNao() > this.getVotosSim() ? 
        								SimNaoEnum.NAO.toString() : 
        								SimNaoEnum.SIM.toString());
    }

	public Resultado(Collection<Voto> votos) {
		new Resultado(new Long(votos.size()), 
					 votos.stream().filter(v -> v.getVoto().compareTo(SimNaoEnum.SIM) == 0).count(), 
					 votos.stream().filter(v -> v.getVoto().compareTo(SimNaoEnum.NAO) == 0).count());
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
