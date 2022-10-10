package com.br.gerenciamentovotacao.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient

@Configuration
open class WebClientConfiguration {

    @Bean
    open fun webClient(jacksonMessageConverter: MappingJackson2HttpMessageConverter): WebClient {
        return WebClient.builder().clientConnector(ReactorClientHttpConnector(HttpClient.create())).build()
    }


    @Bean
    open fun jacksonMessageConverter(objectMapper: ObjectMapper): MappingJackson2HttpMessageConverter {
        return MappingJackson2HttpMessageConverter(objectMapper)
    }
}