package com.lobo.userlist.core.di

import com.lobo.userlist.domain.interactor.*
import com.lobo.userlist.repository.UsersRepository
import com.lobo.userlist.repository.UsersRepositoryImpl
import com.lobo.userlist.view.avatars.UsersListSingleton
import com.lobo.userlist.view.avatars.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { UsersListSingleton() }
}

val viewModelModule = module {
    viewModel {
        MainViewModel(
            getUserInfo = get(),
            getUsersList = get(),
            saveUser = get(),
            updateUserInfo = get(),
            removeUser = get()
        )
    }
}

val useCaseModule = module {
    factory { GetUserInfo(repository = get()) }
    factory { GetUsersList(repository = get()) }
    factory { SaveUser(repository = get()) }
    factory { UpdateUserInfo(repository = get()) }
    factory { RemoveUser(repository = get()) }
}


val repositoryModule = module {
    single<UsersRepository> {
        UsersRepositoryImpl(
            usersListSingleton = get()
        )
    }
}

