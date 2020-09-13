package dv.trubnikov.legends.feature_team.presentation.screens.profile

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import dv.trubnikov.legends.core_models.team.TeamData
import dv.trubnikov.legends.core_models.user.UserData
import dv.trubnikov.legends.core_models.user.UserRole
import dv.trubnikov.legends.feature_team.databinding.TeamProfileFragmentBinding
import dv.trubnikov.legends.feature_team.presentation.screens.profile.adapters.TeamMembersAdapter
import dv.trubnikov.legends.feature_team.presentation.screens.profile.adapters.TeamProfileAdapter

class TeamProfileFragment : Fragment() {

    private lateinit var viewModel: TeamProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bind = TeamProfileFragmentBinding.inflate(inflater, container, false)

        bind.recyclerTeamInfo.adapter = TeamProfileAdapter().apply {
            setTeamData(TeamData(42, "Лесный бобры", 1, 340, 1600, 5, "as2fv41d"))
        }
        bind.recyclerTeamInfo.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        bind.recyclerTeamInfo.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        bind.recyclerMemberInfo.adapter = TeamMembersAdapter().apply {
            setMembers(listOf(
                UserData(1, "trubnikovdv", UserRole.PLAYER, "Дмитрий", "Трубников"),
                UserData(1, "amartoyas", UserRole.PLAYER, "Алексей", "Мартояс"),
                UserData(1, "smalichenko", UserRole.CAPTAIN, "Сергей", "Маличенко"),
                UserData(1, "hllbrnd", UserRole.PLAYER, "Дмитрий", "Тимошилов"),
            ))
        }
        bind.recyclerMemberInfo.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        bind.recyclerMemberInfo.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        return bind.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TeamProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }


}

