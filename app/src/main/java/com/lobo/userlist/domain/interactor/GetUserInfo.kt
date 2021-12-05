package com.lobo.userlist.domain.interactor

import com.lobo.userlist.core.util.Result
import com.lobo.userlist.domain.UserModel
import com.lobo.userlist.repository.UsersRepository

class GetUserInfo(private val repository: UsersRepository) {

    fun invoke(indexUser: Int): Result<UserModel, Exception> {
        return repository.getUserInfo(indexUser)
    }
}