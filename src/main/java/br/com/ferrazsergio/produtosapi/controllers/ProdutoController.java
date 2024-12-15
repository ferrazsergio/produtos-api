package br.com.ferrazsergio.produtosapi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ferrazsergio.produtosapi.model.entities.Produto;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
	
	@PostMapping
	public ResponseEntity<Produto> salvar(@RequestBody Produto produto) {
	    System.out.println("Produto recebido: " + produto);
	    return ResponseEntity.status(HttpStatus.CREATED).body(produto);
	}
}
