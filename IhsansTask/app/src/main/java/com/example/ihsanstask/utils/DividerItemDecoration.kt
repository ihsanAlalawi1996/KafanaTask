package com.example.ihsanstask.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class DividerItemDecoration(
    private var spacing: Int,
    private var isGrid: Boolean = false,
    private var isSingleVertically: Boolean = true
) : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (isGrid) {
            outRect.top = spacing / 2
            outRect.bottom = spacing / 2
            outRect.right = spacing / 2
            outRect.left = spacing / 2

        } else {
            outRect.bottom = spacing
            if (!isSingleVertically) outRect.right = spacing

        }
    }
}