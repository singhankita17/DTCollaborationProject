/**
 * ChatController.js
 */

app.controller('ChatController',['$rootScope','$scope','socket','userService',function($rootScope,$scope,socket,userService){

	console.log('entering chat controller');
	$scope.chats = [];
	$scope.stompClient = socket.stompClient;
	$scope.users = [];
	
	$scope.$on('sockConnected',function(event,frame){
		console.log($rootScope.globals.currentUser.userId)
		$scope.userId = $rootScope.globals.currentUser.userId;
		$scope.stompClient.subscribe("/topic/join",function(message){
			user = JSON.parse(message.body);
			alert(user+" joined chat")
			if (user!=$scope.userId && $.inArray(user,$scope.users) == -1){
				$scope.addUser(user);
				$scope.latestUser = user;
				getUserNames($scope.users)
				$scope.$apply();
				$("#joinedChat").fadeIn(100).delay(2000).fadeOut(200);
			}
		});
		
		$scope.stompClient.subscribe("/app/join/"+$scope.userId,function(message){
			$scope.users = JSON.parse(message.body);
			getUserNames( message.body);
			$scope.$apply();
		})
	});
	
	$scope.sendMessage = function(chat){
		alert("Chat.to = "+chat.to);
		if(chat.to === undefined || chat.to === '' ){
			alert("Please select the Send To Field");
			$scope.chat.message = '';
			to.focus();
			return;
		}
		chat.from = $scope.userId;		
		$scope.stompClient.send("/app/chat",{},JSON.stringify(chat));
		$rootScope.$broadcast('sendingChat',chat);
		$scope.chat.message = '';
	};
	
	$scope.capitalize= function(str){
		return str.charAt(0).toUpperCase() + str.slice(1);
	}
	
	 $scope.addUser = function(user) {
		 
	        $scope.users.push(user);	        
	        $scope.$apply();
	       
	    };
	    
	    
	 $scope.$on('sockConnected',function(event,frame){
		 $scope.userId = $rootScope.globals.currentUser.userId;
		 $scope.user =  $rootScope.globals.currentUser.userId;
		 
		 $scope.stompClient.subscribe("/queue/chats/"+$scope.userId,function(message){
			 $scope.processIncomingMessage(message,false);
		 });
		 
		 $scope.stompClient.subscribe("/queue/chats",function(message){
			 $scope.processIncomingMessage(message,false);
		 });
		 
	 });
	 
	 $scope.$on('sendingChat', function(event, sentChat) {
		 console.log('user '+sentChat);
	        chat = angular.copy(sentChat);
	        chat.from = 'Me';
	        console.log('chat '+chat.from)
	        chat.direction = 'outgoing';
	        $scope.addChat(chat);
	    });

	    $scope.processIncomingMessage = function(message, isBroadcast) {
	        message = JSON.parse(message.body);
	        console.log(message)
	        message.direction = 'incoming';
	        if(message.from != $scope.userId) {
	        	console.log("Mesage from ="+message.from)
	        	message.from = $scope.userNames[message.from];
	        	$scope.addChat(message);
	            $scope.$apply(); // since inside subscribe closure
	        }
	    };
	 
	    $scope.addChat = function(chat) {
	        $scope.chats.push(chat);
	       // alert($scope.chats)
	    };
	    
	    $scope.updateUserList = function(){
	    	
	    	userService.getOnlineUserList()
	    	.then(function(response){
	    		console.log("online user list "+response.data);
	    		$scope.users = response.data;
	    		getUserNames(response.data)
	    	},function(response){
	    		console.log("inside failure");
				console.log(response.data);
				
	    	})
	    	
	    }
	 
	    
	    function getUserNames(userList){
	    	
			console.log("inside list of user Names")
			console.log(userList);
			
			userService.getUserNames(userList)
			.then(function(response){	
				
				console.log("inside success");
				console.log(response.data);
				$scope.userNames = response.data;	
			},function(response){
			
				console.log("inside failure");
				console.log(response.data);
			});
		}
	
}])