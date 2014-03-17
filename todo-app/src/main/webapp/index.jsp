<%@ page pageEncoding="UTF-8" %>
<%@ page import="fr.xebia.gae.ServletHelper" %>
<!doctype html>
<html lang="en" data-framework="angularjs">
	<head>
		<meta charset="utf-8">
		<title>AngularJS • TodoMVC</title>
		<link rel="stylesheet" href="bower_components/todomvc-common/base.css">
		<style>[ng-cloak] { display: none; }</style>

	</head>
	<body ng-app="todomvc">
		<ng-view />

		<script type="text/ng-template" id="todomvc-index.html">
			<section id="todoapp" ng-controller="TodoCtrl">
				<header id="header">
					<h1 ng-click="authenticate()">todos</h1>
					<form id="todo-form" ng-submit="addTodo()">
						<input id="new-todo" placeholder="What needs to be done?" ng-model="newTodo" autofocus>
					</form>
				</header>
				<section id="main" ng-show="todos.length" ng-cloak>
					<input id="toggle-all" type="checkbox" ng-model="allChecked" ng-click="markAll(allChecked)">
					<label for="toggle-all">Mark all as complete</label>
					<ul id="todo-list">
						<li ng-repeat="todo in todos | filter:statusFilter track by $index" ng-class="{completed: todo.completed, editing: todo == editedTodo}">
							<div class="view">
								<input class="toggle" type="checkbox" ng-model="todo.completed" ng-click="doneEditing(todo)">
								<label ng-dblclick="editTodo(todo)">{{todo.title}}</label>
								<button class="destroy" ng-click="removeTodo(todo)"></button>
							</div>
							<form ng-submit="doneEditing(todo)">
								<input class="edit" ng-trim="false" ng-model="todo.title" todo-escape="revertEditing(todo)"  todo-focus="todo == editedTodo">
							</form>
						</li>
					</ul>
				</section>
				<footer id="footer" ng-show="todos.length" ng-cloak>
					<span id="todo-count"><strong>{{remainingCount}}</strong>
						<ng-pluralize count="remainingCount" when="{ one: 'item left', other: 'items left' }"></ng-pluralize>
					</span>
					<ul id="filters">
                        <li>
                            <a ng-hide="authenticated" class="selected" ng-click="authenticate()">Signin</a>
                        </li>
					</ul>
					<button id="clear-completed" ng-click="clearCompletedTodos()" ng-show="completedCount">Clear completed ({{completedCount}})</button>
				</footer>
			</section>
			<footer id="info">
				<p>Double-click to edit a todo</p>
				<p>Credits:
					<a href="http://twitter.com/cburgdorf">Christoph Burgdorf</a>,
					<a href="http://ericbidelman.com">Eric Bidelman</a>,
					<a href="http://jacobmumm.com">Jacob Mumm</a> and
					<a href="http://igorminar.com">Igor Minar</a>
				</p>
				<p>Part of <a href="http://todomvc.com">TodoMVC</a></p>
			</footer>
		</script>
		<script src="bower_components/todomvc-common/base.js"></script>
		<script src="bower_components/angular/angular.js"></script>
		<script src="bower_components/angular-route/angular-route.js"></script>
		<script src="js/app.js"></script>
		<script src="js/controllers/todoCtrl.js"></script>
		<script src="js/services/todoStorage.js"></script>
		<script src="js/directives/todoFocus.js"></script>
		<script src="js/directives/todoEscape.js"></script>

        <script>

            var ROOT = '<%= ServletHelper.getRoot(request)%>';
            var init = function() {
                // sera intercepté par angularjs
                window.init();
            }
        </script>
        <script src="https://apis.google.com/js/client.js?onload=init"></script>
	</body>
</html>
