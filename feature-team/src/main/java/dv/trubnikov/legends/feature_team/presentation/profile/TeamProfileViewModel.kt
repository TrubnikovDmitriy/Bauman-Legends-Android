package dv.trubnikov.legends.feature_team.presentation.profile

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dv.trubnikov.legends.core_models.team.TeamData
import dv.trubnikov.legends.core_models.user.UserData
import dv.trubnikov.legends.feature_team.domain.interactors.TeamChangeInteractor
import dv.trubnikov.legends.feature_team.domain.interactors.TeamCreateInteractor
import dv.trubnikov.legends.feature_team.domain.interactors.TeamInfoInteractor
import dv.trubnikov.legends.utils.android.ioScope
import dv.trubnikov.legends.utils.domain.Result

class TeamProfileViewModel @ViewModelInject constructor(
    private val teamInfo: TeamInfoInteractor,
    private val teamChange: TeamChangeInteractor,
    private val teamCreate: TeamCreateInteractor
) : ViewModel() {

    private val errors = MutableLiveData<String?>()
    private val teamData = MutableLiveData<TeamData>().also { retrieveTeamInfo() }
    private val membersData = MutableLiveData<List<UserData>>()

    fun getErrors(): LiveData<String?> = errors
    fun getTeamData(): LiveData<TeamData> = teamData
    fun getMembersData(): LiveData<List<UserData>> = membersData

    fun retrieveTeamInfo() {
        ioScope {
            val result = teamInfo.getTeamInfo()
            when(result) {
                is Result.Success -> {
                    teamData.postValue(result.data)
                    retrieveMembers()
                }
                is Result.Error -> errors.postValue(result.message)
            }
        }
    }

    fun retrieveMembers() {
        ioScope {
            val result = teamInfo.getTeamMembers()
            when(result) {
                is Result.Success -> membersData.postValue(result.data)
                is Result.Error -> errors.postValue(result.message)
            }
        }
    }

    fun renameTeam(teamName: String) {
        ioScope {
            val result = teamChange.renameTeam(teamName)
            when(result) {
                is Result.Success -> teamData.postValue(result.data)
                is Result.Error -> errors.postValue(result.message)
            }
        }
    }

    fun kickMember(kickId: Long) {
        ioScope {
            val result = teamChange.kickMember(kickId)
            when(result) {
                is Result.Success -> retrieveMembers()
                is Result.Error -> errors.postValue(result.message)
            }
        }
    }

    fun changeCaptain(captainId: Long) {
        ioScope {
            val result = teamChange.changeCaptain(captainId)
            when(result) {
                is Result.Success -> retrieveMembers()
                is Result.Error -> errors.postValue(result.message)
            }
        }
    }

    fun leaveTeam() {
        ioScope {
            teamCreate.leaveTeam()
        }
    }
}
