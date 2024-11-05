package com.example.cred.model

data class OpenStateBody(
    val title: String,
    val subtitle: String,
    val card: Card? = null,
    val items: List<PlanItem>? = null,
    val footer: String
)
