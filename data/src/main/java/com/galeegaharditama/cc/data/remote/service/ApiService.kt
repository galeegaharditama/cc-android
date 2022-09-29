package com.galeegaharditama.cc.data.remote.service

import com.galeegaharditama.cc.data.remote.model.BaseResponse
import com.galeegaharditama.cc.data.remote.model.CathConferenceResponse
import com.galeegaharditama.cc.data.remote.model.DpjpResponse
import com.galeegaharditama.cc.data.remote.model.ErrorResponse
import com.galeegaharditama.cc.data.remote.model.KeputusanResponse
import com.galeegaharditama.cc.data.remote.model.LevelUserResponse
import com.galeegaharditama.cc.data.remote.model.LoginResponse
import com.galeegaharditama.cc.data.remote.model.SubmitResponse
import com.galih.library.remote.NetworkResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

internal interface ApiService {
  @FormUrlEncoded
  @POST("login")
  suspend fun login(
    @Field("email") email: String,
    @Field("password") password: String,
    @Field("firebase") tokenId: String?
  ): NetworkResponse<BaseResponse<LoginResponse>, ErrorResponse>

  @FormUrlEncoded
  @POST("register_user")
  suspend fun register(
    @Field("first_name") firstName: String,
    @Field("last_name") lastName: String,
    @Field("email") email: String,
    @Field("password") password: String,
    @Field("level") level: String
  ): NetworkResponse<BaseResponse<List<String>>, ErrorResponse>

  @GET("level_user")
  suspend fun getLevelUser(): NetworkResponse<BaseResponse<List<LevelUserResponse>>, ErrorResponse>

  @GET("keputusan")
  suspend fun getKeputusan(): NetworkResponse<BaseResponse<List<KeputusanResponse>>, ErrorResponse>

  @GET("dpjp")
  suspend fun getDpjpUtama(): NetworkResponse<BaseResponse<List<DpjpResponse>>, ErrorResponse>

  @GET("dpjp")
  suspend fun getDpjpTindakan(): NetworkResponse<BaseResponse<List<DpjpResponse>>, ErrorResponse>

  @GET("getcc")
  suspend fun getById(
    @Query("id_cc") idCc: Int
  ): NetworkResponse<BaseResponse<CathConferenceResponse>, ErrorResponse>

  @GET("getccall")
  suspend fun getByLimit(
    @Query("limit") limit: Int
  ): NetworkResponse<BaseResponse<List<CathConferenceResponse>>, ErrorResponse>

  @GET("getccall")
  suspend fun getByKeyword(
    @Query("keyword") keyword: String,
    @Query("offset") offset: Int,
  ): NetworkResponse<BaseResponse<List<CathConferenceResponse>>, ErrorResponse>

  @Multipart
  @POST("insertcc")
  suspend fun submitForm(
    @Part("tgl_cc") tglCc: RequestBody,
    @Part("dpjp_utama") dpjpUtama: RequestBody,
    @Part("dpjp_tindakan") dpjpTindakan: RequestBody?,
    @Part("nama_pasien") namaPasien: RequestBody,
    @Part("no_rekam_medik") noRekamMedik: RequestBody,
    @Part("tgl_lahir") tglLahir: RequestBody,
    @Part("alamat") alamat: RequestBody,
    @Part("suku") suku: RequestBody?,
    @Part("pekerjaan") pekerjaan: RequestBody?,
    @Part("pendidikan") pendidikan: RequestBody?,
    @Part("agama") agama: RequestBody?,
    @Part("anamnesis") anamnesis: RequestBody?,
    @Part("pemeriksaan_fisik") pemeriksaanFisik: RequestBody?,
    @Part("hasil_lab") hasilLab: RequestBody?,
    @Part("diagnosis") diagnosis: RequestBody?,
    @Part hasilElektrokardiogram: List<MultipartBody.Part> = listOf(),
    @Part hasilRontgen: List<MultipartBody.Part> = listOf(),
    @Part hasilEchocardiogram: List<MultipartBody.Part> = listOf(),
    @Part hasilPenunjang: List<MultipartBody.Part> = listOf(),
  ): NetworkResponse<BaseResponse<SubmitResponse>, ErrorResponse>

  @Multipart
  @POST("updatecc")
  suspend fun submitForm(
    @Part("id_cc") idCc: RequestBody,
    @Part("tgl_cc") tglCc: RequestBody,
    @Part("dpjp_utama") dpjpUtama: RequestBody,
    @Part("dpjp_tindakan") dpjpTindakan: RequestBody?,
    @Part("nama_pasien") namaPasien: RequestBody,
    @Part("no_rekam_medik") noRekamMedik: RequestBody,
    @Part("tgl_lahir") tglLahir: RequestBody,
    @Part("alamat") alamat: RequestBody,
    @Part("suku") suku: RequestBody?,
    @Part("pekerjaan") pekerjaan: RequestBody?,
    @Part("pendidikan") pendidikan: RequestBody?,
    @Part("agama") agama: RequestBody?,
    @Part("anamnesis") anamnesis: RequestBody?,
    @Part("pemeriksaan_fisik") pemeriksaanFisik: RequestBody?,
    @Part("hasil_lab") hasilLab: RequestBody?,
    @Part("diagnosis") diagnosis: RequestBody?
  ): NetworkResponse<BaseResponse<SubmitResponse>, ErrorResponse>

  @Multipart
  @POST("uploadfilecathconference")
  suspend fun uploadFile(
    @Part("id") idCc: RequestBody,
    @Part("laporan_rontgen") laporanRontgenJson: RequestBody? = null,
    @Part hasilElektrokardiogram: List<MultipartBody.Part>?,
    @Part hasilRontgen: List<MultipartBody.Part>?,
    @Part hasilEchocardiogram: List<MultipartBody.Part>?,
    @Part hasilPenunjang: List<MultipartBody.Part>?,
  ): NetworkResponse<BaseResponse<SubmitResponse>, ErrorResponse>

  companion object {
    operator fun invoke(retrofit: Retrofit) = retrofit.create<ApiService>()
  }
}
