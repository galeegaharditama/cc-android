package com.galeegaharditama.cc.data.local.model

import com.galeegaharditama.cc.domain.model.Account

data class AccountCache(
  val id: Int,
  val firstName: String?,
  val lastName: String?,
  val email: String,
  val level: String?
)

fun AccountCache.toDomain() = Account(
  id = this.id,
  email = this.email,
  firstName = this.firstName,
  lastName = this.lastName,
  level = this.level,
  verified = true
)
