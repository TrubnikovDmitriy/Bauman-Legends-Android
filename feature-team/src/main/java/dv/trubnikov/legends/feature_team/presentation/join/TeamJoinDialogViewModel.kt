package dv.trubnikov.legends.feature_team.presentation.join

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dv.trubnikov.legends.core_models.team.TeamData
import dv.trubnikov.legends.feature_team.domain.interactors.TeamCreateInteractor
import dv.trubnikov.legends.utils.android.ioScope
import dv.trubnikov.legends.utils.domain.Result

class TeamJoinDialogViewModel @ViewModelInject constructor(
    private val teamCreate: TeamCreateInteractor
) : ViewModel() {

    private val errors = MutableLiveData<String>()
    private val teamData = MutableLiveData<TeamData>()

    fun getTeamData(): LiveData<TeamData> = teamData
    fun getErrors(): LiveData<String?> = errors

    fun joinToTeam(teamId: Long, inviteCode: String) {
        ioScope {
            val result = teamCreate.joinToTeam(teamId, inviteCode)
            when(result) {
                is Result.Success -> teamData.postValue(result.data)
                is Result.Error -> errors.postValue(result.message)
            }
        }
    }
}
