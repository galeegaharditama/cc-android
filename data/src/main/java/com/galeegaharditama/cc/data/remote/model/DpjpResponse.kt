package com.galeegaharditama.cc.data.remote.model

import com.galeegaharditama.cc.domain.model.Dpjp
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DpjpResponse(
  val id: Int,
  @Json(name = "nama_dpjp") val namaDpjp: String
)

fun DpjpResponse.toDomain() = Dpjp(
  id = this.id, name = this.namaDpjp
)

fun List<DpjpResponse>.toDomains() = this.map { it.toDomain() }
