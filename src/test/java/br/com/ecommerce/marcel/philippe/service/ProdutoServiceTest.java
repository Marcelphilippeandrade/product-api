package br.com.ecommerce.marcel.philippe.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.ecommerce.marcel.philippe.dto.CategoriaDTO;
import br.com.ecommerce.marcel.philippe.dto.ProdutoDTO;
import br.com.ecommerce.marcel.philippe.exception.ProdutoNotFoundException;
import br.com.ecommerce.marcel.philippe.modelo.Categoria;
import br.com.ecommerce.marcel.philippe.modelo.Produto;
import br.com.ecommerce.marcel.philippe.repository.ProdutoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProdutoServiceTest {

	@MockBean
	private ProdutoRepository produtoRepository;

	@Autowired
	private ProdutoService produtoService;

	private static final String DESCRICAO = "Tv 50 polegadas";
	private static final String IDENTIFICACAO_PRODUTO = "tv";
	private static final float PRECO_PRODUTO = 1000F;
	private static final String NOME_PRODUTO = "TV";
	private static final long PRODUTO_ID = 1L;

	private static final String IDENTIFICADOR_QUALQUER = "identificador qualquer";

	private static final String NOME_CATEGORIA = "Eletrônico";
	private static final long CATEGORIA_ID = 1L;

	@BeforeEach
	public void setUp() {
		List<Produto> produtos = new ArrayList<>();
		Produto produto1 = new Produto();
		produto1.setId(PRODUTO_ID);
		produto1.setNome(NOME_PRODUTO);
		produto1.setPreco(PRECO_PRODUTO);
		produto1.setProdutoIdentifier(IDENTIFICACAO_PRODUTO);
		produto1.setDescricao(DESCRICAO);

		Produto produto2 = new Produto();
		produto1.setId(PRODUTO_ID);
		produto1.setNome(NOME_PRODUTO);
		produto1.setPreco(PRECO_PRODUTO);
		produto1.setProdutoIdentifier(IDENTIFICACAO_PRODUTO);
		produto1.setDescricao(DESCRICAO);

		Categoria categoria = new Categoria();
		categoria.setId(CATEGORIA_ID);
		categoria.setNome(NOME_CATEGORIA);

		produto1.setCategoria(categoria);
		produto2.setCategoria(categoria);
		produtos.add(produto1);
		produtos.add(produto2);

		BDDMockito.given(this.produtoRepository.save(any())).willReturn(produto1);
		BDDMockito.given(this.produtoRepository.findAll()).willReturn(produtos);
		BDDMockito.given(this.produtoRepository.getProdutoByCategoria(CATEGORIA_ID)).willReturn(produtos);
		BDDMockito.given(this.produtoRepository.findByProdutoIdentifier(IDENTIFICACAO_PRODUTO)).willReturn(produto1);
		BDDMockito.given(this.produtoRepository.findById(PRODUTO_ID)).willReturn(Optional.of(produto1));
	}

	@Test
	public void deveRetornarTodosOsProdutos() {
		List<ProdutoDTO> produtos = this.produtoService.getAll();
		assertNotNull(produtos);
		assertEquals(2, produtos.size());
	}

	@Test
	public void deveRetornarTodosOsProdutosDeUmaCategoria() {
		List<ProdutoDTO> produtos = this.produtoService.getProdutoByCategoriaId(CATEGORIA_ID);
		assertNotNull(produtos);
		assertEquals(2, produtos.size());
	}

	@Test
	public void deveRetornarUmProdutoPeloIdentificador() {
		ProdutoDTO produto = this.produtoService.findByProdutoIdentifier(IDENTIFICACAO_PRODUTO);
		assertEquals(IDENTIFICACAO_PRODUTO, produto.getProdutoIdentifier());
		assertEquals(NOME_PRODUTO, produto.getNome());
	}

	@Test
	public void naoDeveRetornarUmProdutoPeloIdentificador() {
		ProdutoDTO produto = this.produtoService.findByProdutoIdentifier(IDENTIFICADOR_QUALQUER);
		assertNull(produto);
	}

	@Test
	public void deveSalvarUmProduto() {
		ProdutoDTO productDTO = new ProdutoDTO();
		productDTO.setNome(NOME_PRODUTO);
		productDTO.setDescricao(DESCRICAO);
		productDTO.setPreco(PRECO_PRODUTO);
		productDTO.setProdutoIdentifier(IDENTIFICACAO_PRODUTO);

		CategoriaDTO categoriaDTO = new CategoriaDTO();
		categoriaDTO.setId(CATEGORIA_ID);
		categoriaDTO.setNome(NOME_CATEGORIA);

		productDTO.setCategoria(categoriaDTO);

		ProdutoDTO usuarioSalvo = this.produtoService.save(productDTO);
		assertNotNull(usuarioSalvo);
	}

	@Test
	public void deveDeletarUmProdutoPeloId() {
		ProdutoDTO produtoDeletado = produtoService.delete(PRODUTO_ID);
		assertEquals(NOME_PRODUTO, produtoDeletado.getNome());
	}

	@Test
	public void naoDeveRetornarUmProdutoPeloId() {
		assertThrows(ProdutoNotFoundException.class, () -> {
			produtoService.delete(2);
		});
	}
}
