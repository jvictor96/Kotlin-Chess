package dev.jvictor.chess.http

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class MoveRequest @JsonCreator constructor(
    @field:NotBlank(message = "required request body: movement")
    @field:Size(min = 2, max = 7, message = "invalid movement notation")
    @JsonProperty("movement")
    val movement: String
)