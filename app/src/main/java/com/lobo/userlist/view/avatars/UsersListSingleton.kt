package com.lobo.userlist.view.avatars

import com.lobo.userlist.domain.UserModel

data class UsersListSingleton(
    var usersList: MutableList<UserModel> = mutableListOf()
)
