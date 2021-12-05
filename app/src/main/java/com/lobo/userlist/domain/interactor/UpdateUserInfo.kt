package com.lobo.userlist.domain.interactor

import com.lobo.userlist.core.util.Result
import com.lobo.userlist.domain.UserModel
import com.lobo.userlist.repository.UsersRepository

class UpdateUserInfo(private val repository: UsersRepository) {
    fun invoke(
        indexList: Int,
        updatedUser: UserModel
    ): Result<List<UserModel>, Exception> {
        return repository.updateUserInfo(
            indexList = indexList,
            updatedUser = updatedUser
        )
    }
}