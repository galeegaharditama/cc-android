package com.galeegaharditama.cc.domain.model

data class Pasien(
  val name: String,
  val medicalRecord: String,
  val tglLahir: String,
  val alamat: String,
  val suku: String?,
  val pekerjaan: String?,
  val pendidikan: String?,
  val agama: String?
) {
  var umur: Int = 0
}
