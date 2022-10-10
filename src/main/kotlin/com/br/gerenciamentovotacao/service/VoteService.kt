package com.br.gerenciamentovotacao.service

import com.br.gerenciamentovotacao.client.cpfvalidator.CpfValidatorClient
import com.br.gerenciamentovotacao.model.VoteDocument
import com.br.gerenciamentovotacao.repository.VoteRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class VoteService(
    val voteRepository: VoteRepository,
    val agendaService: AgendaService,
    val cpfValidatorClient: CpfValidatorClient
) {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    fun doVote(voteDocument: VoteDocument, associatedCpf: String): Mono<VoteDocument> {
        return cpfValidatorClient.validate(associatedCpf)
            .doOnSubscribe { }
            .doOnSuccess { logger.info("m=doVote call to cpf validator completed cpf=${associatedCpf} status=${it.status}") }
            .onErrorResume {
                logger.error("failed to call cpf validator")
                Mono.error(it)
            }.flatMap { agendaService.find(voteDocument.agendaId) }
            .flatMap { voteRepository.insert(voteDocument) }
            .doOnSuccess { logger.info("m=doVote vote with success id=${it.id}") }
            .doOnError { logger.info("m=doVote failed messageException=${it.localizedMessage}") }
    }

}