package com.br.gerenciamentovotacao.repository

import com.br.gerenciamentovotacao.model.VoteDocument
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import java.util.UUID

@Repository
interface VoteRepository: ReactiveMongoRepository<VoteDocument, UUID> {

    fun findAllByAgendaId(agendaId: UUID): Flux<VoteDocument>
}