package com.example.android.interactivestory.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.interactivestory.R;
import com.example.android.interactivestory.model.Page;
import com.example.android.interactivestory.model.Story;


public class StoryActivity extends ActionBarActivity {

    public static final String TAG = StoryActivity.class.getSimpleName();

    private Story mStory = new Story();
    private ImageView mImageView;
    private TextView mTextView;
    private Button mChoice1;
    private Button mChoice2;
    private String mName;
    private Page mPageNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        //getIntent() is inherited from the base class.
        Intent intent = getIntent();
        mName = intent.getStringExtra(getString(R.string.key_name));
        if (mName == null){
            mName = "Adhyan";
        }
        Log.d(TAG, mName);

        mImageView = (ImageView) findViewById(R.id.storyImageView);
        mTextView = (TextView) findViewById(R.id.storyTextView);
        mChoice1 = (Button) findViewById(R.id.choiceButton1);
        mChoice2 = (Button) findViewById(R.id.choiceButton2);

        loadPage(0);
    }

    public void loadPage(int choice){
        mPageNumber = mStory.getPage(choice);
        Drawable drawable = getResources().getDrawable(mPageNumber.getImageId());
        mImageView.setImageDrawable(drawable);
        String pageText = mPageNumber.getText();
        pageText = String.format(pageText, mName);
        mTextView.setText(pageText);

        if(mPageNumber.isFinal()){
            mChoice1.setVisibility(View.INVISIBLE);
            mChoice2.setText("PLAY AGAIN");
            mChoice2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //finishes the activity and gets back to the activity whoever launched it.
                    finish();
                }
            });
        }

        else {
            mChoice1.setText(mPageNumber.getChoice1().getText());
            mChoice2.setText(mPageNumber.getChoice2().getText());
            mChoice1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int nextPage = mPageNumber.getChoice1().getNextPage();
                    loadPage(nextPage);
                }
            });

            mChoice2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int nextPage = mPageNumber.getChoice2().getNextPage();
                    loadPage(nextPage);
                }
            });
        }
    }
}
