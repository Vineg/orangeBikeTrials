package ru.vineg.orangeBikeFree.backend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import ru.vineg.orangeBikeFree.Debug;
import ru.vineg.orangeBikeFree.reflection.LevelEntry;
import ru.vineg.orangeBikeFree.reflection.LevelReplay;
import ru.vineg.orangeBikeFree.Motodroid;

import java.util.HashMap;

/**
 * Created by Vineg on 15.03.14.
 */
public class MotodroidBackendApi {

    private static Dialog dialog;
    private static String apiHost = "vineg.cf";

    public static void submitLevel(String data) {
        Net.HttpResponseListener httpResponseListener = new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                dialog.remove();
                if (httpResponse.getStatus().getStatusCode() == HttpStatus.SC_OK) {
                    Motodroid.gamePreferences.shareLevel(httpResponse.getResultAsString());
                } else {
                    Debug.println(httpResponse.getResultAsString());
                    Motodroid.message("network error");
                }

            }

            @Override
            public void failed(Throwable t) {
                dialog.remove();
                Motodroid.message("network error");

                t.printStackTrace();
            }

            @Override
            public void cancelled() {
                dialog.remove();
                Motodroid.message("network error");
            }
        };

        postData(data, "http://" + apiHost + "/api/level/create", httpResponseListener);
    }

    private static void postData(String data, String uri, Net.HttpResponseListener httpResponseListener) {
        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.POST);
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("data", data);
        request.setContent(HttpParametersUtils.convertHttpParameters(parameters));
        request.setUrl(uri);
        request.setHeader("Accept", "text/plain");

        dialog = Motodroid.loadingDialog("uploading");


        Gdx.net.sendHttpRequest(request, httpResponseListener);
    }

    public static void submitReplay(String data) {
        Net.HttpResponseListener httpResponseListener = new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                removeDialog();
                String resultAsString="";
                try {
                    resultAsString = httpResponse.getResultAsString();
                } catch (NullPointerException e) {
                }
                if (httpResponse.getStatus().getStatusCode() == HttpStatus.SC_OK) {
                    Motodroid.gamePreferences.shareReplay(resultAsString);
                } else {
                    Debug.println(httpResponse.getStatus().getStatusCode() + ": " + resultAsString);
                    Motodroid.message("network error");
                }
            }

            @Override
            public void failed(Throwable t) {
                removeDialog();
                Motodroid.message("network error");
                t.printStackTrace();
            }

            @Override
            public void cancelled() {
                removeDialog();
                Motodroid.message("request cancelled");
            }
        };

        String uri = "http://" + apiHost + "/api/replay/create";
        postData(data, uri, httpResponseListener);
    }

    private static void removeDialog() {
        Motodroid.removeDialog();
    }



    public static void setLevelByUri(String levelUri) {
        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.GET);
        request.setUrl(levelUri);
        request.setHeader("Accept", "text/plain");
        Debug.println(levelUri);

        dialog = Motodroid.loadingDialog("loading level");

        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                String data = httpResponse.getResultAsString();
                Debug.println(data);
                if (httpResponse.getStatus().getStatusCode() == HttpStatus.SC_OK) {
                    removeDialog();
                    Motodroid.setLevelSync(LevelEntry.fromString(data));
                } else {
                    Debug.println(httpResponse.getStatus().getStatusCode());
                    Motodroid.message("network error");
                }
                dialog.remove();
            }

            @Override
            public void failed(Throwable t) {

                t.printStackTrace();
                removeDialog();
                Motodroid.message("network error");
            }

            @Override
            public void cancelled() {
                removeDialog();
                Motodroid.message("network error");
            }
        });
    }

    public static void setReplayByUri(String url) {
        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.GET);
        request.setUrl(url);
        request.setHeader("Accept", "text/plain");
//        Debug.println(url);

        dialog = Motodroid.loadingDialog("loading replay");

        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                String data = httpResponse.getResultAsString();
//                Debug.println(data);
                if (httpResponse.getStatus().getStatusCode() == HttpStatus.SC_OK) {
                    LevelReplay replay = LevelReplay.fromString(data);
                    removeDialog();
                    Motodroid.setReplaySync(replay);
                } else {
                    Debug.println(httpResponse.getStatus().getStatusCode());
                    Motodroid.message("network error");
                }
                removeDialog();
            }

            @Override
            public void failed(Throwable t) {
                try {
                    throw t;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
                removeDialog();
                Motodroid.message("network error");
            }

            @Override
            public void cancelled() {
                removeDialog();
                Motodroid.message("network error");
            }
        });
    }

    public static void submitReplay(LevelReplay replay) {
        submitReplay(new Json(JsonWriter.OutputType.json).toJson(replay));
    }
}
