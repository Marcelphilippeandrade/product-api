package br.com.ecommerce.marcel.philippe.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.jupiter.api.Test;
import br.com.ecommerce.marcel.philippe.modelo.Categoria;
import br.com.ecommerce.marcel.philippe.modelo.Produto;

public class DTOConverterTest {
	
	private static final float PRECO_PRODUTO2 = 1500.0F;
	private static final float PRECO_PRODUTO = 3000.0F;
	private static final long ID_CATEGORIA2 = 2L;
	private static final long ID_CATEGORIA = 1L;
	private static final String NOME_PRODUTO = "Notebook";
	private static final String NOME_CATEGORIA2 = "Informática";
	private static final String NOME_CATEGORIA = "Eletrônicos";

	@Test
    void testConvertCategoria() {
        Categoria categoria = new Categoria();
        categoria.setId(ID_CATEGORIA);
        categoria.setNome(NOME_CATEGORIA);

        CategoriaDTO categoriaDto = DTOConverter.convert(categoria);

        assertNotNull(categoriaDto);
        assertEquals(Long.valueOf(ID_CATEGORIA), categoriaDto.getId());
        assertEquals(NOME_CATEGORIA, categoriaDto.getNome());
    }

    @Test
    void testConvertProduto() {
        Categoria categoria = new Categoria();
        categoria.setId(ID_CATEGORIA2);
        categoria.setNome(NOME_CATEGORIA2);

        Produto produto = new Produto();
        produto.setNome(NOME_PRODUTO);
        produto.setPreco(PRECO_PRODUTO);
        produto.setCategoria(categoria);

        ProdutoDTO produtoDto = DTOConverter.convert(produto);

        assertNotNull(produtoDto);
        assertEquals(NOME_PRODUTO, produtoDto.getNome());
        assertEquals(Float.valueOf(PRECO_PRODUTO), produtoDto.getPreco());
        assertNotNull(produtoDto.getCategoria());
        assertEquals(Long.valueOf(ID_CATEGORIA2), produtoDto.getCategoria().getId());
        assertEquals(NOME_CATEGORIA2, produtoDto.getCategoria().getNome());
    }

    @Test
    void testConvertProdutoSemCategoria() {
        Produto produto = new Produto();
        produto.setNome(NOME_PRODUTO);
        produto.setPreco(PRECO_PRODUTO2);
        produto.setCategoria(null);

        ProdutoDTO produtoDto = DTOConverter.convert(produto);

        assertNotNull(produtoDto);
        assertEquals(NOME_PRODUTO, produtoDto.getNome());
        assertEquals(Float.valueOf(PRECO_PRODUTO2), produtoDto.getPreco());
        assertNull(produtoDto.getCategoria());
    }
}

