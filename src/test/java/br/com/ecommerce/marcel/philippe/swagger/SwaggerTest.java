package br.com.ecommerce.marcel.philippe.swagger;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class SwaggerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void deveRetornarPaginaUiDoSwagger() throws Exception {
		mockMvc.perform(get("/produtos/swagger-ui/index.html"))
			.andExpect(status()
					.isOk());
	}
	
	@Test
	public void deveRetornarOpenApiDocs() throws Exception {
		mockMvc.perform(get("/produtos/v3/api-docs"))
		.andExpect(status()
				.isOk())
		.andExpect(jsonPath("$.paths['/produto']").exists())
		.andExpect(jsonPath("$.paths['/produto'].get.responses['200'].description").exists())
		.andExpect(jsonPath("$.paths['/produto/categoria/{categoriaId}'].get.responses['200'].description").exists())
		.andExpect(jsonPath("$.paths['/produto/categoria/{categoriaId}'].get.responses['404'].description").exists())
		.andExpect(jsonPath("$.paths['/produto/{produtoIdentifier}'].get.responses['200'].description").exists())
		.andExpect(jsonPath("$.paths['/produto/{produtoIdentifier}'].get.responses['404'].description").exists())
		.andExpect(jsonPath("$.paths['/produto'].post.responses['201'].description").exists())
		.andExpect(jsonPath("$.paths['/produto'].post.responses['400'].description").exists())
		.andExpect(jsonPath("$.paths['/produto/{id}'].delete.responses['200'].description").exists())
		.andExpect(jsonPath("$.paths['/produto/{id}'].delete.responses['404'].description").exists());
	}
}
