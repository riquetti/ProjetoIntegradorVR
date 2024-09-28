package com.riquetti.ProjetoIntegrador.dto;

import java.math.BigDecimal;

/**
 * DTO que representa os dados de renda e número de moradores
 * por comércio dentro do raio de ação (dados IBGE).
 *
 * @param idComercio            Representa o identificador único de um comércio.
 * @param nome                  Nome atribuído ao comércio.
 * @param localizacaoTexto      Localização em formato texto (WKT).
 *                              Contém a representação da localização do comércio no
 *                              formato Well-Known Text (WKT) "POINT(-47.3990964 -22.5692409)".
 * @param raioAcaoMetros        Raio de ação em metros (área de influência do comércio).
 *                              Define o alcance em metros ao redor do comércio para fins de análise geoespacial.
 * @param rendaMediaIbge2010    Renda média por setor censitário, de acordo com o IBGE em 2010.
 *                              Representa a renda média dos residentes dentro do raio de ação ao comércio.
 * @param moradoresIbge2010     Número de moradores por setor censitário, de acordo com o IBGE em 2010.
 *                              Soma do número de moradores dentro do raio de ação do comércio.
 */
public record ComercioDadosIbgeDTO(

        Long idComercio,
        String nome,
        String localizacaoTexto,
        Long raioAcaoMetros,
        BigDecimal rendaMediaIbge2010,
        BigDecimal moradoresIbge2010

) {
}
