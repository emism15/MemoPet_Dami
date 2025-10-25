package com.cibertec.memopet_proyecto.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.cibertec.memopet_proyecto.R
import com.cibertec.memopet_proyecto.adapters.DetalleMascotaAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetalleMascotaActivity : AppCompatActivity() {
    private lateinit var tlPestañas : TabLayout
    private lateinit var vpContenido : ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_mascota)

        tlPestañas = findViewById(R.id.tlPestañas)
        vpContenido = findViewById(R.id.vpContenido)


        val mascotaId = intent.getIntExtra("MASCOTA_ID", 0)
        val adapter = DetalleMascotaAdapter(this, mascotaId)
        vpContenido.adapter = adapter



        val tabIcons = listOf(
            R.drawable.ic_home,
            R.drawable.ic_notifi,
            R.drawable.ic_info
        )

        TabLayoutMediator(tlPestañas, vpContenido) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Actividades"
                    tab.setIcon(tabIcons[position])
                }
                1 -> {
                    tab.text = "Recordatorios"
                    tab.setIcon(tabIcons[position])
                }
                2 -> {
                    tab.text = "Información"
                    tab.setIcon(tabIcons[position])
                }
            }
        }.attach()
    }
}