package com.br.gerenciamentovotacao.repository

import com.br.gerenciamentovotacao.model.AgendaDocument
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface AgendaRepository: ReactiveMongoRepository<AgendaDocument, UUID>{
}