package com.jesuskrastev.watodo.utilities.paginator

class DefaultPaginator<Key, Item>(
    private val initialKey: Key,
    private inline val onLoadUpdated: (Boolean) -> Unit,
    private val onRequest: suspend (nextKey: Key) -> List<Item>,
    private val getNextKey: suspend (List<Item>) -> Key,
    private val onError: (Throwable?) -> Unit,
    private val onSuccess: (items: List<Item>, newKey: Key) -> Unit,
): Paginator<Item> {

    private var currentKey = initialKey
    private var isMakingRequest = false

    override suspend fun loadNextItems() {
        if (isMakingRequest) {
            return
        }
        isMakingRequest = true
        onLoadUpdated(true)
        val result = onRequest(currentKey)
        isMakingRequest = false
        val nextKey = getNextKey(result)
        currentKey = nextKey
        onSuccess(result, nextKey)
        onLoadUpdated(false)
    }

    override fun reset() {
        currentKey = initialKey
    }
}