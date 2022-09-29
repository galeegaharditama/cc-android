package com.galeegaharditama.cc.domain.interactor

import com.galeegaharditama.cc.domain.repository.ICathConferenceRepository

class GetAllCathConference(
  private val repository: ICathConferenceRepository
) {
  suspend operator fun invoke() = repository.getAll()
}
