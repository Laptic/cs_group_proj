package RedditAlarm;

import RedditAlarm.Models.RedditJSON;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RedditClient {
    @GET("{value}/hot.json")
    Call<RedditJSON> getRedditPosts(@Path("value") String subredditVal,
                                    @Query("limit") int limitIn);
}