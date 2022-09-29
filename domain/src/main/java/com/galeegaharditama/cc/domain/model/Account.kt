package com.galeegaharditama.cc.domain.model

data class Account(
  val id: Int,
  val email: String,
  val firstName: String?,
  val lastName: String?,
  val level: String?,
  val verified: Boolean?,
)
