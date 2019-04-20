package RedditAlarm.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RedditJSON {
    //@SerializedName("kind")
    //@Expose
    // listing;
    @SerializedName("data")
    @Expose
    RedditResult output;
    public class RedditResult {

        @SerializedName("modhash")
        @Expose
        private String modhash;
        @SerializedName("dist")
        @Expose
        private Integer dist;
        @SerializedName("children")
        @Expose
        private List<Child> resultChild;

        @SerializedName("after")
        @Expose
        private String after;
        @SerializedName("before")
        @Expose
        private Object before;

        public String getModhash() {
            return modhash;
        }

        public void setModhash(String modhash) {
            this.modhash = modhash;
        }

        public Integer getDist() {
            return dist;
        }

        public void setDist(Integer dist) {
            this.dist = dist;
        }

        public List<RedditPost> getRedditPost() {
            ArrayList<RedditPost> postList = new ArrayList<>();
            for(int i = 0; i < resultChild.size(); i++) {
                postList.add(resultChild.get(i).postGot);
            }
            return postList;
        }
        //public void setChildren(List<RedditPost> postsIn) {
        //    this.postsGot = postsIn;
        //}

        public String getAfter() {
            return after;
        }

        public void setAfter(String after) {
            this.after = after;
        }

        public Object getBefore() {
            return before;
        }

        public void setBefore(Object before) {
            this.before = before;
        }

        public class Child {
            @SerializedName("kind")
            @Expose
            String kind;
            @SerializedName("data")
            @Expose
            private RedditPost postGot;
            public RedditPost getPostGot() {
                return postGot;
            }
        }
    }
    public List<RedditPost> getPosts() {
        return this.output.getRedditPost();
    }
}

