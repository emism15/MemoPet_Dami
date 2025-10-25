package com.cibertec.memopet_proyecto.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cibertec.memopet_proyecto.ui.InfoFragment
import com.cibertec.memopet_proyecto.ui.ActividadesFragment
import com.cibertec.memopet_proyecto.ui.RecordatoriosFragment

class DetalleMascotaAdapter(
    fragmentActivity: FragmentActivity,
    private val mascotaId: Int
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ActividadesFragment.newInstance(mascotaId)
            1 -> RecordatoriosFragment.newInstance(mascotaId)
            else -> InfoFragment.newInstance(mascotaId)
        }
    }
}