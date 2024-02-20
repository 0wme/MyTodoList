package fr.iut.mytodolist.android

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val approvedTodoList = MutableLiveData<MutableList<String>>(mutableListOf())
    val approvedDateTimeList = MutableLiveData<MutableList<String>>(mutableListOf())
}