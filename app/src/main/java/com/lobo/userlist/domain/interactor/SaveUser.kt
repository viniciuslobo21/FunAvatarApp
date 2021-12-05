package com.lobo.userlist.domain.interactor

import com.lobo.userlist.core.util.Result
import com.lobo.userlist.domain.UserModel
import com.lobo.userlist.repository.UsersRepository

class SaveUser(private val repository: UsersRepository) {
    fun invoke(
        name: String,
        bio: String,
        avatar: Int
    ): Result<List<UserModel>, Exception> {
        return repository.saveUser(
            name = name,
            bio = bio,
            avatar = avatar
        )
    }
}