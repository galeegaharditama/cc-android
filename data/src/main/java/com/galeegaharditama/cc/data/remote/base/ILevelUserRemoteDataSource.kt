package com.galeegaharditama.cc.data.remote.base

import com.galeegaharditama.cc.data.remote.model.LevelUserResponse
import com.galih.library.Output

interface ILevelUserRemoteDataSource {
  suspend fun get(): Output<List<LevelUserResponse>>
}
