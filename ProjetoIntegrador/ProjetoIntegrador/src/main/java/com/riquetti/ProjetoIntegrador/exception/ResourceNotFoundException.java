package com.riquetti.ProjetoIntegrador.exception;

/**
 * Exceção personalizada que é lançada quando um recurso não é encontrado.
 * Esta exceção estende a classe RuntimeException e fornece
 * dois construtores para personalização de mensagens de erro.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Construtor que aceita uma mensagem de erro.
     *
     * @param message A mensagem que descreve a condição de erro.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Construtor que aceita uma mensagem de erro e uma causa subjacente.
     *
     * @param message A mensagem que descreve a condição de erro.
     * @param cause A causa subjacente da exceção.
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
