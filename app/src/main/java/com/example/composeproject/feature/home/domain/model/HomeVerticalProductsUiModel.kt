package com.example.composeproject.feature.home.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HomeVerticalProductsUiModel(
    val id: String = "",
    val name: String = "",
    val productCount: Double = -1.0,
    val products: ArrayList<VerticalProductsUiModel> = arrayListOf()
) : Parcelable