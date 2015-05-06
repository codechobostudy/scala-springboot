
/*global define*/
define([
    'jquery',
    'underscore',
    'backbone'
], function ($, _, Backbone) {
    var AppView = Backbone.View.extend({

        el: '#content',

        initialize: function(){
            console.log('a');
            this.render();
        },

        render: function(){
            console.log($("#message"));
            this.$el.html($("#message").val());
        }
    });

    return AppView;
});