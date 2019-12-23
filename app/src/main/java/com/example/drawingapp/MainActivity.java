package com.example.drawingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private boolean isDrawInit;

    @Override
    protected void onResume() {
        super.onResume();
        if(!isDrawInit){
            initDraw();
            final Button clearButton = findViewById(R.id.clear);
            clearButton.setOnClickListener(new View.OnClickListener(){
              public void onClick(View v) {
                  paintView.clear();
              }
            });

            final TextView brushSize = findViewById(R.id.brushSize);
            brushSize.setText("20");

            final TextView brushColor = findViewById(R.id.brushColor);
            brushColor.setText("black");

            final Button incBrushSize= findViewById(R.id.brushSizeInc);
            incBrushSize.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    paintView.brushSize+=2;
                    brushSize.setText(String.valueOf(paintView.brushSize));
                }
            });

            final Button decBrushSize= findViewById(R.id.brushSizeDec);
            decBrushSize.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(paintView.brushSize>2){
                        paintView.brushSize-=2;
                        brushSize.setText(String.valueOf(paintView.brushSize));
                    }
                }
            });

            final Button changeColor = findViewById(R.id.changeColor);
            changeColor.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(paintView.brushColor==Color.BLACK){
                        paintView.brushColor = Color.RED;
                        brushColor.setText("red");
                    }else if(paintView.brushColor==Color.RED){
                        paintView.brushColor = Color.WHITE;
                        brushColor.setText("white");
                    }else{
                        paintView.brushColor = Color.BLACK;
                        brushColor.setText("black");
                    }
                }
            });

            isDrawInit = true;
        }
    }

    private PaintView paintView;

    private void initDraw(){
        paintView = findViewById(R.id.paintView);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        paintView.init(metrics);
    }

    static class FingerPath{
        int color;
        int strokeWidth;

        Path path;

        FingerPath(int color,int strokeWidth,Path path){
            this.color = color;
            this.strokeWidth = strokeWidth;
            this.path = path;
        }
    }


}
