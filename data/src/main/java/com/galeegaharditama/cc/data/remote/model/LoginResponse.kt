package com.galeegaharditama.cc.data.remote.model

import com.galeegaharditama.cc.data.local.model.AccountCache
import com.galeegaharditama.cc.domain.model.Account
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse(
  val email: String,
  val id: String,
  val level: String?,
  val verified: String?,
  @Json(name = "created_at")
  val createdAt: String?,
  @Json(name = "first_name")
  val firstName: String?,
  @Json(name = "last_name")
  val lastName: String?,
  @Json(name = "updated_at")
  val updatedAt: String?
)

fun LoginResponse.toDomain() = Account(
  id = this.id.toInt(),
  email = this.email,
  firstName = this.firstName,
  lastName = this.lastName,
  level = this.level,
  verified = this.verified.toBoolean()
)

fun LoginResponse.toCache() = AccountCache(
  id = this.id.toInt(),
  email = this.email,
  firstName = this.firstName ?: "",
  lastName = this.lastName ?: "",
  level = this.level,
)
