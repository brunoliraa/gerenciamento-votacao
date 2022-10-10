package com.br.gerenciamentovotacao.service

import com.br.gerenciamentovotacao.exception.NotFoundException
import com.br.gerenciamentovotacao.exchange.VotingRequest
import com.br.gerenciamentovotacao.model.AgendaDocument
import com.br.gerenciamentovotacao.repository.AgendaRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import java.time.LocalDateTime
import java.util.UUID

@Service
class AgendaService(private val agendaRepository: AgendaRepository) {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    companion object{
        val DEFAULT_VOTING_TIME = 60L
    }

    fun create(agendaDocument: AgendaDocument): Mono<AgendaDocument> {
        return agendaRepository.insert(agendaDocument)
            .elapsed()
            .doOnSuccess { logger.info("new agenda created m=create responseTime=${it.t1}") }
            .doOnError { logger.info("Failed to created new agenda m=create exceptionMessage=${it.localizedMessage}") }
            .map { it.t2 }
    }

    fun initVoting(votingRequest: VotingRequest): Mono<AgendaDocument> {
        return find(votingRequest.agendaId)
            .flatMap{
                agendaRepository.save(
                    it.copy(
                        active = true,
                        votingTimeInSeconds = votingRequest.votingTimeInSeconds ?: DEFAULT_VOTING_TIME,
                        startVotingAt = LocalDateTime.now()
                    )
                )
            }
            .doOnSuccess {logger.info("m=initVoting init voting for agenda ${it.id} with success")}
            .doOnError { logger.info("m=initVoting failed to init voting exMessage=${it.localizedMessage}") }
    }

    fun find(agendaId: UUID): Mono<AgendaDocument> {
        val init = System.currentTimeMillis()
        logger.info("m=find start consulting an agenda by id ${agendaId}")
        return agendaRepository.findById(agendaId)
            .switchIfEmpty { throw NotFoundException("Agenda with ${agendaId} not found") }
            .doOnSuccess { logger.info("find completed m=find responseTime=${System.currentTimeMillis() - init}") }
            .doOnError { logger.error("m=find failed to find an agenda exMessage=${it.localizedMessage}") }
    }
}