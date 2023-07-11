package com.example.moviebox.base

import android.app.Application
import com.example.moviebox.data.db.MovieBoxDatabase
import com.pluto.Pluto
import com.pluto.plugins.layoutinspector.PlutoLayoutInspectorPlugin
import com.pluto.plugins.network.PlutoNetworkPlugin
import com.pluto.plugins.rooms.db.PlutoRoomsDBWatcher
import com.pluto.plugins.rooms.db.PlutoRoomsDatabasePlugin
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MovieBoxApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initialisePluto(this)
    }

    private fun initialisePluto(application: Application) {
        Pluto.Installer(application)
            .addPlugin(PlutoNetworkPlugin("network"))
            .addPlugin(PlutoRoomsDatabasePlugin("rooms-db"))
            .addPlugin(PlutoLayoutInspectorPlugin("layout-inspector"))
            .install()

        PlutoRoomsDBWatcher.watch("movie_database", MovieBoxDatabase::class.java)

    }
}