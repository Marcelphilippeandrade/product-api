package br.com.ecommerce.marcel.philippe.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.ecommerce.marcel.philippe.dto.CategoriaDTO;
import br.com.ecommerce.marcel.philippe.dto.ProdutoDTO;
import br.com.ecommerce.marcel.philippe.modelo.Produto;
import br.com.ecommerce.marcel.philippe.service.ProdutoService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc()
public class ProdutoControllerTest {


	@Autowired
	private MockMvc mvc;

	@MockBean
	private ProdutoService produtoService;
	
	private List<ProdutoDTO> produtos;
	private ProdutoDTO produtoDto;

	private static final String CATEGORIA_NOME = "Eletronico";
	private static final long CATEGORIA_ID = 1L;
	private static final String PRODUTO_IDENTIFICADOR = "tv";
	private static final float PRODUTO_PRECO = 1000F;
	private static final String PRODUTO_DESCRICAO = "TV 50 polegadas";
	private static final String PRODUTO_NOME = "TV";
	private static final String URL_BASE = "/produto";
	private static final long PRODUTO_ID = 1L;
	
	@BeforeEach
	public void setUp() {
		produtos = new ArrayList<>();

		produtoDto = obterDadosDosProdutos();
		produtos.add(produtoDto);		
	}

	@Test
	public void deveRetornarTodosOsProdutos() throws Exception {
		BDDMockito.given(this.produtoService.save(Mockito.any(ProdutoDTO.class))).willReturn(produtoDto);
		BDDMockito.given(this.produtoService.getAll()).willReturn(produtos);

		mvc.perform(MockMvcRequestBuilders.get(URL_BASE)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString(PRODUTO_NOME)))
				.andExpect(content().string(containsString(PRODUTO_IDENTIFICADOR)))
				.andExpect(content().string(containsString(PRODUTO_DESCRICAO)))
				.andExpect(content().string(containsString(Float.toString(PRODUTO_PRECO))));
	}

	@Test
	public void deveRetornarTodosOsProdutosDeUmaCategoria() throws Exception {
		BDDMockito.given(this.produtoService.save(Mockito.any(ProdutoDTO.class))).willReturn(produtoDto);
		BDDMockito.given(this.produtoService.getProdutoByCategoriaId(CATEGORIA_ID)).willReturn(produtos);
		
		mvc.perform(MockMvcRequestBuilders.get(URL_BASE + "/categoria/" + CATEGORIA_ID)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString(PRODUTO_NOME)))
				.andExpect(content().string(containsString(PRODUTO_IDENTIFICADOR)))
				.andExpect(content().string(containsString(PRODUTO_DESCRICAO)))
				.andExpect(content().string(containsString(Long.toString(CATEGORIA_ID))))
				.andExpect(content().string(containsString(CATEGORIA_NOME)));
	}
	
	@Test
	public void deveRetornarUmProdutoPeloIdentificador() throws Exception {
		BDDMockito.given(this.produtoService.save(Mockito.any(ProdutoDTO.class))).willReturn(produtoDto);
		BDDMockito.given(this.produtoService.findByProdutoIdentifier(PRODUTO_IDENTIFICADOR)).willReturn(produtoDto);
		
		mvc.perform(MockMvcRequestBuilders.get(URL_BASE + "/" +PRODUTO_IDENTIFICADOR)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString(PRODUTO_NOME)))
				.andExpect(content().string(containsString(PRODUTO_DESCRICAO)))
				.andExpect(content().string(containsString(Float.toString(PRODUTO_PRECO))))
				.andExpect(content().string(containsString(Long.toString(CATEGORIA_ID))))
				.andExpect(content().string(containsString(CATEGORIA_NOME)));
	}
	
	@Test
	public void deveSalvarUmProduto() throws Exception {
		BDDMockito.given(this.produtoService.save(Mockito.any(ProdutoDTO.class))).willReturn(produtoDto);
		
		mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
				.content(this.obterJsonRequisicaoPost())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString(Long.toString(PRODUTO_ID))))
				.andExpect(content().string(containsString(PRODUTO_NOME)))
				.andExpect(content().string(containsString(PRODUTO_DESCRICAO)))
				.andExpect(content().string(containsString(Float.toString(PRODUTO_PRECO))));
	}
	
	@Test
	public void deveDeletarUmProdutoPeloId() throws Exception {
		BDDMockito.given(this.produtoService.save(Mockito.any(ProdutoDTO.class))).willReturn(produtoDto);
		
		mvc.perform(MockMvcRequestBuilders.delete(URL_BASE + "/" + PRODUTO_ID)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	private String obterJsonRequisicaoPost() throws JsonProcessingException {
		ProdutoDTO produtoDto = obterDadosDosProdutos();
		Produto produto = Produto.convert(produtoDto);
		produto.setId(PRODUTO_ID);
		
		ProdutoDTO produtoDtoConvertido = ProdutoDTO.convert(produto);
		ObjectMapper mapper = new ObjectMapper();
		
		return mapper.writeValueAsString(produtoDtoConvertido);
	}

	private ProdutoDTO obterDadosDosProdutos() {
		ProdutoDTO produtoDto = new ProdutoDTO();
		produtoDto.setNome(PRODUTO_NOME);
		produtoDto.setDescricao(PRODUTO_DESCRICAO);
		produtoDto.setPreco(PRODUTO_PRECO);
		produtoDto.setProdutoIdentifier(PRODUTO_IDENTIFICADOR);

		CategoriaDTO categoriaDto = new CategoriaDTO();
		categoriaDto.setId(CATEGORIA_ID);
		categoriaDto.setNome(CATEGORIA_NOME);

		produtoDto.setCategoria(categoriaDto);
		return produtoDto;
	}
}
