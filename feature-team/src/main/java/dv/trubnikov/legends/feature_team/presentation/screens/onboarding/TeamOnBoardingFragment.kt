package dv.trubnikov.legends.feature_team.presentation.screens.onboarding

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dv.trubnikov.legends.feature_team.R

class TeamOnBoardingFragment : Fragment() {

    private lateinit var viewModel: TeamOnBoardingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.team_onboarding_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TeamOnBoardingViewModel::class.java)
        // TODO: Use the ViewModel
    }

}