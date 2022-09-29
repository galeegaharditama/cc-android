package com.galeegaharditama.cc.domain.interactor

import com.galeegaharditama.cc.domain.repository.ILevelUserRepository

class GetLevelUser(
  private val levelUserRepository: ILevelUserRepository
) {
  suspend operator fun invoke() = levelUserRepository.get()
}
