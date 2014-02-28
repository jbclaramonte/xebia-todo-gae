/*global todomvc */
'use strict';

/**
 * Services that persists and retrieves TODOs from localStorage
 */
todomvc.factory('todoStorage', function () {

	return {

        list: function (callback) {
            console.log("getting todos list");
            gapi.client.todo.list().execute(callback);
        },

		create: function (todo, callback) {
            console.log("create todo:" + todo);
            gapi.client.todo.create(todo).execute(callback);
		},

        update: function (todo, callback) {
            console.log("update todo:" + todo);
            gapi.client.todo.update(todo).execute(callback);
        },

        remove: function (todo, callback) {
            console.log("remove todo:" + todo);
            gapi.client.todo.remove(todo).execute(callback);
        }
	};
});
