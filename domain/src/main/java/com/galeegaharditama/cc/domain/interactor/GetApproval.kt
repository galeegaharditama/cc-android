package com.galeegaharditama.cc.domain.interactor

import com.galeegaharditama.cc.domain.repository.ICathConferenceRepository

class GetApproval(
  private val repository: ICathConferenceRepository
) {
  suspend operator fun invoke() = repository.getKeputusan()
}
