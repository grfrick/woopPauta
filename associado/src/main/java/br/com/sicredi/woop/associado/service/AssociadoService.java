package br.com.sicredi.woop.associado.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.sicredi.woop.associado.exception.WoopException;
import br.com.sicredi.woop.associado.model.Associado;
import br.com.sicredi.woop.associado.repository.AssociadoRepository;

@Service
public class AssociadoService {

    @Autowired
    private AssociadoRepository repository;

    public void criar(String nome, String numeroTitulo) throws WoopException {
        validaAssociado(numeroTitulo);
        repository.save(new Associado(nome, numeroTitulo));
    }

    public Page<Associado> buscarTodosAssociados(PageRequest pageRequest) {
        return repository.findAll(pageRequest);
    }
    
    public Optional<Associado> buscarAssociado(String numeroTitulo) {
    	return Optional.ofNullable(repository.findByNumeroMatricula(numeroTitulo));
    }

    private void validaAssociado(String numeroTitulo) throws WoopException {
		Optional<Associado> associado = Optional.ofNullable(repository.findByNumeroMatricula(numeroTitulo));
        
        if (associado.isPresent()) {
            throw new WoopException(HttpStatus.BAD_REQUEST, "Já existe um associado com o mesmo titulo [" + associado.get().getNumeroMatricula() + "]");
        }
	}
}
