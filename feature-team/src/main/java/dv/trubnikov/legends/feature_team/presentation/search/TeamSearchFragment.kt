package dv.trubnikov.legends.feature_team.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dv.trubnikov.legends.feature_team.R
import dv.trubnikov.legends.feature_team.databinding.TeamSearchFragmentBinding
import dv.trubnikov.legends.feature_team.presentation.search.TeamSearchFragmentDirections.Companion.actionTeamSearchFragmentToTeamJoinDialog as goToJoinTeamDialog

@AndroidEntryPoint
class TeamSearchFragment : Fragment() {

    private val viewModel: TeamSearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.team_search_fragment, container, false)

        val bind = TeamSearchFragmentBinding.bind(view)
        observeData(bind)

        return view
    }

    private fun observeData(bind: TeamSearchFragmentBinding) {
        val adapter = TeamSearchAdapter { teamId ->
            val action = goToJoinTeamDialog(teamId)
            findNavController().navigate(action)
        }

        bind.recyclerTeamsList.adapter = adapter
        bind.recyclerTeamsList.layoutManager = LinearLayoutManager(context)
        bind.recyclerTeamsList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        viewModel.getTeams().observe(viewLifecycleOwner) { teams ->
            bind.root.isRefreshing = false
            adapter.setData(teams)
        }

        bind.root.setOnRefreshListener {
            viewModel.retrieveTeams()
        }
    }
}