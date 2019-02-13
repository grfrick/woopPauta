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
    
    public Resultado() {
    }

	public Resultado getResultadoCalculado(Collection<Voto> votos, Resultado apuracao) {
		if (votos == null) {
			getResultadoZerado(apuracao);
		} else {
			apuracao.setVotosTotalizados(new Long(votos.size()));
			apuracao.setVotosSim(votos.stream().filter(v -> v.getVoto().compareTo(SimNaoEnum.SIM) == 0).count());
			apuracao.setVotosNao(votos.stream().filter(v -> v.getVoto().compareTo(SimNaoEnum.NAO) == 0).count());
		}
		
		calculaVencedor(apuracao);
		
		return apuracao;
	}
	
	public void getResultadoZerado(Resultado apuracao) {
		apuracao.setVotosTotalizados(0L);
		apuracao.setVotosSim(0L);
		apuracao.setVotosNao(0L);
	}
	
	private void calculaVencedor(Resultado resultado) {
		resultado.setVencedor(resultado.getVotosNao() == resultado.getVotosSim() ? 
				EMPATE : 
					resultado.getVotosNao() > resultado.getVotosSim() ? 
						SimNaoEnum.NAO.toString() : 
						SimNaoEnum.SIM.toString());
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
