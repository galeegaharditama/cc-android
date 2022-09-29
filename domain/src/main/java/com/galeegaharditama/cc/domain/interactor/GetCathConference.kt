package com.galeegaharditama.cc.domain.interactor

import com.galeegaharditama.cc.domain.repository.ICathConferenceRepository

class GetCathConference(
  private val repository: ICathConferenceRepository
) {
  suspend operator fun invoke(id: Int) = repository.get(id)
}
