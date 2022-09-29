package com.galeegaharditama.cc.data.remote.source

import com.galeegaharditama.cc.data.remote.base.ICathConferenceRemoteDataSource
import com.galeegaharditama.cc.data.remote.model.CathConferenceResponse
import com.galeegaharditama.cc.data.remote.model.DpjpResponse
import com.galeegaharditama.cc.data.remote.model.KeputusanResponse
import com.galeegaharditama.cc.data.remote.model.SubmitResponse
import com.galeegaharditama.cc.data.remote.service.ApiService
import com.galeegaharditama.cc.domain.model.FileLab
import com.galih.library.Output
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

internal class CathConferenceRemoteDataSourceImpl(
  private val service: ApiService
) : ICathConferenceRemoteDataSource {

  override suspend fun get(): Output<List<CathConferenceResponse>> {
    return networkHandler { service.getByLimit(20) }
  }

  override suspend fun get(id: Int): Output<CathConferenceResponse> {
    return networkHandler { service.getById(id) }
  }

  override suspend fun get(keyword: String, page: Int): Output<List<CathConferenceResponse>> {
    return networkHandler { service.getByKeyword(keyword = keyword, offset = page) }
  }

  override suspend fun getKeputusan(): Output<List<KeputusanResponse>> {
    return networkHandler { service.getKeputusan() }
  }

  override suspend fun getDpjpUtama(): Output<List<DpjpResponse>> {
    return networkHandler { service.getDpjpUtama() }
  }

  override suspend fun getDpjpTindakan(): Output<List<DpjpResponse>> {
    return networkHandler { service.getDpjpTindakan() }
  }

  override suspend fun submit(
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
  ): Output<SubmitResponse> {
    return networkHandler {
      service.submitForm(
        idCc = idCc.toRequestBody(MultipartBody.FORM),
        tglCc = tglCc.toRequestBody(MultipartBody.FORM),
        dpjpUtama = dpjpUtama.toRequestBody(MultipartBody.FORM),
        dpjpTindakan = dpjpTindakan?.toRequestBody(MultipartBody.FORM),
        namaPasien = namaPasien.toRequestBody(MultipartBody.FORM),
        noRekamMedik = noRekamMedik.toRequestBody(MultipartBody.FORM),
        tglLahir = tglLahir.toRequestBody(MultipartBody.FORM),
        alamat = alamat.toRequestBody(MultipartBody.FORM),
        suku = suku?.toRequestBody(MultipartBody.FORM),
        pekerjaan = pekerjaan?.toRequestBody(MultipartBody.FORM),
        pendidikan = pendidikan?.toRequestBody(MultipartBody.FORM),
        agama = agama.toRequestBody(MultipartBody.FORM),
        anamnesis = anamnesis?.toRequestBody(MultipartBody.FORM),
        pemeriksaanFisik = pemeriksaanFisik?.toRequestBody(MultipartBody.FORM),
        hasilLab = hasilLab?.toRequestBody(MultipartBody.FORM),
        diagnosis = diagnosis?.toRequestBody(MultipartBody.FORM),
      )
    }
  }

  override suspend fun submit(
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
  ): Output<SubmitResponse> {
    return networkHandler {
      service.submitForm(
        tglCc = tglCc.toRequestBody(MultipartBody.FORM),
        dpjpUtama = dpjpUtama.toRequestBody(MultipartBody.FORM),
        dpjpTindakan = dpjpTindakan?.toRequestBody(MultipartBody.FORM),
        namaPasien = namaPasien.toRequestBody(MultipartBody.FORM),
        noRekamMedik = noRekamMedik.toRequestBody(MultipartBody.FORM),
        tglLahir = tglLahir.toRequestBody(MultipartBody.FORM),
        alamat = alamat.toRequestBody(MultipartBody.FORM),
        suku = suku?.toRequestBody(MultipartBody.FORM),
        pekerjaan = pekerjaan?.toRequestBody(MultipartBody.FORM),
        pendidikan = pendidikan?.toRequestBody(MultipartBody.FORM),
        agama = agama.toRequestBody(MultipartBody.FORM),
        anamnesis = anamnesis?.toRequestBody(MultipartBody.FORM),
        pemeriksaanFisik = pemeriksaanFisik?.toRequestBody(MultipartBody.FORM),
        hasilLab = hasilLab?.toRequestBody(MultipartBody.FORM),
        diagnosis = diagnosis?.toRequestBody(MultipartBody.FORM),
      )
    }
  }

  override suspend fun uploadFile(
    idCc: String,
    laporanRontgenJson: String?,
    hasilElektrokardiogram: List<FileLab>?,
    hasilRontgen: List<FileLab>?,
    hasilEchocardiogram: List<FileLab>?,
    hasilPenunjang: List<FileLab>?
  ): Output<SubmitResponse> {
    return networkHandler {
      service.uploadFile(
        idCc = idCc.toRequestBody(MultipartBody.FORM),
        laporanRontgenJson = laporanRontgenJson?.toRequestBody(),
        hasilElektrokardiogram = hasilElektrokardiogram?.let { list ->
          list.map {
            val file = File(it.path)
            MultipartBody.Part.createFormData(
              ELEKTROKARDIOGRAM_FORM_NAME,
              file.name,
              file.asRequestBody()
            )
          }
        },
        hasilRontgen = hasilRontgen?.let { list ->
          list.map {
            val file = File(it.path)
            MultipartBody.Part.createFormData(
              RONTGEN_FORM_NAME,
              file.name,
              file.asRequestBody()
            )
          }
        },
        hasilEchocardiogram = hasilEchocardiogram?.let { list ->
          list.map {
            val file = File(it.path)
            MultipartBody.Part.createFormData(
              ECHOCARDIOGRAM_FORM_NAME,
              file.name,
              file.asRequestBody()
            )
          }
        },
        hasilPenunjang = hasilPenunjang?.let { list ->
          list.map {
            val file = File(it.path)
            MultipartBody.Part.createFormData(
              PENUNJANG_FORM_NAME,
              file.name,
              file.asRequestBody()
            )
          }
        }
      )
    }
  }

  companion object {
    private const val ELEKTROKARDIOGRAM_FORM_NAME = "hasil_elektrokardiogram[]"
    private const val RONTGEN_FORM_NAME = "hasil_rontgen[]"
    private const val ECHOCARDIOGRAM_FORM_NAME = "hasil_echocardiogram[]"
    private const val PENUNJANG_FORM_NAME = "hasil_penunjang[]"
  }
}
