# Slideshow

Android Image Slideshow app. It utilizes the Picasso image library that provides a very effecient and quick way to display images in a Material Design Recyclerview Gridlayout and corresponding ViewPager/Fragment slideshow.

See the PicassoCache class for configuration setup.
- There is a boolean value that can be set for the indicatorsEnabled parameter. This will color code Picasso images in order display how the images being cached.  See official Picasso documentation for more information. http://square.github.io/picasso/
- OkHttp is implemented to allow for request responses to be cached
- A listener has been setup in the class to allow for request response caching

The slideshow is implemented with a ViewPager and Fragments
- A set of NASA images are set directly to the ImageItem model and then used to set the RecyclerAdapter.  See MainActivity class
- Slide delay is set with a Runnable thread.
- Images are listed in a RecyclerView Gridlayout. User can select any image in the grid and the slideshow will start from the image. The RecyclerView Activity passes the selected image index and total images account onto the Activity ViewPager.
