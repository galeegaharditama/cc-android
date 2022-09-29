package com.galeegaharditama.cc.domain.repository

import com.galeegaharditama.cc.domain.model.LevelUser
import com.galih.library.Output

interface ILevelUserRepository {
  suspend fun get(): Output<List<LevelUser>>
}
