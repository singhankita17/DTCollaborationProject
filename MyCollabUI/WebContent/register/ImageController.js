/**
 * Image Controller
 */

app.controller('ImageController', ['$scope', 'fileUploadService', function($scope, fileUploadService){
    $scope.uploadFile = function(){
        var file = $scope.myFile;
        alert(file)
        console.log('file is ' );
        console.dir(file);
       
       // var uploadUrl = "/fileUpload";
        fileUploadService.uploadFileToUrl(file);
     };
  }]);