# ILoveZappos
Android application for the Android application development Intern challenge. This app serves to showcase the use of the 
following android framework and concepts:
- **Android Data Binding** Android data binding has been used to bind data to view. Databinding is used to inflate and set the layouts.
 The Recycler View uses data binding to inflate and populate the view holders. The product detail activity uses data binding. 
- **Floating Action Button** There is a floating action button in the first activity (ProductListActivity). It can be pressed to open a 
search view on the toolbar. The floating action buttons does what the Material design guideline says it should do - represent the primary action in an application. 
- **Simple animation on clicking the FAB** Pressing the floating action button will open the search view in the toolbar. The searchview expands with a simple scale
animation on the X axis. The toolbar also is set to use default LayoutTransitions to animate appearing views. The disappearing animations for layout transition are disabled. 
- **Material Design** Material design views like FAB, CardView with elevation are used.

- **Retrofit**  Using Retrofit for REST access. The interface that models the API is [here](https://github.com/bhandarijiwan/ILoveZappos/blob/master/app/src/main/java/com/Challenge/Zappos/data/api/ZapposService.java).

- **Volley** Uses volley to download images. Volley handles all the asynchronous operation in background and also handles caching even though the cache size and type have to be specified. [Here] (https://github.com/bhandarijiwan/ILoveZappos/blob/master/app/src/main/java/com/Challenge/Zappos/utils/NetworkImageLoaderHelper.java) is 
the code for that. 

- **Loader** Uses android loaders which facilitate and simplify the downloading of data from the server off the main thread. Because loaders already work off the main
thread retrofit can now execute synchronously. 

- **MVP Architecture** Follows the Model-View-Presenter architecture. There are primarily 2 features in this app - Products and ProductDetail. Each feature comprises of a View (not an android View) and Presenter.
These to talk to each through each other using an interface also referred to as contract. [Here] (https://github.com/bhandarijiwan/ILoveZappos/blob/master/app/src/main/java/com/Challenge/Zappos/products/ProductsContract.java) is a contract for products feature. The View interface is implemented by a Fragment which is hosted by an activity. 
The presenter is where the main business logic stays. 

  
- **Well handled Configuration changes:** Configuration changes are handled using two ways - loaders and savedstate. Becuase we use laoder to load the list of
product initially, handling configuration changes is almost entirely offloaded to the loader. When a configuration changes and the activity is recreated, we call upon
the loader to fetch the data which will immediately deliver the data. We use savedstate in the product detail activity to store and retrive the displayed product in event
of configuration change.

- **Improvement**:
    - I am not much of a designer so obviously, the design, layouts , bitmaps and animations could be improved a lot.
    - Caching search result locally using sqlite datebase and serving data through a content provider.
    - Could integrate with Firebase and have a quick sharing of products to people close by.