package dv.trubnikov.legends

import android.os.Bundle
import android.service.autofill.UserData
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dv.trubnikov.legends.core_credential.user.UserStore
import javax.inject.Inject

@AndroidEntryPoint
class TempFragment : Fragment() {

    @Inject
    lateinit var userStore: UserStore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.temp_layout, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.temp_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.tempFragment) {
            userStore.value = null
            findNavController().navigate(R.id.login_nav_graph)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}