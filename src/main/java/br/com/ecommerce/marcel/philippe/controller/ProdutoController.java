package br.com.ecommerce.marcel.philippe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.ecommerce.marcel.philippe.dto.ProdutoDTO;
import br.com.ecommerce.marcel.philippe.exception.ProdutoNotFoundException;
import br.com.ecommerce.marcel.philippe.response.Response;
import br.com.ecommerce.marcel.philippe.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@Tag(name = "Produtos", description = "Gerenciamento de produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@Operation(summary = "Lista todos os produtos")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso"), })
	@GetMapping("/produto")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Response<List<ProdutoDTO>>> getProdutos() {
		Response<List<ProdutoDTO>> response = new Response<>();
		List<ProdutoDTO> produtos = produtoService.getAll();
		
		response.setStatusCode(200);
		response.setData(produtos);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "Retornar todos os produtos de uma categoria expecífica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produtos encontrados com sucesso"),
        @ApiResponse(responseCode = "404", description = "Produtos não encontrados")
    })
	@GetMapping("/produto/categoria/{categoriaId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Response<List<ProdutoDTO>>> getProdutoByCategoria(@PathVariable Long categoriaId) {
		Response<List<ProdutoDTO>> response = new Response<>();
		List<ProdutoDTO> produtos = produtoService.getProdutoByCategoriaId(categoriaId);
		
		response.setStatusCode(200);
		response.setData(produtos);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "Retornar produto pelo Identificador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
	@GetMapping("/produto/{produtoIdentifier}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Response<ProdutoDTO>> findById(@PathVariable String produtoIdentifier) {
		Response<ProdutoDTO> response = new Response<ProdutoDTO>();
		
		ProdutoDTO produto = produtoService.findByProdutoIdentifier(produtoIdentifier);
		response.setStatusCode(200);
		response.setData(produto);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "Salvar Produto")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Produto salvo com sucesso"),
			@ApiResponse(responseCode = "400", description = "Erro ao salvar produto") })
	@PostMapping("/produto")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Response<ProdutoDTO>> newProduto(@Valid @RequestBody ProdutoDTO produtoDTO) {
		Response<ProdutoDTO> response = new Response<ProdutoDTO>();
		
		ProdutoDTO produto = this.produtoService.save(produtoDTO);
		response.setStatusCode(201);
		response.setData(produto);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@Operation(summary = "Deletar produto pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto Deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Erro ao deletar produto")
    })
	@DeleteMapping("/produto/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Response<ProdutoDTO>> delete(@PathVariable Long id) throws ProdutoNotFoundException {
		Response<ProdutoDTO> response = new Response<ProdutoDTO>();
		ProdutoDTO produto = this.produtoService.delete(id);
		
		response.setStatusCode(200);
		response.setData(produto);
		
		return ResponseEntity.ok(response);
	}
}
