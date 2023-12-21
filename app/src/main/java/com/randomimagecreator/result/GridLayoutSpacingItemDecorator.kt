package com.randomimagecreator.result

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridLayoutSpacingItemDecorator(
    private val span: Int,
    spacing: Float
) : RecyclerView.ItemDecoration() {
    private val spacing = spacing.toInt()
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        // Remainder should always return 0 if left
        val isLeft = position % span == 0
        // If next position is left, current position is obviously right.
        val isRight = (position + 1) % span == 0
        val isMiddle = !isLeft && !isRight
        if (isLeft || isMiddle) {
            outRect.right = spacing
        }
        if (isRight || isMiddle) {
            outRect.left = spacing
        }
        outRect.top = spacing
        outRect.bottom = spacing
    }
}