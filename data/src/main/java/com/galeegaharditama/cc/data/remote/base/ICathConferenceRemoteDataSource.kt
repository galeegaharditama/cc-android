package com.galeegaharditama.cc.data.remote.base

import com.galeegaharditama.cc.data.remote.model.CathConferenceResponse
import com.galeegaharditama.cc.data.remote.model.DpjpResponse
import com.galeegaharditama.cc.data.remote.model.KeputusanResponse
import com.galeegaharditama.cc.data.remote.model.SubmitResponse
import com.galeegaharditama.cc.domain.model.FileLab
import com.galih.library.Output

interface ICathConferenceRemoteDataSource {
  suspend fun get(): Output<List<CathConferenceResponse>>
  suspend fun get(id: Int): Output<CathConferenceResponse>
  suspend fun get(keyword: String, page: Int): Output<List<CathConferenceResponse>>

  suspend fun submit(
    idCc: String,
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
  ): Output<SubmitResponse>

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
  ): Output<SubmitResponse>

  suspend fun uploadFile(
    idCc: String,
    laporanRontgenJson: String? = null,
    hasilElektrokardiogram: List<FileLab>?,
    hasilRontgen: List<FileLab>?,
    hasilEchocardiogram: List<FileLab>?,
    hasilPenunjang: List<FileLab>?,
  ): Output<SubmitResponse>

  suspend fun getKeputusan(): Output<List<KeputusanResponse>>
  suspend fun getDpjpUtama(): Output<List<DpjpResponse>>
  suspend fun getDpjpTindakan(): Output<List<DpjpResponse>>
}
