package br.com.sicredi.woop.eleitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.sicredi.woop.eleitor.exception.WoopException;
import br.com.sicredi.woop.eleitor.model.Eleitor;
import br.com.sicredi.woop.eleitor.repository.EleitorRepository;

@Service
public class EleitorService {

    @Autowired
    private EleitorRepository repository;

    public void criar(String nome, String numeroTitulo) {
        validaEleitor(numeroTitulo);
        repository.save(new Eleitor(nome, numeroTitulo));
    }

    public Page<Eleitor> buscarTodosEleitores(PageRequest pageRequest) {
        return repository.findAll(pageRequest);
    }
    
	private void validaEleitor(String numeroTitulo) {
		Eleitor cadastro = repository.findByNumeroTitulo(numeroTitulo);
        
        if (null != cadastro) {
            throw new WoopException("Já existe um eleitor com o mesmo titulo eleitora [" + cadastro.getNumeroTitulo() + "]");
        }
	}
}
