package com.galeegaharditama.cc.data.repository

import com.galeegaharditama.cc.data.remote.base.ICathConferenceRemoteDataSource
import com.galeegaharditama.cc.data.remote.model.toDomain
import com.galeegaharditama.cc.data.remote.model.toDomains
import com.galeegaharditama.cc.domain.model.Approval
import com.galeegaharditama.cc.domain.model.CathConference
import com.galeegaharditama.cc.domain.model.Dpjp
import com.galeegaharditama.cc.domain.repository.ICathConferenceRepository
import com.galih.library.Output
import com.galih.library.onFailure
import com.galih.library.onSuccessWithTransform

internal class CathConferenceRepositoryImpl(
  private val remote: ICathConferenceRemoteDataSource,
) : ICathConferenceRepository {
  override suspend fun getAll(): Output<List<CathConference>> {
    val result = remote.get()

    return result.onSuccessWithTransform {
      it.toDomains()
    }.onFailure {
      Output.failure<List<CathConference>>(fail = it)
    }
  }

  override suspend fun get(id: Int): Output<CathConference> {
    val result = remote.get(id)

    return result.onSuccessWithTransform {
      it.toDomain()
    }.onFailure {
      Output.failure<CathConference>(fail = it)
    }
  }

  override suspend fun get(keyword: String, page: Int): Output<List<CathConference>> {
    val result = remote.get()

    return result.onSuccessWithTransform { responses ->
      responses.toDomains()
    }.onFailure {
      Output.failure<List<CathConference>>(fail = it)
    }
  }

  override suspend fun getKeputusan(): Output<List<Approval>> {
    val result = remote.getKeputusan()

    return result.onSuccessWithTransform {
      it.toDomains()
    }.onFailure {
      Output.failure<List<Approval>>(fail = it)
    }
  }

  override suspend fun getDpjpUtama(): Output<List<Dpjp>> {
    val result = remote.getDpjpUtama()

    return result.onSuccessWithTransform {
      it.toDomains()
    }.onFailure {
      Output.failure<List<Dpjp>>(fail = it)
    }
  }

  override suspend fun getDpjpTindakan(): Output<List<Dpjp>> {
    val result = remote.getDpjpTindakan()

    return result.onSuccessWithTransform {
      it.toDomains()
    }.onFailure {
      Output.failure<List<Dpjp>>(fail = it)
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
  ): Output<String> {
    val result = remote.submit(
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

    return result
      .onSuccessWithTransform { it.idCc ?: "" }
      .onFailure {
        Output.failure<String>(fail = it)
      }
  }

  override suspend fun update(
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
  ): Output<String> {
    val result = remote.submit(
      id.toString(),
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

    return result
      .onSuccessWithTransform { it.idCc ?: "" }
      .onFailure {
        Output.failure<String>(fail = it)
      }
  }
}
