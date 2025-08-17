package com.jesuskrastev.watodo.ui.features.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesuskrastev.watodo.data.ActivityRepository
import com.jesuskrastev.watodo.data.UserRepository
import com.jesuskrastev.watodo.data.services.authentication.AuthServiceImplementation
import com.jesuskrastev.watodo.models.Activity
import com.jesuskrastev.watodo.ui.features.UserState
import com.jesuskrastev.watodo.ui.features.activities.ActivityState
import com.jesuskrastev.watodo.ui.features.toUser
import com.jesuskrastev.watodo.ui.features.toUserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
    private val activitiesRepository: ActivityRepository,
    private val userRepository: UserRepository,
    private val authService: AuthServiceImplementation,
) : ViewModel() {
    private val _state = MutableStateFlow<SavedState>(SavedState())
    val state: StateFlow<SavedState> = _state

    init {
        loadActivities()
    }

    private fun loadActivities() {
        val userId = authService.getUser()?.uid ?: return

        viewModelScope.launch {
            _state.value = SavedState(isLoading = true)
            combine(
                userRepository.getByIdFlow(userId),
                activitiesRepository.get()
            ) { user, activities ->
                val userState = user?.toUserState() ?: UserState()
                val activitiesState = activities
                    .map { it.toActivityState() }
                    .filter { userState.saved.contains(it.id) }

                SavedState(
                    user = userState,
                    activities = activitiesState,
                    isLoading = false
                )
            }.collect { newState ->
                _state.value = newState
            }
        }
    }

    private fun Activity.toActivityState(): ActivityState =
        ActivityState(
            id = id,
            title = title,
            description = description,
            isSaved = true,
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

        if(savedActivity == null) return

        val savedActivities = user.saved
            .toMutableList()
            .apply {
                remove(savedActivity.id)
            }
        val updatedUser = user.copy(saved = savedActivities)
        viewModelScope.launch {
            launch {
                userRepository.update(updatedUser.toUser())
            }
        }
    }

    fun onEvent(event: SavedEvent) {
        when (event) {
            is SavedEvent.OnExpandActivity -> {
                onExpandActivity(event.activity)
            }

            is SavedEvent.OnSaveActivity -> {
                onSaveActivity(
                    activity = event.activity,
                )
            }

            else -> {}
        }
    }
}