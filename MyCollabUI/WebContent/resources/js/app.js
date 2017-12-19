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
						})
						.when("/uploadpicture",{
							templateUrl: "register/uploadpic.html",
							controller: "ImageController"
						})
						.when("/addjob",{
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
						.when("/suggestedUsers",{
							templateUrl: "friends/suggestedusers.html",
							controller: "FriendController"
						});
						 
					})
					.run(function run($rootScope, $location, $routeParams,$cookies, $http) {
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
						    	
						        var restrictedPage = $.inArray($location.path(), ['/register','/viewBlogs','','/viewForum']) === -1;
						    	var adminPage = $.inArray($location.path(), ['/manageBlog']) === -1;
						        var loggedInUser =  $rootScope.globals.currentUser;
						    console.log("loggedInUser : ")
						    console.log(loggedInUser)
						   // alert(loggedInUser)
						        if(loggedInUser){
						        	//alert("Logged In user")
						        	   console.log(" $rootScope.islogged = "+ $rootScope.islogged)
						        	   $rootScope.islogged =true;
						        	   if($rootScope.globals.currentUser.role === 'ADMIN')
										   	$rootScope.isAdmin = true;
						        	   else
						        		   $rootScope.isAdmin = false;
						        }
						       
						        if(($location.path().includes('/viewBlogDetail/')||$location.path().includes('/viewForumDetail/')) && !loggedInUser){
						        	 console.log("Not restricted : ")
							           
							    }else if (restrictedPage && !loggedInUser) {
						        	 console.log("Redirect to login : ")
						            $location.path('/login');
						        }
						    });
					});
