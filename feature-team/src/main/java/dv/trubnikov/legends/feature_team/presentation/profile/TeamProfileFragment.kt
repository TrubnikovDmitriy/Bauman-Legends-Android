package dv.trubnikov.legends.feature_team.presentation.profile

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dv.trubnikov.legends.feature_team.R
import dv.trubnikov.legends.feature_team.databinding.TeamProfileFragmentBinding
import dv.trubnikov.legends.feature_team.presentation.profile.recyclers.members.TeamMembersAdapter
import dv.trubnikov.legends.feature_team.presentation.profile.recyclers.money.TeamMoneyAdapter
import dv.trubnikov.legends.feature_team.presentation.profile.recyclers.money.TeamMoneyItemAnimator
import dv.trubnikov.legends.feature_team.presentation.profile.recyclers.money.TeamMoneyRecyclerData
import dv.trubnikov.legends.feature_team.presentation.profile.recyclers.money.TeamMoneyType
import dv.trubnikov.legends.utils.android.hide
import dv.trubnikov.legends.utils.android.show
import dv.trubnikov.legends.utils.domain.appstate.AppState.*
import dv.trubnikov.legends.utils.domain.appstate.AppStateCenter
import dv.trubnikov.legends.utils.domain.appstate.AppStateResolver
import javax.inject.Inject
import dv.trubnikov.legends.feature_team.presentation.profile.TeamProfileFragmentDirections.Companion.actionTeamProfileFragmentToTeamOnboardingFragment as goToOnBoardingFragment

@AndroidEntryPoint
class TeamProfileFragment : Fragment() {

    @Inject lateinit var appStateCenter: AppStateCenter
    @Inject lateinit var appStateResolver: AppStateResolver
    private val viewModel: TeamProfileViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appStateCenter.getEvents().observe(this) { event -> when {
            event has NEED_AUTH -> findNavController().navigate(appStateResolver.navigationForState(NEED_AUTH))
            event has NEED_NETWORK -> findNavController().navigate(appStateResolver.navigationForState(NEED_NETWORK))
            event has NEED_TEAM -> findNavController().navigate(goToOnBoardingFragment())
        }}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val bind = TeamProfileFragmentBinding.inflate(inflater, container, false)

        hideContent(bind)
        initMoneyInfo(bind)
        initMembersInfo(bind)
        initViewListeners(bind)

        return bind.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.team_profile_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_leave_team -> {
                viewModel.leaveTeam()
                return true
            }
        }
        return false
    }


    private fun initViewListeners(bind: TeamProfileFragmentBinding) {
        bind.root.setOnRefreshListener {
            viewModel.retrieveTeamInfo()
        }
    }

    private fun initMoneyInfo(bind: TeamProfileFragmentBinding) {
        val adapter = TeamMoneyAdapter()
        bind.recyclerTeamMoney.layoutManager = LinearLayoutManager(context)
        bind.recyclerTeamMoney.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        bind.recyclerTeamMoney.itemAnimator = TeamMoneyItemAnimator(requireContext())
        bind.recyclerTeamMoney.adapter = adapter

        viewModel.getTeamData().observe(viewLifecycleOwner) { team ->
            bind.root.isRefreshing = false
            bind.recyclerTeamMoney.show()

            val data = mutableListOf(
                TeamMoneyRecyclerData(TeamMoneyType.SCORE, team.score.toString()),
                TeamMoneyRecyclerData(TeamMoneyType.MONEY, team.money.toString())
            )
            val inviteCode = team.inviteCode
            if (inviteCode != null) {
                data.add(TeamMoneyRecyclerData(TeamMoneyType.CODE, inviteCode))
            }
            adapter.setTeamData(data)

            bind.teamName.text = context?.getString(R.string.team_name_format, team.teamName)
            bind.teamAvatar.setTeamName(team.teamId.toString())
        }
    }

    private fun initMembersInfo(bind: TeamProfileFragmentBinding) {
        val adapter = TeamMembersAdapter()
        bind.recyclerTeamMembers.layoutManager = LinearLayoutManager(context)
        bind.recyclerTeamMembers.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        bind.recyclerTeamMembers.adapter = adapter

        viewModel.getMembersData().observe(viewLifecycleOwner) { members ->
            bind.progressBar.hide()
            bind.headerMembers.show()
            bind.recyclerTeamMembers.show()

            adapter.setMembers(members)
        }
    }

    private fun hideContent(bind: TeamProfileFragmentBinding) {
        bind.progressBar.show()
        bind.headerMembers.hide()
        bind.recyclerTeamMembers.hide()
        bind.recyclerTeamMoney.hide()
    }
}
