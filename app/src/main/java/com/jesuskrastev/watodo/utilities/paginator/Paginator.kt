package com.jesuskrastev.watodo.utilities.paginator

interface Paginator<Item> {
    suspend fun loadNextItems()
    fun reset()
}