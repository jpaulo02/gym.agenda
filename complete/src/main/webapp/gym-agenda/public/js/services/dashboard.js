angular.module('nmtApp.services').
factory('DashboardService', ['$log', 'GymService', function($log, GymService){

	var DashboardService = {

		muscleGroups: null,

		getAllMuscleGroups: function(){
			var self = this;
			return GymService.oneUrl('muscleGroup/allMuscles').get().then(function(response){
				self.muscleGroups = response;
				$log.debug('getAllMuscleGroups', self.muscleGroups);
				return self.muscleGroups;
			}, function(response){
				$log.debug('error', response);
			});
		}

	};

	return DashboardService;

}]);
