package com.br.gerenciamentovotacao.model

import com.br.gerenciamentovotacao.model.enums.VoteType
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.UUID

@Document("vote")
@CompoundIndex(def = "{'agendaId': 1, 'associatedId': 1}", unique = true)
data class VoteDocument(
    val id: UUID,
    val createdAt: LocalDateTime,
    val voteType: VoteType,
    val agendaId: UUID,
    val associatedId: UUID
)