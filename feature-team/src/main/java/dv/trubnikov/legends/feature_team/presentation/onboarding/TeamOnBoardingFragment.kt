package dv.trubnikov.legends.feature_team.presentation.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dv.trubnikov.legends.feature_team.R
import dv.trubnikov.legends.feature_team.databinding.TeamOnboardingFragmentBinding
import dv.trubnikov.legends.utils.domain.appstate.AppState.NEED_AUTH
import dv.trubnikov.legends.utils.domain.appstate.AppStateCenter
import dv.trubnikov.legends.utils.domain.appstate.AppStateResolver
import javax.inject.Inject
import dv.trubnikov.legends.feature_team.presentation.onboarding.TeamOnBoardingFragmentDirections.Companion.actionTeamOnboardingFragmentToTeamCreateDialog as goToCreateTeam
import dv.trubnikov.legends.feature_team.presentation.onboarding.TeamOnBoardingFragmentDirections.Companion.actionTeamOnboardingFragmentToTeamSearchFragment as goToSearchTeam

@AndroidEntryPoint
class TeamOnBoardingFragment : Fragment() {

    @Inject lateinit var appStateCenter: AppStateCenter
    @Inject lateinit var appStateResolver: AppStateResolver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appStateCenter.getEvents().observe(this) { event -> when {
            event has NEED_AUTH -> findNavController().navigate(appStateResolver.navigationForState(NEED_AUTH))
        }}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.team_onboarding_fragment, container, false)

        val bind = TeamOnboardingFragmentBinding.bind(view)
        setUpClickListeners(bind)

        return view
    }

    private fun setUpClickListeners(bind: TeamOnboardingFragmentBinding) {
        bind.cardCreateTeam.setOnClickListener {
            val action = goToCreateTeam()
            findNavController().navigate(action)
        }
        bind.cardJoinTeam.setOnClickListener {
            val action = goToSearchTeam()
            findNavController().navigate(action)
        }
    }
}