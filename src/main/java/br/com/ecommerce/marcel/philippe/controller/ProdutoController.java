package br.com.ecommerce.marcel.philippe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.ecommerce.marcel.philippe.dto.ProdutoDTO;
import br.com.ecommerce.marcel.philippe.exception.ProdutoNotFoundException;
import br.com.ecommerce.marcel.philippe.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@Operation(summary = "Lista todos os produtos")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso"), })
	@GetMapping("/produto")
	@ResponseStatus(HttpStatus.OK)
	public List<ProdutoDTO> getProdutos() {
		List<ProdutoDTO> produtos = produtoService.getAll();
		return produtos;
	}

	@Operation(summary = "Retornar categoria pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoria encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Categoria não encontrado")
    })
	@GetMapping("/produto/categoria/{categoriaId}")
	@ResponseStatus(HttpStatus.OK)
	public List<ProdutoDTO> getProdutoByCategoria(@PathVariable Long categoriaId) {
		List<ProdutoDTO> produtos = produtoService.getProdutoByCategoriaId(categoriaId);
		return produtos;
	}

	@Operation(summary = "Retornar produto pelo Identificador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
	@GetMapping("/produto/{produtoIdentifier}")
	@ResponseStatus(HttpStatus.OK)
	public ProdutoDTO findById(@PathVariable String produtoIdentifier) {
		return produtoService.findByProdutoIdentifier(produtoIdentifier);
	}

	@Operation(summary = "Salvar Produto")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Produto salvo com sucesso"),
			@ApiResponse(responseCode = "400", description = "Erro ao salvar produto") })
	@PostMapping("/produto")
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoDTO newProduto(@Valid @RequestBody ProdutoDTO produtoDTO) {
		return produtoService.save(produtoDTO);
	}

	@Operation(summary = "Deletar produto pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto Deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Erro ao deletar produto")
    })
	@DeleteMapping("/produto/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ProdutoDTO delete(@PathVariable Long id) throws ProdutoNotFoundException {
		return produtoService.delete(id);
	}
}
