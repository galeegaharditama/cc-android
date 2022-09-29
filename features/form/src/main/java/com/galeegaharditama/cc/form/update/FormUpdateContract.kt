package com.galeegaharditama.cc.form.update

import com.galeegaharditama.cc.domain.model.CathConference
import com.galeegaharditama.cc.domain.model.Dpjp
import com.galeegaharditama.cc.form.model.FileUploadDataView
import com.galeegaharditama.cc.form.model.toDataViews
import com.galih.library.IOutputError
import com.galih.library.extension.toDateTime
import com.galih.library.extension.toTimeMillis
import com.galih.library.validation.ErrorValidationType
import java.io.File
import java.util.*

internal sealed class FormUpdateAction {
  data class Load(val id: Int) : FormUpdateAction()

  data class ChangeDateCathConference(val value: String) : FormUpdateAction()
  data class ChangeDpjpUtama(val value: Dpjp) : FormUpdateAction()
  data class ChangeDpjpTindakan(val value: Dpjp) : FormUpdateAction()
  data class ChangeName(val value: String) : FormUpdateAction()
  data class ChangeMedicalRecord(val value: String) : FormUpdateAction()
  data class ChangeBirthday(val value: String) : FormUpdateAction()
  data class ChangeAddress(val value: String) : FormUpdateAction()
  data class ChangeSuku(val value: String) : FormUpdateAction()
  data class ChangeWork(val value: String) : FormUpdateAction()
  data class ChangeLastEducation(val value: String) : FormUpdateAction()
  data class ChangeReligion(val value: String) : FormUpdateAction()
  data class ChangeAnamnesis(val value: String) : FormUpdateAction()
  data class ChangePhysicalExamination(val value: String) : FormUpdateAction()
  data class ChangeLabResult(val value: String) : FormUpdateAction()
  data class ChangeDiagnosis(val value: String) : FormUpdateAction()
  data class AddElektrokardiogram(val value: File) : FormUpdateAction()
  data class RemoveElektrokardiogram(val value: FileUploadDataView) : FormUpdateAction()
  data class AddRontgenThorax(val value: File) : FormUpdateAction()
  data class RemoveRontgenThorax(val value: FileUploadDataView) : FormUpdateAction()
  data class AddEchocardiogram(val value: File) : FormUpdateAction()
  data class RemoveEchocardiogram(val value: FileUploadDataView) : FormUpdateAction()
  data class AddPenunjang(val value: File) : FormUpdateAction()
  data class RemovePenunjang(val value: FileUploadDataView) : FormUpdateAction()

  object ClickSubmit : FormUpdateAction()
}

internal data class FormUpdateState(
  val isLoadingContent: Boolean = false,
  val isLoadingSubmit: Boolean = false,
  val listDpjpUtama: List<Dpjp> = listOf(),
  val listDpjpTindakan: List<Dpjp> = listOf(),
  val id: Int = 0,
  val dateCathConference: String = "",
  val dateCathConferenceValidation: ErrorValidationType? = null,
  val dpjpUtama: Dpjp = Dpjp(0, ""),
  val dpjpUtamaValidation: ErrorValidationType? = null,
  val dpjpTindakan: Dpjp? = null,
  val name: String = "",
  val nameValidation: ErrorValidationType? = null,
  val medicalRecord: String = "",
  val medicalRecordValidation: ErrorValidationType? = null,
  val birthday: String = "",
  val birthdayValidation: ErrorValidationType? = null,
  val age: Int? = null,
  val address: String = "",
  val addressValidation: ErrorValidationType? = null,
  val suku: String? = null,
  val work: String? = null,
  val lastEducation: String? = null,
  val religion: String? = "",
  val religionValidation: ErrorValidationType? = null,
  val anamnesis: String? = "",
  val anamnesisValidation: ErrorValidationType? = null,
  val physicalExamination: String? = "",
  val physicalExaminationValidation: ErrorValidationType? = null,
  val labResult: String? = "",
  val labResultValidation: ErrorValidationType? = null,
  val diagnosis: String? = "",
  val diagnosisValidation: ErrorValidationType? = null,
  val hasilElektrokardiogram: List<FileUploadDataView> = listOf(),
  val hasilElektrokardiogramValidation: ErrorValidationType? = null,
  val hasilRontgenThorax: List<FileUploadDataView> = listOf(),
  val hasilRontgenThoraxValidation: ErrorValidationType? = null,
  val hasilEchocardiogram: List<FileUploadDataView> = listOf(),
  val hasilEchocardiogramValidation: ErrorValidationType? = null,
  val hasilPenunjang: List<FileUploadDataView> = listOf(),
) {
  val isFormValid: Boolean
    get() = dateCathConferenceValidation == ErrorValidationType.VALID &&
      dpjpUtamaValidation == ErrorValidationType.VALID &&
      nameValidation == ErrorValidationType.VALID &&
      medicalRecordValidation == ErrorValidationType.VALID &&
      addressValidation == ErrorValidationType.VALID &&
      religionValidation == ErrorValidationType.VALID &&
      anamnesisValidation == ErrorValidationType.VALID &&
      physicalExaminationValidation == ErrorValidationType.VALID &&
      labResultValidation == ErrorValidationType.VALID &&
      diagnosisValidation == ErrorValidationType.VALID &&
      hasilElektrokardiogramValidation == ErrorValidationType.VALID &&
      hasilRontgenThoraxValidation == ErrorValidationType.VALID &&
      hasilEchocardiogramValidation == ErrorValidationType.VALID
}

internal sealed class FormUpdateEffect {
  object SuccessSubmit : FormUpdateEffect()
  data class ShowErrorMessage(
    val errorType: IOutputError?,
    val message: String
  ) : FormUpdateEffect()
  object ErrorNotFound : FormUpdateEffect()
  object LostConnection : FormUpdateEffect()
  object ServerError : FormUpdateEffect()
}

data class DataGeneralFormAction(
  val onDateCathConferenceChanged: (String) -> Unit = {},
  val onDpjpUtamaChanged: (Dpjp) -> Unit = {},
  val onDpjpTindakanChanged: (Dpjp) -> Unit = {},
)

data class DataPasienFormAction(
  val onNameChanged: (String) -> Unit = {},
  val onMedicalRecordChanged: (String) -> Unit = {},
  val onBirthdayChanged: (String) -> Unit = {},
  val onAddressChanged: (String) -> Unit = {},
  val onSukuChanged: (String) -> Unit = {},
  val onWorkChanged: (String) -> Unit = {},
  val onLastEducationChanged: (String) -> Unit = {},
  val onReligionChanged: (String) -> Unit = {},
)

data class DataKlinisFormAction(
  val onAnamnesisChanged: (String) -> Unit = {},
  val onPhysicalExaminationChanged: (String) -> Unit = {},
  val onLabResultChanged: (String) -> Unit = {},
  val onDiagnosisChanged: (String) -> Unit = {},
  val onAddElektrokardiogram: (File) -> Unit = {},
  val onRemoveElektrokardiogram: (FileUploadDataView) -> Unit = {},
  val onAddRontgenThorax: (File) -> Unit = {},
  val onRemoveRontgenThorax: (FileUploadDataView) -> Unit = {},
  val onAddEchocardiogram: (File) -> Unit = {},
  val onRemoveEchocardiogram: (FileUploadDataView) -> Unit = {},
  val onAddPenunjang: (File) -> Unit = {},
  val onRemovePenunjang: (FileUploadDataView) -> Unit = {},
)

internal fun CathConference.toState(): FormUpdateState {
  val dateCreated = this.dateCreated.toTimeMillis("yyyy-MM-dd").toDateTime("dd-MM-yyyy")
  val tglLahir = this.pasien.tglLahir.toTimeMillis("yyyy-MM-dd").toDateTime("dd-MM-yyyy")
  return FormUpdateState(
    id = this.id,
    dateCathConference = dateCreated,
    dpjpUtama = this.dpjpUtama,
    dpjpTindakan = this.dpjpTindakan,
    name = this.pasien.name,
    medicalRecord = this.pasien.medicalRecord,
    birthday = tglLahir,
    age = calculateAge(tglLahir),
    address = this.pasien.alamat,
    suku = this.pasien.suku ?: "",
    work = this.pasien.pekerjaan,
    lastEducation = this.pasien.pendidikan,
    religion = this.pasien.agama,
    anamnesis = this.klinis.anamnesis,
    physicalExamination = this.klinis.pemeriksaanFisik,
    labResult = this.klinis.hasilLaboratorium,
    diagnosis = this.klinis.diagnosis,
    hasilElektrokardiogram = this.klinis.hasilElektrokardiogram.toDataViews(),
    hasilRontgenThorax = this.klinis.hasilRontgen.toDataViews(),
    hasilEchocardiogram = this.klinis.hasilEchocardiogram.toDataViews(),
    hasilPenunjang = this.klinis.hasilPenunjangLain.toDataViews(),
  )
}

// tglLahir with the format "dd-MM-yyyy"
private fun calculateAge(tglLahir: String): Int {
  val tahunLahir = tglLahir.split("-").last()
  return Calendar.getInstance().get(Calendar.YEAR).minus(tahunLahir.toIntOrNull() ?: 0)
}
