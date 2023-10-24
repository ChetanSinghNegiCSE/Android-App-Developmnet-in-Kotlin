package com.example.viewmodellivedata

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
//Live Data
class MainActivityViewModel : ViewModel() {
    //var count =0
    var count = MutableLiveData<Int>()

    fun updateCount() {
        //++count
        count.value = (count.value)?.plus(1)
    }

    init {
        count.value=0
    }
}