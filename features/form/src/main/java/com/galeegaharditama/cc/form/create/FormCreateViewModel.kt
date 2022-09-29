package com.galeegaharditama.cc.form.create

import androidx.lifecycle.viewModelScope
import com.galeegaharditama.cc.common.StatefulViewModel
import com.galeegaharditama.cc.form.FormInteractors
import com.galeegaharditama.cc.form.model.FileUploadDataView
import com.galih.library.extension.toDateTime
import com.galih.library.extension.toTimeMillis
import com.galih.library.onFailure
import com.galih.library.onSuccess
import com.galih.library.validation.ErrorValidationType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

internal class FormCreateViewModel(
  private val interactor: FormInteractors,
) : StatefulViewModel<FormCreateState, FormCreateEffect, FormCreateAction, FormInteractors>(
  initialState = FormCreateState(), contract = interactor
) {
  init {
    dispatch(FormCreateAction.Load)
  }

  @SuppressWarnings("LongMethod", "ComplexMethod")
  override fun dispatch(action: FormCreateAction) {
    when (action) {
      FormCreateAction.Load -> {
        onLoadData()
      }
      is FormCreateAction.ChangeDateCathConference -> {
        viewModelScope.launch {
          setState { copy(dateCathConference = action.value) }
        }
      }
      is FormCreateAction.ChangeDpjpUtama -> {
        viewModelScope.launch {
          setState { copy(dpjpUtama = action.value) }
        }
      }
      is FormCreateAction.ChangeDpjpTindakan -> {
        viewModelScope.launch {
          setState { copy(dpjpTindakan = action.value) }
        }
      }
      is FormCreateAction.ChangeName -> {
        viewModelScope.launch {
          setState { copy(name = action.value) }
        }
      }
      is FormCreateAction.ChangeMedicalRecord -> {
        viewModelScope.launch {
          setState { copy(medicalRecord = action.value) }
        }
      }
      is FormCreateAction.ChangeBirthday -> {
        viewModelScope.launch {
          val age = calculateAge(action.value)
          setState { copy(birthday = action.value, age = age) }
        }
      }
      is FormCreateAction.ChangeAddress -> {
        viewModelScope.launch {
          setState { copy(address = action.value) }
        }
      }
      is FormCreateAction.ChangeSuku -> {
        viewModelScope.launch {
          setState { copy(suku = action.value) }
        }
      }
      is FormCreateAction.ChangeLastEducation -> {
        viewModelScope.launch {
          setState { copy(lastEducation = action.value) }
        }
      }
      is FormCreateAction.ChangeReligion -> {
        viewModelScope.launch {
          setState { copy(religion = action.value) }
        }
      }
      is FormCreateAction.ChangeWork -> {
        viewModelScope.launch {
          setState { copy(work = action.value) }
        }
      }
      is FormCreateAction.ChangeAnamnesis -> {
        viewModelScope.launch {
          setState { copy(anamnesis = action.value) }
        }
      }
      is FormCreateAction.ChangePhysicalExamination -> {
        viewModelScope.launch {
          setState { copy(physicalExamination = action.value) }
        }
      }
      is FormCreateAction.ChangeLabResult -> {
        viewModelScope.launch {
          setState { copy(labResult = action.value) }
        }
      }
      is FormCreateAction.ChangeDiagnosis -> {
        viewModelScope.launch {
          setState { copy(diagnosis = action.value) }
        }
      }
      is FormCreateAction.AddElektrokardiogram -> {
        viewModelScope.launch {
          val mutableList = state.value.hasilElektrokardiogram.toMutableList()
          val value = FileUploadDataView(
            id = mutableList.size + 1,
            title = action.value.name,
            path = action.value.path,
            size = action.value.length().toInt(),
            extension = action.value.extension,
            isLocal = true
          )
          mutableList.add(value)
          setState {
            copy(hasilElektrokardiogram = mutableList.toList())
          }
        }
      }
      is FormCreateAction.RemoveElektrokardiogram -> {
        viewModelScope.launch {
          val mutableList = state.value.hasilElektrokardiogram.toMutableList()
          mutableList.remove(action.value)
          setState { copy(hasilElektrokardiogram = mutableList.toList()) }
        }
      }
      is FormCreateAction.AddRontgenThorax -> {
        viewModelScope.launch {
          val mutableList = state.value.hasilRontgenThorax.toMutableList()
          val value = FileUploadDataView(
            id = mutableList.size + 1,
            title = action.value.name,
            path = action.value.path,
            size = action.value.length().toInt(),
            extension = action.value.extension,
            isLocal = true
          )
          mutableList.add(value)
          setState {
            copy(hasilRontgenThorax = mutableList.toList())
          }
        }
      }
      is FormCreateAction.RemoveRontgenThorax -> {
        viewModelScope.launch {
          val mutableList = state.value.hasilRontgenThorax.toMutableList()
          mutableList.remove(action.value)
          setState { copy(hasilRontgenThorax = mutableList.toList()) }
        }
      }
      is FormCreateAction.AddEchocardiogram -> {
        viewModelScope.launch {
          val mutableList = state.value.hasilEchocardiogram.toMutableList()
          val value = FileUploadDataView(
            id = mutableList.size + 1,
            title = action.value.name,
            path = action.value.path,
            size = action.value.length().toInt(),
            extension = action.value.extension,
            isLocal = true
          )
          mutableList.add(value)
          setState {
            copy(hasilEchocardiogram = mutableList.toList())
          }
        }
      }
      is FormCreateAction.RemoveEchocardiogram -> {
        viewModelScope.launch {
          val mutableList = state.value.hasilEchocardiogram.toMutableList()
          mutableList.remove(action.value)
          setState { copy(hasilEchocardiogram = mutableList.toList()) }
        }
      }
      is FormCreateAction.AddPenunjang -> {
        viewModelScope.launch {
          val mutableList = state.value.hasilPenunjang.toMutableList()
          val value = FileUploadDataView(
            id = mutableList.size + 1,
            title = action.value.name,
            path = action.value.path,
            size = action.value.length().toInt(),
            extension = action.value.extension,
            isLocal = true
          )
          mutableList.add(value)
          setState {
            copy(hasilPenunjang = mutableList.toList())
          }
        }
      }
      is FormCreateAction.RemovePenunjang -> {
        viewModelScope.launch {
          val mutableList = state.value.hasilPenunjang.toMutableList()
          mutableList.remove(action.value)
          setState { copy(hasilPenunjang = mutableList.toList()) }
        }
      }
      FormCreateAction.ClickSubmit -> {
        viewModelScope.launch {
          onValidation()

          onSubmit()
        }
      }
    }
  }

  private fun onLoadData() {
    viewModelScope.launch {
      setState { copy(isLoadingContent = true) }

      val loadDpjpUtama = async { interactor.getDpjpUtama() }
      val loadDpjpTindakan = async { interactor.getDpjpTindakan() }

      withContext(Dispatchers.Main) {

        val resultDpjpUtama = loadDpjpUtama.await()
        val resultDpjpTindakan = loadDpjpTindakan.await()

        try {
          resultDpjpUtama.onSuccess {
            if (it.isEmpty()) setEffect(FormCreateEffect.DataNotFound)
            setState { copy(listDpjpUtama = it) }
          }.onFailure {
            setEffect(
              FormCreateEffect.ShowErrorMessage(it.type, it.message ?: "")
            )
          }
          resultDpjpTindakan.onSuccess {
            setState { copy(listDpjpTindakan = it) }
          }
        } finally {
          setState { copy(isLoadingContent = false) }
        }
      }
    }
  }

  private suspend fun onValidation() {
    if (state.value.dateCathConference.isBlank()) {
      setState { copy(dateCathConferenceValidation = ErrorValidationType.EMPTY) }
    } else setState { copy(dateCathConferenceValidation = ErrorValidationType.VALID) }

    if (state.value.dpjpUtama.name.isBlank()) {
      setState { copy(dpjpUtamaValidation = ErrorValidationType.EMPTY) }
    } else setState { copy(dpjpUtamaValidation = ErrorValidationType.VALID) }

    if (state.value.name.isBlank()) {
      setState { copy(nameValidation = ErrorValidationType.EMPTY) }
    } else setState { copy(nameValidation = ErrorValidationType.VALID) }

    if (state.value.medicalRecord.isBlank()) {
      setState { copy(medicalRecordValidation = ErrorValidationType.EMPTY) }
    } else setState { copy(medicalRecordValidation = ErrorValidationType.VALID) }

    if (state.value.birthday.isBlank()) {
      setState { copy(birthdayValidation = ErrorValidationType.EMPTY) }
    } else setState { copy(birthdayValidation = ErrorValidationType.VALID) }

    if (state.value.address.isBlank()) {
      setState { copy(addressValidation = ErrorValidationType.EMPTY) }
    } else setState { copy(addressValidation = ErrorValidationType.VALID) }

    if (state.value.religion.isBlank()) {
      setState { copy(religionValidation = ErrorValidationType.EMPTY) }
    } else setState { copy(religionValidation = ErrorValidationType.VALID) }

    if (state.value.anamnesis.isBlank()) {
      setState { copy(anamnesisValidation = ErrorValidationType.EMPTY) }
    } else setState { copy(anamnesisValidation = ErrorValidationType.VALID) }

    if (state.value.physicalExamination.isBlank()) {
      setState { copy(physicalExaminationValidation = ErrorValidationType.EMPTY) }
    } else setState { copy(physicalExaminationValidation = ErrorValidationType.VALID) }

    if (state.value.labResult.isBlank()) {
      setState { copy(labResultValidation = ErrorValidationType.EMPTY) }
    } else setState { copy(labResultValidation = ErrorValidationType.VALID) }

    if (state.value.diagnosis.isBlank()) {
      setState { copy(diagnosisValidation = ErrorValidationType.EMPTY) }
    } else setState { copy(diagnosisValidation = ErrorValidationType.VALID) }

    if (state.value.hasilElektrokardiogram.isEmpty()) {
      setState { copy(hasilElektrokardiogramValidation = ErrorValidationType.EMPTY) }
    } else setState { copy(hasilElektrokardiogramValidation = ErrorValidationType.VALID) }

    if (state.value.hasilEchocardiogram.isEmpty()) {
      setState { copy(hasilEchocardiogramValidation = ErrorValidationType.EMPTY) }
    } else setState { copy(hasilEchocardiogramValidation = ErrorValidationType.VALID) }

    if (state.value.hasilRontgenThorax.isEmpty()) {
      setState { copy(hasilRontgenThoraxValidation = ErrorValidationType.EMPTY) }
    } else setState { copy(hasilRontgenThoraxValidation = ErrorValidationType.VALID) }
  }

  private suspend fun onSubmit() {
    if (!state.value.isFormValid) return

    setState { copy(isLoadingSubmit = true) }

    val dateCc = state.value.dateCathConference.toTimeMillis("dd-MM-yyyy").toDateTime("yyyy-MM-dd")
    val dateBirthday = state.value.birthday.toTimeMillis("dd-MM-yyyy").toDateTime("yyyy-MM-dd")

    val result =
      interactor.submitCathConference(
        dateCc,
        state.value.dpjpUtama.id.toString(),
        state.value.dpjpTindakan?.id.toString(),
        state.value.name,
        state.value.medicalRecord,
        dateBirthday,
        state.value.address,
        state.value.suku,
        state.value.work,
        state.value.lastEducation,
        state.value.religion,
        state.value.anamnesis,
        state.value.physicalExamination,
        state.value.labResult,
        state.value.diagnosis
      )
    result.onSuccess {
      setState { copy(isLoadingSubmit = false) }
      setEffect(FormCreateEffect.SuccessSubmit)
    }.onFailure {
      setState { copy(isLoadingSubmit = false) }
      setEffect(FormCreateEffect.ShowErrorMessage(it.type, it.message ?: ""))
    }
  }

  // tglLahir with the format "dd-MM-yyyy"
  private fun calculateAge(tglLahir: String): Int {
    val tahunLahir = tglLahir.split("-").last()
    return Calendar.getInstance().get(Calendar.YEAR).minus(tahunLahir.toIntOrNull() ?: 0)
  }
}
