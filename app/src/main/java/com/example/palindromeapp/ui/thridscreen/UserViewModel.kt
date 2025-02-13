package com.example.palindromeapp.ui.thridscreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.palindromeapp.data.remote.response.DataItem
import com.example.palindromeapp.data.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val repository = UserRepository()

    private val _users = MutableLiveData<List<DataItem>>()
    val users: LiveData<List<DataItem>> get() = _users

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> get() = _isError

    private var currentPage = 1
    private var allUsers = mutableListOf<DataItem>()

    fun fetchUsers(isRefreshing: Boolean = false) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                if (isRefreshing) {
                    currentPage = 1
                    allUsers.clear()
                }

                val response = repository.getUsers(currentPage)
                val newData = response.data?.filterNotNull() ?: emptyList()

                if (newData.isNotEmpty()) {
                    if (isRefreshing) {
                        allUsers = newData.toMutableList()
                    } else {
                        allUsers.addAll(newData)
                    }
                    _users.postValue(allUsers)
                    currentPage++
                }

            } catch (e: Exception) {
                _isError.postValue(true)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun loadMoreUsers() {
        if (_isLoading.value == true) return
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                val response = repository.getUsers(currentPage)
                val newUsers = response.data?.filterNotNull() ?: emptyList()

                Log.d("UserViewModel", "Loaded more users: ${newUsers.size}")

                if (newUsers.isNotEmpty()) {
                    allUsers.addAll(newUsers)
                    _users.postValue(allUsers)
                    currentPage++
                } else {
                    _users.postValue(allUsers)
                }
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error loading more users", e)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

}