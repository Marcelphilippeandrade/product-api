package br.com.ecommerce.marcel.philippe.dto;

import br.com.ecommerce.marcel.philippe.modelo.Categoria;
import br.com.ecommerce.marcel.philippe.modelo.Produto;

public class DTOConverter {

	public static CategoriaDTO convert(Categoria categoria) {
		CategoriaDTO categoriaDto = new CategoriaDTO();
		categoriaDto.setId(categoria.getId());
		categoriaDto.setNome(categoria.getNome());
		return categoriaDto;
	}

	public static ProdutoDTO convert(Produto produto) {
		ProdutoDTO produtoDto = new ProdutoDTO();
		produtoDto.setNome(produto.getNome());
		produtoDto.setPreco(produto.getPreco());
		if (produto.getCategoria() != null) {
			produtoDto.setCategoria(DTOConverter.convert(produto.getCategoria()));
		}
		return produtoDto;
	}
}
