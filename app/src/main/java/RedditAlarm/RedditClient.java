package RedditAlarm;

import RedditAlarm.Models.RedditResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RedditClient {
    @GET("/{value}/hot.json")
    Call<RedditResult> getRedditPosts(@Query("limit") int numPosts,
                                     @Path("value") String subredditVal);
}

