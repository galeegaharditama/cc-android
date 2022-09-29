package com.galeegaharditama.cc.domain.model

data class FileLab(
  val id: Int,
  val title: String?,
  val path: String,
  val size: Int,
  val laporan: String? = null,
)
