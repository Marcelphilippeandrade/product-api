package br.com.ecommerce.marcel.philippe.modelo;

import br.com.ecommerce.marcel.philippe.dto.ProdutoDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "produto")
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String nome;
	private Float preco;
	private String descricao;
	private String produtoIdentifier;

	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;

	public static Produto convert(ProdutoDTO produtoDTO) {
		Produto produto = new Produto();
		produto.setNome(produtoDTO.getNome());
		produto.setPreco(produtoDTO.getPreco());
		produto.setDescricao(produtoDTO.getDescricao());
		produto.setProdutoIdentifier(produtoDTO.getProdutoIdentifier());
		if (produtoDTO.getCategoria() != null) {
			produto.setCategoria(Categoria.convert(produtoDTO.getCategoria()));
		}
		return produto;
	}
}
