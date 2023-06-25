package com.invictastudios.automate.ui.service_stations_screen


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.invictastudios.automate.data.api.api_services.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class ServiceStationsViewModel @Inject constructor(
    private val apiRepository: ApiRepository
) : ViewModel() {

    val loading = MutableLiveData(false)
    private val disposables = CompositeDisposable()


    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}