package com.galeegaharditama.cc.domain.interactor

import com.galeegaharditama.cc.domain.repository.ICathConferenceRepository

class GetDpjpTindakan(
  private val repository: ICathConferenceRepository
) {
  suspend operator fun invoke() = repository.getDpjpTindakan()
}
