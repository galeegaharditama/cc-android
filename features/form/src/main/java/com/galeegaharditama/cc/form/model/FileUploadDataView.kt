package com.galeegaharditama.cc.form.model

import com.galeegaharditama.cc.domain.model.FileLab

data class FileUploadDataView(
  val id: Int,
  val title: String?,
  val path: String?,
  val size: Int,
  val laporan: String? = null,
  val tempPath: String = "",
  val extension: String = "",
  val isLocal: Boolean = false,
  val thumbnail: String = "",
)

fun FileLab.toDataView() = FileUploadDataView(
  id = this.id,
  title = this.title,
  path = this.path,
  size = this.size,
  laporan = this.laporan,
  isLocal = false
)

fun List<FileLab>.toDataViews() = this.map { it.toDataView() }
