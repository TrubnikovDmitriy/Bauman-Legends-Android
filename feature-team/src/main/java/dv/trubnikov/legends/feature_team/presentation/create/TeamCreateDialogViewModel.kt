package dv.trubnikov.legends.feature_team.presentation.create

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dv.trubnikov.legends.core_models.team.TeamData
import dv.trubnikov.legends.feature_team.domain.interactors.TeamCreateInteractor
import dv.trubnikov.legends.utils.domain.Result
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TeamCreateDialogViewModel @ViewModelInject constructor(
    private val teamCreate: TeamCreateInteractor
) : ViewModel() {

    private val errors = MutableLiveData<String?>(null)
    private val teamData = MutableLiveData<TeamData>(null)

    fun getTeamData(): LiveData<TeamData> = teamData
    fun getErrors(): LiveData<String?> = errors

    fun createTeam(teamName: String) {
        viewModelScope.launch {
            withContext(IO) {
                val result = teamCreate.createTeam(teamName)
                when(result) {
                    is Result.Success -> teamData.postValue(result.data)
                    is Result.Error -> errors.postValue(result.message)
                }
            }
        }
    }
}