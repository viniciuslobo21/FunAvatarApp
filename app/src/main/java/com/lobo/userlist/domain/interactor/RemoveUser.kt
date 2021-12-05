package com.lobo.userlist.domain.interactor

import com.lobo.userlist.core.util.Result
import com.lobo.userlist.domain.UserModel
import com.lobo.userlist.repository.UsersRepository

class RemoveUser(private val repository: UsersRepository) {
    fun invoke(
        indexList: Int
    ): Result<List<UserModel>, Exception> {
        return repository.removeUser(
            indexList = indexList
        )
    }
}