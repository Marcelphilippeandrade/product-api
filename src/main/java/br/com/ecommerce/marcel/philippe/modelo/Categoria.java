package br.com.ecommerce.marcel.philippe.modelo;

import br.com.ecommerce.marcel.philippe.dto.CategoriaDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="categoria")
public class Categoria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String nome;
	
	public static Categoria convert(CategoriaDTO categoriaDTO) {
		Categoria categoria = new Categoria();
		categoria.setId(categoriaDTO.getId());
		categoria.setNome(categoriaDTO.getNome());
		return categoria;
		}

}
