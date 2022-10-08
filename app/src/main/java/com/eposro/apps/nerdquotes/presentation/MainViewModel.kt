package com.eposro.apps.nerdquotes.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eposro.apps.nerdquotes.domain.RetrofitInstance
import com.eposro.apps.nerdquotes.domain.entities.ProgrammingQuote
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

class MainViewModel : ViewModel() {
    private val _uiState: MutableLiveData<MainActivityState> = MutableLiveData(MainActivityState())
    val uiState get() = _uiState as LiveData<MainActivityState>

    private val _recentQuotes: MutableList<ProgrammingQuote> = mutableListOf()
    val recentQuotes get() = _recentQuotes as List<ProgrammingQuote>

    fun loadQuote() {
        _uiState.postValue(_uiState.value!!.copy(isLoading = true))
        viewModelScope.launch {

            val response = try {
                RetrofitInstance.api.getRandomQuote()
            } catch (e: IOException) {
                // Most likely no internet connection available.
                _uiState.postValue(
                    _uiState.value!!.copy(
                        isLoading = false,
                        hasError = true,
                        error = "No internet connection."
                    )
                )
                Timber.e(e)
                return@launch
            } catch (e: HttpException) {
                // Unexpected 2xx http response
                _uiState.postValue(
                    _uiState.value!!.copy(
                        isLoading = false,
                        hasError = true,
                        error = "API returned unexpected response"
                    )
                )
                Timber.e(e)
                return@launch
            } catch (e: Throwable) {
                _uiState.postValue(
                    _uiState.value!!.copy(
                        isLoading = false,
                        hasError = true,
                        error = "Something went wrong."
                    )
                )
                Timber.e("An unrecognized error occurred.\n$e")
                return@launch
            }

            if (response.isSuccessful) {
                response.body()?.let {
                    _recentQuotes.add(it)
                }
                _uiState.postValue(_uiState.value!!.copy(isLoading = false, hasError = false))
            }
        }
    }

    init {
        loadQuote()
    }
}