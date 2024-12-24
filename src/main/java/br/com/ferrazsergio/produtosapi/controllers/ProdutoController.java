package br.com.ferrazsergio.produtosapi.controllers;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ferrazsergio.produtosapi.infra.exceptions.ResourceNotFoundException;
import br.com.ferrazsergio.produtosapi.model.entities.Produto;
import br.com.ferrazsergio.produtosapi.services.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	private ProdutoService produtoService;

	@Autowired
	public ProdutoController(ProdutoService produtoService) {
		this.produtoService = produtoService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
		try {
			Produto produto = produtoService.buscaPorId(id);
			return ResponseEntity.ok(produto);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno no servidor.");
		}
	}

	@PostMapping
	public ResponseEntity<?> salvar(@Validated @RequestBody Produto produto) {
		try {
			produtoService.salvar(produto);
			return ResponseEntity.status(HttpStatus.CREATED).body(produto);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar o produto.");
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletarPorId(@PathVariable Long id) {
		try {
			produtoService.deletarPorId(id);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Produto produto) {
		try {
			Produto produtoAtualizado = produtoService.atualizarPorId(id, produto);
			return ResponseEntity.ok(produtoAtualizado);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao atualizar o produto.");
		}
	}
	
	@GetMapping
	public ResponseEntity<?> buscar(@RequestParam("nome") String nome){
		 if (nome == null || nome.isBlank()) {
	            return ResponseEntity.badRequest().body(Collections.emptyList());
	        }
	        try {
	            List<Produto> produtos = produtoService.buscarPorNome(nome);
	            return ResponseEntity.ok(produtos);
	        } catch (ResourceNotFoundException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
	        }
	    }
}
