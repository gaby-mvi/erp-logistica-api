package com.transportadora.erp.security;

import com.transportadora.erp.model.Perfil;
import com.transportadora.erp.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class TokenServiceTest {

    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        tokenService = new TokenService();
        // Injeta a chave secreta de teste na propriedade @Value da classe
        ReflectionTestUtils.setField(tokenService, "secret", "ChaveSecretaDeTesteSuperSegura123");
    }

    @Test
    @DisplayName("1. Deve gerar um token JWT válido para um usuário")
    void deveGerarTokenComSucesso() {
        // Arrange (Preparar)
        Usuario usuario = Usuario.builder()
                .nome("Carlos Silva")
                .email("carlos@transportadora.com")
                .perfil(Perfil.ROLE_ADMIN)
                .build();

        // Act (Agir)
        String token = tokenService.gerarToken(usuario);

        // Assert (Verificar)
        assertNotNull(token, "O token não pode ser nulo");
        assertFalse(token.isEmpty(), "O token não pode vir vazio");
    }

    @Test
    @DisplayName("2. Deve validar o token e extrair o e-mail do usuário corretamente")
    void deveValidarTokenComSucesso() {
        // Arrange
        Usuario usuario = Usuario.builder()
                .nome("Carlos Silva")
                .email("carlos@transportadora.com")
                .perfil(Perfil.ROLE_ADMIN)
                .build();

        String tokenGerado = tokenService.gerarToken(usuario);

        // Act
        String emailExtraido = tokenService.validarToken(tokenGerado);

        // Assert
        assertEquals("carlos@transportadora.com", emailExtraido);
    }

    @Test
    @DisplayName("3. Deve retornar string vazia ao tentar validar um token forjado ou inválido")
    void deveRetornarVazioParaTokenInvalido() {
        // Arrange
        String tokenInvalido = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.tokenForjadoInvalido123";

        // Act
        String emailExtraido = tokenService.validarToken(tokenInvalido);

        // Assert
        assertEquals("", emailExtraido);
    }
}