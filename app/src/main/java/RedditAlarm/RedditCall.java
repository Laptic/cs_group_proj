package RedditAlarm;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import RedditAlarm.Models.RedditJSON;
import RedditAlarm.Models.RedditPost;
import retrofit2.Call;
import retrofit2.Response;

public class RedditCall extends AsyncTask<Call<RedditJSON>, Void, RedditJSON> {
    public AsyncResponse delegate = null;
    public Context contextIn;

    @Override
    protected RedditJSON doInBackground(Call<RedditJSON>... params){
        try {
            Call<RedditJSON> call = params[0];
            Response<RedditJSON> data = call.execute();
            return data.body();
        }
        catch (IOException e) {
            return null;
        }

    }

    @Override
    protected void onPostExecute(RedditJSON response) {
        List<RedditPost> posts = new ArrayList<>();
        if (response != null) {
            posts = response.getPosts();
        }
        delegate.processFinish(posts, contextIn);
        cancel(true);
    }

    public interface AsyncResponse {
        void processFinish(List<RedditPost> output, Context out);
    }
}