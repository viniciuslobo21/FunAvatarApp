package com.lobo.userlist.repository

import com.lobo.userlist.core.util.Result
import com.lobo.userlist.domain.UserModel

interface UsersRepository {
    fun getUserList(): Result<List<UserModel>, Exception>
    fun getUserInfo(userIndex: Int): Result<UserModel, Exception>
    fun saveUser(
        name: String,
        bio: String,
        avatar: Int
    ): Result<List<UserModel>, Exception>

    fun updateUserInfo(
        indexList: Int,
        updatedUser: UserModel
    ): Result<List<UserModel>, Exception>


    fun removeUser(
        indexList: Int
    ): Result<List<UserModel>, Exception>

}