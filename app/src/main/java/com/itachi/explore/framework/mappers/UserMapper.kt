package com.itachi.explore.framework.mappers

import com.itachi.core.domain.AncientVO
import com.itachi.core.domain.PhotoVO
import com.itachi.core.domain.UserVO
import com.itachi.explore.persistence.entities.AncientEntity
import com.itachi.explore.persistence.entities.UserEntity

class UserMapper(
    private val userEntityToVoMapper: UserEntityToVoMapper,
    private val userVoToEntityMapper: UserVoToEntityMapper,
    private val userVoToFirebaseMapper: UserVoToFirebaseMapper
) {
    fun entityToVO(userEntity: UserEntity?) = userEntityToVoMapper.map(userEntity)
    fun voToEntity(userVO: UserVO?) = userVoToEntityMapper.map(userVO)
    fun voToFirebaseHashmap(userVO: UserVO?) = userVoToFirebaseMapper.map(userVO)
}