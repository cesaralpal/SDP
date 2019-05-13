package com.villaindustrias.sdpv3.utilidades


import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class SpacingRecyclerDecorator(var spaceLeft: Int,var spaceRight: Int,var spaceTop: Int,var spaceBottom: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: RecyclerView, state: RecyclerView.State) {
        outRect.left = spaceLeft
        outRect.right = spaceRight
        outRect.bottom = spaceBottom
        outRect.top = spaceTop
    }

}