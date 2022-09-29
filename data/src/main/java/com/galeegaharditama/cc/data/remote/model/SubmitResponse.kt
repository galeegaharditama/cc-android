package com.galeegaharditama.cc.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SubmitResponse(
  @Json(name = "id_cc") val idCc: String?
)
