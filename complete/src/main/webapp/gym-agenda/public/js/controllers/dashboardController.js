angular.module('nmtApp.controllers').
controller('DashboardController', ['$scope', '$filter', '$state', '$stateParams', '$rootScope', 'DashboardService', function($scope, $filter, $state, $stateParams, $rootScope, DashboardService){ 
	
	$scope.username = $stateParams.username;
	console.log('username',$scope.username);
	$scope.muscleGroups = null;

	$scope.test = function(){
		console.log('hit DashboardController');
		DashboardService.getAllMuscleGroups().then(function(response){
			$scope.muscleGroups = response;
		});
	};


	$(document).ready(function () {
  $('[data-toggle="offcanvas"]').click(function () {
    $('.row-offcanvas').toggleClass('active');
  });
});

	$scope.test();

}]);