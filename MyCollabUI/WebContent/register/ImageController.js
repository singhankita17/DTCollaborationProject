/**
 * Image Controller
 */

app.controller('ImageController', ['$scope', 'fileUploadService','userService', function($scope, fileUploadService){
    $scope.uploadFile = function(){
        var file = $scope.myFile;
        alert(file)
        console.log('file is ' );
        console.dir(file);
       
       // var uploadUrl = "/fileUpload";
        fileUploadService.uploadFileToUrl(file);
     };
     
     function getImage(userId){
    	 
    	 userService.getImage(userId)
    	 	.then(function(response){
    		   $scope.imagePath = response.data
    	 	},function(response){
    		 
    	 		console.log("Error in fetching image")
    	 	})
     }
  }]);