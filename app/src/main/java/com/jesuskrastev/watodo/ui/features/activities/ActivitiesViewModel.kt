package com.jesuskrastev.watodo.ui.features.activities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesuskrastev.watodo.data.ActivityRepository
import com.jesuskrastev.watodo.data.UserRepository
import com.jesuskrastev.watodo.data.services.authentication.AuthServiceImplementation
import com.jesuskrastev.watodo.models.Activity
import com.jesuskrastev.watodo.ui.features.UserState
import com.jesuskrastev.watodo.ui.features.toUser
import com.jesuskrastev.watodo.ui.features.toUserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivitiesViewModel @Inject constructor(
    private val activitiesRepository: ActivityRepository,
    private val userRepository: UserRepository,
    private val authService: AuthServiceImplementation,
) : ViewModel() {
    private val _state = MutableStateFlow<ActivitiesState>(ActivitiesState())
    val state: StateFlow<ActivitiesState> = _state

    init {
        viewModelScope.launch {
            loadUser()
            loadActivities()
        }
    }

    private suspend fun loadUser() {
        val userId = authService.getUser()?.uid

        if(userId == null) return

        _state.value = _state.value.copy(
            user = userRepository.getById(userId)?.toUserState() ?: UserState(),
        )
    }

    private suspend fun loadActivities() {
        _state.value = _state.value.copy(
            activities = activitiesRepository.get().map { it.toActivityState() },
            isLoading = false,
        )
    }

    private fun Activity.toActivityState(): ActivityState =
        ActivityState(
            id = id,
            title = title,
            description = description,
            isSaved = _state.value.user.saved.contains(id)
        )

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

    private fun onSaveActivity(activity: ActivityState) {
        val savedActivity = _state.value.activities.find { it.id == activity.id }
        val user = _state.value.user

        if (savedActivity == null) return

        val savedActivities = user.saved.toMutableList().apply {
            if (contains(savedActivity.id)) remove(savedActivity.id)
            else add(savedActivity.id)
        }
        val updatedActivities = _state.value.activities.map { act ->
            if (act.id == activity.id) {
                act.copy(isSaved = !act.isSaved)
            } else act
        }
        val updatedUser = user.copy(saved = savedActivities)
        _state.value = _state.value.copy(
            user = updatedUser,
            activities = updatedActivities,
        )
        viewModelScope.launch {
            userRepository.update(updatedUser.toUser())
        }
    }

    fun onEvent(event: ActivitiesEvent) {
        when (event) {
            is ActivitiesEvent.OnExpandActivity -> {
                onExpandActivity(event.activity)
            }

            is ActivitiesEvent.OnSaveActivity -> {
                if (authService.isAuthenticated()) {
                    onSaveActivity(
                        activity = event.activity,
                    )
                } else {
                    event.onNavigateToLogin()
                }
            }

            else -> {}
        }
    }
}