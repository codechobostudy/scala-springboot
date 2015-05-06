require([
    'backbone',
    'scripts/app.view'
], function (Backbone, AppView) {
    /*jshint nonew:false*/
    // Initialize routing and start Backbone.history()
    Backbone.history.start();

    // Initialize the application view
    new AppView();
});