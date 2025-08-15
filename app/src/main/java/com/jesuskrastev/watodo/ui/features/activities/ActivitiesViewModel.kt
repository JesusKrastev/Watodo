package com.jesuskrastev.watodo.ui.features.activities

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesuskrastev.watodo.data.ActivityRepository
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

    init {
        loadActivities()
    }

    private fun loadActivities() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                activities = activitiesRepository.get().map { it.toActivityState() },
                isLoading = false,
            )
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

            else -> {}
        }
    }
}