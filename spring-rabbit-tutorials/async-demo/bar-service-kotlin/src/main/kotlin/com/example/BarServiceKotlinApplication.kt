package com.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BarServiceKotlinApplication

fun main(args: Array<String>) {
    runApplication<BarServiceKotlinApplication>(*args)
}
