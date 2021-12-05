package com.lobo.userlist.repository

import com.lobo.userlist.core.util.Result
import com.lobo.userlist.domain.Avatar
import com.lobo.userlist.domain.UserModel
import com.lobo.userlist.view.avatars.UsersListSingleton

class UsersRepositoryImpl(
    val usersListSingleton: UsersListSingleton
) : UsersRepository {

    override fun getUserList(): Result<List<UserModel>, Exception> {
        return try {
            Result.Success(usersListSingleton.usersList)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override fun getUserInfo(userIndex: Int): Result<UserModel, Exception> {
        return try {
            Result.Success(usersListSingleton.usersList[userIndex])
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override fun saveUser(
        name: String,
        bio: String,
        avatar: Int
    ): Result<List<UserModel>, Exception> {
        return try {
            usersListSingleton.usersList.add(
                UserModel(
                    name = name,
                    bio = bio,
                    avatar = Avatar(
                        drawable = avatar
                    )
                )
            )
            Result.Success(usersListSingleton.usersList)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override fun updateUserInfo(
        indexList: Int,
        updatedUser: UserModel
    ): Result<List<UserModel>, Exception> {
        return try {
            usersListSingleton.usersList[indexList] = updatedUser
            Result.Success(usersListSingleton.usersList)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override fun removeUser(indexList: Int): Result<List<UserModel>, Exception> {
        return try {
            usersListSingleton.usersList.removeAt(indexList)
            Result.Success(usersListSingleton.usersList)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

}
