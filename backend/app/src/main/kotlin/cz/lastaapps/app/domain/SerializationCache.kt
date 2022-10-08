package cz.lastaapps.app.domain

import kotlinx.coroutines.flow.Flow

interface SerializationCache {
    fun getState(): Flow<String>
}