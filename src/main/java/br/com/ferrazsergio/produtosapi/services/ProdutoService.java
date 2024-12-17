package br.com.ferrazsergio.produtosapi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.ferrazsergio.produtosapi.infra.exceptions.DataBaseException;
import br.com.ferrazsergio.produtosapi.infra.exceptions.ResourceNotFoundException;
import br.com.ferrazsergio.produtosapi.model.entities.Produto;
import br.com.ferrazsergio.produtosapi.model.repositorys.ProdutoRepository;

@Service
public class ProdutoService {

	private ProdutoRepository produtoRepository;

	@Autowired
	public ProdutoService(ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
	}

	public Produto buscaPorId(Long id) {
		Optional<Produto> obj = produtoRepository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public Produto salvar(Produto produto) {
		return produtoRepository.save(produto);
	}

	public void deletarPorId(Long id) {
		try {
			if (produtoRepository.existsById(id)) {
				produtoRepository.deleteById(id);
			} else {
				throw new ResourceNotFoundException(id);
			}
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException(e.getMessage());
		}
	}

}
