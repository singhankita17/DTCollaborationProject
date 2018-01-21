/**
 * Image Controller
 */

app.controller('ImageController', ['$scope', 'fileUploadService','userService','$rootScope','$cacheFactory', function($scope, fileUploadService, userService, $rootScope,$cacheFactory){
	$scope.imageUpload = function(event){
		
		var files = event.target.files;
		var file = files[files.length - 1];
		$scope.file = file;
		var reader = new FileReader();
		reader.onload = $scope.imageIsLoaded;
		reader.readAsDataURL(file);
	}
	
	$scope.imageIsLoaded = function(e){
		$scope.$apply(function(){
			$scope.step = e.target.result;
		})
	}
	$scope.isProcessing = false;
    $scope.uploadFile = function(){
        var file = $scope.myFile;
        alert(file)
        console.log('file is ' );
        console.dir(file);
       
       // var uploadUrl = "/fileUpload";
        fileUploadService.uploadFileToUrl(file)
        .then(function(response){
        	console.log(response.data);
        	alert("Image uploaded successfully");
        	
        	$scope.imagesrc="http://localhost:8181/MyCollab/getimage/"+$rootScope.globals.currentUser.userId;
        },function(response){
        	console.log(response.data)
        })
        
        $scope.isProcessing = true;
       
     };
     
     function getImage(userId){
    	 
    	 userService.getImage(userId)
    	 	.then(function(response){
    		   $scope.imagesrc = response
    	 	},function(response){
    		 
    	 		console.log("Error in fetching image")
    	 	})
     }
  }]);