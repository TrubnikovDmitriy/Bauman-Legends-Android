package dv.trubnikov.legends.feature_team.presentation.profile.recyclers.money

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dv.trubnikov.legends.feature_team.presentation.profile.recyclers.money.TeamMoneyViewHolderType.*


class TeamMoneyAdapter : RecyclerView.Adapter<TeamMoneyViewHolder>() {

    private var infoPositions = HashSet<Int>()
    private var teamData: List<TeamMoneyRecyclerData> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamMoneyViewHolder {
        return TeamMoneyViewHolder.createViewHolder(parent, TeamMoneyViewHolderType.values()[viewType])
    }

    override fun onBindViewHolder(holder: TeamMoneyViewHolder, position: Int) {
        holder.bindTeamData(teamData[position])
        holder.setToggleClickListener {
            if (!infoPositions.remove(position)) {
                infoPositions.add(position)
            }
            notifyItemChanged(position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            infoPositions.contains(position) -> INFORMATION.ordinal
            teamData[position].type == TeamMoneyType.CODE -> CLICKABLE.ordinal
            else -> MONEY.ordinal
        }
    }

    override fun getItemCount(): Int {
        return teamData.size
    }


    fun setTeamData(teamData: List<TeamMoneyRecyclerData>) {
        this.teamData = teamData
        notifyDataSetChanged()
    }
}
