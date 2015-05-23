angular.module('nmtApp.controllers').
controller('MainController', ['$scope', '$filter', '$state', '$stateParams', '$rootScope', function($scope, $filter, $state, $stateParams, $rootScope){


	$scope.loadUser = function(username){
		console.log(username);
		$rootScope.username = username;

		$state.go('dashboard', {username: username}, {location: true});
		//$state.go('welcome', {username: username}, {location: true});
	};



}]);