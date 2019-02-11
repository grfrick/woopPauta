package br.com.sicredi.woop.eleitor.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.sicredi.woop.eleitor.exception.WoopException;
import br.com.sicredi.woop.eleitor.model.Eleitor;
import br.com.sicredi.woop.eleitor.repository.EleitorRepository;

@Service
public class EleitorService {

    @Autowired
    private EleitorRepository repository;

    public void criar(String nome, String numeroTitulo) throws WoopException {
        validaEleitor(numeroTitulo);
        repository.save(new Eleitor(nome, numeroTitulo));
    }

    public Page<Eleitor> buscarTodosEleitores(PageRequest pageRequest) {
        return repository.findAll(pageRequest);
    }
    
    public Optional<Eleitor> buscarEleitor(String numeroTitulo) {
    	return Optional.ofNullable(repository.findByNumeroTitulo(numeroTitulo));
    }

    private void validaEleitor(String numeroTitulo) throws WoopException {
		Optional<Eleitor> cadastro = Optional.ofNullable(repository.findByNumeroTitulo(numeroTitulo));
        
        if (cadastro.isPresent()) {
            throw new WoopException(HttpStatus.BAD_REQUEST, "Já existe um eleitor com o mesmo titulo [" + cadastro.get().getNumeroTitulo() + "]");
        }
	}
}
