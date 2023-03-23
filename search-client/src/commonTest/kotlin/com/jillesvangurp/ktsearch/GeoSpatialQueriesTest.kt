package com.jillesvangurp.ktsearch

import com.jillesvangurp.searchdsls.querydsl.GeoBoundingBoxQuery
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class GeoSpatialQueriesTest : SearchTestBase() {

    @Test
    fun shouldDoBoundingBoxTest() = coRun {
        val index = testDocumentIndex()
        client.bulk(target = index, refresh = Refresh.WaitFor) {
            create(TestDocument("1", point = listOf(13.0, 52.0)))
        }
        client.search(index) {
            query = GeoBoundingBoxQuery(TestDocument::point) {
                topLeft(arrayOf(12.0, 53.0))
                bottomRight(arrayOf(14.0, 51.0))
            }
        }.total shouldBe 1
        
        client.search(index) {
            query = GeoBoundingBoxQuery(TestDocument::point) {
                topLeft(arrayOf(14.0, 53.0))
                bottomRight(arrayOf(16.0, 51.0))
            }
        }.total shouldBe 0

    }
}