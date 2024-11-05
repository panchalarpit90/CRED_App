package com.example.cred.model

data class Item(
    val open_state: OpenState,
    val closed_state: ClosedState,
    val cta_text: String
)