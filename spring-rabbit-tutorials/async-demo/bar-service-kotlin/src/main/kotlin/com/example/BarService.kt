package com.example

import com.hazelcast.core.HazelcastInstance
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class BarService(private val hazelcastInstance: HazelcastInstance) {

    private val logger = LoggerFactory.getLogger(BarService::class.java)

    fun calculateBar(id: String) {
        logger.info("calculateBar for id:{}", id)
        val map = hazelcastInstance.getMap<Any, Any>("bar")
        map.lock(id)
        map.put(id, "work with id $id is done")
        try {
            simulateSlowService() // delay 2 when the third party service is slow
        } finally {
            map.unlock(id)
        }
    }

    private fun simulateSlowService() {
        try {
            val time = 5000L
            Thread.sleep(time)
        } catch (e: InterruptedException) {
            throw IllegalStateException(e)
        }
    }
}