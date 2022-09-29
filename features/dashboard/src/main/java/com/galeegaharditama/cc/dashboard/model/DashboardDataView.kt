package com.galeegaharditama.cc.dashboard.model

import com.galeegaharditama.cc.domain.model.Approval
import com.galeegaharditama.cc.domain.model.CathConference
import java.util.*

internal data class DashboardAppBarDataView(
  val id: Int,
  val name: String,
  val level: String,
)

internal data class DashboardItemDataView(
  val id: Int,
  val name: String,
  val rekamMedik: String,
  val umur: Int,
  val agama: String,
  val tglCathConference: String,
  val tglAction: String? = null,
  val diagnosis: String,
  val dpjpUtama: String,
  val dpjpTindakan: String,
  val approval: Approval?
)

internal fun CathConference.toDashboardDataView() =
  DashboardItemDataView(
    id = this.id,
    name = this.pasien.name,
    agama = this.pasien.agama ?: "-",
    rekamMedik = this.pasien.medicalRecord,
    approval = this.statusApproval,
    diagnosis = this.klinis.diagnosis,
    umur = calculateAge(this.pasien.tglLahir),
    tglCathConference = this.dateCreated,
    tglAction = this.dateAction,
    dpjpUtama = this.dpjpUtama.name,
    dpjpTindakan = this.dpjpTindakan?.name ?: "-"
  )

internal fun List<CathConference>.toDashboardDataViews() = this.map { it.toDashboardDataView() }

// tglLahir with the format "yyyy-MM-dd"
private fun calculateAge(tglLahir: String): Int {
  val tahunLahir = tglLahir.split("-").first()
  return Calendar.getInstance().get(Calendar.YEAR).minus(tahunLahir.toIntOrNull() ?: 0)
}
