var app = angular.module('myModule',['ngRoute','ngCookies'])
					.config(function ($routeProvider,$locationProvider){
						$locationProvider.hashPrefix('');
						
						$routeProvider
						.when("/home",{
							templateUrl: "home/home.html",
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
						.when("/manageBlog",{
							templateUrl: "blogs/manageBlog.html",
							controller: "blogController"
						})
						.when("/viewForum",{
							templateUrl: "forums/viewForum.html",
							controller: "forumController"
						})
						.when("/createForum",{
							templateUrl: "forums/createNewForum.html",
							controller: "forumController"
						})
						.when("/viewBlogDetail/:id",{
							templateUrl: "blogs/viewBlogDetail.html",
							controller: "blogViewController"
						});
						 
					})
					.run(function run($rootScope, $location, $routeParams,$cookies, $http) {
						    // keep user logged in after page refresh
						 	$rootScope.isAdmin = false; 	
					       $rootScope.globals = $cookies.getObject('globals') || {};
						   $rootScope.currentuser =  $cookies.getObject('currentuser') || {};
						    console.log(" $rootScope.globals = "+ $rootScope.globals)
						  
						    console.log(" $rootScope.globals.currentUser = "+ $rootScope.globals.currentUser)
						    if ($rootScope.globals.currentUser) {
						    	
						        $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata;
						    }
						
						    $rootScope.$on('$locationChangeStart', function (event, next, current) {
						        // redirect to login page if not logged in and trying to access a restricted page
						    	console.log("$location.path() ="+$location.path());
						    	console.log($routeParams);
						    	
						    	var	blogid = $routeParams.id;
						    	
						        var restrictedPage = $.inArray($location.path(), ['/register','/viewBlogs','','/viewForum']) === -1;
						    	var adminPage = $.inArray($location.path(), ['/manageBlog']) === -1;
						        var loggedInUser = $rootScope.globals.currentUser;
						    
						        if(loggedInUser){
						        	   console.log(" $rootScope.islogged = "+ $rootScope.islogged)
						        	   $rootScope.islogged =true;
						        	   if($rootScope.globals.currentUser.role === 'ADMIN')
										   	$rootScope.isAdmin = true;
						        	   else
						        		   $rootScope.isAdmin = false;
						        }
						       
						        if(($location.path().includes('/viewBlogDetail/')) && !loggedInUser){
						        	 console.log("Not restricted : ")
							           
							    }else if (restrictedPage && !loggedInUser) {
						        	 console.log("Redirect to login : ")
						            $location.path('/login');
						        }
						    });
					});
