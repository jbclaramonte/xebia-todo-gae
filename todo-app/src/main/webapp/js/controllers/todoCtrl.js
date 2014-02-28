/*global todomvc, angular */
'use strict';

/**
 * The main controller for the app. The controller:
 * - retrieves and persists the model via the todoStorage service
 * - exposes the model to the template and provides event handlers
 */
todomvc.controller('TodoCtrl', function TodoCtrl($scope, $routeParams, $window, todoStorage, filterFilter) {
    $scope.todos = [];

	$scope.newTodo = '';
	$scope.editedTodo = null;

    // ajout pour gae >>
    /**
     * fonction interceptant l'appel à window.init() effectué dans index.html
     */
    $window.init= function() {
        console.log("$window.init called");
        $scope.$apply($scope.load_gapi_todo_lib);
    };


    $scope.load_gapi_todo_lib = function() {
        console.log("load_todo_lib called");
        var ROOT = 'http://localhost:9090/_ah/api';
        gapi.client.load('todo', 'v1', function() {
            console.log("todo api loaded");
            $scope.getTodos();
        }, ROOT);

    };


    $scope.getTodos = function() {
        todoStorage.list(function(resp) {
            console.log(resp);
            $scope.todos = resp.items;
            $scope.$apply();
        });
    }
    // << fin ajout pour gae


	// Monitor the current route for changes and adjust the filter accordingly.
	$scope.$on('$routeChangeSuccess', function () {
		var status = $scope.status = $routeParams.status || '';

		$scope.statusFilter = (status === 'active') ?
			{ completed: false } : (status === 'completed') ?
			{ completed: true } : null;
	});

	$scope.addTodo = function () {
		var newTodoTile = $scope.newTodo.trim();
		if (!newTodoTile.length) {
			return;
		}

        var newTodo = {
            title: newTodoTile,
            completed: false
        };

        todoStorage.create(newTodo, function(todoResp) {
            $scope.todos.push({
                id: todoResp.id,
                title: todoResp.title,
                completed: todoResp.completed
            });
            $scope.$apply();
        });

		$scope.newTodo = '';
	};

	$scope.editTodo = function (todo) {
		$scope.editedTodo = todo;
		// Clone the original todo to restore it on demand.
		$scope.originalTodo = angular.extend({}, todo);
	};

	$scope.doneEditing = function (todo) {
		$scope.editedTodo = null;
		todo.title = todo.title.trim();

		if (!todo.title) {
			$scope.removeTodo(todo);
		} else {
            todoStorage.update(todo, function(todo) {
                console.log('todo with id ' + todo.result.id + ' successfully updated');
            });
        }
	};

	$scope.revertEditing = function (todo) {
		$scope.todos[$scope.todos.indexOf(todo)] = $scope.originalTodo;
        $scope.editedTodo = null;
	};

	$scope.removeTodo = function (todo) {
		$scope.todos.splice($scope.todos.indexOf(todo), 1);
        todoStorage.remove(todo, function() {
            $scope.$apply();
        })
	};

	$scope.clearCompletedTodos = function () {
		$scope.todos = scope.todos.filter(function (val) {
			return !val.completed;
		});
	};

	$scope.markAll = function (completed) {
		$scope.todos.forEach(function (todo) {
			todo.completed = !completed;
		});
	};
});
