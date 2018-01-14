/**
 * HomeService.js
 */

app.factory('HomeService',function($http){
	var homeService = {};
	
	homeService.getNotificationNotViewed = getNotificationNotViewed;
	homeService.getNotificationViewed = getNotificationViewed;
	homeService.updateNotification = updateNotification;
	
	var BASE_URL = "http://localhost:8181/MyCollab";
	
	function getNotificationNotViewed(){
		alert("inside not viewed service")
		$http.get(BASE_URL+"/getnotification/"+0)
	}
	
	function getNotificationViewed(){
		alert("inside viewed service")
		$http.get(BASE_URL+"/getnotification/"+1)
	}
	
	function updateNotification(notificationId){
		$http.get(BASE_URL+"/updatenotification/"+notificationId)
	}
	return homeService;
})