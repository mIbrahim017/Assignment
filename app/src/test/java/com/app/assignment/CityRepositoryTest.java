package com.app.assignment;

import com.app.assignment.repository.CityRepository;
import com.app.assignment.repository.remote.APIsServices;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import io.reactivex.subscribers.TestSubscriber;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static junit.framework.Assert.assertEquals;

/**
 * Created by mohamed ibrahim on 7/19/2017.
 */

public class CityRepositoryTest {

    private MockWebServer mockWebServer;
    private CityRepository repo;
    private TestSubscriber testSubscriber;
    private APIsServices service;


    @Before
    public void setUp() throws IOException {
        this.mockWebServer = new MockWebServer();
        this.mockWebServer.start();
        service = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(APIsServices.class);

        repo = new CityRepository(service, new DaoCityDB());
        testSubscriber = new TestSubscriber();

    }

    @Test
    public void testGetListOfCities() throws Exception {
        mockWebServer.enqueue(getListOfCities());
        repo.getCitiesRemotely(1).subscribe(testSubscriber);
        RecordedRequest request = this.mockWebServer.takeRequest();

        assertEquals("GET", request.getMethod());


    }


    private MockResponse getListOfCities() throws Exception {
        InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream("api-response/" + "cities.json");
        BufferedSource source = Okio.buffer(Okio.source(inputStream));
        MockResponse mockResponse = new MockResponse();
        mockResponse.setBody(source.readString(Charset.defaultCharset()));
        return mockResponse;
    }


    @After
    public void tearDown() throws Exception {
        this.mockWebServer.shutdown();
    }

}
