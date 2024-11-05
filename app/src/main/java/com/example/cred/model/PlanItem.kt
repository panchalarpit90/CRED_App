package com.example.cred.model

data class PlanItem(
    val id: Int,
    val emi: String,
    val duration: String,
    val title: String,
    val subtitle: String,
    val tag: String? = null,
    var isSelected: Boolean = false

)
