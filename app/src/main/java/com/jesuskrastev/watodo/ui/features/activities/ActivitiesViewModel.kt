package com.jesuskrastev.watodo.ui.features.activities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesuskrastev.watodo.data.ActivityRepository
import com.jesuskrastev.watodo.utilities.paginator.DefaultPaginator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivitiesViewModel @Inject constructor(
    private val activitiesRepository: ActivityRepository
) : ViewModel() {
    private val _state = MutableStateFlow<ActivitiesState>(ActivitiesState())
    val state: StateFlow<ActivitiesState> = _state
    private val paginator = DefaultPaginator(
        initialKey = state.value.page,
        onLoadUpdated = {
            _state.value = _state.value.copy(isLoading = it)
        },
        onRequest = { nextPage ->
            activitiesRepository.getWithPagination(nextPage, 20)
        },
        getNextKey = {
            _state.value.page + 1
        },
        onError = {
            // TODO:
        },
        onSuccess = { items, newKey ->
            _state.value = _state.value.copy(
                activities = _state.value.activities + items.map { it.toActivityState() },
                page = newKey,
                endReached = items.isEmpty(),
            )
        }
    )

    init {
        loadActivities()
    }

    private fun loadActivities() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    private fun onExpandActivity(activity: ActivityState) {
        val expandedActivities = _state.value.expandedActivities.toMutableList()

        if (expandedActivities.contains(activity.id))
            expandedActivities.remove(activity.id)
        else
            expandedActivities.add(activity.id)
        _state.value = _state.value.copy(
            expandedActivities = expandedActivities
        )
    }

    fun onEvent(event: ActivitiesEvent) {
        when (event) {
            is ActivitiesEvent.OnExpandActivity -> {
                onExpandActivity(event.activity)
            }

            is ActivitiesEvent.OnLoadMoreActivities -> {
                loadActivities()
            }

            else -> {}
        }
    }
}