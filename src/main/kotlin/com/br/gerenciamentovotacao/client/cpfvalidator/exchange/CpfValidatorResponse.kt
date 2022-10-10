package com.br.gerenciamentovotacao.client.cpfvalidator.exchange

data class CpfValidatorResponse(
    val status: VoteStatus
)

enum class VoteStatus {
    ABLE_TO_VOTE, UNABLE_TO_VOTE
}
