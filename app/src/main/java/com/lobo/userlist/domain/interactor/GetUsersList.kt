package com.lobo.userlist.domain.interactor

import com.lobo.userlist.core.util.Result
import com.lobo.userlist.domain.UserModel
import com.lobo.userlist.repository.UsersRepository

class GetUsersList(private val repository: UsersRepository) {

    fun invoke(): Result<List<UserModel>, Exception> {
        return repository.getUserList()
    }
}