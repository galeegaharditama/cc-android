package com.galeegaharditama.cc.form.create

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Notes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.VideoFrameDecoder
import com.galeegaharditama.cc.common.handleEffect
import com.galeegaharditama.cc.common.ui.component.CardExpandable
import com.galeegaharditama.cc.common.ui.component.CathConferenceButton
import com.galeegaharditama.cc.common.ui.component.CathConferenceFileUpload
import com.galeegaharditama.cc.common.ui.component.CathConferenceTextField
import com.galeegaharditama.cc.common.ui.component.LoadingContent
import com.galeegaharditama.cc.common.ui.component.TopBar
import com.galeegaharditama.cc.common.ui.component.bottomsheet.showAsBottomSheet
import com.galeegaharditama.cc.common.ui.theme.CCTheme
import com.galeegaharditama.cc.domain.model.Dpjp
import com.galeegaharditama.cc.form.R
import com.galeegaharditama.cc.form.model.FileUploadDataView
import com.galih.library.FileUtils
import com.galih.library.validation.ErrorValidationType
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import org.koin.androidx.compose.getViewModel
import java.io.File
import java.util.*

@Composable
fun FormCreateScreen(
  activity: Activity,
  onSuccessSubmit: () -> Unit,
  onClickNavigation: () -> Unit
) {
  val viewModel = getViewModel<FormCreateViewModel>()
  val state = viewModel.state.collectAsState()

  val context = LocalContext.current

  viewModel.handleEffect {
    when (it) {
      is FormCreateEffect.ShowErrorMessage -> {
        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
      }
      is FormCreateEffect.SuccessSubmit -> {
        onSuccessSubmit()
      }
      FormCreateEffect.DataNotFound -> {
        Handler(Looper.getMainLooper()).postDelayed({
          val message = context.getString(com.galeegaharditama.cc.common.R.string.error_not_found)
          Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
          onClickNavigation.invoke()
        }, 1000)
      }
    }
  }

  val dataGeneralAction = DataGeneralFormAction(
    onDateCathConferenceChanged = { viewModel.dispatch(FormCreateAction.ChangeDateCathConference(it)) },
    onDpjpUtamaChanged = { viewModel.dispatch(FormCreateAction.ChangeDpjpUtama(it)) },
    onDpjpTindakanChanged = { viewModel.dispatch(FormCreateAction.ChangeDpjpTindakan(it)) }
  )

  val dataPasienAction = DataPasienFormAction(
    onNameChanged = { viewModel.dispatch(FormCreateAction.ChangeName(it)) },
    onMedicalRecordChanged = { viewModel.dispatch(FormCreateAction.ChangeMedicalRecord(it)) },
    onBirthdayChanged = { viewModel.dispatch(FormCreateAction.ChangeBirthday(it)) },
    onAddressChanged = { viewModel.dispatch(FormCreateAction.ChangeAddress(it)) },
    onSukuChanged = { viewModel.dispatch(FormCreateAction.ChangeSuku(it)) },
    onLastEducationChanged = { viewModel.dispatch(FormCreateAction.ChangeLastEducation(it)) },
    onReligionChanged = { viewModel.dispatch(FormCreateAction.ChangeReligion(it)) },
    onWorkChanged = { viewModel.dispatch(FormCreateAction.ChangeWork(it)) },
  )

  val dataKlinisAction = DataKlinisFormAction(
    onAnamnesisChanged = { viewModel.dispatch(FormCreateAction.ChangeAnamnesis(it)) },
    onPhysicalExaminationChanged = {
      viewModel.dispatch(
        FormCreateAction.ChangePhysicalExamination(
          it
        )
      )
    },
    onLabResultChanged = { viewModel.dispatch(FormCreateAction.ChangeLabResult(it)) },
    onDiagnosisChanged = { viewModel.dispatch(FormCreateAction.ChangeDiagnosis(it)) },
    onAddElektrokardiogram = { viewModel.dispatch(FormCreateAction.AddElektrokardiogram(it)) },
    onRemoveElektrokardiogram = { viewModel.dispatch(FormCreateAction.RemoveElektrokardiogram(it)) },
    onAddRontgenThorax = { viewModel.dispatch(FormCreateAction.AddRontgenThorax(it)) },
    onRemoveRontgenThorax = { viewModel.dispatch(FormCreateAction.RemoveRontgenThorax(it)) },
    onAddEchocardiogram = { viewModel.dispatch(FormCreateAction.AddEchocardiogram(it)) },
    onRemoveEchocardiogram = { viewModel.dispatch(FormCreateAction.RemoveEchocardiogram(it)) },
    onAddPenunjang = { viewModel.dispatch(FormCreateAction.AddPenunjang(it)) },
    onRemovePenunjang = { viewModel.dispatch(FormCreateAction.RemovePenunjang(it)) },
  )

  FormCreateContent(
    activity = activity,
    state = state.value,
    onClickNavigation = onClickNavigation,
    formGeneralActions = dataGeneralAction,
    formPasienActions = dataPasienAction,
    formKlinsiActions = dataKlinisAction,
    onSubmit = { viewModel.dispatch(FormCreateAction.ClickSubmit) }
  )
}

@Composable
private fun FormCreateContent(
  activity: Activity? = null,
  formGeneralActions: DataGeneralFormAction = DataGeneralFormAction(),
  formPasienActions: DataPasienFormAction = DataPasienFormAction(),
  formKlinsiActions: DataKlinisFormAction = DataKlinisFormAction(),
  onSubmit: () -> Unit = {},
  state: FormCreateState = FormCreateState(),
  onClickNavigation: () -> Unit = {}
) {
  Scaffold(
    topBar = {
      TopBarContent(onClickNavigation = onClickNavigation)
    }
  ) { innerPadding ->
    if (state.isLoadingContent) LoadingContent()
    else Content(
      state = state,
      formGeneralActions = formGeneralActions,
      formPasienActions = formPasienActions,
      formKlinisActions = formKlinsiActions,
      onSubmit = onSubmit,
      activity = activity,
      paddingValues = innerPadding,
    )
  }
}

@Composable
private fun TopBarContent(
  onClickNavigation: () -> Unit,
) {
  TopBar(
    title = stringResource(id = R.string.form_create_title_top_bar),
    navigationIcon = {
      IconButton(onClick = onClickNavigation) {
        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
      }
    }
  )
}

@Composable
private fun Content(
  state: FormCreateState,
  paddingValues: PaddingValues,
  formGeneralActions: DataGeneralFormAction,
  formPasienActions: DataPasienFormAction,
  formKlinisActions: DataKlinisFormAction,
  onSubmit: () -> Unit,
  activity: Activity? = null,
) {
  Box(
    modifier = Modifier
      .padding(paddingValues)
      .fillMaxSize()
  ) {
    val focusManager = LocalFocusManager.current

    LazyColumn(modifier = Modifier.padding(bottom = 48.dp)) {
      item {
        FormCardContentGeneral(
          state = state,
          dataGeneralAction = formGeneralActions,
          modifier = Modifier.padding(paddingValues),
          activity = activity
        )
      }
      item {
        FormCardContentPasien(
          state = state,
          formPasienAction = formPasienActions
        )
      }
      item {
        FormCardContentClicinal(
          state = state,
          formKlinisAction = formKlinisActions,
          activity = activity
        )
      }
      item {
        Spacer(modifier = Modifier.height(16.dp))
      }
    }

    CathConferenceButton(
      shape = MaterialTheme.shapes.large,
      onClick = {
        focusManager.clearFocus()
        onSubmit()
      },
      modifier = Modifier
        .fillMaxWidth()
        .align(Alignment.BottomCenter),
      isShowLoading = true,
      isLoading = state.isLoadingSubmit,
    ) {
      Text(
        text = stringResource(id = R.string.form_create_button),
        style = MaterialTheme.typography.body1,
      )
    }
  }
}

@Composable
private fun FormCard(
  title: String,
  modifier: Modifier = Modifier,
  content: @Composable ((ColumnScope) -> Unit),
) {
  var isExpanded by rememberSaveable { mutableStateOf(true) }

  CardExpandable(
    modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp),
    modifierColumn = Modifier.padding(8.dp)
  ) {
    FormCardTitle(
      title = title,
      isExpand = isExpanded,
      onClickExpand = { isExpanded = it }
    )

    if (isExpanded) {
      Divider(modifier = Modifier.padding(horizontal = 8.dp))
      Spacer(modifier = Modifier.height(8.dp))
      content.invoke(this)
      Spacer(modifier = Modifier.height(8.dp))
    }
  }
}

@Composable
private fun FormCardTitle(
  title: String,
  isExpand: Boolean,
  onClickExpand: (Boolean) -> Unit
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier.padding(start = 8.dp)
  ) {
    Text(
      text = title,
      style = MaterialTheme.typography.subtitle1.copy(
        fontWeight = FontWeight.Light
      ),
      maxLines = 2,
      overflow = TextOverflow.Ellipsis
    )
    Spacer(modifier = Modifier.weight(1f))
    IconButton(
      onClick = { onClickExpand.invoke(!isExpand) },
      modifier = Modifier.align(Alignment.CenterVertically)
    ) {
      Icon(
        imageVector = if (isExpand) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
        contentDescription = null
      )
    }
  }
}

@Composable
private fun FormCardContentGeneral(
  modifier: Modifier = Modifier,
  activity: Activity? = null,
  state: FormCreateState,
  dataGeneralAction: DataGeneralFormAction,
) {
  val mCalendar = Calendar.getInstance()
  val mContext = LocalContext.current
  val mYear = mCalendar.get(Calendar.YEAR)
  val mMonth = mCalendar.get(Calendar.MONTH)
  val mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

  mCalendar.time = Date()

  val mDatePickerDialog = DatePickerDialog(
    mContext,
    { _: DatePicker, year: Int, month: Int, mDayOfMonth: Int ->
      dataGeneralAction.onDateCathConferenceChanged("$mDayOfMonth-${month + 1}-$year")
    }, mYear, mMonth, mDay
  )

  FormCard(
    title = stringResource(id = R.string.form_create_title_data_cc),
    modifier = modifier,
  ) {
    CathConferenceTextField(
      modifier = Modifier
        .padding(horizontal = 8.dp, vertical = 2.dp)
        .fillMaxWidth(),
      value = state.dateCathConference,
      label = com.galeegaharditama.cc.common.R.string.tgl_cc,
      isError = state.dateCathConferenceValidation != null &&
          state.dateCathConferenceValidation != ErrorValidationType.VALID,
      trailingIcon = {
        IconButton(onClick = {
          mDatePickerDialog.show()
        }) {
          Icon(
            imageVector = Icons.Filled.DateRange,
            contentDescription = null
          )
        }
      },
      isReadOnly = true,
      textError = when (state.dateCathConferenceValidation) {
        ErrorValidationType.EMPTY -> stringResource(id = com.galeegaharditama.cc.common.R.string.input_blank)
        else -> null
      }
    )

    CathConferenceTextField(
      modifier = Modifier
        .padding(horizontal = 8.dp, vertical = 2.dp)
        .fillMaxWidth(),
      value = state.dpjpUtama.name,
      label = com.galeegaharditama.cc.common.R.string.dpjp_utama,
      isError = state.dpjpUtamaValidation != null &&
          state.dpjpUtamaValidation != ErrorValidationType.VALID,
      onChanged = {},
      trailingIcon = {
        IconButton(onClick = {
          activity?.showAsBottomSheet { actionClose ->
            DpjpBottomSheet(
              modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 25.dp)
                .wrapContentHeight(),
              data = state.listDpjpUtama,
              onClickItem = dataGeneralAction.onDpjpUtamaChanged,
              onClose = actionClose
            )
          }
        }) {
          Icon(
            imageVector = Icons.Filled.ArrowDropDown,
            contentDescription = null
          )
        }
      },
      isReadOnly = true,
      textError = when (state.dpjpUtamaValidation) {
        ErrorValidationType.EMPTY -> stringResource(id = com.galeegaharditama.cc.common.R.string.input_blank)
        else -> null
      }
    )

    CathConferenceTextField(
      modifier = Modifier
        .padding(horizontal = 8.dp, vertical = 2.dp)
        .fillMaxWidth(),
      value = state.dpjpTindakan?.name ?: "",
      label = com.galeegaharditama.cc.common.R.string.dpjp_tindakan,
      isError = false,
      onChanged = {},
      trailingIcon = {
        IconButton(onClick = {
          activity?.showAsBottomSheet { actionClose ->
            DpjpBottomSheet(
              modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 25.dp)
                .wrapContentHeight(),
              data = state.listDpjpTindakan,
              onClickItem = dataGeneralAction.onDpjpTindakanChanged,
              onClose = actionClose
            )
          }
        }) {
          Icon(
            imageVector = Icons.Filled.ArrowDropDown,
            contentDescription = null
          )
        }
      },
      isReadOnly = true,
    )
  }
}

@Composable
private fun FormCardContentPasien(
  state: FormCreateState,
  formPasienAction: DataPasienFormAction
) {
  val mCalendar = Calendar.getInstance()
  val mContext = LocalContext.current
  val mYear = mCalendar.get(Calendar.YEAR)
  val mMonth = mCalendar.get(Calendar.MONTH)
  val mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

  mCalendar.time = Date()

  val mDatePickerDialog = DatePickerDialog(
    mContext,
    { _: DatePicker, year: Int, month: Int, mDayOfMonth: Int ->
      formPasienAction.onBirthdayChanged("$mDayOfMonth-${month + 1}-$year")
    }, mYear, mMonth, mDay
  )

  FormCard(
    title = stringResource(id = R.string.form_create_title_data_pasien),
    modifier = Modifier.padding(vertical = 8.dp)
  ) {
    CathConferenceTextField(
      modifier = Modifier
        .padding(horizontal = 8.dp, vertical = 2.dp)
        .fillMaxWidth(),
      value = state.medicalRecord,
      label = com.galeegaharditama.cc.common.R.string.medical_record,
      isError = state.medicalRecordValidation != null &&
          state.medicalRecordValidation != ErrorValidationType.VALID,
      onChanged = formPasienAction.onMedicalRecordChanged,
      textError = when (state.medicalRecordValidation) {
        ErrorValidationType.EMPTY -> stringResource(id = com.galeegaharditama.cc.common.R.string.input_blank)
        else -> null
      }
    )

    CathConferenceTextField(
      modifier = Modifier
        .padding(horizontal = 8.dp, vertical = 2.dp)
        .fillMaxWidth(),
      value = state.name,
      label = com.galeegaharditama.cc.common.R.string.patient_name,
      isError = state.nameValidation != null &&
          state.nameValidation != ErrorValidationType.VALID,
      onChanged = formPasienAction.onNameChanged,
      textError = when (state.medicalRecordValidation) {
        ErrorValidationType.EMPTY -> stringResource(id = com.galeegaharditama.cc.common.R.string.input_blank)
        else -> null
      }
    )

    CathConferenceTextField(
      modifier = Modifier
        .padding(horizontal = 8.dp, vertical = 2.dp)
        .fillMaxWidth(),
      value = state.birthday,
      label = R.string.patient_birthday,
      isError = state.birthdayValidation != null &&
          state.birthdayValidation != ErrorValidationType.VALID,
      trailingIcon = {
        IconButton(onClick = {
          mDatePickerDialog.show()
        }) {
          Icon(
            imageVector = Icons.Filled.DateRange,
            contentDescription = null
          )
        }
      },
      isReadOnly = true,
      textError = when (state.birthdayValidation) {
        ErrorValidationType.EMPTY -> stringResource(id = com.galeegaharditama.cc.common.R.string.input_blank)
        else -> null
      }
    )
    val valueAge = if (state.age != null) "${state.age} Tahun" else ""
    CathConferenceTextField(
      modifier = Modifier
        .padding(horizontal = 8.dp, vertical = 2.dp)
        .fillMaxWidth(),
      value = valueAge,
      label = com.galeegaharditama.cc.common.R.string.patient_age,
      isError = false,
      isReadOnly = true,
    )

    CathConferenceTextField(
      modifier = Modifier
        .padding(horizontal = 8.dp, vertical = 2.dp)
        .fillMaxWidth(),
      value = state.address,
      label = R.string.patient_address,
      isError = state.addressValidation != null &&
          state.addressValidation != ErrorValidationType.VALID,
      onChanged = formPasienAction.onAddressChanged,
      textError = when (state.addressValidation) {
        ErrorValidationType.EMPTY -> stringResource(id = com.galeegaharditama.cc.common.R.string.input_blank)
        else -> null
      }
    )

    CathConferenceTextField(
      modifier = Modifier
        .padding(horizontal = 8.dp, vertical = 2.dp)
        .fillMaxWidth(),
      value = state.suku ?: "",
      label = R.string.patient_suku,
      isError = false,
      onChanged = formPasienAction.onSukuChanged,
    )

    CathConferenceTextField(
      modifier = Modifier
        .padding(horizontal = 8.dp, vertical = 2.dp)
        .fillMaxWidth(),
      value = state.work ?: "",
      label = R.string.patient_work,
      isError = false,
      onChanged = formPasienAction.onWorkChanged,
    )

    CathConferenceTextField(
      modifier = Modifier
        .padding(horizontal = 8.dp, vertical = 2.dp)
        .fillMaxWidth(),
      value = state.lastEducation ?: "",
      label = R.string.patient_last_education,
      isError = false,
      onChanged = formPasienAction.onLastEducationChanged,
    )

    CathConferenceTextField(
      modifier = Modifier
        .padding(horizontal = 8.dp, vertical = 2.dp)
        .fillMaxWidth(),
      value = state.religion,
      label = com.galeegaharditama.cc.common.R.string.patient_religion,
      isError = state.religionValidation != null &&
          state.religionValidation != ErrorValidationType.VALID,
      onChanged = formPasienAction.onReligionChanged,
      textError = when (state.religionValidation) {
        ErrorValidationType.EMPTY -> stringResource(id = com.galeegaharditama.cc.common.R.string.input_blank)
        else -> null
      }
    )
  }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun FormCardContentClicinal(
  modifier: Modifier = Modifier,
  activity: Activity? = null,
  state: FormCreateState,
  formKlinisAction: DataKlinisFormAction,
) {
  FormCard(
    title = stringResource(id = R.string.form_create_title_data_klinis),
    modifier = modifier,
  ) {
    CathConferenceTextField(
      modifier = Modifier
        .padding(horizontal = 8.dp, vertical = 2.dp)
        .fillMaxWidth(),
      value = state.anamnesis,
      label = R.string.klinis_anamnesis,
      isError = state.anamnesisValidation != null &&
          state.anamnesisValidation != ErrorValidationType.VALID,
      onChanged = formKlinisAction.onAnamnesisChanged,
      textError = when (state.anamnesisValidation) {
        ErrorValidationType.EMPTY -> stringResource(id = com.galeegaharditama.cc.common.R.string.input_blank)
        else -> null
      }
    )

    CathConferenceTextField(
      modifier = Modifier
        .padding(horizontal = 8.dp, vertical = 2.dp)
        .fillMaxWidth(),
      value = state.physicalExamination,
      label = R.string.klinis_physical,
      isError = state.physicalExaminationValidation != null &&
          state.physicalExaminationValidation != ErrorValidationType.VALID,
      onChanged = formKlinisAction.onPhysicalExaminationChanged,
      textError = when (state.physicalExaminationValidation) {
        ErrorValidationType.EMPTY -> stringResource(id = com.galeegaharditama.cc.common.R.string.input_blank)
        else -> null
      }
    )

    CathConferenceTextField(
      modifier = Modifier
        .padding(horizontal = 8.dp, vertical = 2.dp)
        .fillMaxWidth(),
      value = state.labResult,
      label = R.string.klinis_laboratorium,
      isError = state.labResultValidation != null &&
          state.labResultValidation != ErrorValidationType.VALID,
      onChanged = formKlinisAction.onLabResultChanged,
      textError = when (state.labResultValidation) {
        ErrorValidationType.EMPTY -> stringResource(id = com.galeegaharditama.cc.common.R.string.input_blank)
        else -> null
      }
    )

    CathConferenceTextField(
      modifier = Modifier
        .padding(horizontal = 8.dp, vertical = 2.dp)
        .fillMaxWidth(),
      value = state.diagnosis,
      label = R.string.klinis_diagnosis,
      isError = state.diagnosisValidation != null &&
          state.diagnosisValidation != ErrorValidationType.VALID,
      onChanged = formKlinisAction.onDiagnosisChanged,
      textError = when (state.diagnosisValidation) {
        ErrorValidationType.EMPTY -> stringResource(id = com.galeegaharditama.cc.common.R.string.input_blank)
        else -> null
      }
    )

    val filePermissionState =
      rememberPermissionState(android.Manifest.permission.READ_EXTERNAL_STORAGE)
    val intent =
      Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        .apply {
          putExtra(
            Intent.EXTRA_MIME_TYPES,
            arrayOf("image/jpeg", "image/png", "video/mp4", "application/pdf")
          )
          addCategory(Intent.CATEGORY_OPENABLE)
        }
    val context = LocalContext.current

    val launcherElektrokardiogram =
      rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        it.data?.data?.let { uri ->
          FileUtils.getPath(context, uri)?.let { path ->
            formKlinisAction.onAddElektrokardiogram.invoke(File(path))
          }
        }
      }

    CathConferenceFileUpload(
      label = stringResource(id = R.string.klinis_elektrokardiogram),
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
      isError = state.hasilElektrokardiogramValidation != null &&
          state.hasilElektrokardiogramValidation != ErrorValidationType.VALID,
      textError = when (state.hasilElektrokardiogramValidation) {
        ErrorValidationType.EMPTY -> stringResource(id = com.galeegaharditama.cc.common.R.string.input_blank)
        else -> null
      },
      onClickBtn = {
        if (filePermissionState.status.isGranted) {
          launcherElektrokardiogram.launch(intent)
        } else {
          filePermissionState.launchPermissionRequest()
        }
      },
    ) {
      LazyRow {
        items(state.hasilElektrokardiogram) { item ->
          PlaceCard(
            activity = activity,
            file = item,
            onRemoveClick = formKlinisAction.onRemoveElektrokardiogram
          )
        }
      }
    }

    val launcherRontgenThorax =
      rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        it.data?.data?.let { uri ->
          FileUtils.getPath(context, uri)?.let { path ->
            formKlinisAction.onAddRontgenThorax.invoke(File(path))
          }
        }
      }

    CathConferenceFileUpload(
      label = stringResource(id = R.string.klinis_rontgen_thorax),
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
      isError = state.hasilRontgenThoraxValidation != null &&
          state.hasilRontgenThoraxValidation != ErrorValidationType.VALID,
      textError = when (state.hasilRontgenThoraxValidation) {
        ErrorValidationType.EMPTY -> stringResource(id = com.galeegaharditama.cc.common.R.string.input_blank)
        else -> null
      },
      onClickBtn = {
        if (filePermissionState.status.isGranted) {
          launcherRontgenThorax.launch(intent)
        } else {
          filePermissionState.launchPermissionRequest()
        }
      },
    ) {
      LazyRow {
        items(state.hasilRontgenThorax) { item ->
          PlaceCard(
            activity = activity,
            file = item,
            onRemoveClick = formKlinisAction.onRemoveRontgenThorax
          )
        }
      }
    }

    val launcherEchocardiogram =
      rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        it.data?.data?.let { uri ->
          FileUtils.getPath(context, uri)?.let { path ->
            formKlinisAction.onAddEchocardiogram.invoke(File(path))
          }
        }
      }

    CathConferenceFileUpload(
      label = stringResource(id = R.string.klinis_echocardiogram),
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
      isError = state.hasilEchocardiogramValidation != null &&
          state.hasilEchocardiogramValidation != ErrorValidationType.VALID,
      textError = when (state.hasilEchocardiogramValidation) {
        ErrorValidationType.EMPTY -> stringResource(id = com.galeegaharditama.cc.common.R.string.input_blank)
        else -> null
      },
      onClickBtn = {
        if (filePermissionState.status.isGranted) {
          launcherEchocardiogram.launch(intent)
        } else {
          filePermissionState.launchPermissionRequest()
        }
      },
    ) {
      LazyRow {
        items(state.hasilEchocardiogram) { item ->
          PlaceCard(
            activity = activity,
            file = item,
            onRemoveClick = formKlinisAction.onRemoveEchocardiogram
          )
        }
      }
    }

    val launcherPenunjang =
      rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        it.data?.data?.let { uri ->
          FileUtils.getPath(context, uri)?.let { path ->
            formKlinisAction.onAddPenunjang.invoke(File(path))
          }
        }
      }

    CathConferenceFileUpload(
      label = stringResource(id = R.string.klinis_penunjang),
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
      isError = false,
      onClickBtn = {
        if (filePermissionState.status.isGranted) {
          launcherPenunjang.launch(intent)
        } else {
          filePermissionState.launchPermissionRequest()
        }
      },
    ) {
      LazyRow {
        items(state.hasilPenunjang) { item ->
          PlaceCard(
            activity = activity,
            file = item,
            onRemoveClick = formKlinisAction.onRemovePenunjang
          )
        }
      }
    }
  }
}

@Composable
fun PlaceCard(
  activity: Activity? = null,
  file: FileUploadDataView,
  onRemoveClick: ((FileUploadDataView) -> Unit)? = null
) {
  println("SELECT3 $file")
  Box(
    contentAlignment = Alignment.BottomStart,
    modifier = Modifier
      .padding(horizontal = 8.dp)
      .width(150.dp)
      .height(200.dp)
  ) {
    val context = LocalContext.current
    Surface(
      elevation = 4.dp,
      color = Color.LightGray,
      shape = RoundedCornerShape(8.dp),
      modifier = Modifier.clickable {
        file.path?.let { path ->
          activity?.openFile(
            context = context,
            file = File(path)
          )
        }
      }
    ) {
      when (file.extension) {
        in listOf("jpg", "png", "jpeg") -> {
          SubcomposeAsyncImage(
            model = file.path,
            loading = {
              CircularProgressIndicator()
            },
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
              .width(150.dp)
              .height(200.dp)
          )
        }
        in listOf("mp4", "avi", "3gp") -> {
          val loader = ImageLoader(context).newBuilder().apply {
            components {
              add(VideoFrameDecoder.Factory())
            }
          }.build()
          val painter = rememberAsyncImagePainter(
            model = file.path,
            imageLoader = loader
          )
          Image(
            painter = painter, contentDescription = null, modifier = Modifier
              .width(150.dp)
              .height(200.dp)
          )
        }
        in listOf("pdf", "docx", "doc") -> {
          Image(
            imageVector = Icons.Filled.Notes, contentDescription = null, modifier = Modifier
              .width(150.dp)
              .height(200.dp)
          )
        }
      }
    }

    Box(
      modifier = Modifier
        .align(Alignment.TopEnd)
        .padding(8.dp)
        .clip(CircleShape)
        .clickable { onRemoveClick?.invoke(file) }
        .background(
          color = MaterialTheme.colors.surface,
          shape = CircleShape
        )
    ) {
      Icon(
        modifier = Modifier.padding(8.dp),
        imageVector = Icons.Filled.Delete,
        contentDescription = null,
        tint = MaterialTheme.colors.error
      )
    }

    Text(
      modifier = Modifier
        .padding(16.dp),
      text = file.title ?: "",
      color = Color.White,
      fontWeight = FontWeight.Bold,
      fontSize = 16.sp
    )
  }
}

private fun Activity.openFile(
  context: Context,
  file: File
) {
  // Get URI and MIME type of file
  val uri =
    FileProvider.getUriForFile(context, context.applicationContext.packageName + ".provider", file)
  val mime: String? = context.contentResolver.getType(uri)

  // Open file with user selected app
  val intent = Intent()
  intent.action = Intent.ACTION_VIEW
  intent.setDataAndType(uri, mime)
  intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
  this.startActivity(intent)
}

@Composable
private fun DpjpBottomSheet(
  modifier: Modifier = Modifier,
  data: List<Dpjp>,
  onClickItem: (Dpjp) -> Unit,
  onClose: () -> Unit
) {
  Column(modifier = modifier) {
    data.forEach {
      TextButton(onClick = {
        onClickItem.invoke(it)
        onClose()
      }) {
        Text(
          text = it.name,
          modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
          style = MaterialTheme.typography.body2.copy(
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.8f)
          )
        )
      }
    }
  }
}

@Preview(name = "Light", showSystemUi = true, showBackground = true)
@Preview(
  uiMode = Configuration.UI_MODE_NIGHT_YES,
  name = "Dark", showSystemUi = true, showBackground = true
)
@Composable
private fun PreviewContent() {
  CCTheme {
    FormCreateContent()
  }
}

@Preview(name = "Light", showSystemUi = true, showBackground = true)
@Preview(
  uiMode = Configuration.UI_MODE_NIGHT_YES,
  name = "Dark", showSystemUi = true, showBackground = true
)
@Composable
private fun PreviewFormCardClinicalContent() {
  CCTheme {
    FormCardContentClicinal(
      state = FormCreateState(),
      formKlinisAction = DataKlinisFormAction()
    )
  }
}
