package com.br.gerenciamentovotacao.controller

import com.br.gerenciamentovotacao.exchange.AgendaRequest
import com.br.gerenciamentovotacao.exchange.AgendaResponse
import com.br.gerenciamentovotacao.exchange.VotingRequest
import com.br.gerenciamentovotacao.exchange.VotingResponse
import com.br.gerenciamentovotacao.mapper.toDocument
import com.br.gerenciamentovotacao.mapper.toResponse
import com.br.gerenciamentovotacao.mapper.toVotingResponse
import com.br.gerenciamentovotacao.service.AgendaService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.net.URI

@RestController
@RequestMapping("/v1/agenda")
class AgendaController(private val agendaService: AgendaService) {

    @PostMapping
    fun create(@RequestBody agendaRequest: AgendaRequest) : Mono<ResponseEntity<AgendaResponse>> {
        return agendaService.create(agendaRequest.toDocument()).map {
            ResponseEntity.created(URI.create("")).body(it.toResponse()) }
    }

    @PatchMapping("/init-voting")
    fun initVoting(@RequestBody votingRequest: VotingRequest) : Mono<ResponseEntity<VotingResponse>> {
        return agendaService.initVoting(votingRequest).map {
            ResponseEntity.created(URI.create("")).body(it.toVotingResponse()) }
        }

}