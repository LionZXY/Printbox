package ru.lionzxy.printbox.view.print_map.ui

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import ru.lionzxy.printbox.R
import ru.lionzxy.printbox.view.print_map.presenter.PrintMapPresenter


class PrintMapActivity : MvpAppCompatActivity(), IPrintMapView {
    @InjectPresenter
    lateinit var printMapPresenter: PrintMapPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //MapKitFactory.setApiKey(BuildConfig.MAPKIT_API)
        //MapKitFactory.initialize(this)

        setContentView(R.layout.activity_print_map)
        /*
        mapview.map.move(
                CameraPosition(Point(55.751574, 37.573856), 11.0f, 0.0f, 0.0f),
                Animation(Animation.Type.SMOOTH, 0f),
                null)
    }

    override fun onStop() {
        super.onStop()
        mapview.onStop()
        MapKitFactory.getInstance().onStop()
    }

    override fun onStart() {
        super.onStart()
        mapview.onStart()
        MapKitFactory.getInstance().onStart()*/
    }
}