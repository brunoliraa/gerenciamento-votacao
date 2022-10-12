package com.br.gerenciamentovotacao

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class GerenciamentoVotacaoApplication

fun main(args: Array<String>) {
	runApplication<GerenciamentoVotacaoApplication>(*args)
}
