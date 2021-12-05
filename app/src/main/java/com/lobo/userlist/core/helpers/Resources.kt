package com.lobo.userlist.core.helpers

import com.lobo.repogit.core.helpers.ResourceHelper
import org.koin.core.KoinComponent
import org.koin.core.get

object Resources: KoinComponent {
    var resourceHelper: ResourceHelper = get()
}