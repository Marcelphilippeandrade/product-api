package br.com.ecommerce.marcel.philippe.dto;

import br.com.ecommerce.marcel.philippe.modelo.Categoria;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDTO {

	@NotNull
	private Long id;
	private String nome;

	public static CategoriaDTO convert(Categoria categoria) {
		CategoriaDTO categoriaDTO = new CategoriaDTO();
		categoriaDTO.setId(categoria.getId());
		categoriaDTO.setNome(categoria.getNome());
		return categoriaDTO;
	}

}
