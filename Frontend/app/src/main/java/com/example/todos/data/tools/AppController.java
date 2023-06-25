package com.example.todos.data.tools;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
//import com.example.sumon.androidvolley.utils.LruBitmapCache;

/**
 * Class that handles controlling request queues for JSON
 */
public class AppController extends Application {

	public static final String TAG = AppController.class
			.getSimpleName();

	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;

	private static AppController mInstance;

    /**
     * Creates an appcontroller
     */
	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
	}

    /**
     *
     * @return instance of AppController
     */
	public static synchronized AppController getInstance() {
		return mInstance;
	}

    /**
     *
     * @return Current request queue so it can be added to
     */
	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}

		return mRequestQueue;
	}

    /**
     *	 This method adds as request of generic type to the request queue. For example
	 * 	 this could be a JSON get request
     * @param req Request to be made
     * @param tag Name of Request
     * @param <T> Generic type to be used for request
	 *
     */
	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	/**
	 *This method adds as request of generic type to the request queue. For example
	 * this could be a JSON get request. It does not have a tag
	 * @param req Request to be made
	 * @param <T> Generic type to be used for request
	 */
	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	/**
	 * Cancels all requests in the queue
	 * @param tag Object to be cancelled
	 */
	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}
}
