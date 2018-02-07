var app = angular.module('myModule',['ngRoute','ngCookies','ngIdle'])
					.config(function ($routeProvider,$locationProvider,IdleProvider, KeepaliveProvider){
						$locationProvider.hashPrefix('');
						
						$routeProvider
						.when("/home",{
							templateUrl: "home/home.html",
							controller: "homeController"
						})
						.when("/admin/home",{
							templateUrl: "home/adminHome.html",
							controller: "homeController"
						})
						.when("/login",{
							templateUrl: "login/login.html",
							controller: "LoginController"
						})
						.when("/register",{
							templateUrl: "register/userregister.html",
							controller: "userRegistrationController"
						})
						.when("/editprofile",{
							templateUrl: "register/editprofile.html",
							controller: "userRegistrationController"
						})
						.when("/viewBlogs",{
							templateUrl: "blogs/viewBlogs.html",
							controller: "blogController"
						})
						.when("/viewMyBlogs",{
							templateUrl: "blogs/viewUserBlogs.html",
							controller: "blogViewController"
						})
						.when("/createBlog",{
							templateUrl: "blogs/createNewBlog.html",
							controller: "blogController"
						})
						.when("/admin/manageBlog",{
							templateUrl: "blogs/manageBlog.html",
							controller: "blogController"
						})
						.when("/viewForum",{
							templateUrl: "forums/viewForum.html",
							controller: "forumController"								
						})
						.when("/admin/manageForum",{
							templateUrl: "forums/manageForum.html",
							controller: "forumController"
						})
						.when("/admin/viewBlogDetail/:id",{
							templateUrl: "blogs/blogApprovalForm.html",
							controller: "blogViewController"
						})
						.when("/createForum",{
							templateUrl: "forums/createNewForum.html",
							controller: "forumController"
						})
						.when("/viewBlogDetail/:id",{
							templateUrl: "blogs/viewBlogDetail.html",
							controller: "blogViewController"
						})
						.when("/uploadpicture",{
							templateUrl: "register/uploadpic.html"
							//controller: "ImageController"
						})
						.when("/admin/addjob",{
							templateUrl: "jobs/jobform.html",
							controller: "JobController"
						})
						.when("/viewjobs",{
							templateUrl: "jobs/joblist.html",
							controller: "JobController"
						})
						.when("/viewForumDetail/:id",{
							templateUrl: "forums/viewForumDetail.html",
							controller: "forumController"
						})
						.when("/admin/viewForumDetail/:id",{
							templateUrl: "forums/forumApprovalForm.html",
							controller: "forumController"
						})
						.when("/suggestedUsers",{
							templateUrl: "friends/suggestedusers.html",
							controller: "FriendController"
						})
						.when("/pendingrequests",{
							templateUrl: "friends/pendingrequest.html",
							controller: "FriendController"
						})
						.when("/friendList",{
							templateUrl: "friends/friendList.html",
							controller: "FriendController"
						})
						.when("/chat",{
							templateUrl: "chats/chat.html",
							controller: "ChatController"
						})
						.when("/termandconditions",{
							templateUrl: "utils/termandconditions.html"
						})
						.when("/privacypolicy",{
							templateUrl: "utils/privacypolicy.html"
						})
						.when("/contentpolicy",{
							templateUrl: "utils/contentpolicy.html"
						})
						.otherwise({templateUrl: "home/home.html",controller: "homeController"});
						
						IdleProvider.idle(900); // 15 min
						  IdleProvider.timeout(120);
						  KeepaliveProvider.interval(600); // heartbeat every 10 min
						//  KeepaliveProvider.http('/api/heartbeat'); // URL that makes sure session is alive
						 
					})
					.run(function run($rootScope, $location, $routeParams,$cookies, $http,AuthenticationService,Idle) {
						
						 Idle.watch();
						//  $rootScope.$on('IdleStart', function() { /* Display modal warning or sth */ });
						  $rootScope.$on('IdleTimeout', function() { /* Logout user */

							  console.log("logout func")
				              AuthenticationService.Logout(function (response) {	 
				                 if (response.success) {
				                	 
				                 	AuthenticationService.ClearCredentials();
				                     console.log("success"+response.data)
				                     $location.path('/login');
				                     
				                 } else {
				                	 
				                   //  FlashService.Error(response.message);
				                 //	     alert("error")
				                	
				                        console.log(response.data)
				                		 AuthenticationService.ClearCredentials();
				                         console.log("User is invalid"+response.data)
				                         $rootScope.islogged=false;
				                         $location.path('/login');
				                	
				                 }
				             });
						  });
						
						    // keep user logged in after page refresh
						 	$rootScope.isAdmin = false; 	
					       $rootScope.globals = $cookies.getObject('globals') || {};
						 //  $rootScope.currentuser =  $cookies.getObject('currentuser') || {};
						    console.log(" $rootScope.globals = "+ $rootScope.globals)
						  //alert( $rootScope.globals)
						    console.log(" $rootScope.globals.currentUser = "+ $rootScope.globals.currentUser)
						    if ($rootScope.globals.currentUser) {
						    	
						        $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata;
						    }
						
						   // alert( $rootScope.globals.currentUser)
						    $rootScope.$on('$locationChangeStart', function (event, next, current) {
						        // redirect to login page if not logged in and trying to access a restricted page
						    	console.log("$location.path() ="+$location.path());
						    	console.log($routeParams);
						    	
						    	//var	blogid = $routeParams.id;
						    	
						        var restrictedPage = $.inArray($location.path(), ['/register','/viewBlogs','','/viewForum','/home','/termandconditions','/privacypolicy','/contentpolicy']) === -1;
						    	var adminPage = $.inArray($location.path(), ['/admin/manageBlog','/admin/addjob','/admin/home']) !== -1;
						        var loggedInUser =  $rootScope.globals.currentUser;
							    console.log("loggedInUser : ")
							    console.log(loggedInUser)
							        if(loggedInUser){
							        	
						        	   console.log(" $rootScope.islogged = "+ $rootScope.islogged)
						        	   $rootScope.islogged = true;
						        	   
						        	   if($rootScope.globals.currentUser.role === 'ADMIN')
										   	$rootScope.isAdmin = true;
						        	   else
						        		   $rootScope.isAdmin = false;
						        }
							    
						        if(($location.path().includes('/viewBlogDetail/')||$location.path().includes('/viewForumDetail/')||$location.path().includes('/admin/viewBlogDetail/')||$location.path().includes('/admin/viewForumDetail/')) && !loggedInUser){
						        	 console.log("Not restricted : ")
							           
							    }else if (restrictedPage && !loggedInUser) {
						        	console.log("Redirect to login : ")
						            $location.path('/login');
						        }else if(adminPage && !$rootScope.isAdmin){
						        	alert("not authorized to view this page")
						        	$location.path('/home');
						        }
						        if($rootScope.userNames === undefined){
							        AuthenticationService.getAllApplicationUserNames(function(response){
						    			 if(response.success){
						    				 $rootScope.userNames = response.data;	
						    				 console.log($rootScope.userNames);
						    				 console.log("$rootScope.userNames");
							    		}else{
							    			console.log(response.data)
							    			
							    		}
						    		})
						        }
						 if(loggedInUser){
						       						        
						        if($rootScope.notificationNotViewed === undefined){
						        	 AuthenticationService.getNotificationNotViewed(function(response){
						        		 if(response.success){
						     			console.log(response.data)
						     			$rootScope.notificationNotViewed = response.data;
						     			$rootScope.notificationNotViewedLength = response.data.length;
						        		 }else{
							     			console.log(response.data)
							     			if(response.status==401){
							     				$location.path("/login");
							     			}
						        		 }
						     		});
						     		
						        }
						        if($rootScope.notificationViewed === undefined){
						     		AuthenticationService.getNotificationViewed(function(response){
						     			 if(response.success){
						     			console.log(response.data)
						     			$rootScope.notificationViewed = response.data;
						     		}else{
						     			console.log(response.data)
						     			if(response.status==401){
						     				$location.path("/login");
						     			}
						     		}
						     		});
						        }
						 }
					});
						    
					});
