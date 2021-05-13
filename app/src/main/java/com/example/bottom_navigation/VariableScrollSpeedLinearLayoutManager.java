package com.example.bottom_navigation;


import android.content.Context;
import android.graphics.PointF;
import android.util.DisplayMetrics;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

public class VariableScrollSpeedLinearLayoutManager extends LinearLayoutManager {

        private final float factor;

        public VariableScrollSpeedLinearLayoutManager(Context context, float factor) {
            super(context);
            this.factor = factor;
        }

        @Override
        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {

            final LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {

                @Override
                public PointF computeScrollVectorForPosition(int targetPosition) {
                    return VariableScrollSpeedLinearLayoutManager.this.computeScrollVectorForPosition(targetPosition);
                }

                @Override
                protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                    return super.calculateSpeedPerPixel(displayMetrics) * factor;
                }
            };

            linearSmoothScroller.setTargetPosition(position);
            startSmoothScroll(linearSmoothScroller);
        }
    }

