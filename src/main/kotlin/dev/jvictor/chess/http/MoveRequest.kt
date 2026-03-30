package dev.jvictor.chess.http

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class MoveRequest(
    @field:NotBlank(message = "required request body: movement")
    @field:Size(min = 2, max = 7, message = "invalid movement notation")
    val movement: String,
)