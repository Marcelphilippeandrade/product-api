package br.com.ecommerce.marcel.philippe.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.ecommerce.marcel.philippe.modelo.Categoria;
import br.com.ecommerce.marcel.philippe.modelo.Produto;

@ExtendWith(SpringExtension.class)
public class ProdutoRepositoryTest {

	@Autowired
	private ProdutoRepository produtoRepository;

	@MockBean
	private ProdutoRepository mockProdutoRepository;

	private static final Long CATEGORIA_ID = 1L;
	private static final String NOME_CATEGORIA = "Eletrônico";
	private static final String NOME = "TV";
	private static final float PRECO = 1000F;
	private static final String DESCRICAO = "Tv 50 polegadas";
	private static final String IDENTIFICACAO_PRODUTO = "tv";

	@Test
	public void deveRetornarTodosOsProdutosDeUmaCategoria() {
		when(mockProdutoRepository.getProdutoByCategoria(CATEGORIA_ID)).thenReturn(obterDadosProduto());
		List<Produto> produtos = produtoRepository.getProdutoByCategoria(CATEGORIA_ID);

		assertFalse("A lista não pode ser vazia!", produtos.isEmpty());
	}
	
	@Test
	public void deveRetornarUmProdutoPelaIdentificacao() {
		when(mockProdutoRepository.findByProdutoIdentifier(IDENTIFICACAO_PRODUTO)).thenReturn(obterDadosProduto().stream().findAny().get());
		Produto produto = produtoRepository.findByProdutoIdentifier(IDENTIFICACAO_PRODUTO);
		assertEquals(IDENTIFICACAO_PRODUTO, produto.getProdutoIdentifier());
	}

	private List<Produto> obterDadosProduto() {

		List<Produto> produtos = new ArrayList<Produto>();
		Produto produto1 = new Produto();

		produto1.setNome(NOME);
		produto1.setPreco(PRECO);
		produto1.setDescricao(DESCRICAO);
		produto1.setProdutoIdentifier(IDENTIFICACAO_PRODUTO);

		Categoria categoria = new Categoria();
		categoria.setId(CATEGORIA_ID);
		categoria.setNome(NOME_CATEGORIA);

		produto1.setCategoria(categoria);

		Produto produto2 = new Produto();

		produto2.setNome(NOME);
		produto2.setPreco(PRECO);
		produto2.setDescricao(DESCRICAO);
		produto2.setProdutoIdentifier(IDENTIFICACAO_PRODUTO);

		produtos.add(produto2);

		return produtos;
	}
}
