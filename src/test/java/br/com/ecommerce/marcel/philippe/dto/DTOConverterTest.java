package br.com.ecommerce.marcel.philippe.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.jupiter.api.Test;
import br.com.ecommerce.marcel.philippe.modelo.Categoria;
import br.com.ecommerce.marcel.philippe.modelo.Produto;

public class DTOConverterTest {
	
	@Test
    void testConvertCategoria() {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("Eletrônicos");

        CategoriaDTO categoriaDto = DTOConverter.convert(categoria);

        assertNotNull(categoriaDto);
        assertEquals(Long.valueOf(1L), categoriaDto.getId());
        assertEquals("Eletrônicos", categoriaDto.getNome());
    }

    @Test
    void testConvertProduto() {
        Categoria categoria = new Categoria();
        categoria.setId(2L);
        categoria.setNome("Informática");

        Produto produto = new Produto();
        produto.setNome("Notebook");
        produto.setPreco(3000.0F);
        produto.setCategoria(categoria);

        ProdutoDTO produtoDto = DTOConverter.convert(produto);

        assertNotNull(produtoDto);
        assertEquals("Notebook", produtoDto.getNome());
        assertEquals(Float.valueOf(3000.0F), produtoDto.getPreco());
        assertNotNull(produtoDto.getCategoria());
        assertEquals(Long.valueOf(2L), produtoDto.getCategoria().getId());
        assertEquals("Informática", produtoDto.getCategoria().getNome());
    }

    @Test
    void testConvertProdutoSemCategoria() {
        Produto produto = new Produto();
        produto.setNome("Smartphone");
        produto.setPreco(1500.0F);
        produto.setCategoria(null);

        ProdutoDTO produtoDto = DTOConverter.convert(produto);

        assertNotNull(produtoDto);
        assertEquals("Smartphone", produtoDto.getNome());
        assertEquals(Float.valueOf(1500.0F), produtoDto.getPreco());
        assertNull(produtoDto.getCategoria());
    }
}

