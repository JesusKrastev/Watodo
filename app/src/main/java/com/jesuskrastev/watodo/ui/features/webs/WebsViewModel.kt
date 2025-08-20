package com.jesuskrastev.watodo.ui.features.webs

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesuskrastev.watodo.data.WebRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WebsViewModel @Inject constructor(
    private val websRepository: WebRepository,
) : ViewModel() {
    private val _state = MutableStateFlow<WebsState>(WebsState())
    val state: StateFlow<WebsState> = _state

    init {
        loadWebs()
    }

    private fun loadWebs() {
        _state.value = WebsState(
            isLoading = true,
        )
        viewModelScope.launch {
            websRepository.get().collect { webs ->
                _state.value = WebsState(
                    webs = webs.map { web ->
                        web.toWebState()
                    },
                    isLoading = false,
                )
            }
        }
    }

    private fun onExpandWeb(web: WebState) {
        val expandedWebs = _state.value.expandedWebs.toMutableList()

        if (expandedWebs.contains(web.id))
            expandedWebs.remove(web.id)
        else
            expandedWebs.add(web.id)
        _state.value = _state.value.copy(
            expandedWebs = expandedWebs
        )
    }

    fun onEvent(event: WebsEvent) {
        when (event) {
            is WebsEvent.OnExpandWeb -> {
                onExpandWeb(event.web)
            }
            is WebsEvent.OnOpenWeb -> {
                val intent = Intent(Intent.ACTION_VIEW, event.web.url)
                event.context.startActivity(intent)
            }
        }
    }
}