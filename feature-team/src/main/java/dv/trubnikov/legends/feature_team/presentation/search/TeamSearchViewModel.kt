package dv.trubnikov.legends.feature_team.presentation.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dv.trubnikov.legends.core_models.team.TeamData
import dv.trubnikov.legends.feature_team.domain.interactors.TeamInfoInteractor
import dv.trubnikov.legends.utils.android.ioScope
import dv.trubnikov.legends.utils.domain.Result

class TeamSearchViewModel @ViewModelInject constructor(
    private val teamInfo: TeamInfoInteractor
) : ViewModel() {

    private val teams = MutableLiveData<List<TeamData>>().also { retrieveTeams() }
    private val errors = MutableLiveData<String>()

    fun getTeams(): LiveData<List<TeamData>> = teams
    fun getErrors(): LiveData<String> = errors

    fun retrieveTeams() {
        ioScope {
            val result = teamInfo.getAllTeams()
            when(result) {
                is Result.Success -> teams.postValue(result.data.sortedBy { it.teamId })
                is Result.Error -> errors.postValue(result.message)
            }
        }
    }
}