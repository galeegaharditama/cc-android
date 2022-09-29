package com.galeegaharditama.cc.domain.interactor

import com.galeegaharditama.cc.domain.repository.ICathConferenceRepository

class UpdateCathConference(
  private val repository: ICathConferenceRepository
) {
  suspend operator fun invoke(
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
  ) = repository.update(
    id,
    tglCc,
    dpjpUtama,
    dpjpTindakan,
    namaPasien,
    noRekamMedik,
    tglLahir,
    alamat,
    suku,
    pekerjaan,
    pendidikan,
    agama,
    anamnesis,
    pemeriksaanFisik,
    hasilLab,
    diagnosis
  )
}
