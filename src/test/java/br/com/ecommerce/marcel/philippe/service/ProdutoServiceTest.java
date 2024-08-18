package br.com.ecommerce.marcel.philippe.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

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
import br.com.ecommerce.marcel.philippe.exception.CategoriaNotFoundException;
import br.com.ecommerce.marcel.philippe.exception.ProdutoNotFoundException;
import br.com.ecommerce.marcel.philippe.modelo.Categoria;
import br.com.ecommerce.marcel.philippe.modelo.Produto;
import br.com.ecommerce.marcel.philippe.repository.CategoriaRepository;
import br.com.ecommerce.marcel.philippe.repository.ProdutoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProdutoServiceTest {

	@MockBean
	private ProdutoRepository produtoRepository;

	@MockBean
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoService produtoService;

	private Produto produto1;

	private Produto produto2;

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
		produto1 = new Produto();
		produto1.setId(PRODUTO_ID);
		produto1.setNome(NOME_PRODUTO);
		produto1.setPreco(PRECO_PRODUTO);
		produto1.setProdutoIdentifier(IDENTIFICACAO_PRODUTO);
		produto1.setDescricao(DESCRICAO);

		produto2 = new Produto();
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
	public void deveRetornarUmaExcecaoQuandoNaoExistirUmProduto() {
		when(produtoRepository.findByProdutoIdentifier(IDENTIFICADOR_QUALQUER)).thenReturn(null);
		assertThrows(ProdutoNotFoundException.class, () -> {
			produtoService.findByProdutoIdentifier(IDENTIFICADOR_QUALQUER);
		});
	}

	@Test
	public void deveSalvarUmProduto() {
		when(categoriaRepository.existsById(anyLong())).thenReturn(true);

		ProdutoDTO productDTO = retornaProdutoDTO();

		CategoriaDTO categoriaDTO = retornaCategoriaDTO();

		productDTO.setCategoria(categoriaDTO);

		ProdutoDTO usuarioSalvo = this.produtoService.save(productDTO);
		assertNotNull(usuarioSalvo);
	}

	@Test
	public void deveRetonarUmaExcecaoQuandoNaoExistirACategoriaCadastrada() {
		when(categoriaRepository.findById(anyLong())).thenReturn(Optional.empty());

		ProdutoDTO productDTO = retornaProdutoDTO();

		productDTO.setCategoria(retornaCategoriaDTO());

		assertThrows(CategoriaNotFoundException.class, () -> {
			produtoService.save(productDTO);
		});
	}

	@Test
	public void deveDeletarUmProdutoPeloId() {
		ProdutoDTO produtoDeletado = produtoService.delete(PRODUTO_ID);
		assertEquals(NOME_PRODUTO, produtoDeletado.getNome());
	}

	@Test
	public void deveRetornarUmprodutoPeloId() {
		when(produtoRepository.findById(PRODUTO_ID)).thenReturn(Optional.of(produto1));
		ProdutoDTO produtoDTO = produtoService.findById(PRODUTO_ID);
		assertEquals(NOME_PRODUTO, produtoDTO.getNome());
	}

	@Test
	public void deveRetornarUmaExcecaoQuandoNaoExitirUmProdutoParaDeletar() {
		when(produtoRepository.findById(PRODUTO_ID)).thenReturn(Optional.empty());
		assertThrows(ProdutoNotFoundException.class, () -> {
			produtoService.findById(PRODUTO_ID);
		});
	}

	@Test
	public void naoDeveRetornarUmProdutoPeloId() {
		assertThrows(ProdutoNotFoundException.class, () -> {
			produtoService.delete(2);
		});
	}

	public CategoriaDTO retornaCategoriaDTO() {
		CategoriaDTO categoriaDTO = new CategoriaDTO();
		categoriaDTO.setId(CATEGORIA_ID);
		categoriaDTO.setNome(NOME_CATEGORIA);
		return categoriaDTO;
	}

	public ProdutoDTO retornaProdutoDTO() {
		ProdutoDTO productDTO = new ProdutoDTO();
		productDTO.setNome(NOME_PRODUTO);
		productDTO.setDescricao(DESCRICAO);
		productDTO.setPreco(PRECO_PRODUTO);
		productDTO.setProdutoIdentifier(IDENTIFICACAO_PRODUTO);
		return productDTO;
	}
}
