/**
 * JobController
 */

app.controller("JobController",function($scope,JobService,$location){
	
	$scope.addJob = function(){
		JobService .addJob($scope.job)
		.then(function(response){
			alert("Job added Successfully");
			$location.path('/home')
		},function(response){
			if(response.status == 401){
				if(response.data.error==8){
					alert("Access Denied")
					$location.path('/home')
				}else{
					$scope.error = response.data
					$location.path = "/login"
				}
			}
			if(response.status == 500){
				$scope.error = response.data;
				$location.path("/addJob");
			}
		})
	}
	
	function getAllJobs(){
		
		JobService.getAllJobs().then(function(response){
			
			$scope.jobList = response.data;
			
		},function(response){
			$scope.errorMessage = reponse.data.errorMessage;			
			if(response.status == 401){
				
				$location.path('/login')
			}
		})
	}
	
	getAllJobs();
	
	$scope.getJob  = function(jobId){
		
		JobService.getJob(jobId).then(function(response){
			
			$scope.job = response.data;
			
		},function(response){
			
			$scope.errorMessage = reponse.data.errorMessage;	
			
			if(response.status == 401){				
				$location.path('/login')
			}
			
		})
	}
	
	$scope.reset = function(){
		
		$scope.job = {};
		$scope.jobCreationForm.$setPristine();
		$scope.jobCreationForm.$setUntouched();
	}
})