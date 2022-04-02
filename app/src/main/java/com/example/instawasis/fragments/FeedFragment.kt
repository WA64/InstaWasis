package com.example.instawasis.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.instawasis.MainActivity
import com.example.instawasis.Post
import com.example.instawasis.PostAdapter
import com.example.instawasis.R
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery


open class FeedFragment : Fragment() {

    lateinit var postsRecyclerView: RecyclerView

    lateinit var adapter: PostAdapter

    lateinit var swipeContainer: SwipeRefreshLayout

    var allPosts: MutableList<Post> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)

        swipeContainer = requireActivity().findViewById<SwipeRefreshLayout>(R.id.swipeContainer)

        swipeContainer.setOnRefreshListener {
            Log.i(TAG,"Refreshing")
            queryPosts()

        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postsRecyclerView = view.findViewById<RecyclerView>(R.id.postRecyclerView)
        adapter = PostAdapter(requireContext(),allPosts)
        postsRecyclerView.adapter=adapter
        postsRecyclerView.layoutManager = LinearLayoutManager(requireContext())



        queryPosts()
    }


    //Query for all posts in server
    open fun queryPosts(){
        // Specify which class to query
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        //Find all Post objects
        query.limit = 20;
        query.include(Post.KEY_USER)
        query.addDescendingOrder("createdAt")

        query.findInBackground(object : FindCallback<Post> {
            override fun done(posts: MutableList<Post>?, e: ParseException?) {
                if (e != null){
                    //Something went wrong
                    Log.e(TAG,"Error fetching posts")
                } else {
                    if (posts != null) {
                        for (post in posts){
                            Log.i(
                                TAG,"Post " + post.getDescrpition() + " , username: " +
                                    post.getUser()?.username)
                        }

                         allPosts.addAll(posts)
                        adapter.notifyDataSetChanged()

                    }
                }
            }

        })
    }
    companion object{
        const val TAG = "FeedFragment"
    }
}