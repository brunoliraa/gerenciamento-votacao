package com.br.gerenciamentovotacao.service

import com.br.gerenciamentovotacao.model.AgendaDocument
import com.br.gerenciamentovotacao.repository.AgendaRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class AgendaService(private val agendaRepository: AgendaRepository) {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    fun create(agendaDocument: AgendaDocument): Mono<AgendaDocument> {
        return agendaRepository.insert(agendaDocument)
            .elapsed()
            .doOnSuccess{ logger.info("new agenda created m=create responseTime=${it.t1}") }
            .doOnError{ logger.info("Failed to created new agenda m=create exceptionMessage=${it.localizedMessage}")}
            .map { it.t2 }
    }
}