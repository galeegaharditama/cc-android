package com.galeegaharditama.cc.domain.model

data class Klinis(
  val anamnesis: String?,
  val pemeriksaanFisik: String?,
  val hasilLaboratorium: String?,
  val diagnosis: String,
  val hasilElektrokardiogram: List<FileLab>,
  val hasilRontgen: List<FileLab>,
  val hasilEchocardiogram: List<FileLab>,
  val hasilPenunjangLain: List<FileLab>
)
