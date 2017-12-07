/**
 * fileUploadService.js
 */

app.service('fileUploadService', ['$http', function ($http) {	
	   
            this.uploadFileToUrl = function(file){            	
            	var BASE_URL = "http://localhost:8181/MyCollab";
               var fd = new FormData();
               fd.append('file', file);
            
               $http.post(BASE_URL+"/uploadUserImage", fd, {
                  transformRequest: angular.identity,
                  headers: {'Content-Type': undefined}
               })          
               .then(function(response){
            	   console.log(response.data)
               },function(response){
            	   console.log(response.data)
               });
            }
         }]);