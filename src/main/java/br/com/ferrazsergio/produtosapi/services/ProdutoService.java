package br.com.ferrazsergio.produtosapi.services;

import java.util.List;
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
	
	public Produto atualizarPorId(Long id, Produto produto) {
		Produto produtoExistente = produtoRepository.findProdutoById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Produto com ID " + id + " não encontrado."));
		 
		 produtoExistente.setNome(produto.getNome());
		 produtoExistente.setPreco(produto.getPreco());
		 produtoExistente.setDescricao(produto.getDescricao());
		 
		 if (produto.getPreco() <= 0) {
			    throw new IllegalArgumentException("O preço do produto deve ser maior que zero.");
			}
		 
		 Produto produtoAlterado = produtoRepository.save(produtoExistente);
		 return produtoAlterado;
	}
	
	public List<Produto> buscarPorNome(String nome) {
        List<Produto> produtos = produtoRepository.findByNomeContainingIgnoreCase(nome);
        if (produtos.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum produto encontrado com o nome: " + nome);
        }
        return produtos;
    }

}
