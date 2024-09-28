package com.riquetti.ProjetoIntegrador.exception;

/**
 * Exceção personalizada para lidar com cenários em que entradas inválidas são fornecidas ao sistema.
 */
public class InvalidInputException extends RuntimeException {
    /**
     * Construtor que recebe uma mensagem explicando o motivo do erro
     * @param message nomeDoParametro Descrição do que esse parâmetro representa ou como ele deve ser usado.
     */
    public InvalidInputException(String message) {
        super(message);
    }
}
