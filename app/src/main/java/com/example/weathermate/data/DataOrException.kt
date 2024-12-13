package com.example.weathermate.data

// A generic data class to handle data, loading state, and exceptions
data class DataOrException<T, Boolean, E : Exception>(
    var data: T? = null,       // Holds the data of type T
    var loading: Boolean? = null,  // Indicates the loading state
    var e: E? = null           // Holds the exception of type E
)