package br.com.ferrazsergio.produtosapi.model.repositorys;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ferrazsergio.produtosapi.model.entities.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	Optional<Produto> findProdutoById(Long id);
    List<Produto> findByNomeContainingIgnoreCase(String nome);
}
