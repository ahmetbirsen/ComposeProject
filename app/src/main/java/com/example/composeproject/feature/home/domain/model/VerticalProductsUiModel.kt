package com.example.composeproject.feature.home.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VerticalProductsUiModel(
    val id: String = "",
    val name: String = "",
    val attribute: String = "",
    val imageURL: String = "",
    val price: Double = -1.0,
    val priceText: String = ""
) : Parcelable
