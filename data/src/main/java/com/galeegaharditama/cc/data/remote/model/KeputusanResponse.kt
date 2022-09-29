package com.galeegaharditama.cc.data.remote.model

import com.galeegaharditama.cc.domain.model.Approval
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class KeputusanResponse(
  val slug: String,
  val name: String
)

fun KeputusanResponse.toDomain() = Approval(
  slug = this.slug, name = this.name
)

fun List<KeputusanResponse>.toDomains() = this.map { it.toDomain() }
