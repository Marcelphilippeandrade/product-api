package br.com.ecommerce.marcel.philippe.dto;

import br.com.ecommerce.marcel.philippe.modelo.Produto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDTO {

	@NotBlank
	private String produtoIdentifier;
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String descricao;
	
	@NotNull
	private Float preco;
	
	@NotNull
	private CategoriaDTO categoria;

	public static ProdutoDTO convert(Produto produto) {
		ProdutoDTO productDTO = new ProdutoDTO();
		productDTO.setNome(produto.getNome());
		productDTO.setPreco(produto.getPreco());
		productDTO.setProdutoIdentifier(produto.getProdutoIdentifier());
		productDTO.setDescricao(produto.getDescricao());
		if (produto.getCategoria() != null) {
			productDTO.setCategoria(CategoriaDTO.convert(produto.getCategoria()));
		}
		return productDTO;
	}

}
