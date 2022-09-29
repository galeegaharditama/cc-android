package com.galeegaharditama.cc.domain.model

data class CathConference(
  val id: Int,
  val dateCreated: String, // format date yyyy-MM-dd
  val dpjpUtama: Dpjp,
  val dpjpTindakan: Dpjp?,
  val pasien: Pasien,
  val klinis: Klinis,
  val alasan: String?,
  val dateAction: String?, // format date yyyy-MM-dd
  val dateActionEnd: String?, // format date yyyy-MM-dd
  val statusApproval: Approval?
)
