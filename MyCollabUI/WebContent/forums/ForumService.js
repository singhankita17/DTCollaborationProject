app.factory("ForumService",ForumService);

var BASE_URL = 'http://localhost:8181/MyCollab';

ForumService.$inject = ['$http', '$cookieStore', '$rootScope'];

function ForumService($http,$cookieStore,$rootScope){
	
	var service = {}
	
	service.createForum = createForum;
		
	return service;
	
	function createForum(forum,callback){
		
		$http.post(BASE_URL+"/createForum",forum).then(function(response){
			
		
			if(response != null){
				 response = { success: true, data: response.data };
			}else{
				 response = { success: false, message :'Forum creation failed' };
			}
			
			callback(response)
			
		},function(response){
			
			 response = { success: false,message : 'Forum creation failed' };
			 callback(response)
		})
	}
}