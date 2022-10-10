package com.br.gerenciamentovotacao.exception

class NotFoundException(override val message: String) : RuntimeException(message)  {
}