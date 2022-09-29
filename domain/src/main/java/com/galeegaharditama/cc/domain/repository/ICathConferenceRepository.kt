package com.galeegaharditama.cc.domain.repository

import com.galeegaharditama.cc.domain.model.Approval
import com.galeegaharditama.cc.domain.model.CathConference
import com.galeegaharditama.cc.domain.model.Dpjp
import com.galih.library.Output

interface ICathConferenceRepository {
  suspend fun getAll(): Output<List<CathConference>>
  suspend fun get(id: Int): Output<CathConference>
  suspend fun get(keyword: String, page: Int): Output<List<CathConference>>

  suspend fun submit(
    tglCc: String,
    dpjpUtama: String,
    dpjpTindakan: String?,
    namaPasien: String,
    noRekamMedik: String,
    tglLahir: String,
    alamat: String,
    suku: String?,
    pekerjaan: String?,
    pendidikan: String?,
    agama: String,
    anamnesis: String?,
    pemeriksaanFisik: String?,
    hasilLab: String?,
    diagnosis: String?
  ): Output<String>

  suspend fun update(
    id: Int,
    tglCc: String,
    dpjpUtama: String,
    dpjpTindakan: String?,
    namaPasien: String,
    noRekamMedik: String,
    tglLahir: String,
    alamat: String,
    suku: String?,
    pekerjaan: String?,
    pendidikan: String?,
    agama: String,
    anamnesis: String?,
    pemeriksaanFisik: String?,
    hasilLab: String?,
    diagnosis: String?
  ): Output<String>

  suspend fun getKeputusan(): Output<List<Approval>>
  suspend fun getDpjpUtama(): Output<List<Dpjp>>
  suspend fun getDpjpTindakan(): Output<List<Dpjp>>
}
