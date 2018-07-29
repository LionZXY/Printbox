package ru.lionzxy.printbox.view.print_map.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.tbruyelle.rxpermissions2.RxPermissions
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.logo.Alignment
import com.yandex.mapkit.logo.HorizontalAlignment
import com.yandex.mapkit.logo.VerticalAlignment
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.runtime.image.ImageProvider
import kotlinx.android.synthetic.main.activity_print_map.*
import ru.lionzxy.printbox.BuildConfig
import ru.lionzxy.printbox.R
import ru.lionzxy.printbox.data.model.EXTRA_PRINT
import ru.lionzxy.printbox.data.model.PrintPlace
import ru.lionzxy.printbox.utils.toast
import ru.lionzxy.printbox.view.print_map.presenter.PrintMapPresenter

class PrintMapActivity : MvpAppCompatActivity(), IPrintMapView {
    @InjectPresenter
    lateinit var printMapPresenter: PrintMapPresenter
    private val rxPermissions: RxPermissions by lazy { RxPermissions(this) }
    private lateinit var googleApi: FusedLocationProviderClient
    val printerAdapter: PrintAdapter by lazy { PrintAdapter(emptyList()) }
    lateinit var printObjects: MapObjectCollection
    private var selectedPrinter: PrintPlace? = null
    private var printToMark = HashMap<PrintPlace, PlacemarkMapObject>()
    private var currentLocationMark: PlacemarkMapObject? = null
    private var snapHelper = PagerSnapHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey(BuildConfig.MAPKIT_API)
        MapKitFactory.initialize(this)

        setContentView(R.layout.activity_print_map)
        mapview.map.logo.setAlignment(Alignment(HorizontalAlignment.RIGHT, VerticalAlignment.TOP))

        googleApi = LocationServices.getFusedLocationProviderClient(this)

        location_button.setOnClickListener { requestCurPosition() }
        printObjects = mapview.map.mapObjects.addCollection()
    }

    override fun requestCurPosition() {
        rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe({
                    focusCurrentLocation()
                }, {
                    toast(R.string.map_location_access_fail)
                })
    }

    override fun invalidate() {
        val currPrinter = selectedPrinter ?: return

        val position = printerAdapter.setCurrentPrint(currPrinter)
        printers.scrollToPosition(position)
        for ((printPlace, mark) in printToMark) {
            mark.setIcon(if (printPlace == currPrinter) {
                mark.zIndex = 100f
                ImageProvider.fromResource(this@PrintMapActivity, R.drawable.baseline_location_on_black_48)
            } else {
                mark.zIndex = 0f
                ImageProvider.fromResource(this@PrintMapActivity, R.drawable.baseline_location_on_black_36)
            })
        }

        if (printToMark.containsKey(currPrinter)) {
            setActiveNext(true)
        }
    }

    override fun selectPrintPlace(printPlace: PrintPlace) {
        selectedPrinter = printPlace
        invalidate()
        setPosition(CameraPosition(Point(printPlace.latitude, printPlace.longitude),
                15f, 0f, 0f))
    }

    override fun setPrinters(list: List<PrintPlace>) {
        printerAdapter.setList(list)
        printerAdapter.setListener { printMapPresenter.onSelectPrinter(it) }
        printers.adapter = printerAdapter
        printers.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        snapHelper.attachToRecyclerView(printers)

        list.forEach { pp ->
            val mark = printObjects.addPlacemark(Point(pp.latitude, pp.longitude))
            mark.isDraggable = false
            mark.setIcon(ImageProvider.fromResource(this@PrintMapActivity, R.drawable.baseline_location_on_black_36))
            printToMark[pp] = mark
            mark.addTapListener { _, _ -> printMapPresenter.onSelectPrinter(pp);true }
        }

        invalidate()
    }

    override fun setActiveNext(visible: Boolean) {
        next.isClickable = visible
        next.isFocusable = visible

        val color = ContextCompat.getColor(this,
                if (visible) R.color.white else R.color.gray)
        next_text.setTextColor(color)
        next_icon.setColorFilter(color, PorterDuff.Mode.MULTIPLY)

        if (visible) {
            next.setOnClickListener { printMapPresenter.onNext(selectedPrinter) }
        } else {
            next.setOnClickListener(null)
        }
    }

    override fun setPosition(position: CameraPosition) {
        mapview.map.move(position,
                Animation(Animation.Type.SMOOTH, 0.5f),
                null)
    }

    override fun showProgressBarNext(visible: Boolean) {
        next_progressbar.visibility = if (visible) View.VISIBLE else View.GONE
        next_icon.visibility = if (visible) View.GONE else View.VISIBLE
        next_text.visibility = if (visible) View.GONE else View.VISIBLE
    }

    override fun setCurrentPosition(position: CameraPosition) {
        setPosition(position)
        currentLocationMark?.let { mapview.map.mapObjects.remove(it) }
        currentLocationMark = mapview.map.mapObjects.addPlacemark(position.target)
        currentLocationMark?.setIcon(ImageProvider.fromResource(this@PrintMapActivity, com.yandex.mapkit.R.drawable.selected))
        currentLocationMark?.isDraggable = false
    }

    override fun showPrinterLoading(visible: Boolean) {
        printer_progress.visibility = if (visible) View.VISIBLE else View.GONE
        printers.visibility = if (visible) View.GONE else View.VISIBLE
    }

    override fun onError(resId: Int) {
        toast(resId)
    }

    @SuppressLint("MissingPermission")
    private fun focusCurrentLocation() {
        googleApi.lastLocation.addOnCompleteListener { if (it.isSuccessful) printMapPresenter.onLastLocation(it.result) }
    }

    override fun finishWithPrinter(printPlace: PrintPlace) {
        val bundle = Bundle()
        bundle.putParcelable(EXTRA_PRINT, printPlace)
        val intent = Intent()
        intent.putExtras(bundle)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onStop() {
        super.onStop()
        mapview.onStop()
        MapKitFactory.getInstance().onStop()
    }

    override fun onStart() {
        super.onStart()
        mapview.onStart()
        MapKitFactory.getInstance().onStart()
    }
}