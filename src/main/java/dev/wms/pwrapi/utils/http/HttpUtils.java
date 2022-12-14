package dev.wms.pwrapi.utils.http;

import dev.wms.pwrapi.utils.generalExceptions.SystemTimeoutException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.SocketTimeoutException;

public class HttpUtils {

    public static String makeRequestWithClientAndGetString(OkHttpClient client, Request request){
        try(Response response = client.newCall(request).execute()){
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String makeRequestWithClientAndGetString(OkHttpClient client, String url){

        Request request = new Request.Builder()
                .url(url)
                .build();

        try(Response response = client.newCall(request).execute()){
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Makes request with OkHttp's client and parses it to Jsoup's Document. Needed for proper response closing
     * @param client OkHttp client which will execute the request
     * @param url URL that will be requested
     * @return Jsoup's Document containing parsed html from OkHttp response
     * @throws IOException when parsing goes wrong
     */
    public static Document makeRequestWithClientAndGetDocument(OkHttpClient client, String url)  {

        Request financeRequest = new Request.Builder()
                .url(url)
                .build();

        String responseString;

        try(Response response = client.newCall(financeRequest).execute()){
            responseString = response.body().string();
        } catch (SocketTimeoutException e){
            throw new SystemTimeoutException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Jsoup.parse(responseString);

    }

    /**
     * Makes request with client and does not return anything. Needed mostly for navigating through the page
     * @param client OkHttp client which will execute the request
     * @param url URL that will be requested
     * @throws IOException when parsing goes wrong
     */
    public static void makeRequestWithClient(OkHttpClient client, String url) throws IOException {

        Request financeRequest = new Request.Builder()
                .url(url)
                .build();

        try(Response response = client.newCall(financeRequest).execute()){

        }

    }


}
