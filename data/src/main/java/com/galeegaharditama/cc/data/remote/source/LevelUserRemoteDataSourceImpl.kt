package com.galeegaharditama.cc.data.remote.source

import com.galeegaharditama.cc.data.remote.base.ILevelUserRemoteDataSource
import com.galeegaharditama.cc.data.remote.model.LevelUserResponse
import com.galeegaharditama.cc.data.remote.service.ApiService
import com.galih.library.Output

internal class LevelUserRemoteDataSourceImpl(
  private val service: ApiService
) : ILevelUserRemoteDataSource {
  override suspend fun get(): Output<List<LevelUserResponse>> {
    return networkHandler { service.getLevelUser() }
  }
}
