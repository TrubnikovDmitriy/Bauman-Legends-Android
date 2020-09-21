package dv.trubnikov.legends.feature_team.presentation.profile.recyclers.money

import android.animation.Animator
import android.animation.AnimatorInflater
import android.content.Context
import androidx.core.animation.doOnEnd
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import dv.trubnikov.legends.feature_team.R
import dv.trubnikov.legends.feature_team.presentation.profile.recyclers.money.TeamMoneyViewHolderType.INFORMATION
import dv.trubnikov.legends.utils.android.logv

class TeamMoneyItemAnimator(private val context: Context) : DefaultItemAnimator() {

    override fun animateChange(
        oldHolder: ViewHolder,
        newHolder: ViewHolder,
        preInfo: ItemHolderInfo,
        postInfo: ItemHolderInfo
    ): Boolean {
        logv("${TeamMoneyType.values()[oldHolder.itemViewType].name} -> ${TeamMoneyType.values()[newHolder.itemViewType].name}")
        if (oldHolder == newHolder) {
            dispatchAnimationFinished(oldHolder)
            return false
        }

        val animatorForOld: Animator
        val animatorForNew: Animator
        if (oldHolder.itemViewType != INFORMATION.ordinal) {
            animatorForOld = AnimatorInflater.loadAnimator(context, R.animator.flip_in_first)
            animatorForNew = AnimatorInflater.loadAnimator(context, R.animator.flip_in_second)
            oldHolder.itemView.rotationX = 0f
            newHolder.itemView.rotationX = -90f
        } else {
            animatorForOld = AnimatorInflater.loadAnimator(context, R.animator.flip_out_first)
            animatorForNew = AnimatorInflater.loadAnimator(context, R.animator.flip_out_second)
            oldHolder.itemView.rotationX = 0f
            newHolder.itemView.rotationX = +90f
        }

        flipHolders(oldHolder, newHolder, animatorForOld, animatorForNew)
        return true
    }


    private fun flipHolders(
        oldHolder: ViewHolder,
        newHolder: ViewHolder,
        animatorForOld: Animator,
        animatorForNew: Animator
    ) {
        animatorForOld.apply {
            doOnEnd { dispatchAnimationFinished(oldHolder) }
            setTarget(oldHolder.itemView)
            start()
        }
        animatorForNew.apply {
            doOnEnd { dispatchAnimationFinished(newHolder) }
            setTarget(newHolder.itemView)
            start()
        }
    }
}