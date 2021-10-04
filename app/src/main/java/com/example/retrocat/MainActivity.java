package com.example.retrocat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private List<ImagesResponse> imagesResponseList = new ArrayList<>();
    private List<ImagesResponse> imagesResponseList2 = new ArrayList<>();


    GridView gridView;
    CustomAdapter customAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        gridView = findViewById(R.id.gridView);

        getAllImages();


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                startActivity(new Intent(MainActivity.this, ClickedActivity.class)
                        .putExtra("data",imagesResponseList.get(i)));
            }
        });

        final int[] firstVisibleItem = new int[1];
        final int[] visibleItemCount = new int[1];
        final int[] totalItemCount = new int[1];

        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScroll(AbsListView view,
                                 int firstVisibleItemm, int visibleItemCountt,
                                 int totalItemCountt) {
                firstVisibleItem[0] = firstVisibleItemm;
                visibleItemCount[0] = visibleItemCountt;
                totalItemCount[0] = totalItemCountt;


            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                final int lastItem = firstVisibleItem[0] + visibleItemCount[0];
                if (lastItem == totalItemCount[0] && scrollState == SCROLL_STATE_IDLE) {

                    getAllImages2();
                    //get next 10-20 items(your choice)items

                }
            }
        });









    }

    public void getAllImages2(){
        Call<List<ImagesResponse>> imagesResponse = ApiClient.getInterface().getAllImages();

        imagesResponse.enqueue(new Callback<List<ImagesResponse>>() {
            @Override
            public void onResponse(Call<List<ImagesResponse>> call, Response<List<ImagesResponse>> response) {
                if(response.isSuccessful()){
                    String message =  "Success";
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();






                }else {
                    String message =  "Error getAllImage";
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

                }



            }

            @Override
            public void onFailure(Call<List<ImagesResponse>> call, Throwable t) {
                String message =  t.getLocalizedMessage();
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                System.out.print(message);


            }
        });

        customAdapter.notifyDataSetChanged();
    }

    public void getAllImages(){
        Call<List<ImagesResponse>> imagesResponse = ApiClient.getInterface().getAllImages();

        imagesResponse.enqueue(new Callback<List<ImagesResponse>>() {
            @Override
            public void onResponse(Call<List<ImagesResponse>> call, Response<List<ImagesResponse>> response) {
                if(response.isSuccessful()){
                    String message =  "Success";
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

                    imagesResponseList = response.body();

                    customAdapter = new CustomAdapter(imagesResponseList, MainActivity.this);

                    gridView.setAdapter(customAdapter);


                }else {
                    String message =  "Error getAllImage";
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

                }



            }

            @Override
            public void onFailure(Call<List<ImagesResponse>> call, Throwable t) {
                String message =  t.getLocalizedMessage();
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                System.out.print(message);


            }
        });
    }
    public class CustomAdapter extends BaseAdapter{

        private List<ImagesResponse> imagesResponseList;
        private Context context;
        private LayoutInflater layoutInflater;

        public CustomAdapter(List<ImagesResponse> imagesResponseList, Context context) {
            this.imagesResponseList = imagesResponseList;
            this.context = context;
            this.layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return imagesResponseList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null){
                view = layoutInflater.inflate(R.layout.row_grid_items, viewGroup,false);
            }
            ImageView imageView = view.findViewById(R.id.imageView);
            imageView.layout(0,0,0,0);
            Glide.with(context).load(imagesResponseList.get(i).getUrl()).into(imageView);


            return view;
        }
    }

}