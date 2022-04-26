package com.itachi.explore.framework

import com.itachi.core.data.db.UserRoomDataSource
import com.itachi.core.domain.UserVO
import com.itachi.explore.framework.mappers.UserMapper
import com.itachi.explore.persistence.MyDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject

class UserRoomDataSourceImpl(private val userMapper: UserMapper) : UserRoomDataSource,
    KoinComponent {

    private val database: MyDatabase by inject()
    override suspend fun add(
        userVO: UserVO,
        onSuccess: (UserVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.userDao().insertUser(userMapper.voToEntity(userVO))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                onSuccess(userVO)
            }

    }

    override suspend fun get(onSuccess: (UserVO) -> Unit, onFailure: (String) -> Unit) {
        if(database.userDao().userInDbExist()){
            database.userDao().getUser().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    onSuccess(userMapper.entityToVO(it))
                }
        }else onFailure("Something wrong in db")
    }

    override suspend fun delete() {
        database.userDao().deleteUser()
    }

    override suspend fun update(userVO: UserVO, onSuccess: (UserVO) -> Unit, onFailure: (String) -> Unit) {
        TODO("Not yet implemented")
    }
}