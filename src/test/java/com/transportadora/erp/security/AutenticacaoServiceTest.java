package com.transportadora.erp.security;

import com.transportadora.erp.model.Perfil;
import com.transportadora.erp.model.Usuario;
import com.transportadora.erp.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AutenticacaoServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository; // Cria um banco de dados simulado (falso)

    @InjectMocks
    private AutenticacaoService autenticacaoService; // Injeta o repositório falso no serviço

    @Test
    @DisplayName("1. Deve retornar UserDetails quando o e-mail for encontrado no banco")
    void deveCarregarUsuarioPorEmailComSucesso() {
        // Arrange (Ensina o Mockito o que o banco falso deve responder)
        String email = "admin@transportadora.com";
        Usuario usuarioFalso = Usuario.builder()
                .nome("Admin Teste")
                .email(email)
                .senha("123456")
                .perfil(Perfil.ROLE_ADMIN)
                .build();

        when(usuarioRepository.findByEmail(email)).thenReturn(usuarioFalso);

        // Act
        UserDetails userDetails = autenticacaoService.loadUserByUsername(email);

        // Assert
        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        
        // Verifica se o método do repositório foi chamado exatamente 1 vez
        verify(usuarioRepository, times(1)).findByEmail(email);
    }

    @Test
    @DisplayName("2. Deve lançar exceção UsernameNotFoundException quando o e-mail não existir")
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        // Arrange
        String emailInexistente = "naoexiste@transportadora.com";
        when(usuarioRepository.findByEmail(emailInexistente)).thenReturn(null);

        // Act & Assert (Verifica se o código lança o erro esperado)
        assertThrows(UsernameNotFoundException.class, () -> {
            autenticacaoService.loadUserByUsername(emailInexistente);
        });

        verify(usuarioRepository, times(1)).findByEmail(emailInexistente);
    }
}