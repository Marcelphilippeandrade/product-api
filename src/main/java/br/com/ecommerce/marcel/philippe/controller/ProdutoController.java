package br.com.ecommerce.marcel.philippe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.ecommerce.marcel.philippe.dto.ProdutoDTO;
import br.com.ecommerce.marcel.philippe.exception.ProdutoNotFoundException;
import br.com.ecommerce.marcel.philippe.service.ProdutoService;
import jakarta.validation.Valid;

@RestController
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@GetMapping("/produto")
	public List<ProdutoDTO> getProdutos() {
		List<ProdutoDTO> produtos = produtoService.getAll();
		return produtos;
	}

	@GetMapping("/produto/categoria/{categoriaId}")
	public List<ProdutoDTO> getProdutoByCategoria(@PathVariable Long categoriaId) {
		List<ProdutoDTO> produtos = produtoService.getProdutoByCategoriaId(categoriaId);
		return produtos;
	}

	@GetMapping("/produto/{produtoIdentifier}")
	public ProdutoDTO findById(@PathVariable String produtoIdentifier) {
		return produtoService.findByProdutoIdentifier(produtoIdentifier);
	}

	@PostMapping("/produto")
	public ProdutoDTO newProduto(@Valid @RequestBody ProdutoDTO produtoDTO) {
		return produtoService.save(produtoDTO);
	}

	@DeleteMapping("/produto/{id}")
	public ProdutoDTO delete(@PathVariable Long id) throws ProdutoNotFoundException {
		return produtoService.delete(id);
	}
}
