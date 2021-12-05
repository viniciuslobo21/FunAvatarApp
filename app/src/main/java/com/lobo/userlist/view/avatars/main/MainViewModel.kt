package com.lobo.userlist.view.avatars.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lobo.userlist.core.base.BaseViewModel
import com.lobo.userlist.domain.Avatar
import com.lobo.userlist.domain.UserModel
import com.lobo.userlist.domain.interactor.*

class MainViewModel(
    private val getUserInfo: GetUserInfo,
    private val getUsersList: GetUsersList,
    private val saveUser: SaveUser,
    private val updateUserInfo: UpdateUserInfo,
    private val removeUser: RemoveUser
) : BaseViewModel() {

    var drawable = 0

    private val _users = MutableLiveData<List<UserModel>>()
    val users: LiveData<List<UserModel>>
        get() = _users

    private val _usersUpdated = MutableLiveData<List<UserModel>>()
    val usersUpdated: LiveData<List<UserModel>>
        get() = _usersUpdated

    private val _userInserted = MutableLiveData<List<UserModel>>()
    val userInserted: LiveData<List<UserModel>>
        get() = _userInserted

    private val _userInfo = MutableLiveData<UserModel>()
    val userInfo: LiveData<UserModel>
        get() = _userInfo

    private val _currentUser = MutableLiveData<UserModel>()
    val currentUser: LiveData<UserModel>
        get() = _currentUser

    private val _userAfterDelete = MutableLiveData<List<UserModel>>()
    val userAfterDelete: LiveData<List<UserModel>>
        get() = _userAfterDelete

    fun drawableSelected(drawable: Int) {
        this.drawable = drawable
        updateAvatar()
    }

    private fun updateAvatar() {
        val updatedUser = UserModel(
            name = _userInfo.value?.name.orEmpty(),
            bio = _userInfo.value?.bio.orEmpty(),
            avatar = Avatar(drawable = drawable)
        )
        _currentUser.value = updatedUser
    }

    fun updateUser(
        currentUser: Int,
        name: String,
        bio: String,
        avatar: Int
    ) {
        showLoading()
        updateUserInfo.invoke(
            indexList = currentUser,
            updatedUser = UserModel(
                name = name,
                bio = bio,
                avatar = Avatar(
                    drawable = avatar
                )
            )
        ).mapSuccess {
            _usersUpdated.value = it
        }
    }

    fun fetchUsers() {
        showLoading()
        getUsersList.invoke().mapSuccess {
            _users.value = it
            hideLoading()
        }
    }

    fun saveUser(
        name: String,
        bio: String,
        avatar: Int
    ) {
        showLoading()
        saveUser.invoke(
            name = name,
            bio = bio,
            avatar = avatar
        ).mapSuccess {
            _userInserted.value = it
            hideLoading()
        }
    }

    fun getInfoUser(indexUserList: Int) {
        showLoading()
        getUserInfo.invoke(indexUserList).mapSuccess {
            _userInfo.value = it
            hideLoading()
        }
    }

    fun removeUser(indexUser: Int) {
        showLoading()
        removeUser.invoke(indexUser).mapSuccess {
            hideLoading()
            _userAfterDelete.value = it
        }
    }
}