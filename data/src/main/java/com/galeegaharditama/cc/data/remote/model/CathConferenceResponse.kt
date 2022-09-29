package com.galeegaharditama.cc.data.remote.model

import com.galeegaharditama.cc.domain.model.Approval
import com.galeegaharditama.cc.domain.model.CathConference
import com.galeegaharditama.cc.domain.model.Dpjp
import com.galeegaharditama.cc.domain.model.FileLab
import com.galeegaharditama.cc.domain.model.Klinis
import com.galeegaharditama.cc.domain.model.Pasien
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CathConferenceResponse(
  val id: Int,
  val alamat: String,
  val suku: String?,
  val pekerjaan: String?,
  val pendidikan: String?,
  val agama: String?,
  val anamnesis: String?,
  val diagnosis: String,
  val approval: Approval?,
  val alasan: String?,
  @Json(name = "tgl_cc") val tglCc: String,
  @Json(name = "dpjp_utama") val dpjpUtama: Dpjp,
  @Json(name = "dpjp_tindakan") val dpjpTindakan: Dpjp?,
  @Json(name = "nama_pasien") val namaPasien: String,
  @Json(name = "no_rekam_medik") val noRekamMedik: String,
  @Json(name = "tgl_lahir") val tglLahir: String,
  @Json(name = "pemeriksaan_fisik") val pemeriksaanFisik: String?,
  @Json(name = "hasil_lab") val hasilLab: String?,
  @Json(name = "tgl_rencana_tindakan") val tglRencanaTindakan: String?,
  @Json(name = "tgl_rencana_tindakan_akhir") val tglRencanaTindakanAkhir: String?,
  @Json(name = "created_at") val createdAt: String?,
  @Json(name = "updated_at") val updatedAt: String?,
  @Json(name = "link_export_ppt") val linkExportPpt: String?,
  @Json(name = "link_export_pdf") val linkExportPdf: String?,
  @Json(name = "hasil_elektrokardiogram") val hasilElektrokardiogram: List<FileUploaded>,
  @Json(name = "hasil_rontgen") val hasilRontgen: List<FileUploaded>,
  @Json(name = "hasil_echocardiogram") val hasilEchocardiogram: List<FileUploaded>,
  @Json(name = "hasil_penunjang") val hasilPenunjang: List<FileUploaded>,
) {
  data class Dpjp(
    val id: Int,
    @Json(name = "nama_dpjp") val name: String
  )

  data class Approval(
    val slug: String,
    val name: String
  )

  data class FileUploaded(
    val id: String?,
    val name: String?,
    val path: String?,
    val size: Int?,
    val laporan: String?
  )
}

fun CathConferenceResponse.toDomain() = CathConference(
  id = this.id,
  dateCreated = this.tglCc,
  dpjpUtama = Dpjp(
    id = this.dpjpUtama.id, name = this.dpjpUtama.name
  ),
  dpjpTindakan = this.dpjpTindakan?.let {
    Dpjp(id = it.id, name = it.name)
  },
  pasien = Pasien(
    name = this.namaPasien,
    medicalRecord = this.noRekamMedik,
    tglLahir = this.tglLahir,
    alamat = this.alamat,
    suku = this.suku,
    pekerjaan = this.pekerjaan,
    pendidikan = this.pendidikan,
    agama = this.agama
  ),
  klinis = Klinis(
    anamnesis = this.anamnesis,
    pemeriksaanFisik = this.pemeriksaanFisik,
    hasilLaboratorium = this.hasilLab,
    diagnosis = this.diagnosis,
    hasilElektrokardiogram = this.hasilElektrokardiogram.map { it.toDomain() },
    hasilRontgen = this.hasilRontgen.map { it.toDomain() },
    hasilEchocardiogram = this.hasilEchocardiogram.map { it.toDomain() },
    hasilPenunjangLain = this.hasilPenunjang.map { it.toDomain() }
  ),
  alasan = this.alasan,
  dateAction = this.tglRencanaTindakan,
  dateActionEnd = this.tglRencanaTindakanAkhir,
  statusApproval = this.approval?.let {
    Approval(slug = it.slug, name = it.name)
  }
)

fun List<CathConferenceResponse>.toDomains() = this.map { it.toDomain() }

private fun CathConferenceResponse.FileUploaded.toDomain(): FileLab = FileLab(
  id = this.id?.toIntOrNull() ?: 0,
  title = this.name,
  path = this.path ?: "",
  size = this.size ?: 0,
  laporan = this.laporan
)
