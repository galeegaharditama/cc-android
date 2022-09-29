package com.galeegaharditama.cc.form.update

import androidx.lifecycle.viewModelScope
import com.galeegaharditama.cc.common.StatefulViewModel
import com.galeegaharditama.cc.form.FormInteractors
import com.galeegaharditama.cc.form.model.FileUploadDataView
import com.galeegaharditama.cc.form.model.toDataViews
import com.galih.library.OutputError
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

internal class FormUpdateViewModel(
  private val interactor: FormInteractors,
  id: Int
) : StatefulViewModel<FormUpdateState, FormUpdateEffect, FormUpdateAction, FormInteractors>(
  initialState = FormUpdateState(), contract = interactor
) {
  init {
    dispatch(FormUpdateAction.Load(id))
  }

  @SuppressWarnings("LongMethod", "ComplexMethod")
  override fun dispatch(action: FormUpdateAction) {
    when (action) {
      is FormUpdateAction.Load -> {
        onLoadData(action.id)
      }
      is FormUpdateAction.ChangeDateCathConference -> {
        viewModelScope.launch {
          setState { copy(dateCathConference = action.value) }
        }
      }
      is FormUpdateAction.ChangeDpjpUtama -> {
        viewModelScope.launch {
          setState { copy(dpjpUtama = action.value) }
        }
      }
      is FormUpdateAction.ChangeDpjpTindakan -> {
        viewModelScope.launch {
          setState { copy(dpjpTindakan = action.value) }
        }
      }
      is FormUpdateAction.ChangeName -> {
        viewModelScope.launch {
          setState { copy(name = action.value) }
        }
      }
      is FormUpdateAction.ChangeMedicalRecord -> {
        viewModelScope.launch {
          setState { copy(medicalRecord = action.value) }
        }
      }
      is FormUpdateAction.ChangeBirthday -> {
        viewModelScope.launch {
          val age = calculateAge(action.value)
          setState { copy(birthday = action.value, age = age) }
        }
      }
      is FormUpdateAction.ChangeAddress -> {
        viewModelScope.launch {
          setState { copy(address = action.value) }
        }
      }
      is FormUpdateAction.ChangeSuku -> {
        viewModelScope.launch {
          setState { copy(suku = action.value) }
        }
      }
      is FormUpdateAction.ChangeLastEducation -> {
        viewModelScope.launch {
          setState { copy(lastEducation = action.value) }
        }
      }
      is FormUpdateAction.ChangeReligion -> {
        viewModelScope.launch {
          setState { copy(religion = action.value) }
        }
      }
      is FormUpdateAction.ChangeWork -> {
        viewModelScope.launch {
          setState { copy(work = action.value) }
        }
      }
      is FormUpdateAction.ChangeAnamnesis -> {
        viewModelScope.launch {
          setState { copy(anamnesis = action.value) }
        }
      }
      is FormUpdateAction.ChangePhysicalExamination -> {
        viewModelScope.launch {
          setState { copy(physicalExamination = action.value) }
        }
      }
      is FormUpdateAction.ChangeLabResult -> {
        viewModelScope.launch {
          setState { copy(labResult = action.value) }
        }
      }
      is FormUpdateAction.ChangeDiagnosis -> {
        viewModelScope.launch {
          setState { copy(diagnosis = action.value) }
        }
      }
      is FormUpdateAction.AddElektrokardiogram -> {
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
      is FormUpdateAction.RemoveElektrokardiogram -> {
        viewModelScope.launch {
          val mutableList = state.value.hasilElektrokardiogram.toMutableList()
          mutableList.remove(action.value)
          setState { copy(hasilElektrokardiogram = mutableList.toList()) }
        }
      }
      is FormUpdateAction.AddRontgenThorax -> {
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
      is FormUpdateAction.RemoveRontgenThorax -> {
        viewModelScope.launch {
          val mutableList = state.value.hasilRontgenThorax.toMutableList()
          mutableList.remove(action.value)
          setState { copy(hasilRontgenThorax = mutableList.toList()) }
        }
      }
      is FormUpdateAction.AddEchocardiogram -> {
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
      is FormUpdateAction.RemoveEchocardiogram -> {
        viewModelScope.launch {
          val mutableList = state.value.hasilEchocardiogram.toMutableList()
          mutableList.remove(action.value)
          setState { copy(hasilEchocardiogram = mutableList.toList()) }
        }
      }
      is FormUpdateAction.AddPenunjang -> {
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
      is FormUpdateAction.RemovePenunjang -> {
        viewModelScope.launch {
          val mutableList = state.value.hasilPenunjang.toMutableList()
          mutableList.remove(action.value)
          setState { copy(hasilPenunjang = mutableList.toList()) }
        }
      }
      FormUpdateAction.ClickSubmit -> {
        viewModelScope.launch {
          onValidation()

          onSubmit()
        }
      }
    }
  }

  private fun onLoadData(id: Int) {
    viewModelScope.launch {
      setState { copy(isLoadingContent = true) }

      val loadDpjpUtama = async { interactor.getDpjpUtama() }
      val loadDpjpTindakan = async { interactor.getDpjpTindakan() }
      val loadCc = async { interactor.getCathConference(id) }

      withContext(Dispatchers.Main) {

        val resultDpjpUtama = loadDpjpUtama.await()
        val resultDpjpTindakan = loadDpjpTindakan.await()
        val resultCc = loadCc.await()

        try {
          resultCc.onSuccess {
            val dateCreated = it.dateCreated.toTimeMillis("yyyy-MM-dd").toDateTime("dd-MM-yyyy")
            val tglLahir = it.pasien.tglLahir.toTimeMillis("yyyy-MM-dd").toDateTime("dd-MM-yyyy")
            setState {
              copy(
                id = it.id,
                dateCathConference = dateCreated,
                dpjpUtama = it.dpjpUtama,
                dpjpTindakan = it.dpjpTindakan,
                name = it.pasien.name,
                medicalRecord = it.pasien.medicalRecord,
                birthday = tglLahir,
                age = calculateAge(tglLahir),
                address = it.pasien.alamat,
                suku = it.pasien.suku ?: "",
                work = it.pasien.pekerjaan,
                lastEducation = it.pasien.pendidikan,
                religion = it.pasien.agama,
                anamnesis = it.klinis.anamnesis,
                physicalExamination = it.klinis.pemeriksaanFisik,
                labResult = it.klinis.hasilLaboratorium,
                diagnosis = it.klinis.diagnosis,
                hasilElektrokardiogram = it.klinis.hasilElektrokardiogram.toDataViews(),
                hasilRontgenThorax = it.klinis.hasilRontgen.toDataViews(),
                hasilEchocardiogram = it.klinis.hasilEchocardiogram.toDataViews(),
                hasilPenunjang = it.klinis.hasilPenunjangLain.toDataViews()
              )
            }
          }.onFailure {
            when(it.type) {
              OutputError.SERVER -> {
                if (it.status == 404) setEffect(FormUpdateEffect.ErrorNotFound)
                else if (it.status == 500) setEffect(FormUpdateEffect.ServerError)
              }
              OutputError.NETWORK -> setEffect(FormUpdateEffect.LostConnection)
              else -> setEffect(FormUpdateEffect.ShowErrorMessage(it.type, it.message ?: ""))
            }
          }
          resultDpjpUtama.onSuccess {
            if (it.isEmpty()) setEffect(FormUpdateEffect.ErrorNotFound)
            setState { copy(listDpjpUtama = it) }
          }.onFailure {
//            setEffect(
//              FormUpdateEffect.ShowErrorMessage(it.type, it.message ?: "")
//            )
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

    if (state.value.religion.isNullOrBlank()) {
      setState { copy(religionValidation = ErrorValidationType.EMPTY) }
    } else setState { copy(religionValidation = ErrorValidationType.VALID) }

    if (state.value.anamnesis.isNullOrBlank()) {
      setState { copy(anamnesisValidation = ErrorValidationType.EMPTY) }
    } else setState { copy(anamnesisValidation = ErrorValidationType.VALID) }

    if (state.value.physicalExamination.isNullOrBlank()) {
      setState { copy(physicalExaminationValidation = ErrorValidationType.EMPTY) }
    } else setState { copy(physicalExaminationValidation = ErrorValidationType.VALID) }

    if (state.value.labResult.isNullOrBlank()) {
      setState { copy(labResultValidation = ErrorValidationType.EMPTY) }
    } else setState { copy(labResultValidation = ErrorValidationType.VALID) }

    if (state.value.diagnosis.isNullOrBlank()) {
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
      interactor.updateCathConference(
        state.value.id,
        dateCc,
        state.value.dpjpUtama.id.toString(),
        state.value.dpjpTindakan?.id?.toString(),
        state.value.name,
        state.value.medicalRecord,
        dateBirthday,
        state.value.address,
        state.value.suku,
        state.value.work,
        state.value.lastEducation,
        state.value.religion ?: "",
        state.value.anamnesis,
        state.value.physicalExamination,
        state.value.labResult,
        state.value.diagnosis
      )
    result.onSuccess {
      setState { copy(isLoadingSubmit = false) }
      setEffect(FormUpdateEffect.SuccessSubmit)
    }.onFailure {
      setState { copy(isLoadingSubmit = false) }
      setEffect(FormUpdateEffect.ShowErrorMessage(it.type, it.message ?: ""))
    }
  }

  // tglLahir with the format "dd-MM-yyyy"
  private fun calculateAge(tglLahir: String): Int {
    val tahunLahir = tglLahir.split("-").last()
    return Calendar.getInstance().get(Calendar.YEAR).minus(tahunLahir.toIntOrNull() ?: 0)
  }
}
