package com.jillesvangurp.ktsearch

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestResult
import kotlin.random.Random
import kotlin.random.nextULong
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds


expect fun coTest(timeout: Duration = 30.seconds, block: suspend () -> Unit): TestResult

/**
 * Base class for search tests. Use together with the gradle compose plugin.
 *
 * It will talk to whatever runs on port 9999. That's intentionally different
 * from the default port, so you don't fill your production cluster with test data.
 */
open class SearchTestBase() {
    // make sure we use the same client in all tests
    val client by lazy { SearchClient(sharedClient) }

    fun randomIndexName() = "index-${Random.nextULong()}"

    suspend fun testDocumentIndex(): String {
        val index = randomIndexName()
        return TestDocument.mapping.let {
            client.createIndex(index,it)
        }.index
    }

    companion object {
        private val sharedClient by lazy {
            KtorRestClient(
                nodes = arrayOf(Node("127.0.0.1", 9999)),
                client = defaultKtorHttpClient(true)
            )
        }
    }
}
