package com.galeegaharditama.cc.data.repository

import com.galeegaharditama.cc.data.remote.base.ILevelUserRemoteDataSource
import com.galeegaharditama.cc.data.remote.model.toDomains
import com.galeegaharditama.cc.domain.model.LevelUser
import com.galeegaharditama.cc.domain.repository.ILevelUserRepository
import com.galih.library.Output
import com.galih.library.onFailure
import com.galih.library.onSuccessWithTransform

internal class LevelUserRepositoryImpl(
  private val remote: ILevelUserRemoteDataSource,
) : ILevelUserRepository {
  override suspend fun get(): Output<List<LevelUser>> {
    val result = remote.get()

    return result.onSuccessWithTransform { responses ->
      responses.toDomains()
    }.onFailure {
      Output.failure<List<LevelUser>>(fail = it)
    }
  }
}
