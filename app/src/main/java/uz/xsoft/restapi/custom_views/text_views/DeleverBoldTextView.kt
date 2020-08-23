package uz.delever.custom_views.text_views

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class DeleverBoldTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        typeface = Typeface.createFromAsset(
            context.resources.assets, "fonts/Roboto-Bold.ttf")
    }
}