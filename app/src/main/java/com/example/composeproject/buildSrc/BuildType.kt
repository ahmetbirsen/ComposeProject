package com.example.composeproject.buildSrc

interface BuildType {

    val flavors: Flavors

    fun isDebuggable(): Boolean = isDev()

    fun isDev(): Boolean = flavors == Flavors.DEVELOPMENT

    fun isProd(): Boolean = flavors == Flavors.PRODUCTION
}
