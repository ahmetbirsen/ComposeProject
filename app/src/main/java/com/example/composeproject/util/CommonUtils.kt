package com.example.composeproject.util


import com.example.composeproject.BuildConfig
import com.example.composeproject.buildSrc.BuildType
import com.example.composeproject.buildSrc.Flavors
import javax.inject.Inject

class CommonUtils @Inject constructor() : BuildType {

    override val flavors: Flavors
        get() {
            return when (BuildConfig.BUILD_TYPE) {
                AppConstants.DEV -> Flavors.DEVELOPMENT
                AppConstants.PROD -> Flavors.PRODUCTION
                else -> Flavors.DEVELOPMENT
            }
        }
}
