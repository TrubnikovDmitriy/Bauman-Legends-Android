package dv.trubnikov.legends.utils.android

import androidx.recyclerview.widget.DiffUtil

class SillyDiffUtilsCallback<T>(
    private val oldData: List<T>,
    private val newData: List<T>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldData.size

    override fun getNewListSize(): Int = newData.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = true

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldData[oldItemPosition] == newData[newItemPosition]
    }
}