package com.riquetti.ProjetoIntegrador.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Classe de configuração para criar um bean do RestTemplate.
 *
 * O RestTemplate é usado para realizar requisições HTTP em aplicações Spring.
 * Esta classe define um bean do RestTemplate que pode ser injetado em outros
 * componentes e serviços da aplicação, facilitando a comunicação com APIs externas.
 *
 * Ao declarar este bean, o Spring gerencia o ciclo de vida do objeto RestTemplate,
 * garantindo sua reutilização e facilitando a injeção de dependência em qualquer parte da aplicação.
 */
@Configuration

public class RestTemplateConfig {

    /**
     * Define um bean do tipo RestTemplate.
     *
     * O RestTemplate é uma classe que oferece métodos simplificados para interações
     * com APIs RESTful, como GET, POST, PUT e DELETE. Ele pode ser configurado com interceptadores,
     * manipuladores de erros e customizações adicionais.
     *
     * @return uma nova instância do RestTemplate.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
