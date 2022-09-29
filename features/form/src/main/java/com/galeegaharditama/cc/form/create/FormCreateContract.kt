package com.galeegaharditama.cc.form.create

import com.galeegaharditama.cc.domain.model.Dpjp
import com.galeegaharditama.cc.form.model.FileUploadDataView
import com.galih.library.IOutputError
import com.galih.library.validation.ErrorValidationType
import java.io.File

internal sealed class FormCreateAction {
  object Load : FormCreateAction()

  data class ChangeDateCathConference(val value: String) : FormCreateAction()
  data class ChangeDpjpUtama(val value: Dpjp) : FormCreateAction()
  data class ChangeDpjpTindakan(val value: Dpjp) : FormCreateAction()
  data class ChangeName(val value: String) : FormCreateAction()
  data class ChangeMedicalRecord(val value: String) : FormCreateAction()
  data class ChangeBirthday(val value: String) : FormCreateAction()
  data class ChangeAddress(val value: String) : FormCreateAction()
  data class ChangeSuku(val value: String) : FormCreateAction()
  data class ChangeWork(val value: String) : FormCreateAction()
  data class ChangeLastEducation(val value: String) : FormCreateAction()
  data class ChangeReligion(val value: String) : FormCreateAction()
  data class ChangeAnamnesis(val value: String) : FormCreateAction()
  data class ChangePhysicalExamination(val value: String) : FormCreateAction()
  data class ChangeLabResult(val value: String) : FormCreateAction()
  data class ChangeDiagnosis(val value: String) : FormCreateAction()
  data class AddElektrokardiogram(val value: File) : FormCreateAction()
  data class RemoveElektrokardiogram(val value: FileUploadDataView) : FormCreateAction()
  data class AddRontgenThorax(val value: File) : FormCreateAction()
  data class RemoveRontgenThorax(val value: FileUploadDataView) : FormCreateAction()
  data class AddEchocardiogram(val value: File) : FormCreateAction()
  data class RemoveEchocardiogram(val value: FileUploadDataView) : FormCreateAction()
  data class AddPenunjang(val value: File) : FormCreateAction()
  data class RemovePenunjang(val value: FileUploadDataView) : FormCreateAction()

  object ClickSubmit : FormCreateAction()
}

internal data class FormCreateState(
  val isLoadingContent: Boolean = false,
  val isLoadingSubmit: Boolean = false,
  val listDpjpUtama: List<Dpjp> = listOf(),
  val listDpjpTindakan: List<Dpjp> = listOf(),
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
  val religion: String = "",
  val religionValidation: ErrorValidationType? = null,
  val anamnesis: String = "",
  val anamnesisValidation: ErrorValidationType? = null,
  val physicalExamination: String = "",
  val physicalExaminationValidation: ErrorValidationType? = null,
  val labResult: String = "",
  val labResultValidation: ErrorValidationType? = null,
  val diagnosis: String = "",
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

internal sealed class FormCreateEffect {
  object SuccessSubmit : FormCreateEffect()
  data class ShowErrorMessage(
    val errorType: IOutputError?,
    val message: String
  ) : FormCreateEffect()
  object DataNotFound : FormCreateEffect()
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
