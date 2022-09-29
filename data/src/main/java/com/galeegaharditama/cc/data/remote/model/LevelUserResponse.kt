package com.galeegaharditama.cc.data.remote.model

import com.galeegaharditama.cc.domain.model.LevelUser
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LevelUserResponse(
  val id: String?,
  val name: String?,
  val slug: String?
)

fun LevelUserResponse.toDomain() = LevelUser(
  name = this.name ?: "",
  slug = this.slug ?: ""
)

fun List<LevelUserResponse>.toDomains() = this.map { it.toDomain() }
