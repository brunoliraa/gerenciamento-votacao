package com.br.gerenciamentovotacao.schedule

import com.br.gerenciamentovotacao.repository.AgendaRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class AgendaSchedule(val agendaRepository: AgendaRepository) {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Scheduled(fixedDelay = 60_000)
    fun closeVoting() {
        agendaRepository.findTop100ByActiveTrue()
            .toStream()
            .filter {
                LocalDateTime.now().isAfter(it.startVotingAt?.plusSeconds(it.votingTimeInSeconds!!))
            }
            .forEach {
                agendaRepository.save(it.copy(active = false))
                    .doOnSuccess {
                        logger.info("m=close voting agenda=${it}")
                    }
                    .doOnError {
                        logger.error("m=failed to close voting for agenda=${it}")
                    }.subscribe()
            }
    }
}