package org.gampiot.robok.feature.util.base.preference

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View

import androidx.preference.PreferenceFragmentCompat
import androidx.recyclerview.widget.RecyclerView

import com.google.android.material.color.MaterialColors

import org.gampiot.robok.feature.util.R
import org.gampiot.robok.feature.util.dpToPx
import org.gampiot.robok.feature.util.enableEdgeToEdgePaddingListener

abstract class BasePreferenceFragment : PreferenceFragmentCompat() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setBackgroundColor(MaterialColors.getColor(view, android.R.attr.colorBackground))
        view.findViewById<RecyclerView>(androidx.preference.R.id.recycler_view).apply {
            setPadding(paddingLeft, paddingTop + 12.dpToPx(context), paddingRight, paddingBottom)
            enableEdgeToEdgePaddingListener()
        }
    }

    override fun setPreferencesFromResource(preferencesResId: Int, key: String?) {
        super.setPreferencesFromResource(preferencesResId, key)
    }

    override fun setDivider(divider: Drawable?) {
        super.setDivider(ColorDrawable(Color.TRANSPARENT))
    }

    override fun setDividerHeight(height: Int) {
        super.setDividerHeight(0)
    }
}