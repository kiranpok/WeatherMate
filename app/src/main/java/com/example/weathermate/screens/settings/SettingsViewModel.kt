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

/*@HiltViewModel
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
}*/


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: WeatherDbRepository
) : ViewModel() {

    // MutableStateFlow to hold the list of units
    private val _unitList = MutableStateFlow<List<Unit>>(emptyList())
    val unitList = _unitList.asStateFlow()

    // Tag for logging
    private val TAG = "SettingsViewModel"

    // Initialize and load the current list of units
    init {
        loadUnits()
    }

    /**
     * Load units from the database and handle empty state by adding a default unit.
     */
    private fun loadUnits() {
        viewModelScope.launch {
            repository.getUnits()
                .distinctUntilChanged() // Ensure updates are only sent if the data changes
                .collect { listOfUnits ->
                    if (listOfUnits.isNullOrEmpty()) {
                        Log.d(TAG, "Unit list is empty. Adding a default unit.")
                        addDefaultUnit() // Add default unit if the list is empty
                    } else {
                        Log.d(TAG, "Unit list loaded: ${listOfUnits.map { it.unit }}")
                        _unitList.value = listOfUnits // Update the state flow with the new list
                    }
                }
        }
    }

    /**
     * Add a default unit to the database.
     */
    private suspend fun addDefaultUnit() {
        val defaultUnit = Unit(unit = "Imperial (F)")
        repository.insertUnit(defaultUnit)
        Log.d(TAG, "Default unit added: ${defaultUnit.unit}")
    }

    /**
     * Insert a new unit into the database.
     */
    fun insertUnit(unit: Unit) {
        viewModelScope.launch {
            repository.insertUnit(unit)
            Log.d(TAG, "Unit inserted: ${unit.unit}")
        }
    }

    /**
     * Update an existing unit in the database.
     */
    fun updateUnit(unit: Unit) {
        viewModelScope.launch {
            repository.updateUnit(unit)
            Log.d(TAG, "Unit updated: ${unit.unit}")
        }
    }

    /**
     * Delete all units from the database.
     */
    fun deleteAllUnits() {
        viewModelScope.launch {
            repository.deleteAllUnits()
            Log.d(TAG, "All units deleted")
        }
    }

    /**
     * Delete a specific unit from the database.
     */
    fun deleteUnit(unit: Unit) {
        viewModelScope.launch {
            repository.deleteUnit(unit)
            Log.d(TAG, "Unit deleted: ${unit.unit}")
        }
    }
}

