package dv.trubnikov.legends.feature_team.presentation.profile.recyclers.money

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import dv.trubnikov.legends.feature_team.R


data class TeamMoneyRecyclerData(
    val type: TeamMoneyType,
    val value: String,
)

enum class TeamMoneyType(
    @StringRes val valueName: Int,
    @StringRes val valueDescription: Int,
    @DrawableRes val valueIcon: Int
) {
    SCORE(R.string.profile_score, R.string.profile_score_info, R.drawable.icon_profile_score),
    MONEY(R.string.profile_money, R.string.profile_money_info, R.drawable.icon_profile_money),
    CODE(R.string.profile_code, R.string.profile_code_info, R.drawable.icon_profile_code)
}
