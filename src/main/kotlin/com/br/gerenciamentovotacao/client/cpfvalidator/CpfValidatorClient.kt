package com.br.gerenciamentovotacao.client.cpfvalidator

import com.br.gerenciamentovotacao.client.cpfvalidator.exchange.CpfValidatorResponse
import com.br.gerenciamentovotacao.client.cpfvalidator.exchange.VoteStatus
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class CpfValidatorClient(
    private val webClient: WebClient
) {

    @Value("\${url.app.cpf-validator}")
    private lateinit var uri: String

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    open fun validate(cpf: String): Mono<CpfValidatorResponse> {
        logger.info("m=validate cpf=${cpf}")

        return webClient.get()
            .uri("${uri}/users/${cpf}")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .exchange()
            .elapsed()
            .doOnNext { logger.info("m=validate cpf${cpf} responseTime=${it.t1}") }
            .map { it.t2 }
            .flatMap {
                it.bodyToMono(CpfValidatorResponse::class.java)
            }.filter{
                it.status.equals(VoteStatus.ABLE_TO_VOTE)
            }
    }

}