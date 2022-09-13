package com.nikitosii.domain.repository.base

import android.util.LruCache
import com.nikitosii.core.base.channel.ErrorHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext

@Suppress("UNCHECKED_CAST")
open class BaseRepo(private val networkErrorHandler: ErrorHandler) : CoroutineScope {

    override val coroutineContext: CoroutineContext =
        Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    fun clearCache() {
        cache = createCache()
    }

    suspend fun <T> runWithErrorHandler(block: suspend () -> T) =
        networkErrorHandler.runWithErrorHandler(block)

    suspend fun <T> runCached(key: String, rewrite: Boolean = false, block: suspend () -> T) =
        if (rewrite) runCached(key, block) else cache[key] as? T ?: runCached(key, block)

    private suspend fun <T> runCached(key: String, block: suspend () -> T) =
        block().also { cache.put(key, it) }

    companion object {
        private const val DEFAULT_CACHE_LIMIT = 10
        private var cache = createCache()

        private fun createCache() = LruCache<String, Any>(DEFAULT_CACHE_LIMIT)
    }
}