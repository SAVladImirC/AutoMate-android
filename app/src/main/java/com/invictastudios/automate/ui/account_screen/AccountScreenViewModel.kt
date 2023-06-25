package com.invictastudios.automate.ui.account_screen

import androidx.lifecycle.ViewModel
import com.invictastudios.automate.data.api.api_services.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AccountScreenViewModel @Inject constructor(
    private val apiRepository: ApiRepository
) : ViewModel() {


}