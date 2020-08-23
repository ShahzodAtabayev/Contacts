package uz.xsoft.restapi.utils.bindings

import android.annotation.SuppressLint
import android.net.Uri
import android.os.CountDownTimer
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.pinball83.maskededittext.MaskedEditText
import com.google.android.material.textfield.TextInputEditText
import uz.xsoft.restapi.viewModels.main.CountDownTimerWorker
import uz.xsoft.restapi.R
import uz.xsoft.restapi.apps.App
import uz.xsoft.restapi.data.local.room.entities.ContactData
import uz.xsoft.restapi.ui.adapters.ContactAdapter
import uz.xsoft.restapi.viewModels.main.ApiStatus
import java.util.concurrent.TimeUnit


/**
 * When there is no Mars property data (data is null), hide the [RecyclerView], otherwise show it.
 */
/*@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<GdgChapter>?) {
    val adapter = recyclerView.adapter as GdgListAdapter
    adapter.submitList(data) {
        // scroll the list to the top after the diffs are calculated and posted
        recyclerView.scrollToPosition(0)
    }
}*/

/*
@BindingAdapter("showOnlyWhenEmpty")
fun View.showOnlyWhenEmpty(data: List<GdgChapter>?) {
    visibility = when {
        data == null || data.isEmpty() -> View.VISIBLE
        else -> View.GONE
    }
}*/
@BindingAdapter("app:textWatcher")
fun watcher(view: TextInputEditText, textWatcher: TextWatcher) {
    view.addTextChangedListener(textWatcher)
}

@BindingAdapter("app:textWatcherMask")
fun watcherMask(view: MaskedEditText, textWatcher: TextWatcher) {
    view.addTextChangedListener(textWatcher)
}

@BindingAdapter("app:apiStatus")
fun bindStatus(statusImageView: ImageView, status: ApiStatus?) {
    when (status) {
        ApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        ApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
            Toast.makeText(App.instance, "Check your internet connection!!!", Toast.LENGTH_SHORT)
                .show()
        }
        ApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

@BindingAdapter("app:workTimer")
fun bindTimer(textView: TextView, countDownTimerWorker: CountDownTimerWorker) {
    val timer = MyCountDownTimer(textView, 60000, 1000)
    when (countDownTimerWorker) {
        CountDownTimerWorker.START -> {
            timer.start()
        }
        CountDownTimerWorker.STOP -> {
            timer.cancel()
        }
    }
}

class MyCountDownTimer(
    private val textView: TextView, time: Long, countDownInterval: Long
) :
    CountDownTimer(time, countDownInterval) {
    override fun onFinish() {

    }

    @SuppressLint("SetTextI18n")
    override fun onTick(millisUntilFinished: Long) {
        textView.text = "" + String.format(
            "%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
        )
    }
}

@BindingAdapter("app:listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<ContactData>?) {
    val adapter = recyclerView.adapter as ContactAdapter
    adapter.submitList(data)
}
//
//@BindingAdapter("app:listDataPromos")
//fun bindRecyclerView1(recyclerView: RecyclerView, data: List<PromoSlimModel>?) {
//    val adapter = recyclerView.adapter as PromosAdapter
//    adapter.submitList(data)
//}
//
//@BindingAdapter("app:listDataProducts")
//fun bindRecyclerView2(recyclerView: RecyclerView, data: List<ProductModel>?) {
//    val adapter = recyclerView.adapter as ProductsAdapter
//    adapter.submitList(data)
//}
//
//@BindingAdapter("app:listDataBrands")
//fun bindRecyclerView3(recyclerView: RecyclerView, data: List<BrandModel>?) {
//    val adapter = recyclerView.adapter as BrandsAdapter
//    adapter.submitList(data)
//}

@BindingAdapter("app:imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String) {
    imgUrl.let {
        val imgUri = Uri.parse(imgUrl).buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imgView)
    }
}


@BindingAdapter("app:setText")
fun bindText(textView: TextView, text: String) {
    textView.text = text.trim()
}
