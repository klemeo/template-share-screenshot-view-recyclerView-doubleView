package ru.android.easyscreenshots.home

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_home.*
import ru.android.easyscreenshots.R
import ru.android.easyscreenshots.Screenshot
import ru.android.easyscreenshots.base.FragmentListenerUtils
import ru.android.easyscreenshots.base.SimpleScreenshot.createScreenImageFile
import ru.android.easyscreenshots.base.SimpleScreenshot.getScreenBitmapFromView
import ru.android.easyscreenshots.base.SimpleScreenshot.getScreenshotFromRecyclerView
import ru.android.easyscreenshots.base.SimpleScreenshot.mergeMultiple
import ru.android.easyscreenshots.databinding.FragmentHomeBinding
import java.io.File

class HomeFragment : Fragment(), HomeAdapter.Listener {

    private lateinit var screenshotContract: Screenshot

    private val homeAdapter = HomeAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        screenshotContract =
            FragmentListenerUtils.getFragmentListener(this, Screenshot::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        homeAdapter.setListener(this)
        with(recyclerView) {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = homeAdapter
        }
        homeAdapter.add(
            listOf(
                Model(
                    number = 1
                ),
                Model(
                    number = 2
                ), Model(
                    number = 3
                ),
                Model(
                    number = 4
                ),
                Model(
                    number = 5
                ), Model(
                    number = 6
                ),
                Model(
                    number = 7
                ),
                Model(
                    number = 8
                ), Model(
                    number = 9
                ),
                Model(
                    number = 10
                ),
                Model(
                    number = 11
                ), Model(
                    number = 12
                ),
                Model(
                    number = 13
                )
            )
        )
        sharedAllView.setOnClickListener {
            val file = createScreenImageFile("AllView", ".jpg")
            mergeMultiple(
                getScreenBitmapFromView(imageSharedView),
                getScreenshotFromRecyclerView(recyclerView)
            )?.let {
                screenshot("AllView", file, it)
            }
        }
        imageSharedView.setOnClickListener {
            val bitmap = getScreenBitmapFromView(imageSharedView)
            val file = createScreenImageFile("ImageView", ".jpg")
            screenshot("ImageView", file, bitmap)
        }
        sharedRecyclerView.setOnClickListener {
            val bitmap = getScreenshotFromRecyclerView(recyclerView)
            val file = createScreenImageFile("RecyclerView", ".jpg")
            if (bitmap != null) {
                screenshot("RecyclerView", file, bitmap)
            }
        }

    }

    private fun screenshot(title: String, file: File, bitmap: Bitmap) {
        screenshotContract.onScreenshot(title, file, bitmap)
    }

    override fun onScreenshot(title: String, file: File, bitmap: Bitmap) {
        screenshotContract.onScreenshot(title, file, bitmap)
    }

}