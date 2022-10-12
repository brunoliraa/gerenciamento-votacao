package com.br.gerenciamentovotacao.repository

import com.br.gerenciamentovotacao.model.AgendaDocument
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import java.util.UUID

@Repository
interface AgendaRepository: ReactiveMongoRepository<AgendaDocument, UUID>{
    fun findTop100ByActiveTrue(): Flux<AgendaDocument>
}