package br.com.ecommerce.marcel.philippe.exception.advice;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import br.com.ecommerce.marcel.philippe.dto.ErrorDTO;
import br.com.ecommerce.marcel.philippe.exception.CategoriaNotFoundException;
import br.com.ecommerce.marcel.philippe.exception.ProdutoNotFoundException;

class ProdutoControllerAdviceTest {

	@InjectMocks
	private ProdutoControllerAdvice produtoControllerAdvaice;

	@Mock
	private ProdutoNotFoundException produtoNotFoundException;

	@Mock
	private CategoriaNotFoundException categoriaNotFoundException;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void deveRetornarOErrohandleProdutoNotFound() {
		when(produtoNotFoundException.getMessage()).thenReturn("Produto não encontrado.");

		ErrorDTO errorDTO = produtoControllerAdvaice.handleUsarioNotFound(produtoNotFoundException);

		assertEquals(HttpStatus.NOT_FOUND.value(), errorDTO.getStatus());
		assertEquals("Produto não encontrado.", errorDTO.getMessage());
		assertEquals(new Date().getClass(), errorDTO.getTimestamp().getClass());
	}

	@Test
	public void deveRetornarhandleCategoriaNotFound() {
		when(categoriaNotFoundException.getMessage()).thenReturn("Categoria não encontrada.");

		ErrorDTO errorDTO = produtoControllerAdvaice.handleCategoriaNotFound(categoriaNotFoundException);

		assertEquals(HttpStatus.NOT_FOUND.value(), errorDTO.getStatus());
		assertEquals("Categoria não encontrada.", errorDTO.getMessage());
		assertEquals(new Date().getClass(), errorDTO.getTimestamp().getClass());
	}

	@Test
	public void deveRetornarProcessValidationError() {
		BindingResult bindingResult = mock(BindingResult.class);
		MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

		List<FieldError> fieldErrors = List.of(new FieldError("objectName", "fieldName", "defaultMessage"));
		when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

		ErrorDTO response = produtoControllerAdvaice.processValidationError(ex);

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		assertEquals("Valor inválido para o(s) campo(s): fieldName,", response.getMessage());
		assertEquals(Date.class, response.getTimestamp().getClass());
	}
}
