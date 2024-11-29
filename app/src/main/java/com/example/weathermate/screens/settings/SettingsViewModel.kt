package com.example.weathermate.screens.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathermate.model.Unit
import com.example.weathermate.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: WeatherDbRepository
) : ViewModel() {

    // MutableStateFlow to hold the list of units
    private val _unitList = MutableStateFlow<List<Unit>>(emptyList())
    val unitList = _unitList.asStateFlow()

    // Initialize and load the current list of units
    init {
        viewModelScope.launch {
            repository.getUnits()
                .distinctUntilChanged() // Ensure updates are only sent if the data changes
                .collect { listOfUnits ->
                    if (listOfUnits.isNullOrEmpty()) {
                        Log.d("SettingsViewModel", "Unit list is empty")
                    } else {
                        _unitList.value = listOfUnits // Update the state flow with the new list
                    }
                }
        }
    }

    // Insert a new unit into the database
    fun insertUnit(unit: Unit) = viewModelScope.launch {
        repository.insertUnit(unit)
    }

    // Update an existing unit in the database
    fun updateUnit(unit: Unit) = viewModelScope.launch {
        repository.updateUnit(unit)
    }

    // Delete all units from the database
    fun deleteAllUnits() = viewModelScope.launch {
        repository.deleteAllUnits()
    }

    // Delete a specific unit from the database
    fun deleteUnit(unit: Unit) = viewModelScope.launch {
        repository.deleteUnit(unit)
    }
}


