package com.example.retrocat;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("v1/images/search/?limit=10")
    Call<List<ImagesResponse>> getAllImages();
}
