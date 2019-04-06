package RedditAlarm;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import RedditAlarm.Models.RedditPost;
import RedditAlarm.Models.RedditResult;
import retrofit2.Call;
import retrofit2.Response;

public class RedditCall extends AsyncTask<Call<RedditResult>, Void, RedditResult> {
    public AsyncResponse delegate = null;
    public Context contextIn;

    @Override
    protected RedditResult doInBackground(Call<RedditResult>... params){
        try {
            Call<RedditResult> call = params[0];
            Response<RedditResult> data = call.execute();
            return data.body();
        }
        catch (IOException e) {
            return null;
        }

    }

    @Override
    protected void onPostExecute(RedditResult response) {
        List<RedditPost> posts = new ArrayList<>();
        if (response != null) {
            posts = response.getRedditPost();
        }
        delegate.processFinish(posts, contextIn);
        cancel(true);
    }

    public interface AsyncResponse {
        void processFinish(List<RedditPost> output, Context out);
    }
}
